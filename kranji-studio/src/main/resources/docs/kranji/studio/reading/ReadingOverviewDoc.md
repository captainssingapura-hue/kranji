# Reading — Overview

An adaptive Chinese reading app for children, built on the Kranji corpus.

## The problem with graded readers

Reading material for children learning Chinese usually takes one of two
positions on 汉语拼音, and both are wrong for most of the page.

**Pinyin above everything.** Safe, and the reason most primary material looks
this way. But the child reads the pinyin instead of the character — the
annotation is easier than the thing it annotates, so the eye takes the shortcut.
Characters the child already knows get undermined every time they appear.

**No pinyin at all.** Honest, but a single unknown character stops the reader
dead. The child either guesses, asks an adult, or gives up on the sentence.

The real need is per-character and changes weekly: *show me pinyin for the
characters I don't know yet, and stay out of the way for the ones I do.*

## What this app does

Two things that need a structural corpus.

**Adaptive annotation.** The app tracks which characters this reader knows. A
known character is shown bare; an unknown one carries its reading. As the
reader's known set grows, annotations recede on their own. The same article
looks different in March than it did in January, and the change is a record of
progress.

**A character can explain itself.** When a reader stops at an unfamiliar
character, most apps offer a dictionary gloss — a second thing to memorise.
Kranji can offer its structure instead:

```
清  qīng   clear
├─ 氵  water        ← what it means
└─ 青  qīng         ← how it sounds
```

清 is water plus 青. That is not a mnemonic someone invented for a flashcard;
it is why the character is shaped the way it is. A reader who has met 青 already
has most of 清. This is the payoff for the whole typed corpus — 2,532 characters
that know their own composition, etymology, and components.

## Who it is for

Children reading Chinese at roughly primary level, and the adult sitting beside
them. Design consequences:

- **No accounts, no sign-in.** A child should not need credentials to read a
  story.
- **Nothing leaves the device.** A child's reading record is not telemetry. See
  the privacy section below.
- **The adult stays in control.** Progress is a file the parent can export,
  inspect, back up, or delete.
- **Failure is quiet.** Not knowing a character is the normal state, not an
  error condition. No streaks to break, no red marks.

## Privacy is a design constraint, not a feature

The Homing framework has no authentication, no session identity, and a doctrine
— *State Belongs to the User* — under which it refuses to ship server-sync of
user state, account-keyed storage, or sync UIs. If a downstream plugs a remote
store through the `WorkspaceStateStore` SPI, the shell renders a persistent
banner telling the user their state is leaving the device.

For a children's app this is the correct default handed to us for free:

- The learner profile lives in IndexedDB on the child's own device.
- Backup and transfer happen by explicit file export, through storage the family
  already trusts.
- There is no server that could accumulate a record of what a child struggles
  with, because there is no server-side profile at all.

We adopt this rather than work around it.

## Scope

**In scope:** article catalogue, reader with adaptive pinyin, known-character
tracking, review of characters in progress, composition hints drawn from the
corpus.

**Out of scope for v1:** audio and pronunciation, handwriting practice, stroke
order animation, quizzes and scoring, multi-device sync, classroom or teacher
features, article authoring inside the app.

## Where this fits

Kranji already models 2,532 characters as typed composition trees with pinyin,
stroke counts, radicals, and etymology. Until now that corpus has been explored
by developer-facing tools. Reading is the first thing built on it that a child
would use — and the first real test of whether structural decomposition helps
someone actually learning to read.
