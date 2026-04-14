package kranji.zi;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.PinyinSyllable;

import java.util.List;

/**
 * A standalone Chinese character that is both a {@link Zi} (semantic identity)
 * and a {@link SingularBlock} (atomic structural block).
 *
 * <p>Stateless singleton records implement this interface directly — each
 * record <em>is</em> the character, carrying glyph, pronunciation, meaning,
 * stroke count, and layout behavior intrinsically.</p>
 *
 * <p>For radical variants and component parts that are not standalone
 * characters, see {@link SingularPart}.</p>
 */
public interface SingularZi extends SingularBlock, Zi {

    // ── Zi bridge defaults ───────────────────────────────────────────

    /** Defaults to {@link #glyph()}. */
    @Override default String character() { return glyph(); }

    /** A SingularZi <em>is</em> its own structure. */
    @Override default BlockStructure structure() { return this; }

    /**
     * Kangxi radical number (1–214).
     * Defaults to 0 (unset) — override in concrete records.
     */
    @Override default int radicalNo() { return 0; }

    /**
     * Etymological classification.
     * Defaults to {@link EtymologicalCategory.Pictograph} — override as needed.
     */
    @Override default EtymologicalCategory etymology() {
        return new EtymologicalCategory.Pictograph();
    }

    /**
     * Structured pinyin readings.
     * Defaults to an empty list — override to provide parsed syllables.
     */
    @Override default List<PinyinSyllable> pinyin() { return List.of(); }

    /**
     * Total stroke count.
     * Re-declared to resolve the default-vs-abstract conflict between
     * {@link SingularBlock#strokes()} and {@link Zi#strokes()}.
     * Defaults to 0 — override in concrete records.
     */
    @Override default int strokes() { return 0; }

    /**
     * English semantic meaning.
     * Re-declared to resolve the default-vs-abstract conflict between
     * {@link SingularBlock#meaning()} and {@link Zi#meaning()}.
     * Defaults to empty string — override in concrete records.
     */
    @Override default String meaning() { return ""; }
}
