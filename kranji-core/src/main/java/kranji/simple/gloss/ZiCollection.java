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
     * Who wins when two collections gloss the same character. Lower is more
     * authoritative.
     *
     * <h2>Why this cannot be left to discovery order</h2>
     *
     * <p>{@link Glosses#of} keeps the FIRST entry it sees for a character and
     * shadows the rest, so the order sources arrive in decides which gloss a
     * reader gets. {@link java.util.ServiceLoader} does not specify that order
     * — it follows the classpath, which differs between a jar, an IDE and a
     * shaded build.</p>
     *
     * <p>With one collection that was invisible. With two it means a character
     * glossed both by hand and by machine shows whichever the classpath
     * happened to yield, and the same build can answer differently on another
     * machine. Precedence makes the order a property of the data.</p>
     *
     * <p>0 is hand-authored: somebody read it and meant it. Seeded material
     * ranks above 0 and yields to it.</p>
     */
    default int precedence() { return 0; }

    /**
     * The collections this one is built from, most authoritative first.
     *
     * <p>A leaf answers with itself, which is what makes this safe to walk
     * everywhere: {@code for (ZiCollection layer : c.layers())} reads the same
     * whether {@code c} is one body of data or a stack of them.</p>
     *
     * <h2>Why a caller ever needs to look inside</h2>
     *
     * <p>{@link #precedence()} answers "who wins", and a composed collection
     * has to answer that with a single number for the whole stack — which
     * erases the question the workbench actually asks: <em>has a person
     * written this row, or did a machine guess it?</em> Layers keep that
     * answerable. Without them, one layered collection at precedence 0 makes
     * every seeded row look hand-authored, and the review queue empties
     * itself.</p>
     */
    default List<ZiCollection> layers() { return List.of(this); }

    /**
     * The glosses, for {@link Glosses#of}. Defaulted from the repository so an
     * implementation states its data once.
     */
    @Override
    default List<ZiGloss> entries() { return characters().all(); }
}
