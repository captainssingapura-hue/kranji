# Known Characters and Review

Everything adaptive depends on one judgement: does this reader know this
character? Get it wrong and the app either patronises or abandons.

## Three states

```
UNSEEN  ──encountered──▶  LEARNING  ──secured──▶  KNOWN
                              ▲                      │
                              └────── faltered ──────┘
```

| State | Meaning | Annotated in `ADAPTIVE` |
|---|---|---|
| `UNSEEN` | never met in any article read | yes |
| `LEARNING` | met, not yet secure | yes |
| `KNOWN` | reads it without support | no |

Three, not five. A child's parent should be able to hold the whole model in
their head, and every additional state needs a rule for entering and leaving it.

Demotion matters as much as promotion. A character learned in March and not seen
since is not permanently known, and an app that only promotes will slowly stop
annotating things the reader has quietly lost.

## How a character reaches KNOWN

**Explicitly, and never by algorithm.** An earlier draft weighed three
inference mechanisms; the resolved answer is that none of them is used.

Inferring knowledge from silence is guessing. "Did not ask for the reading" is
not "knew the character" — the child may have skipped the word, guessed from
context, or simply kept going. An app that promotes on that evidence withdraws
support from characters the reader never secured, which is the exact failure the
adaptive mechanic exists to prevent.

So marking is a deliberate act, made cheap from wherever the reader already is:
from the reader mid-article, from the known-set manager, from a practice
session, or by bulk import. Every mark records which channel it came from, and
every mark can be removed.

The full design is in *Known Zi Management*; this document covers the state
model those channels write to.

## Practice

Characters in `LEARNING` are available to practise. A session is short and shaped like
reading, not like a test:

1. Show the character in a **sentence from an article already read**, not in
   isolation. Context is how it will be met again.
2. Ask for the reading.
3. On hesitation, offer the **composition hint** before the answer — 清 is 氵
   plus 青 — so the reader has a route to reconstruct it rather than simply
   being told.
4. Record the outcome. Nothing is scheduled - see below.

Step 3 is the point. A conventional flashcard has one move when the reader is
stuck: reveal. Kranji has a move in between, and it is the move that generalises
— a reader who recovers 清 from 氵 and 青 has learned something reusable about
how characters are built.

## No scheduling

There is no spaced-repetition algorithm and no due dates. An earlier draft
proposed a gentler interval progression than SM-2; the resolved answer is that
the app does not schedule at all.

Spaced repetition optimises retention per minute for a motivated adult clearing
a deck. For a child the failure mode to avoid is discouragement, and a scheduler
that decides what *must* be reviewed today produces exactly that.

What the app does instead: surface what is in progress, make practice easy to
start, and offer sort orders the reader chooses — least recently seen, most
recently added, longest in progress. Ordering hints are fine. An authority on
what happens next is not.

**Practice never blocks reading.** A child who wants to open a story should
never have to clear a queue first.

## The profile stays on the device

State lives in **IndexedDB** in the browser, with explicit export to a file the
family owns. IndexedDB rather than `localStorage` because a known set plus
per-article progress will outgrow the localStorage budget, and IndexedDB is
already the framework's substrate for checkpoint storage.

**The server ships the application and nothing else.** It holds no profile, so
there is nothing to breach, subpoena, or quietly analyse. A record of which
characters a specific child finds difficult is exactly the kind of data that
should not accumulate on someone else's computer.

This follows the framework's *State Belongs to the User* doctrine, which refuses
server-sync of user state as a default and renders a warning banner if a remote
store is ever plugged in.

Consequences we accept:

- **No cross-device sync** beyond export and import — a file moved through
  storage the family already trusts.
- **Clearing browser data loses progress.** Mitigated by making export easy to
  reach and prompting at sensible moments. The alternative is an account
  system, and the trade is not close.
- **Several children may share a device.** Profiles are switchable through an
  unprotected local selector, which suits the audience but requires that the
  current profile is obvious at all times and that a bulk operation cannot
  silently land on the wrong child's record.

## Making progress visible without making it a score

Some visibility is genuinely motivating — a child seeing annotations disappear
from a story they struggled with is seeing their own progress in the material
itself. That is the honest version, and it comes free from the adaptive
mechanic.

What we avoid: streaks, points, badges, and daily targets. They optimise for
returning to the app rather than for reading, and the failure mode — a child who
feels they have failed by missing a day — is worse than the benefit.
