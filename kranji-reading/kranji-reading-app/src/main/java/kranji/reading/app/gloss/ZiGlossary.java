package kranji.reading.app.gloss;

import kranji.pinyin.PinyinSyllable;
import kranji.simple.gloss.Glosses;
import kranji.simple.gloss.Meaning;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiCollections;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiCharUTF8;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * What a reading <em>means</em>, for the app that already says how it sounds.
 *
 * <h2>The seam</h2>
 *
 * <p>The reader has always been able to answer "how is this character read
 * here" — the corpus gives it readings and the census gives it counts. It could
 * never answer "and what does it mean", which is the question a child asks
 * second and the one that makes the first worth answering.</p>
 *
 * <p>The collections are found by {@link ZiCollections}, not named: nothing in
 * this package mentions a particular body of glosses, and swapping which one is
 * on the classpath is a deployment decision rather than an edit.</p>
 *
 * <h2>Absence is a state, not a failure</h2>
 *
 * <p>{@link ZiCollections#glosses()} throws when nothing is on the classpath,
 * which is right for the studio — a workbench with no data to work on is a
 * broken workbench. It is wrong here. Meanings <b>enhance</b> the reader; a
 * deployment that ships without them should still open an article and still
 * mark a reading known, with the meaning simply absent.</p>
 *
 * <p>So this holds an empty registry rather than propagating the failure, and
 * every lookup answers "nothing known" the same way it answers for a character
 * no collection happens to cover. The two are genuinely the same case to a
 * caller, and the caller has to handle the second regardless.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the collections.</p>
 */
public final class ZiGlossary {

    private ZiGlossary() {}

    /**
     * The composed collections, or an empty registry.
     *
     * <p>Resolved once at class-init. The SPI is scanned by {@code
     * ServiceLoader} on first touch, and the answer cannot change while the
     * process runs — a lazy holder would buy nothing and would make one
     * unlucky request pay for the scan.</p>
     */
    private static final Glosses GLOSSES = compose();

    private static Glosses compose() {
        try {
            return ZiCollections.glosses();
        } catch (RuntimeException noCollections) {
            // Nothing on the classpath. The reader is still a reader.
            return Glosses.of();
        }
    }

    /** Whether any collection was found — for a status line, not for control. */
    public static boolean isEmpty() { return ZiCollections.discovered().isEmpty(); }

    /** How many characters can be explained at all. */
    public static int characterCount() { return GLOSSES.characterCount(); }

    /**
     * The gloss for one (character, reading), if a collection has it.
     *
     * <p>Keyed on the pair, because that is the grain meaning lives at: 地 di4
     * is earth and 地 de0 is a particle, and answering for the character alone
     * would have to pick one and be wrong half the time.</p>
     */
    public static Optional<SoundGloss> find(int codePoint, String reading) {
        if (reading == null || reading.isBlank()) return Optional.empty();
        PinyinSyllable syllable;
        try {
            syllable = PinyinSyllable.parseCanonical(reading);
        } catch (RuntimeException notCanonical) {
            // A display form arriving from a URL or an old stored key. Not a
            // fault to raise - just a reading nothing is filed under.
            return Optional.empty();
        }
        try {
            return GLOSSES.find(new ZiCharUTF8(codePoint)).flatMap(g -> g.at(syllable));
        } catch (IllegalArgumentException notHan) {
            return Optional.empty();
        }
    }

    /**
     * Every meaning of a reading, most central first, or an empty list.
     *
     * <p>Ordered by the priority the collection authored, so a caller showing
     * one shows the right one and a caller showing all shows them in the order
     * somebody meant.</p>
     */
    public static List<String> meaningsOf(int codePoint, String reading) {
        return find(codePoint, reading)
                .map(sound -> sound.orderedMeanings().stream().map(Meaning::text).toList())
                .orElse(List.of());
    }

    /**
     * The one meaning to show when only one fits, or {@code ""}.
     *
     * <p>Empty rather than a placeholder: what to say in place of a meaning is
     * a question for the view, and a model that answers it has decided
     * something that belongs on screen.</p>
     */
    public static String primaryOf(int codePoint, String reading) {
        return find(codePoint, reading).map(sound -> sound.primary().text()).orElse("");
    }

    /**
     * Every glossed pair, as {@code codepoint:reading} to its primary meaning.
     *
     * <p>The whole thing at once, for a view that needs a meaning per row and
     * would otherwise ask per row. The same bargain the census makes: it is
     * small, it depends on nothing about the reader, and one fetch beats a
     * few hundred.</p>
     */
    public static Map<String, String> primaries() {
        var out = new LinkedHashMap<String, String>();
        for (ZiGloss gloss : GLOSSES.all()) {
            for (SoundGloss sound : gloss.sounds()) {
                out.put(sound.key(), sound.primary().text());
            }
        }
        // Not Map.copyOf: its iteration order is unspecified, and this map is
        // written straight out as a cacheable module. An order that shuffled
        // between runs would make every build emit a different file for
        // identical data.
        return Collections.unmodifiableMap(out);
    }
}
