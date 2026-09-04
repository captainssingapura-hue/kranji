package kranji.reading.app.ui;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The display boundary.
 *
 * <p>Checked against a <b>written spec</b>, not against Java's renderer. Two
 * implementations agreeing shows that they agree; it does not show that either
 * is right, and a rule this small is better stated than derived. Every pair
 * below can be checked against a dictionary by a person.</p>
 */
class PinyinSwfModuleTest extends JsModuleTestBase {

    private static final String MODULE =
            "/homing/js/kranji/reading/app/ui/PinyinSwfModule.js";

    /**
     * The spec: canonical form on the left, what a child should read on the
     * right.
     */
    private static final String[][] SPEC = {
            // the four tones on a plain syllable
            { "ma1",     "mā"     },
            { "ma2",     "má"     },
            { "ma3",     "mǎ"     },
            { "ma4",     "mà"     },
            { "ma0",     "ma"     },

            // a takes the mark wherever it sits
            { "hao3",    "hǎo"    },
            { "shuang1", "shuāng" },
            { "xiang4",  "xiàng"  },

            // no a: o or e takes it, and they never co-occur
            { "dong1",   "dōng"   },
            { "xiong1",  "xiōng"  },
            { "feng1",   "fēng"   },
            { "zhou1",   "zhōu"   },

            // no a, o or e: the LAST vowel takes it. This is the rule that
            // makes iu -> iù and ui -> uì rather than ìu and ùi.
            { "liu2",    "liú"    },
            { "dui4",    "duì"    },
            { "gui1",    "guī"    },
            { "jiu3",    "jiǔ"    },

            // ü, which survives only after n and l
            { "nü3",     "nǚ"     },
            { "lü4",     "lǜ"     },
            { "lüe4",    "lüè"    },   // e wins over ü, being o/e before last-vowel

            // syllabic consonants keep their placeholder i
            { "zhi1",    "zhī"    },
            { "si4",     "sì"     },
            { "ri4",     "rì"     },

            // zero initial
            { "er2",     "ér"     },
            { "yi1",     "yī"     },
            { "wu3",     "wǔ"     },
            { "an4",     "àn"     },

            // the collapses, already applied in the canonical form
            { "jun1",    "jūn"    },
            { "chuang2", "chuáng" },

            // neutral loses the digit and takes no mark
            { "de0",     "de"     },
            { "le0",     "le"     },
            { "men0",    "men"    },
            { "zhe0",    "zhe"    },
    };

    private Value swf;

    @BeforeEach
    void load() {
        loadModule(MODULE);
        swf = global("createPinyinSwf").execute();
    }

    private String toSWF(String numbered) {
        return swf.getMember("toSWF").execute(numbered).asString();
    }

    @Test
    void matchesTheSpec() {
        var wrong = new ArrayList<String>();
        for (String[] row : SPEC) {
            String got = toSWF(row[0]);
            if (!row[1].equals(got)) {
                wrong.add(row[0] + " -> " + got + ", expected " + row[1]);
            }
        }
        assertEquals(List.of(), wrong,
                () -> wrong.size() + " conversions wrong:\n  " + String.join("\n  ", wrong));
    }

    @Test
    void theMarkGoesOnTheLastVowelOnlyWhenThereIsNoAOrOOrE() {
        // Stated separately because it is the rule people get wrong, and the
        // one that decides iu/ui.
        assertEquals("liú", toSWF("liu2"));
        assertEquals("duì", toSWF("dui4"));
        assertEquals("hǎo", toSWF("hao3"));      // a wins over the last vowel
        assertEquals("zhōu", toSWF("zhou1"));    // o wins over the last vowel
    }

    @Test
    void aNeutralSyllableIsJustTheLetters() {
        assertEquals("de", toSWF("de0"));
        assertFalse(toSWF("de0").endsWith("0"));
    }

    // ── What it refuses to mangle ──────────────────────────────────────

    @Test
    void somethingAlreadyInDisplayFormComesBackUnchanged() {
        // A caller handing this a diacritic string has a bug further up.
        // Returning nonsense would hide it; returning the input leaves the
        // wrong reading visible on the page, where somebody will notice.
        assertEquals("dōng", toSWF("dōng"));
        assertEquals("hǎo", toSWF("hǎo"));
    }

    @Test
    void rubbishComesBackUnchanged() {
        assertEquals("", toSWF(""));
        assertEquals("x", toSWF("x"));
        assertEquals("dong9", toSWF("dong9"));
    }

    // ── Telling the two forms apart ────────────────────────────────────

    @Test
    void knowsTheCanonicalFormFromTheDisplayForm() {
        Value is = swf.getMember("isCanonical");

        assertTrue(is.execute("dong1").asBoolean());
        assertTrue(is.execute("nü3").asBoolean());
        assertTrue(is.execute("de0").asBoolean());

        assertFalse(is.execute("dōng").asBoolean(), "a tone mark is not canonical");
        assertFalse(is.execute("dong").asBoolean(), "no digit is not canonical");
        assertFalse(is.execute("dong5").asBoolean(), "neutral is 0, not 5");
    }

    // ── The way back in ────────────────────────────────────────────────

    private String fromSWF(String display) {
        Value v = swf.getMember("fromSWF").execute(display);
        return v.isNull() ? null : v.asString();
    }

    @Test
    void readsTheSpecBackwards() {
        // The same table, right to left. Stated as its own test rather than
        // as a round-trip through toSWF: a round-trip passes if both
        // directions are wrong in the same way.
        var wrong = new ArrayList<String>();
        for (String[] row : SPEC) {
            String got = fromSWF(row[1]);
            if (!row[0].equals(got)) {
                wrong.add(row[1] + " -> " + got + ", expected " + row[0]);
            }
        }
        assertEquals(List.of(), wrong,
                () -> wrong.size() + " conversions wrong:\n  " + String.join("\n  ", wrong));
    }

    @Test
    void anUnmarkedSyllableIsNeutral() {
        // Which is how the display form writes it - there is no other reading
        // of a syllable carrying no mark.
        assertEquals("de0", fromSWF("de"));
        assertEquals("men0", fromSWF("men"));
    }

    @Test
    void aMarkWrittenAsItsOwnCodePointIsStillTheSameSyllable() {
        // NFD: the caron written as its own code point after a plain a. It
        // looks identical on the page and compares unequal - the hazard the
        // canonical form exists to remove - so the way in has to close it
        // rather than pass it through.
        String decomposed = "ha\u030Co";
        assertNotEquals("h\u01CEo", decomposed, "the point: these differ as strings");
        assertEquals("hao3", fromSWF(decomposed), "decomposed reads the same");
        assertEquals("hao3", fromSWF("h\u01CEo"), "and so does composed");
    }

    @Test
    void refusesWhatIsNotASyllable() {
        assertNull(fromSWF(""));
        assertNull(fromSWF(null));
        assertNull(fromSWF("hao3"), "already canonical - that is canonical()'s job, not this");
        assertNull(fromSWF("hǎǒ"), "two marks is not one syllable");
        assertNull(fromSWF("床"));
        assertNull(fromSWF("hao "), "a stray space is not a syllable");
    }

    @Test
    void canonicalTakesEitherForm() {
        Value c = swf.getMember("canonical");

        assertEquals("dong1", c.execute("dōng").asString(), "the old stored form");
        assertEquals("dong1", c.execute("dong1").asString(), "already canonical, untouched");
        assertEquals("de0", c.execute("de").asString());
        assertTrue(c.execute("床").isNull(), "not a syllable, and says so");
    }
}
