package kranji.gloss.handcrafted;

import kranji.simple.gloss.ZiCollection;
import kranji.simple.gloss.ZiCollections;
import kranji.zi.ZiCharUTF8;
import org.junit.jupiter.api.Test;

import java.util.List;

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
     * The collections this build is supposed to have.
     *
     * <p>Written out on purpose. Asserting "at least one" would pass a build
     * that had lost half its data, and asserting a count would pass one that
     * had swapped a collection for a different one.</p>
     */
    private static final List<String> EXPECTED = List.of("Kranji hand-crafted");

    @Test
    void exactlyTheExpectedCollectionsAreOnTheClasspath() {
        assertEquals(EXPECTED,
                ZiCollections.discovered().stream().map(ZiCollection::name).toList(),
                "a collection appearing or vanishing changes what every reader sees");
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
    void theComposedRegistriesSeeEverythingDiscovered() {
        assertEquals(ZiCollections.discovered().stream()
                        .mapToInt(c -> c.characters().size()).sum(),
                ZiCollections.glosses().characterCount());
        assertEquals(ZiCollections.discovered().stream()
                        .mapToInt(c -> c.phrases().size()).sum(),
                ZiCollections.phrases().size());
    }
}
