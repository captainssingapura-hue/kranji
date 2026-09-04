package kranji.gloss.handcrafted;

import kranji.phonic.SyllableIndex;
import kranji.pinyin.PinyinSyllable;
import kranji.simple.gloss.EgKey;
import kranji.simple.gloss.EgRef;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.Examples;
import kranji.simple.gloss.Glosses;
import kranji.simple.gloss.Sense;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Does every gloss point at something real?
 *
 * <p>These are the checks types cannot reach, and they share a failure mode:
 * each mistake produces data that <em>looks</em> like coverage. A wrong tone is
 * a gloss nothing ever matches; a phrase referenced but never defined shows a
 * character with nothing beside it; an example under the wrong entry reads
 * perfectly and teaches something else. None of them throws, and none shows up
 * in a count.</p>
 *
 * <p>Kept out of static initialisers deliberately — they compare the gloss tier
 * against the phonic tier, and doing that at class-init would make one depend
 * on the other loading first.</p>
 */
class GlossValidityTest {

    private static final Glosses GLOSSES = Glosses.of(HandCrafted.INSTANCE);
    private static final Examples PHRASES = HandCrafted.registry();

    @Test
    void everyGlossNamesAReadingTheCorpusLists() {
        SyllableIndex index = SyllableIndex.instance();
        var wrong = new ArrayList<String>();

        for (ZiGloss entry : GLOSSES.all()) {
            var readings = index.readingsOf(entry.zi());
            if (readings.isEmpty()) {
                wrong.add(entry.zi().value() + " is not in the phonic corpus at all");
                continue;
            }
            for (SoundGloss sound : entry.sounds()) {
                if (!readings.get().all().contains(sound.reading())) {
                    wrong.add(entry.zi().value() + " " + sound.reading().toDiacritic()
                            + " - the corpus lists " + readings.get().readingTexts());
                }
            }
        }

        assertEquals(List.of(), wrong,
                () -> "glosses pointing at readings nothing has:\n  "
                    + String.join("\n  ", wrong));
    }

    @Test
    void everyPhraseReferencedIsDefined() {
        // The seam the split introduced. A gloss names a phrase by key and the
        // registry holds its content, so a reference nobody defined leaves the
        // character with an example that renders as nothing.
        var dangling = new ArrayList<String>();
        for (ZiGloss entry : GLOSSES.all()) {
            for (EgRef ref : entry.examples()) {
                var defined = PHRASES.find(ref.phrase());
                if (defined.isEmpty()) {
                    dangling.add(entry.zi().value() + " points at " + ref
                            + ", which no source defines");
                } else if (defined.get().sense(ref.sense()).isEmpty()) {
                    dangling.add(entry.zi().value() + " points at " + ref
                            + ", but that phrase has only " + defined.get().senses().size()
                            + " sense(s)");
                }
            }
        }
        assertEquals(List.of(), dangling, () -> String.join("\n", dangling));
    }

    @Test
    void everyPhraseDefinedIsUsed() {
        // The other direction. A phrase nobody references is not wrong, but it
        // is work that bought nothing, and usually means a gloss was edited
        // and its example left behind.
        var used = new ArrayList<EgKey>();
        for (ZiGloss entry : GLOSSES.all()) {
            for (EgRef ref : entry.examples()) used.add(ref.phrase());
        }

        var orphaned = new ArrayList<String>();
        for (ExampleEntry defined : PHRASES.all()) {
            if (!used.contains(defined.key())) {
                orphaned.add(defined.key() + " (\"" + defined.primary().english() + "\")");
            }
        }
        assertEquals(List.of(), orphaned,
                () -> "phrases defined but referenced by nothing:\n  "
                    + String.join("\n  ", orphaned));
    }

    @Test
    void everyExampleContainsTheCharacterItExplains() {
        // The mistake nobody would notice: a phrase filed under the wrong
        // entry reads perfectly and teaches a different character.
        var stray = new ArrayList<String>();
        for (ZiGloss entry : GLOSSES.all()) {
            for (EgRef ref : entry.examples()) {
                if (!ref.phrase().contains(entry.zi())) {
                    stray.add(ref + " is filed under " + entry.zi().value()
                            + ", which it does not contain");
                }
            }
        }
        assertEquals(List.of(), stray, () -> String.join("\n", stray));
    }

