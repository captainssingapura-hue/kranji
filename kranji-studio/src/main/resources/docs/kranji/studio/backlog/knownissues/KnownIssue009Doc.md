# KI-009 — Widget JavaScript is unchecked until it runs

**Severity:** medium · **Area:** reading app, tooling · **Status:** open

## What it is

A widget's behaviour is authored as `List<String>` in a Java file:

```java
protected List<String> constructBodyJs() {
    return List.of(
        "    var root = branch.createElement('root', 'div');",
        "    css.setClass(root, kr_widget_root);",
        ...
```

To `javac` that is a list of strings — always valid. Conformance parses it well
enough to catch **rule** violations (`document.createElement`, inline styles,
inline CJK, module length), but not to catch a **syntax** error or an undefined
reference. So a broken edit compiles, passes the build, is served, and fails in
the browser.

## Evidence

Both of these happened in one afternoon, on the same file:

**A dropped comment marker.** A Java string concatenation was written as

```java
"                // Prose wraps; a verse does not. A poem's lines are the",
"                "  + "author's, and re-breaking them would destroy the form.",
```

which emits the second line *without* the `//`. The result is a bare sentence
in the middle of a JavaScript function:

```
    // Prose wraps; a verse does not. A poem's lines are the
    author's, and re-breaking them would destroy the form.
```

Browser: `SyntaxError: Invalid or unexpected token`. Build: green.

**A bulk replacement that took too much.** Replacing a block of control-building
lines also removed `var loaded = null;` and `var __mod = null;`, which sat
inside the replaced range. Browser: `ReferenceError: loaded is not defined`.
Build: green.

## What it costs

Every edit to a widget's behaviour needs a browser round-trip to know whether it
is even syntactically valid. That is slow, and worse, it is easy to *skip* —
which is how a change ships broken. Two other bugs this session had the same
shape: a branch created but never activated, and a render path that never
assigned its classes. None were catchable without loading the page.

## What would fix it

Cheapest first:

1. **Syntax-check the emitted module in a test.** Each widget can render its own
   body and hand it to a parser. There is no JS engine on the classpath today,
   but a `SyntaxError` check is a much smaller ask than a full engine — even
   balanced-delimiter and `//`-prefix checks would have caught both cases above.
2. **A conformance rule for the comment shape**, since the dropped-`//` failure
   is mechanical and recognisable.
3. **Author the JS as a resource file** rather than a Java string, as the
   Secretary and the `ui` modules already do. Those files are real `.js` and an
   editor will flag a syntax error while typing. This is the real fix; it is
   also the largest change, because the CSS class handles and imports are bound
   by the widget's Java side.

## Note

Option 3 is already the pattern for `TypefacePickerModule`, `GlyphMetricsModule`,
`TextBandsModule` and `ReaderControlsModule` — all authored as `.js` resources.
Every one of them was extracted from a widget under conformance pressure, and
every one is easier to edit than the widget it came from. That is a hint about
which way this should go.
