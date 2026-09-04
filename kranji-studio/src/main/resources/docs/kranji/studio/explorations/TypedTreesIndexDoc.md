# Typed Composition Trees — Index

Kranji solves a specific problem — the structural typing of Chinese character
composition — with a general pattern:

- a **sealed sum** of named structural shapes,
- **named typed slots** in each shape,
- **recursive nesting** through any slot,
- **per-class records** as a canonical registry of named instances,
- **pattern-matching** across every downstream consumer.

That pattern fits a surprising number of domains where humans have spent
centuries inventing compositional vocabularies. Each study asks the same
question: would the Kranji-style typed tree fit here, where does it sing, where
does it strain, and what would the experiment look like?

## Catalogue

| Domain | Verdict |
|---|---|
| **Mathematical formulas** | Strong. Sealed primitives, open operator symbols, macros. Execution turns out to be *codegen*, structurally identical to rendering. |
| **Structured slide decks** | Strong. Slide layouts are already a sealed sum; Microsoft simply never wrote down the type. Free-form slides are explicitly out of scope. |
| **Molecular structure** | Strong. Functional groups, rings, chains. Real safety story: compile-time functional-group recognition. Cycles need explicit ring-closure records. |
| **Music notation** | Strong. Form → section → phrase → measure → note, with transposition as a type-parameter swap. |

## The recurring architecture

Every study lands on the same three-layer answer:

1. **Sealed structural primitives** — closed and small. Renderers and analysers
   pattern-match exhaustively; forgetting a variant is a compile error.
2. **Open symbol vocabulary** — operators, themes, atom species, articulations.
   Unbounded by nature; registered, never sealed.
3. **A `Custom` escape hatch plus macros** — one explicit branch for genuine
   novelty, and plain functions for domain-specific composites that desugar to
   primitives.

Seal where it matters, open where it does not.

## The named-intermediate discipline

The move that makes deep structures readable. Any sub-expression that appears
more than once, carries an interpretable meaning, or is referenced downstream
gets its own record. The type names the structure; the record names the meaning.

Black-Scholes is unreadable inlined and obvious once `D1`, `D2`, `VolTime`, and
`Discount` are named — and those names survive all the way into generated code
as local variables. Kranji already does this without saying so: 清 references
`QingTop` rather than restating its subtree.

## Hygiene rules

- **No coupling.** Each experiment is a fresh repository. The pattern is
  inspired by Kranji, never derived from its types.
- **Scope discipline.** Every study states what it does *not* model. Without
  that, "typed trees for X" degenerates into "reimplement X".
- **Honest tiering.** Mathematics and slides are realistic candidates; heraldry
  and ikebana would be showcases. Each study says which it is.
- **Renderer pluggability.** Every study proposes at least two output formats
  from one sealed switch. A domain with only one renderer gains little.
- **Canonical instances only.** Per-class records cover the named corner —
  Aspirin, ii-V-I, Black-Scholes — not the open frontier of user-generated
  content.

## Candidates not yet written up

Legal contracts (rigid clause composition, large market), heraldry (the most
formally compositional vocabulary ever invented), classical architecture,
BPMN workflows, recipes, constitutional drafting, choreography, knitting
patterns.

## The observation underneath

Compositionality with named structural roles is a meta-pattern across human
knowledge — language, music, mathematics, law, chemistry, architecture, cooking.
Java 21 sealed interfaces and records are the first widely-deployed type system
expressive enough to write those vocabularies down so a compiler can check them.
