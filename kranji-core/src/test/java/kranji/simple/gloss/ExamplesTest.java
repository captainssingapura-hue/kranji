package kranji.simple.gloss;

import kranji.zi.ZiCharUTF8Codec;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The phrase registry.
 *
 * <p>A phrase belongs to no character in particular — 银行 teaches 银 and 行
 * equally — so it is defined once here and referenced from both. What is worth
 * testing is that identity really is the phrase alone, and that a reading is
 * stated only where it would surprise.</p>
 */
class ExamplesTest {

    // ── Identity is the phrase ─────────────────────────────────────────

    @Test
    void twoWritingsOfOnePhraseAreOneKey() {
        assertEquals(EgKey.of("银行"), EgKey.of("银行"));
        assertEquals(EgKey.of("银行").hashCode(), EgKey.of("银行").hashCode());
    }

    @Test
    void aKeyKnowsWhichCharactersItTeaches() {
        // The reverse index, free: which characters does this word explain?
        EgKey bank = EgKey.of("银行");

        assertTrue(bank.contains(ZiCharUTF8Codec.INSTANCE.from("银")));
        assertTrue(bank.contains(ZiCharUTF8Codec.INSTANCE.from("行")));
        assertEquals("银行", bank.phrase());
    }

    @Test
    void aPhraseMustBeAllChinese() {
        // Caught a real one: "当time" was an unfinished placeholder that passed
        // every other check, because it did contain 当.
        assertThrows(IllegalArgumentException.class, () -> EgKey.of("当time"));
        assertThrows(IllegalArgumentException.class, () -> EgKey.of(""));
    }

    // ── Readings, only where they surprise ─────────────────────────────

    @Test
    void mostPhrasesStateNoReadingsAtAll() {
        // 得到 reads 得 as dé, which is the corpus principal. Nothing to say.
        ExampleEntry got = ExampleEntry.of("得到", "to obtain");

        assertTrue(got.primary().sounds().isEmpty());
    }

    @Test
    void aReadingIsStatedWhereItIsNotThePrincipal() {
        // 行 is principally xíng and is háng here. The override marks exactly
        // the places a reader would guess wrong.
        ExampleEntry bank = ExampleEntry.of("银行", "a bank").sound(1, "hang2");

        assertEquals("háng", bank.primary().soundAt(1).orElseThrow().toDiacritic());
        assertTrue(bank.primary().soundAt(0).isEmpty(), "银 is read as the corpus expects");
    }

    @Test
    void aReadingIsStatedByPositionNotByCharacter() {
        // A phrase can repeat a character and read it two ways, so the index
        // is the position and not the glyph.
        ExampleEntry entry = ExampleEntry.of("好好", "properly").sound(1, "hao1");

        assertTrue(entry.primary().soundAt(0).isEmpty());
        assertEquals("hāo", entry.primary().soundAt(1).orElseThrow().toDiacritic());
    }

    @Test
    void aReadingOutsideThePhraseIsRefused() {
        assertThrows(IllegalArgumentException.class,
                () -> ExampleEntry.of("银行", "a bank").sound(5, "hang2"));
    }

    // ── Composition ────────────────────────────────────────────────────

    @Test
    void aPhraseIsDefinedOnceAndReferencedFromAnywhere() {
        Examples registry = Examples.of(List.of(
                ExampleEntry.of("银行", "a bank").sound(1, "hang2"),
                ExampleEntry.of("行走", "to walk")));

        assertEquals(2, registry.size());
        assertEquals("a bank", registry.find(EgKey.of("银行")).orElseThrow().primary().english().text());
    }

    @Test
    void anIdenticalRepeatIsAbsorbed() {
        // Saying the same thing twice is not a disagreement.
        Examples registry = Examples.of(
                List.of(ExampleEntry.of("银行", "a bank")),
                List.of(ExampleEntry.of("银行", "a bank")));

        assertEquals(1, registry.size());
        assertEquals(0, registry.shadowed().size());
    }

    @Test
    void twoGlossesOfOnePhraseAreKeptForReview() {
        // This one IS a disagreement, and where two sources disagree is
        // exactly where somebody should look.
        Examples registry = Examples.of(
                List.of(ExampleEntry.of("银行", "a bank")),
                List.of(ExampleEntry.of("银行", "a financial institution")));

        assertEquals(1, registry.size());
        assertEquals("a bank", registry.find(EgKey.of("银行")).orElseThrow().primary().english().text());
        assertEquals(1, registry.shadowed().size());
    }

    @Test
    void aPhraseCanMeanMoreThanOneThing() {
        // 东西 is east-and-west read dōngxī and a thing read dōngxi. The senses
        // do not even share a reading, which is why the readings sit on the
        // sense and not on the phrase.
        ExampleEntry dongxi = ExampleEntry.of("东西", "east and west")
                .also("a thing");

        assertEquals(2, dongxi.senses().size());
        assertEquals("east and west", dongxi.primary().english().text());
        assertEquals("a thing", dongxi.sense(1).orElseThrow().english().text());
        assertTrue(dongxi.sense(2).isEmpty());
    }

    @Test
    void eachSenseCarriesItsOwnReadings() {
        ExampleEntry dongxi = ExampleEntry.of("东西", "east and west")
                .also("a thing").sound(1, "xi1");

        assertTrue(dongxi.primary().sounds().isEmpty(), "sound() applies to the latest sense");
        assertEquals(1, dongxi.sense(1).orElseThrow().sounds().size());
    }

    @Test
    void aReferenceNamesASenseNotJustAPhrase() {
        assertEquals(0, EgRef.to("银行").sense());
        assertEquals(1, EgRef.to("东西", 1).sense());
        assertEquals(EgRef.to("银行"), EgRef.to("银行", 0));
    }

    @Test
    void anExampleGlossIsHeldToTheSameLimitAsAMeaning() {
        // One rule, one constant, one type. An earlier version restated the
        // cap inside ExampleSense, which is two places for one decision.
        String essay = "to go, to walk, to travel, to move about, to circulate, "
                     + "to be current, to publish, to do, to perform";

        assertThrows(IllegalArgumentException.class,
                () -> ExampleEntry.of("行走", essay));
        assertThrows(IllegalArgumentException.class,
                () -> ExampleEntry.of("行走", "to walk").also(essay));
        assertThrows(IllegalArgumentException.class,
                () -> ExampleEntry.of("行走", "   "));
    }

    @Test
    void anExampleGlossIsTheSameTypeAsAMeaning() {
        assertEquals(Meaning.of("a bank"),
                ExampleEntry.of("银行", "a bank").primary().english());
    }

    @Test
    void anUndefinedPhraseIsAbsentNotBlank() {
        assertTrue(Examples.none().find(EgKey.of("银行")).isEmpty());
    }

    @Test
    void phrasesComeBackInTheOrderTheyWereAuthored() {
        // The registry's counterpart to the same bug in Glosses: a
        // LinkedHashMap frozen with Map.copyOf loses its insertion order, and
        // nothing notices until the phrases are listed for a person to read.
        var authored = List.of(
                ExampleEntry.of("东方", "the east"),
                ExampleEntry.of("低头", "to lower the head"),
                ExampleEntry.of("到了", "arrived"),
                ExampleEntry.of("动物", "an animal"),
                ExampleEntry.of("大人", "an adult"),
                ExampleEntry.of("对了", "that's right"),
                ExampleEntry.of("山洞", "a cave"));

        assertEquals(List.of("东方", "低头", "到了", "动物", "大人", "对了", "山洞"),
                Examples.of(authored).all().stream()
                        .map(e -> e.key().phrase()).toList());
    }
}
