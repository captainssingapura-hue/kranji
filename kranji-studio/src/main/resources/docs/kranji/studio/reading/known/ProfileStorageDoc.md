# Profiles and Storage

The set lives in **IndexedDB in the browser**. The server ships the application
and holds nothing.

IndexedDB rather than `localStorage` because a known set of a few thousand
readings plus whatever comes later will outgrow the localStorage budget, and
IndexedDB is already the framework's substrate for checkpoint storage.

## The schema

```
database     : "kranji.known"
object store : "sets"        key: profile
               row: { profile, known: ["34892:xíng", ...] }
```

**One row per profile, holding the whole set.** The party already broadcasts the
set whole rather than as a delta, so storing it whole keeps the two in step:
what is written is exactly what was published. It also makes a save atomic and
idempotent, which is what lets any pane perform one without coordinating with
the others.

The `profile` key is there from the start although there is only ever `default`
today. Several children per device is a requirement of this document, and a key
added later is a migration whereas a key with one value is a word.

**No timestamps**, per *The Known Set*. Not an omission — storing a time nothing
reads invites the feature that reads it.

## Loading is a union, and writing waits for it

Every pane seeds the party from the device at mount, with a message the
secretary applies as a **union**. Nothing has to elect a loader: a second seed of
the same rows changes nothing and says nothing, and a slow load cannot take back
a reading claimed while it was in flight.

**Nothing is written until the device has answered.** The party replies to a
new member immediately, with whatever it holds — which at boot is an empty set,
while the disk is still being read. Saving that reply would erase the record on
every visit, silently, in a way only the *next* visit could reveal. A change
arriving before the load completes is held, not dropped.

Only Mark Known writes; the review is read-only and cannot change the set.

**A failure to save is reported, not swallowed.** Marking looks identical
whether or not it reached the disk, so the tally says `NOT SAVED on this device`
when it did not. Losing a term of marks quietly is the worst outcome available
here.

**Still to build:** profiles beyond `default`, and the profile selector.

## Nothing leaves the device

A record of which characters a specific child finds difficult is exactly the
kind of data that should not accumulate on someone else's computer. There is no
account, no sync, and nothing transmitted — so there is nothing to breach,
subpoena, or quietly analyse.

This follows the framework's *State Belongs to the User* doctrine, which refuses
server-sync of user state by default.

**Consequences we accept:**

- **No cross-device sync** beyond export and import — a file moved through
  storage the family already trusts.
- **Clearing browser data loses the record.** Mitigated by making export easy to
  reach and offering it at sensible moments. The alternative is an account
  system, and the trade is not close.

## Export and import

Export writes the set as a plain list the family owns and can read — a character
and its reading per line, since that is what a mark is. Import takes one back,
as a batch, so it can be undone as a unit like any other bulk operation.

Plain text rather than an opaque format: a parent should be able to open the
file and see which readings are claimed. It is their record.

```
# Kranji reading record
# 2 characters, 3 readings
床	chuáng
地	de
地	dì
```

The **glyph**, not the codepoint, because the file is for a person; the
codepoint is recovered on the way in, so the round trip is exact. **Sorted** by
codepoint then reading, so two exports of nearly the same record differ only
where the record differs — a diff shows what was learnt, not what order it was
clicked in.

### Reading back is reported on, never half-applied

Comments and blank lines are skipped, spaces are accepted where a tab was meant
— a parent who retyped a line should not have their record refused over an
invisible character — and a repeated line is not an error.

Everything else comes back **with its line number and the reason**: a word where
a character belongs, a character with no reading. One mistyped line in an
eight-hundred-line record must not cost the other 799, and a report that gave
only its successes would leave a parent believing a record had been restored
whole.

The import says all three numbers — what the file held, what was new, and what
could not be read — because "imported 12" against a file of 800 is the
difference between a restore that worked and one that did not.

### Export copies rather than downloads

A download needs an anchor's `href`, and the framework's href manager — the only
sanctioned way to write one — is injected solely into modules that import an
`AppLink`. There is no `AppLink` that means *save a file the page just made*.
Raised as **UD-002**; meanwhile Export copies the record to the clipboard and
shows it, selected, so it can be copied by hand where the clipboard is refused.
The parent pastes it into a file.

### Where the controls live

Export is in the review, because it is a read and the whole set is that pane's
subject. Import is in Mark Known, because it writes — which keeps every change
to the record in one pane.

## Several children, one device

Profiles are switchable through an unprotected local selector. No passwords —
the audience is a family, and a lock on a child's reading record protects
nothing worth protecting.

Two requirements follow, and they are the whole reason this section exists:

- **The current profile is visible at all times.** Not on a settings pane — in
  the chrome, wherever marking or reviewing happens.
- **A bulk operation cannot land on the wrong child unseen.** Import, undo and
  bulk-remove all name the profile they are about to change, at the point of
  changing it.

The second is the one that bites. A misdirected import is recoverable because it
is a batch; a misdirected bulk removal is not, because what it destroyed was a
set of individual claims with no record that they were ever made.
