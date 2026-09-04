package kranji.simple.gloss;

import kranji.zi.ZiCharUTF8Codec;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kranji.simple.gloss.GlossDsl.zi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Composing gloss sources.
 *
 * <p>What is worth testing is the key and the precedence. A gloss keyed a hair
 * differently from the known set matches nothing and shows nothing; a registry
 * that quietly preferred imported data over a reviewed entry would be wrong in
 * a way nobody would see.</p>
 */
class GlossesTest {

    private static GlossSource source(String name, String licence, ZiGloss... entries) {
        return new GlossSource() {
            @Override public String name()          { return name; }
            @Override public String licence()       { return licence; }
            @Override public List<ZiGloss> entries() { return List.of(entries); }
        };
    }

    private static final ZiGloss HAO = zi("好")
            .read("hao3").means("good; well").eg("很好").done()
            .done()
            .read("hao4").means("to be fond of").eg("爱好").build();

    // ── The key ────────────────────────────────────────────────────────

    @Test
    void keysExactlyAsTheKnownSetAndCensusDo() {
        // All three join on this string. One character of difference and every
        // lookup misses, silently.
        assertEquals("22909:hao3", HAO.sounds().get(0).key());
        assertEquals("22909:hao4", HAO.sounds().get(1).key());
    }

    @Test
    void oneCharacterCarriesADifferentMeaningPerReading() {
        Glosses g = Glosses.of(source("test", "project", HAO));

        assertEquals("good; well", g.find("22909:hao3").orElseThrow().primary().text());
        assertEquals("to be fond of", g.find("22909:hao4").orElseThrow().primary().text());
    }

    @Test
    void aReadingWithNoEntryIsAbsentNotBlank() {
        Glosses g = Glosses.of(source("test", "project",
                zi("好").read("hao3").means("good; well").eg("很好").build()));

        assertTrue(g.find("22909:hao4").isEmpty(),
                "an unglossed reading must not borrow the other one's meaning");
    }

    @Test
    void findsTheWholeCharacterAsWellAsOneReading() {
        Glosses g = Glosses.of(source("test", "project", HAO));

        assertEquals(2, g.find(ZiCharUTF8Codec.INSTANCE.from("好"))
                .orElseThrow().sounds().size());
        assertEquals(1, g.characterCount());
        assertEquals(2, g.pairCount());
    }

    // ── Composition ────────────────────────────────────────────────────

    @Test
    void anEarlierSourceWinsOnARepeatedCharacter() {
        // Hand-crafted first, ported second: an entry somebody reviewed beats
        // one that was imported.
        Glosses g = Glosses.of(
                source("hand", "project", HAO),
                source("ported", "CC BY-SA",
                        zi("好").read("hao3").means("good/well/proper").eg("很好").build()));

        assertEquals("good; well", g.find("22909:hao3").orElseThrow().primary().text());
        assertEquals(1, g.characterCount());
    }

    @Test
    void theLoserIsKeptSoAnOverlapCanBeReviewed() {
        // Where two sources disagree is exactly where somebody should look.
        Glosses g = Glosses.of(
                source("hand", "project", HAO),
                source("ported", "CC BY-SA",
                        zi("好").read("hao3").means("good/well/proper").eg("很好").build()));

        assertEquals(1, g.shadowed().size());
        assertEquals("good/well/proper",
                g.shadowed().get(0).sounds().get(0).primary().text());
    }

    @Test
    void everySourceNamesItsLicence() {
        // A build including share-alike data is a different distribution from
        // one that does not, and the difference must be readable off the data.
        Glosses g = Glosses.of(source("hand-crafted", "project-authored"));

        assertEquals("project-authored", g.sources().get(0).licence());
    }

    @Test
    void everythingComesBackInTheOrderItWasAuthored() {
        // Order is authored information, the same as a sense's rank. It was
        // being lost: the registry builds a LinkedHashMap and then freezes it
        // with Map.copyOf, whose iteration order is explicitly unspecified - so
        // reading the values back gave an arbitrary order that looked stable
        // until the data changed. Nothing revealed it until the rows were put
        // in a grid in front of somebody.
        //
        // Enough entries that a hash order is unlikely to match by luck.
        ZiGloss[] authored = {
                zi("东").read("dong1").means("east").eg("东方").build(),
                zi("低").read("di1").means("low").eg("低头").build(),
                zi("到").read("dao4").means("to arrive").eg("到了").build(),
                zi("动").read("dong4").means("to move").eg("动物").build(),
                zi("大").read("da4").means("big").eg("大人").build(),
                zi("对").read("dui4").means("correct").eg("对了").build(),
                zi("洞").read("dong4").means("a hole").eg("山洞").build()
        };

        Glosses g = Glosses.of(source("hand-crafted", "project-authored", authored));

        assertEquals(List.of("东", "低", "到", "动", "大", "对", "洞"),
                g.all().stream().map(e -> e.zi().value()).toList());
    }

    @Test
    void aBuildWithNoGlossModuleStillWorks() {
        Glosses none = Glosses.none();

        assertEquals(0, none.characterCount());
        assertTrue(none.find("22909:hao3").isEmpty());
    }
}
