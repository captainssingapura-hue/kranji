# EN-001 — Row width per collection, overridable per article

**Area:** reading app, article library · **Size:** M · **Status:** open

## What we have

How many squares a line holds is decided entirely by the pane:

```js
banding.cellsPerRow(body.clientWidth, controls.size().w)   // → one of 6 8 10 12 16 20 24
```

The content has no say. That is right for prose and accidental for everything
else.

Verse escapes it today only by luck: a verse's rows are the lines its author
wrote, and the board is as wide as its widest row, so 五言绝句 gets five columns
because it happens to contain five characters — not because anything asked for
five.

## Why the form should get a say

A line length is a property of the writing, not of the window.

- **五言 wants five, 七言 wants seven.** These are the form. A 五言绝句 laid out
  eight to a row is no longer the poem — the reader loses the couplet.
- **儿歌 lines are short and irregular**, and look wrong stretched to the
  widest band the pane allows.
- **Prose genuinely wants what fits**, which is the current behaviour and
  should stay the default.
- **说明文 for a young reader** may want deliberately fewer columns than fit, so
  a line is a manageable unit rather than a wall.

Under the library design the collection is the unit of curation, so the
collection is where the form's preference belongs — every article in 唐诗启蒙
shares a shape. A particular article still needs to overrule it: an anthology of
五言 with one 七言 in it should not have to be split into two collections to lay
that one poem out correctly.

## Shape

Two optional declarations, neither of which any existing collection has to
supply:

```java
interface ArticleCollection {
    default OptionalInt preferredColumns() { return OptionalInt.empty(); }
}

record ArticleRef(..., OptionalInt columns) { }   // overrules the collection
```

Resolution order: **article, then collection, then the band**. Absent both, the
behaviour is exactly what it is today.

## The question that needs deciding first

**Is a declared width a fixed count or a cap?** They are different features and
the doc should not pretend otherwise:

- **Fixed** is what verse wants — five means five, and if the pane is narrow the
  board scrolls rather than re-breaking the poem. We have already accepted that
  a board may be wider than its pane.
- **A cap** is what a graded 说明文 wants — no more than ten, but fewer if the
  pane is small.

Best guess is that both are wanted and the declaration needs to say which, but
that is a guess, and picking one silently would be the wrong kind of decision to
bury in an implementation.

## Interaction to watch

The size control changes the square's width, so a fixed column count at **Large**
may overflow a pane that was comfortable at Medium. That is acceptable — the
board is already not strictly bounded by the pane — but it means the
combination should be tried at all three sizes before this is called done, not
only at the default.

## Not this

Not a per-reader preference. This is the content declaring its own shape, not a
setting a child adjusts; the controls that belong to the reader are already
there and are deliberately few.
