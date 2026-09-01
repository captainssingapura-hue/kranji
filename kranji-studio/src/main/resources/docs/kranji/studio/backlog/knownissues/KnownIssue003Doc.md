# KI-003 — A badge counts appearances but says characters

**Severity:** low · **Area:** reading app, display · **Status:** half fixed

## What it is

`ZiTreeAdapter.detailOf` renders a node's `characterCount()` as
`"N characters"`. But `characterCount()` sums content across terminals, and
since sound and shape were decoupled a polyphonic character appears under
**every reading it has**. So the number is an appearance count wearing a
character count's label.

## Evidence

The root of the phonic tree currently reads **"83 characters"** for a corpus of
**79** characters. The difference is exactly the 4 extra appearances of the
three polyphonic characters in the seed set — 好, 和, 还.

At full coverage the same label would read 8,759 for 8,100 characters.

```java
// ZiTreeAdapter
int n = source.characterCount();
return n + (n == 1 ? " character" : " characters");
```

## What it costs to leave

A number that is quietly wrong. Nobody is misled badly at 83-versus-79, but the
gap widens with the corpus, and a count nobody trusts is worse than no count.

## What was done

The label now says **readings** rather than characters, which is the honest
reading of the number and arguably the more useful one for a tree organised by
sound. The count itself is unchanged.

Still open: nothing reports the distinct-character count anywhere, so "how many
characters are under h-" has no answer in the UI.

## What would fix it

Decide what the badge is *for* and say that.

- If it counts **readings filed here**, the number is right and the label is
  wrong — say "readings" or "appearances".
- If it counts **distinct characters**, the label is right and the number needs
  to deduplicate.

For a tree organised by sound, "readings" is arguably the more useful figure —
it says how much is filed under this branch. Either way the two should agree.
