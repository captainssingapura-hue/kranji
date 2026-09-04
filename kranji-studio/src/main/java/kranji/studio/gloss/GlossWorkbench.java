package kranji.studio.gloss;

import kranji.simple.gloss.ZiCollection;
import kranji.simple.gloss.ZiCollections;
import kranji.simple.gloss.EgKey;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.Examples;
import kranji.simple.gloss.Glosses;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiCharUTF8;

import java.util.List;
import java.util.Optional;

/**
 * The gloss data, held in memory, as two repositories.
 *
 * <h2>Why a repository and not just the registries</h2>
 *
 * <p>{@link Glosses} and {@link Examples} are already collection-shaped — they
 * find by identity and enumerate. This adds one thing they do not have: a
 * <b>seam</b>. The studio's tools are read-only today and are meant not to
 * stay that way, and an editor needs somewhere for {@code save} to arrive that
 * is not a static registry built from Java constants.</p>
 *
 * <p>So the interfaces below are deliberately thin, and deliberately named for
 * the two aggregate roots rather than for the files the data happens to live
 * in. A {@link ZiGloss} is an aggregate: its sounds and senses have no identity
 * or lifecycle outside it, and nothing hands one out separately. An
 * {@link ExampleEntry} is the other, which is why a phrase is referenced by key
 * rather than nested inside the character that cites it.</p>
 *
 * <p>Composed once, eagerly, at class-init. The data is a few hundred records
 * built from constants; a lazy holder would buy nothing and would make the
 * first request pay for it.</p>
 */
public final class GlossWorkbench {

    /** The aggregate root for a character and everything said about it. */
    public interface ZiGlossRepository {
        Optional<ZiGloss> find(ZiCharUTF8 zi);
        List<ZiGloss> all();
        int size();
    }

    /** The other root: a phrase, its senses, and how each is read. */
    public interface ExampleRepository {
        Optional<ExampleEntry> find(EgKey key);
        List<ExampleEntry> all();
        int size();
    }

    private static final Glosses GLOSSES = ZiCollections.glosses();
    private static final Examples PHRASES = ZiCollections.phrases();

    private GlossWorkbench() {}

    public static ZiGlossRepository glosses() { return GLOSS_REPO; }

    public static ExampleRepository examples() { return EXAMPLE_REPO; }

    /** The composed registries, for checks that need more than the roots. */
    public static Glosses registry() { return GLOSSES; }

    /** What was found, so a tool can show where its data came from. */
    public static List<ZiCollection> collections() { return ZiCollections.discovered(); }

    /** The phrase registry, for the same reason. */
    public static Examples phraseRegistry() { return PHRASES; }

    private static final ZiGlossRepository GLOSS_REPO = new ZiGlossRepository() {
        @Override public Optional<ZiGloss> find(ZiCharUTF8 zi) { return GLOSSES.find(zi); }
        @Override public List<ZiGloss> all()  { return GLOSSES.all(); }
        @Override public int size()           { return GLOSSES.characterCount(); }
    };

    private static final ExampleRepository EXAMPLE_REPO = new ExampleRepository() {
        @Override public Optional<ExampleEntry> find(EgKey key) { return PHRASES.find(key); }
        @Override public List<ExampleEntry> all() { return PHRASES.all(); }
        @Override public int size()               { return PHRASES.size(); }
    };
}
