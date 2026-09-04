package kranji.simple.gloss;

import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Glosses, composed from named sources and looked up by character or by pair.
 *
 * <h2>Composition is explicit</h2>
 *
 * <p>A registry is built from the sources its caller hands it. No
 * {@code ServiceLoader}, no static mutable list, no boot step — because the
 * failure those produce is silent, and in a corpus where "less coverage than we
 * hoped" is the expected state, a missing source is indistinguishable from a
 * genuine gap. A build without a source should be a compile-time fact.</p>
 *
 * <h2>Earlier sources win, and losers are kept</h2>
 *
 * <p>{@code Glosses.of(handCrafted, ported)} means a reviewed entry beats an
 * imported one. The shadowed entry is retained rather than dropped, because
 * where two sources disagree is exactly where somebody should look.</p>
 *
 * <p>Nothing here checks that a reading is one the phonic corpus lists. That
 * check is essential — a gloss pointing at a reading nothing has matches
 * nothing, shows nothing and errors nowhere — but it compares two corpora, and
 * doing it in a static initialiser would make this tier depend on the reading
 * tier loading first.</p>
 */
public final class Glosses {

    private final Map<ZiCharUTF8, ZiGloss> byCharacter;
    private final Map<String, SoundGloss> byPair;
    private final List<GlossSource> sources;
    /** Authoring order, which the frozen map does not keep - see {@link #all()}. */
    private final List<ZiGloss> ordered;
    private final List<ZiGloss> shadowed;

    private Glosses(Map<ZiCharUTF8, ZiGloss> byCharacter, Map<String, SoundGloss> byPair,
                    List<ZiGloss> ordered,
                    List<GlossSource> sources, List<ZiGloss> shadowed) {
        this.byCharacter = byCharacter;
        this.ordered = ordered;
        this.byPair = byPair;
        this.sources = sources;
        this.shadowed = shadowed;
    }

    /** Composes the sources given, earlier ones winning on a repeated character. */
    public static Glosses of(GlossSource... sources) {
        Objects.requireNonNull(sources, "sources");
        var byCharacter = new LinkedHashMap<ZiCharUTF8, ZiGloss>();
        var shadowed = new ArrayList<ZiGloss>();
        for (GlossSource source : sources) {
            for (ZiGloss entry : source.entries()) {
                ZiGloss prior = byCharacter.putIfAbsent(entry.zi(), entry);
                if (prior != null) shadowed.add(entry);
            }
        }
        var byPair = new LinkedHashMap<String, SoundGloss>();
        for (ZiGloss entry : byCharacter.values()) {
            for (SoundGloss sound : entry.sounds()) byPair.put(sound.key(), sound);
        }
        return new Glosses(Map.copyOf(byCharacter), Map.copyOf(byPair),
                List.copyOf(byCharacter.values()),
                List.of(sources), List.copyOf(shadowed));
    }

    /** An empty registry — what a build with no gloss module gets. */
    public static Glosses none() {
        return new Glosses(Map.of(), Map.of(), List.of(), List.of(), List.of());
    }

    /** Everything known about a character, however it is read. */
    public Optional<ZiGloss> find(ZiCharUTF8 zi) {
        return Optional.ofNullable(byCharacter.get(Objects.requireNonNull(zi, "zi")));
    }

    /** What a character means read this way. */
    public Optional<SoundGloss> find(ZiCharUTF8 zi, PinyinSyllable reading) {
        Objects.requireNonNull(zi, "zi");
        Objects.requireNonNull(reading, "reading");
        return find(zi.value().codePointAt(0) + ":" + reading.numbered());
    }

    /**
     * What a {@code codePoint:reading} key means.
     *
     * <p>The key the known set and the article census already use, so a caller
     * holding one of those asks without taking the pair apart.</p>
     */
    public Optional<SoundGloss> find(String key) {
        return Optional.ofNullable(byPair.get(key));
    }

    /** Every character glossed, in the order its sources were composed. */
    /**
     * Every character glossed, <b>in the order it was authored</b>.
     *
     * <p>Not {@code byCharacter.values()}. The map is built insertion-ordered
     * and then frozen with {@code Map.copyOf}, whose iteration order is
     * explicitly unspecified - so reading the values back gives an arbitrary
     * order that looks stable until the data changes. Order is authored
     * information here, the same way a sense's rank is.</p>
     */
    public List<ZiGloss> all() { return ordered; }

    /** How many characters are glossed. */
    public int characterCount() { return byCharacter.size(); }

    /** How many (character, reading) pairs are glossed. */
    public int pairCount() { return byPair.size(); }

    /** The sources this registry was composed from, in precedence order. */
    public List<GlossSource> sources() { return sources; }

    /** Entries a later source supplied for a character an earlier one already had. */
    public List<ZiGloss> shadowed() { return shadowed; }
}
