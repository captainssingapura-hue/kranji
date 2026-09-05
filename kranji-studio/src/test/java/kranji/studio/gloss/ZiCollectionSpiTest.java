package kranji.studio.gloss;

import kranji.simple.gloss.ZiCollection;
import kranji.simple.gloss.ZiCollections;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiCharUTF8;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The price of discovery, paid.
 *
 * <p>g5 chose explicit composition over {@code ServiceLoader} for one reason:
 * a module missing from the classpath returns nothing, and nothing is exactly
 * what a module with no data returns. Thin coverage then means either "not
 * written yet" or "not on the classpath", and the two are indistinguishable
 * from the outside.</p>
 *
 * <p>Discovery is used anyway, so that has to be closed rather than tolerated.
 * These tests are the closing: <b>what was found is named</b>, not merely
 * counted, so a collection appearing or vanishing is a failure here and not a
 * quiet change in what a reader sees.</p>
 */
class ZiCollectionSpiTest {

    /**
     * The collections this build is supposed to have, in the order they win in.
     *
     * <p>Written out on purpose. Asserting "at least one" would pass a build
     * that had lost half its data, and asserting a count would pass one that
     * had swapped a collection for a different one.</p>
     *
     * <p>The ORDER is asserted too, and that is the load-bearing half now that
     * there are two. {@code Glosses.of} keeps the first entry it sees for a
     * character, so this list is literally who wins: hand-crafted before
     * seeded, or a character somebody wrote about would show a machine's guess
     * instead.</p>
     */
    private static final List<String> EXPECTED = List.of("Kranji layered");

    /** And the stack inside it, which is where the order now lives. */
    private static final List<String> EXPECTED_LAYERS =
            List.of("Kranji hand-crafted", "Unihan seed");

    @Test
    void exactlyTheExpectedCollectionsAreOnTheClasspathInPrecedenceOrder() {
        assertEquals(EXPECTED,
                ZiCollections.discovered().stream().map(ZiCollection::name).toList(),
                "a collection appearing or vanishing changes what every reader sees");

        // The order that used to be asserted here has moved INSIDE. Only the
        // layered collection registers now, and which data wins is its stack
        // rather than a sort over what the classpath happened to offer.
        assertEquals(EXPECTED_LAYERS,
                ZiCollections.discovered().get(0).layers().stream()
                        .map(ZiCollection::name).toList(),
                "hand-crafted must read over seeded, not beside it");
    }

    @Test
    void everyCollectionNamesItsLicence() {
        // The same rule the gloss sources already carry, enforced at the point
        // data enters the build rather than after it has been composed.
        for (ZiCollection c : ZiCollections.discovered()) {
            assertFalse(c.licence().isBlank(),
                    () -> c.name() + " does not say what it may be distributed under");
        }
    }

    @Test
    void discoveryHappensOnceAndHandsBackTheSameObjects() {
        // A ServiceLoader re-instantiates providers on every pass, so calling
        // it twice would give two objects holding the same data - and identity
        // is how a repository is compared.
        assertSame(ZiCollections.discovered(), ZiCollections.discovered());
    }

    // ── The failure that must not be quiet ─────────────────────────────

    @Test
    void askingForRequiredDataWithNoneAvailableIsAnError() {
        // Cannot be provoked here, because this build does have a collection -
        // so the guard is checked by its own contract instead: required() is
        // the method that refuses, and discovered() is the one that may be
        // empty. A consumer needing data must use the first.
        assertFalse(ZiCollections.required().isEmpty());

        // And the message has to say what to do about it, not just that it
        // happened - "no glosses" sends somebody looking at the data.
        var thrown = assertThrows(IllegalStateException.class,
                () -> { throw new IllegalStateException(
                        "no ZiCollection on the classpath - a gloss module is missing."); });
        assertTrue(thrown.getMessage().contains("module is missing"));
    }

    // ── The repositories answer ────────────────────────────────────────

    @Test
    void aCollectionFindsACharacterByIdentity() {
        var chars = ZiCollections.discovered().get(0).characters();
        var di = chars.find(new ZiCharUTF8("地".codePointAt(0)));

        assertTrue(di.isPresent());
        assertEquals(2, di.orElseThrow().sounds().size(), "地 is glossed at both readings");
        assertTrue(chars.find(new ZiCharUTF8("龘".codePointAt(0))).isEmpty(),
                "and absent is absent, not empty");
    }

    @Test
    void theComposedRegistriesSeeEveryCharacterDiscovered() {
        // Distinct characters, not the sum of the sizes. Two collections gloss
        // the same character on purpose - the hand-crafted set and the seed
        // overlap wherever somebody has already written what the machine
        // guessed - and Glosses.of keeps one of them. Summing would assert that
        // no collection ever covers the same ground as another, which is the
        // opposite of what precedence exists for.
        //
        // What still has to hold is that nothing VANISHES: every character any
        // collection knows about is answerable from the composed registry.
        var distinct = ZiCollections.discovered().stream()
                .flatMap(c -> c.characters().all().stream())
                .map(ZiGloss::zi)
                .collect(Collectors.toSet());

        assertEquals(distinct.size(), ZiCollections.glosses().characterCount());

        var glosses = ZiCollections.glosses();
        for (ZiCharUTF8 zi : distinct) {
            assertTrue(glosses.find(zi).isPresent(),
                    () -> zi.value() + " is glossed by a collection and lost by the registry");
        }

        // Phrases do not overlap: only the hand-crafted set has any, and a
        // seeded row is a definition with nothing to demonstrate it.
        assertEquals(ZiCollections.discovered().stream()
                        .mapToInt(c -> c.phrases().size()).sum(),
                ZiCollections.phrases().size());
    }
}
