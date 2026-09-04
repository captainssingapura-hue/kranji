# UD-002 — No sanctioned way to offer a generated file

A widget that produces a file for the user to keep — an export, a report, a
backup — has no conformant way to hand it over.

## What upstream does

Two rules meet and leave no gap between them.

`no-raw-href` rejects every way of writing a URL onto an element:

```
\.href\b            setAttribute\s*\(\s*["']href
\bhref\s*=          \bwindow\.location\b        \bwindow\.open\s*\(
```

The message names the sanctioned alternative — *use `href.*`* — and
`HrefManager` provides exactly the right method:

```js
href.set(el, url)
```

But `href` is injected **only into modules that import an `AppLink<?>`**. The
three link proxies that exist are `Mailto`, `Sms` and `Tel`; every other
`AppLink` is a particular app's `link` record. None of them means *save a file
this page just made*.

So a widget can satisfy the scanner and still fail at runtime. `href.set(...)`
passes the regexes and throws `ReferenceError: href is not defined` in the
browser — which is how this was found, since conformance was green.

## What we do instead

Export copies the record to the clipboard and shows it in a read-only textarea,
selected, so it can also be copied by hand where the clipboard API is refused.
The parent pastes it into a text file.

It works, and the fallback path is honest about what happened rather than
reporting a save that did not occur. But it is a step longer than it should be,
and "paste this somewhere safe" is a poor instruction for the one copy of a
record that survives clearing the browser.

**We did not take the other route available**, which was to import an unrelated
app's `link` purely to obtain the manager. That would put a dependency in the
widget that lies about why it is there, and the next person to read it would
have no way to discover the real reason.

## What would let us stop

Any of:

- A link proxy for object URLs — something like `Blob`/`Download` alongside
  `Mailto`, so a module that wants to hand over a file imports the thing that
  says so.
- `href.download(name, blob)` on the manager, with the manager injected for it.
- The manager injected into any `DomModule`, since the rule already prevents
  the raw operations whether or not it is present.

The first is the most in keeping: the injection is currently a statement about
what a module does with URLs, and "offers a file" is a real category of that.
