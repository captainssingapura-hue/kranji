package kranji.simple.gloss;

import kranji.zi.ZiCharUTF8;

import java.util.List;
import java.util.Optional;

/**
 * A self-contained body of glossed characters, discovered on the classpath.
 *
 * <p>A collection carries its own data, its own licence, and its own way of
 * holding both. A consumer asks it for characters and phrases and never learns
 * whether they came from a file, a database or a constant — which is the point:
 * the 447-pair set is a pair of TSV files today and need not stay one.</p>
 *
 * <h2>Discovery, and the hole it opens</h2>
 *
 * <p>Provided through {@link java.util.ServiceLoader}. That reverses g5, which
 * chose explicit composition precisely because discovery fails quietly: a
 * module absent from the classpath is indistinguishable from a module with
 * nothing in it, and the symptom — thin coverage — is the same symptom as work
 * not yet done.</p>
 *
 * <p>So the failure mode is closed rather than accepted. {@link ZiCollections}
 * refuses to answer with nothing, and a test asserts that what was discovered
 * is what was expected. Discovery is convenient; it is not allowed to be
 * silent.</p>
 */
public interface ZiCollection extends GlossSource {

    /** What a consumer asks for a character. */
    interface Characters {
        Optional<ZiGloss> find(ZiCharUTF8 zi);
        List<ZiGloss> all();
        int size();
    }

    /** And for a phrase, which has an identity of its own. */
    interface Phrases {
        Optional<ExampleEntry> find(EgKey key);
        List<ExampleEntry> all();
        int size();
    }

    Characters characters();

    Phrases phrases();

    /**
     * The glosses, for {@link Glosses#of}. Defaulted from the repository so an
     * implementation states its data once.
     */
    @Override
    default List<ZiGloss> entries() { return characters().all(); }
}
