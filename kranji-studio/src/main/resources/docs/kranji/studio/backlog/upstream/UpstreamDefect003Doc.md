# UD-003 — A pane's first click never reaches the widget

Clicking a row in a workspace pane that does not already have focus selects
nothing. The row has to be clicked twice: once to wake the pane, once to
select. Every click after that behaves normally, until focus moves to another
pane and the toll is charged again.

## What it looks like

A grid that drives other grids looks broken. The gloss workbench cascades a
selection from Zi to Sound to Sense to Citation, and the first click on a
character does nothing at all — no cursor move, no downstream refresh. There is
no error, in the console or anywhere else. The natural reading is that the tool
is not wired up.

## What actually happens

The first `mousedown` in an unfocused pane lands on a shell element, not on the
widget's content:

```
click 1   target = DIV.hmtp-content        (the pane's own layer)
click 2   target = TD.hgr-td               (the grid cell)
```

`document.activeElement` before the first click is `BODY`; after it, the pane's
`DIV`; after the second, `TABLE[aria-label="Gloss zi"]`. So the event is
consumed above the widget, and the widget's DOM never sees it.

That last point is what makes this worth writing down rather than working
around. **A widget cannot fix this from the inside.** Three attempts, all
reasonable, all ineffective:

| Attempt | Why it cannot work |
|---|---|
| `setActive(true) → grid.focus()` | Not called for the pane that is already active at page load |
| Deferring that focus until the grid exists | Same — the callback never arrives to be deferred |
| `mousedown` listener on the grid host, capturing | The event never reaches the host, so the listener never runs |

The third is the decisive one: a capturing listener on our own element is as
early as a widget can be, and it still does not see the click.

## What is confirmed working

Focus is genuinely the discriminator, not something else about the click.
Focusing the grid by hand and then issuing a single click selects and cascades
correctly:

```js
document.querySelector('[aria-label="Gloss zi"]').focus();
// one click on a row -> cursor moves, downstream grids narrow
```

So the grid's own selection handling is sound; only the delivery of the first
event is not.

## What we would want

Either the pane's activation layer should let the pointer event through to the
content beneath it, or activation should be driven from `pointerdown` with the
event re-dispatched once the pane is live. A widget-side hook would also do —
something the shell calls *before* it consumes the event — but the simplest fix
is for activation not to swallow.

## Until then

Nothing in Kranji works around it. A workaround would have to reach outside the
widget's own DOM and depend on shell class names (`hmtp-content`), which trades
a small annoyance for a brittle coupling that would break silently on an
upstream change.

The cost is one click per pane per focus change, in an internal tool. That is
worth recording and not worth hacking.
