# Annotation from the Known Set

```
in the set      →  no pinyin
not in the set  →  pinyin
```

That is the entire rule. There is no threshold, no confidence, no decay — the
membership test is the annotation decision.

## Three modes

The reader keeps its two manual modes and gains this one:

| Mode | Shows pinyin for |
|---|---|
| `all` | every character |
| `none` | nothing |
| `known` | characters not in the set |

`all` and `none` stay because they are useful for reasons unrelated to the
record: reading aloud with a parent, or checking whether the child can manage
without support at all. They are overrides, not fallbacks.

**Which is the default is an open question.** `known` is the point of the
feature, but a fresh profile has an empty set, so `known` and `all` look
identical on day one and diverge silently as marks accumulate. Starting on `all`
and offering `known` once the set is non-empty may read better than a mode that
appears to do nothing.

## Showing and hiding never moves anything

Already true and it must stay true: an annotation is hidden with `visibility`,
never `display`, so the row keeps its height. Switching modes — or marking a
character known while an article is open — cannot reflow the page.

This matters more with the known set than it did with the manual modes. Marking
a character changes the annotation of *every occurrence of that character in the
article*, potentially several lines apart. If that reflowed, the child's place
on the page would move as a reward for knowing something.

## Cost

The reader already fills readings from `/syllable-map` before drawing. The
membership test is a set lookup per cell against local state — no request, no
async, and it can be applied to an already-rendered article by restyling cells
rather than rebuilding them, the same way the mode switch works today.

## What this makes possible

Once the set exists, an article's **readability** — known ÷ total, and the count
of distinct unknown characters — is a lookup rather than a new mechanism. That
is what makes the library navigable by difficulty, and it is specified in
*Article Catalogue and Readability*. It is a consumer of this, not part of it.
