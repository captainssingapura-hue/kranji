# The Zi Catalogue

The simple Zi layer is partitioned by phonic. A partition is a tree. The studio
already knows how to browse trees — so the catalogue comes almost for free, and
it arrives with the data rather than long after it.

## The default projection: phonics

```
Zi
├── b
│   ├── a1        巴 八 ...
│   ├── ai2       白 ...
│   └── ...
├── h
│   ├── ang2      行 航 杭
│   └── ao3       好 ...
├── zero          (zero-initial syllables)
└── zh
```

Three levels and a leaf: **initial → final+tone → character**. This is not a
view invented for the browser; it is the storage layout made visible. A
character's position in the tree *is* its default phonic, which is the same
invariant the build already enforces — so a character that browses to the wrong
place is a build failure, not a display bug.

Selecting a character shows what both tiers know: its default phonic, any
additional phonics, its meaning, and — when a structural record exists — its
composition.

## Projections, not filters

Phonics is the default, not the only one. The same character set admits several
trees, and each answers a different question:

| Projection | Shape | Answers |
|---|---|---|
| **Phonics** | initial → final+tone → 字 | "how is it said?" |
| **Radical** | radical → 字 | "what family is it in?" |
| **Strokes** | count → 字 | "how hard is it to write?" |
| **Frequency** | tier → initial → 字 | "how common is it?" |
| **Structure** | LeftRight / TopBottom / … → 字 | "how is it built?" |
| **Known state** | known / learning / unseen → 字 | "where am I?" |

The radical and structure projections are where the corpus earns its keep — no
flat character list can offer them, because they need the typed composition
tree. A parent pulling up every character built on 氵 is navigating by a
relationship that actually exists between the characters, not by an alphabet.

Projections are navigation, not filtering. A filter chip narrows a list;
a projection reorganises the whole space and gives every node an address. That
matters because addresses are shareable and bookmarkable, and because the
framework's tree gives folding and breadcrumbs to any hierarchy that has them.

Structure and known-state projections need data that arrives later — the
structural tier is already there, but known-state needs a profile. Phonics is
the one that ships with the data.

## The collection is a Catalogue

The Zi collection is modelled as a **Catalogue** in the studio's own sense — the
same L0 / L1 structure the documentation sections use — rather than as a
tree-document. The regular catalogue listing then works on it unchanged:
breadcrumbs, addressing, and the standard tile listing all come from being a
catalogue rather than from a bespoke viewer.

Concretely the partition maps onto the catalogue levels: the collection is the
root, each initial is a sub-catalogue, and characters are its entries.

The mechanics of deriving a catalogue from an existing data structure — rather
than hand-writing one record per node, which is untenable for thousands of
characters — are pending guidance from the Homing side. That guidance determines the
shape of the adapter, so the adapter is deliberately not designed here.

## Where the boundary sits

Whatever the adapter turns out to be, it names framework types, and `-model`
must not depend on Homing. So the projection splits:

```
-model   SimpleZi set ──▶ ZiTree       plain records, framework-free, testable
-app     ZiTree        ──▶ Catalogue   adapter and entries
```

`-model` owns the *shape* — which characters sit under which node, in what
order, with what labels. That is the part with logic worth testing, and it
stays testable with plain JUnit. `-app` owns the adaptation.

Without that split the projection logic would drag the framework into the
domain module, and the rule that keeps `-model` browser-free would erode on its
first useful feature.

## Why build it with the data

Three reasons beyond it being cheap:

**It validates the data as it is entered.** Two thousand characters typed into
a DSL will contain mistakes. A browser over the partition surfaces a wrong
default phonic immediately — the character appears under the wrong node — where
a spreadsheet of the same data would hide it.

**It is the known-set manager's browse surface.** That workspace needs to
filter by radical, stroke count, and composition. Those are projections. Built
here, the manager becomes a state overlay on an existing tree rather than a
second browser.

**It gives the corpus a face.** Kranji has modelled 2,532 characters and has
never had a way to simply look at them. The catalogue is useful to the project
independent of the reading app.

## Scope

**In:** the phonics projection over the simple tier, character detail from both
tiers, the model-side tree shape with its own tests.

**Out for now:** projections needing a profile, editing from the catalogue —
marking known belongs to the known-set manager, which arrives later and owns
the channel and provenance rules.
