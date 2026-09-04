# UD-001 — RelationGrid cell borders cannot be turned off

**Component:** `homing-relation-grid` · **Severity:** medium · **Status:** open
· **Workaround in place:** yes, and it uses `!important`

## What the grid does

`GridLayoutModule.js` puts a hairline on the bottom and right of every cell:

```js
".hgr-td{padding:0;border-bottom:1px solid var(--color-border);",
"  border-right:1px solid color-mix(in srgb, var(--color-border) 50%, transparent);",
"  vertical-align:middle;}",
```

The class name is fixed at the same time — `td.className = "hgr-td"` — and the
whole sheet is injected as a plain element:

```js
document.head.appendChild(s);
```

That last line is the part that makes this hard rather than merely annoying: the
stylesheet is **unlayered**.

## Why it is a defect and not a preference

RFC 0050's own claim is that the grid is a general **cell-widget substrate**,
not merely a data table — Minesweeper is in the repo to prove it. A board of
tiles wants those lines. A page of 方块字 where the ruling is a control the
reader turns on and off does not, and there is no way to decline them.

The precedent is already in the codebase. The header band got an option rather
than leaving consumers to hide it, and the demo says so in as many words:

```java
header: { show: false },   // no header band — the option, not a CSS trick
```

Cell rules are the same kind of chrome and have no such option, so today they
*are* a CSS trick downstream.

## Why a consumer cannot simply override it

Consumer CSS from a `CssGroup` renders into `@layer component`. In the cascade,
an **unlayered normal declaration outranks every layer**, regardless of
specificity. So this loses:

```css
.kr-gr-quiet .hgr-td { border-color: transparent; }   /* @layer component */
```

even though it is strictly more specific than the `.hgr-td` it is trying to
beat. Verified in the browser: the rule is present, applied to a matching
element, and the computed `border-bottom-color` is still the grid's.

Raising importance is the only lever a layer has against unlayered CSS, so the
workaround is:

```css
.kr-gr-quiet .hgr-td { border-color: transparent !important; }
```

`border-color` rather than `border` so the hairline keeps its 0.667px of space
and the squares do not shift when the ruling is toggled.

## What would let us drop the workaround

In the order we would prefer them:

1. **Emit the grid's stylesheet into a layer.** One line, and it fixes the whole
   class of problem rather than this one border — every consumer override starts
   working by ordinary specificity. This is the change we would actually like.
2. **An option, beside the header one.** `rules: false`, or `cellBorders: false`
   — consistent with `header: { show: false }` and with the comment that already
   explains why that option exists.
3. **A `cellClass` hook**, so a consumer can put its own class on the `td` and
   style from there. Weaker: it still leaves the consumer overriding rather than
   declining.

## Where the workaround lives

`ReadingCss.kr_gr_quiet`, applied to the reader's board host. The class carries
a comment pointing here. **Remove both when this is fixed** — the `!important`
is the whole reason this entry exists.
