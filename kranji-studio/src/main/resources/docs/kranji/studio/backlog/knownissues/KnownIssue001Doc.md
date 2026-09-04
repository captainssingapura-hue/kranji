# KI-001 — Unihan provenance and build reproducibility

**Severity:** medium · **Area:** corpus, build · **Status:** open, blocking nothing yet

## What it is

Two problems that arrived together.

`input/_2500/Unihan_Readings.txt` is **untracked**. It is the source for every
phonic partition, so a fresh clone cannot regenerate them. The build still
works — the generated `.tsv` files are committed — but nobody else can
reproduce or audit the generation.

Meanwhile those generated files *are* committed, under
`kranji-core/src/main/resources/kranji/phonic/`. That is a redistribution of
Unihan-derived data. **Unicode's terms of use have not actually been read.**
They are very likely permissive — redistribution with the notice retained —
but "very likely" is not the same as "checked", and the check has not
happened.

## Evidence

- `git status` shows `input/` as untracked.
- 22 partition files, 264 KB, committed and carrying a source header naming
  Unihan, Unicode 17.0.0, and the copyright.

## What it costs to leave

Nothing today. It bites when someone else clones the repo and tries to
regenerate, or when the licence question is asked by someone who needs a real
answer.

## What would fix it

1. Read <https://www.unicode.org/terms_of_use.html> and record the conclusion.
2. Either commit the source file, or pin its retrieval — version and checksum —
   so the generation step is reproducible from a clean checkout.
3. Record the attribution where a reader will find it, not only in a file
   header.

## Related

Tracked as decision `pc2` on the Full Phonic Coverage plan. It is recorded here
too because committed derived data made it an active fact rather than a
question about future work.
