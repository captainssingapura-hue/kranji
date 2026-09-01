# Module Structure

`kranji-reading` is a nested multi-module tree, not a single module.

## The tree

```
kranji-reading/                  parent pom, packaging=pom
├── kranji-reading-model/        domain types + corpus bridge
├── kranji-reading-content/      article source SPI + bundled catalogue
├── kranji-reading-app/          Homing workspaces, widgets, server   [crate]
└── kranji-reading-prep/         preparation + validation CLI
```

## What each module holds

**`-model`** — the sealed token model, `Article` / `Passage` / `Sentence`,
`LearnerProfile` / `KnownEntry` / `CharacterState` / `Channel`, the corpus
bridge that resolves a glyph to a `Zi` and tolerates absence, and readability as
a pure function of `(article, profile)`.

Depends on `kranji-core` only. No Homing, no browser, no server. Everything
interesting about the app is testable here with plain JUnit.

**`-content`** — the `ArticleSource` SPI and the bundled classpath
implementation, plus the prepared article resources. Depends on `-model`.

**`-app`** — everything Homing: workspace specs, widgets, GetActions, studio,
server, and the crate. Depends on `-model`, `-content`, and the framework.

**`-prep`** — the preparation pipeline and the polyphony validator, as a CLI
run at build time. Depends on `-model`. Separate from `-content` because it is
a build-time tool, not something the running app should carry.

## Dependency rules

```
kranji-core
    ▲
    │
-model ◀── -content ◀── -app ──▶ homing-*
    ▲
    └───── -prep
```

Three rules, each load-bearing:

1. **`-model` and `-content` never depend on Homing.** The domain has to be
   testable without a browser, and it should survive the UI being replaced.
2. **Nothing in Kranji proper depends on `kranji-reading`.** The corpus does not
   know the reading app exists. The arrow points one way.
3. **`-app` is the only module that ships served modules,** so it is the only
   one that needs a crate.

## The crate boundary

One `Crate` per Maven module is a framework constraint, not a style preference —
`OrphanCheck` scans a crate class's entire module output for served `EsModule`s
and fails on any the crate omits. Two partial crates in one module each flag the
other's modules as orphans.

With one UI module there is one crate. If the UI later splits — say a
`-app-known` alongside a `-app-reader` — each gets its own complete crate, and
`CrateCoverage.check` takes one anchor class per module.

## Why not a single module

The reading model is the substantial part of this app: adaptive display rules,
readability, known-set state transitions, corpus fallback. All of it is pure
logic and all of it deserves fast unit tests.

Bundled with the UI, that logic ends up transitively depending on the framework,
and its tests end up needing a rendering context. The split keeps the part most
likely to be wrong also the part easiest to test.

The secondary reason is optionality: a different front end — a CLI reader, a
different framework, an export to static HTML — needs `-model` and `-content`
and nothing else.
