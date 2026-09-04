package kranji.pinyin;

import kranji.phonic.SyllableIndex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The canonical internal form.
 *
 * <p>Standard written spelling plus a tone digit — {@code dong1}, {@code jun1},
 * {@code nü3}, {@code de0}. Everything below the display layer keys on this, so
 * two properties have to hold over the <em>whole</em> corpus rather than over a
 * handful of examples: it must round-trip, and it must be unambiguous.</p>
 *
 * <p>Both failures are silent. A syllable that does not round-trip produces a
 * key nothing matches; two syllables sharing a form produce a key that matches
 * the wrong thing. Neither throws.</p>
 */
class NumberedFormTest {

    /**
     * The spec, written out rather than derived.
     *
     * <p>Deliberately not a comparison against {@code toDiacritic()} — two
     * implementations agreeing proves they agree, not that either is right.
     * These are the cases a person can check against a dictionary.</p>
     */
    private static final String[][] SPEC = {
            // plain
            { "hǎo",    "hao3"    },
            { "dōng",   "dong1"   },
            { "chuáng", "chuang2" },
            { "xiōng",  "xiong1"  },
            // the written collapses — the whole reason this is not base()
            { "jūn",    "jun1"    },   // üen → un, and ü → u after j
            { "liú",    "liu2"    },   // iou → iu
            { "duì",    "dui4"    },   // uei → ui
            { "guī",    "gui1"    },   // uei → ui
            // ü survives after n and l, and nowhere else
            { "nǚ",     "nü3"     },
            { "lüè",    "lüe4"    },
            { "lǜ",     "lü4"     },
            // syllabic consonants get their placeholder i
            { "zhī",    "zhi1"    },
            { "sì",     "si4"     },
            // zero initial
            { "ér",     "er2"     },
            { "yī",     "yi1"     },
            { "wǔ",     "wu3"     },
            // neutral is 0, not 5
            { "de",     "de0"     },
            { "le",     "le0"     },
            { "men",    "men0"    },
    };

    @Test
    void matchesTheSpec() {
        var wrong = new ArrayList<String>();
        for (String[] row : SPEC) {
            String got = PinyinSyllable.parse(row[0]).numbered();
            if (!row[1].equals(got)) {
                wrong.add(row[0] + " -> " + got + ", expected " + row[1]);
            }
        }
        assertEquals(List.of(), wrong, () -> String.join("\n", wrong));
    }

    @Test
    void theSpecParsesBackToWhatItCameFrom() {
        var wrong = new ArrayList<String>();
        for (String[] row : SPEC) {
            String back = PinyinSyllable.parse(row[1]).toDiacritic();
            if (!row[0].equals(back)) {
                wrong.add(row[1] + " -> " + back + ", expected " + row[0]);
            }
        }
        assertEquals(List.of(), wrong, () -> String.join("\n", wrong));
    }

    // ── Over the whole corpus ──────────────────────────────────────────

    @Test
    void everySyllableRoundTrips() {
        // numbered -> parse -> numbered is identity for all 1,284.
        var wrong = new ArrayList<String>();
        for (PinyinSyllable syl : SyllableIndex.instance().syllables()) {
            String numbered = syl.numbered();
            try {
                String back = PinyinSyllable.parse(numbered).numbered();
                if (!numbered.equals(back)) wrong.add(numbered + " -> " + back);
            } catch (RuntimeException e) {
                wrong.add(numbered + " does not parse: " + e.getMessage());
            }
        }
        assertEquals(List.of(), wrong,
                () -> wrong.size() + " syllables do not round-trip:\n  "
                    + String.join("\n  ", wrong));
    }

    @Test
    void noTwoSyllablesShareANumberedForm() {
        // The ambiguity proof. A collision would make one key mean two
        // syllables, and every lookup on it silently wrong for one of them.
        Map<String, PinyinSyllable> seen = new HashMap<>();
        var clashes = new ArrayList<String>();
        for (PinyinSyllable syl : SyllableIndex.instance().syllables()) {
            PinyinSyllable prior = seen.put(syl.numbered(), syl);
            if (prior != null) {
                clashes.add(syl.numbered() + " is both " + prior.toDiacritic()
                        + " and " + syl.toDiacritic());
            }
        }
        assertEquals(List.of(), clashes, () -> String.join("\n", clashes));
    }

    @Test
    void theOnlyNonAsciiLetterIsUUmlaut() {
        // What makes this form safe as a key: no combining marks, so no two
        // byte sequences that look identical and compare unequal.
        var offenders = new ArrayList<String>();
        for (PinyinSyllable syl : SyllableIndex.instance().syllables()) {
            for (char c : syl.numbered().toCharArray()) {
                if (c >= 128 && c != 'ü') {
                    offenders.add(syl.numbered() + " contains U+"
                            + String.format("%04X", (int) c));
                }
            }
        }
        assertEquals(List.of(), offenders, () -> String.join("\n", offenders));
    }

    @Test
    void uUmlautIsConfinedToFourSyllables() {
        var withU = new java.util.TreeSet<String>();
        for (PinyinSyllable syl : SyllableIndex.instance().syllables()) {
            String n = syl.numbered();
            if (n.indexOf('ü') >= 0) withU.add(n.substring(0, n.length() - 1));
        }
        assertEquals(java.util.Set.of("lü", "lüe", "nü", "nüe"), withU,
                "ü survives only after n and l; j/q/x/y drop it by orthography");
    }

    @Test
    void neutralIsZero() {
        assertEquals(0, Tone.NEUTRAL.number());
        assertEquals(Tone.NEUTRAL, Tone.ofNumber(0));
        assertTrue(PinyinSyllable.parse("de").numbered().endsWith("0"));
    }

    @Test
    void fiveIsStillAcceptedOnTheWayIn() {
        // The generated per-class corpus was written when neutral was 5, and
        // its package names still say so. Only 0 is ever emitted.
        assertEquals(Tone.NEUTRAL, Tone.ofNumber(5));
    }
}