    @Test
    void everyStatedReadingDisagreesWithTheCorpus() {
        // A reading is stated only where it is NOT the principal, so one that
        // agrees is noise - and worse, it suggests the author thought the
        // corpus said something else.
        SyllableIndex index = SyllableIndex.instance();
        var redundant = new ArrayList<String>();

        for (ExampleEntry entry : PHRASES.all()) {
            for (var sense : entry.senses()) for (int at = 0; at < entry.key().length(); at++) {
                var stated = sense.soundAt(at);
                if (stated.isEmpty()) continue;
                var known = index.readingsOf(entry.key().characters().get(at));
                if (known.isEmpty()) continue;
                PinyinSyllable principal = known.get().all().get(0);
                if (principal.equals(stated.get())) {
                    redundant.add(entry.key() + " states " + stated.get().toDiacritic()
                            + " at " + at + ", which is already the principal");
                }
            }
        }
        assertEquals(List.of(), redundant, () -> String.join("\n", redundant));
    }

    @Test
    void everyStatedReadingIsOneTheCharacterHas() {
        SyllableIndex index = SyllableIndex.instance();
        var wrong = new ArrayList<String>();

        for (ExampleEntry entry : PHRASES.all()) {
            for (var sense : entry.senses()) for (int at = 0; at < entry.key().length(); at++) {
                var stated = sense.soundAt(at);
                if (stated.isEmpty()) continue;
                var known = index.readingsOf(entry.key().characters().get(at));
                if (known.isEmpty()) continue;
                if (!known.get().all().contains(stated.get())) {
                    wrong.add(entry.key() + " reads position " + at + " as "
                            + stated.get().toDiacritic() + ", which the corpus does not list: "
                            + known.get().readingTexts());
                }
            }
        }
        assertEquals(List.of(), wrong, () -> String.join("\n", wrong));
    }

    @Test
    void everyExampleReadsTheCharacterTheWayTheGlossSays() {
        // THE check the sound annotation exists for, and the one that was
        // missing while the annotation was already built. An example filed
        // under 行 háng must actually read 行 as háng; the annotation is
        // decorative without this, and two records were wrong because of it.
        SyllableIndex index = SyllableIndex.instance();
        var wrong = new ArrayList<String>();

        for (ZiGloss entry : GLOSSES.all()) {
            for (SoundGloss sound : entry.sounds()) {
                for (Sense sense : sound.senses().values()) {
                    for (EgRef ref : sense.orderedExamples()) {
                        var defined = PHRASES.find(ref.phrase());
                        if (defined.isEmpty()) continue;   // reported elsewhere
                        var phraseSense = defined.get().sense(ref.sense());
                        if (phraseSense.isEmpty()) continue;
                        for (int at = 0; at < ref.phrase().length(); at++) {
                            if (!ref.phrase().characters().get(at).equals(entry.zi())) continue;
                            var stated = phraseSense.get().soundAt(at);
                            PinyinSyllable used = stated.orElseGet(() ->
                                    index.readingsOf(entry.zi()).orElseThrow().all().get(0));
                            if (!used.equals(sound.reading())) {
                                wrong.add(entry.zi().value() + " is filed under "
                                        + sound.reading().toDiacritic() + " but " + ref
                                        + " reads position " + at + " as "
                                        + used.toDiacritic());
                            }
                        }
                    }
                }
            }
        }
        assertEquals(List.of(), wrong, () -> String.join("\n", wrong));
    }

    @Test
    void everyMeaningShowsAtLeastOneExample() {
        // A meaning without an example is a definition; with one it is a
        // memory. This is the whole argument for the extra structure.
        var bare = new ArrayList<String>();
        for (ZiGloss entry : GLOSSES.all()) {
            for (SoundGloss sound : entry.sounds()) {
                for (var e : sound.senses().entrySet()) {
                    Sense sense = e.getValue();
                    if (sense.examples().isEmpty()) {
                        bare.add(sound.key() + " \"" + e.getKey() + "\"");
                    }
                }
            }
        }
        assertEquals(List.of(), bare,
                () -> "meanings with nothing to show:\n  " + String.join("\n  ", bare));
    }

    @Test
    void aPolyphoneIsGlossedAtEveryReadingOrNone() {
        // A half-glossed polyphone is worse than an unglossed one: it implies
        // the reading without a gloss has no meaning.
        SyllableIndex index = SyllableIndex.instance();
        var partial = new ArrayList<String>();

        for (ZiGloss entry : GLOSSES.all()) {
            if (entry.sounds().size() < 2) continue;
            var readings = index.readingsOf(entry.zi());
            if (readings.isEmpty()) continue;
            for (PinyinSyllable reading : readings.get().all()) {
                if (entry.at(reading).isEmpty()) {
                    partial.add(entry.zi().value() + " is glossed at "
                            + entry.sounds().size() + " readings but not at "
                            + reading.toDiacritic());
                }
            }
        }
        assertEquals(List.of(), partial, () -> String.join("\n", partial));
    }

    @Test
    void everySourceStatesItsProvenance() {
        for (var source : GLOSSES.sources()) {
            assertTrue(!source.licence().isBlank(),
                    () -> source.name() + " does not state its licence");
        }
    }
}
