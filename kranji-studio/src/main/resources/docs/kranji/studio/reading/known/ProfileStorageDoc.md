# Profiles and Storage

The set lives in **IndexedDB in the browser**. The server ships the application
and holds nothing.

IndexedDB rather than `localStorage` because a known set of a few thousand
characters plus whatever comes later will outgrow the localStorage budget, and
IndexedDB is already the framework's substrate for checkpoint storage.

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

Export writes the set as a plain list the family owns and can read. Import takes
one back, as a batch, so it can be undone as a unit like any other bulk
operation.

Plain text rather than an opaque format: a parent should be able to open the
file and see a list of characters. It is their record.

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
