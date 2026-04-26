package kranji.zi;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.PinyinSyllable;

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
     * Structured pinyin reading, derived from {@link #pinyinText()}.
     *
     * <p>Default: parse the display-form string using
     * {@link PinyinSyllable#parse(String)}. Concrete records have two
     * equally valid options:</p>
     * <ul>
     *   <li>Override {@link #pinyinText()} with the diacritic string
     *       ({@code "shuǐ"}) and let this default produce the structured
     *       form. Suited to hand-written code where the string is the
     *       natural source.</li>
     *   <li>Override this method directly with a constructed
     *       {@link PinyinSyllable} literal. Suited to code-generation,
     *       where the structured form is what the generator has in hand
     *       and {@link #pinyinText()} can itself be derived on the fly.</li>
     * </ul>
     *
     * <p>The old {@code List.of()} default is gone — an empty pinyin list
     * was never a meaningful answer for a real character; now the absence
     * of both overrides fails loudly at parse time instead of silently
     * returning nothing.</p>
     */
    @Override default PinyinSyllable pinyin() {
        return PinyinSyllable.parse(pinyinText());
    }

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

    /**
     * <em>Provisional / unverified</em> marker.
     *
     * <p>Defaults to {@code false}. Override to {@code true} in records
     * that are registered as singular only as a pragmatic placeholder —
     * typically obscure phonetic components (e.g. 爰, 尧, 屰, 皀) whose
     * internal sub-structure has not yet been authored, but which are
     * needed <em>now</em> as building blocks for higher-depth composed
     * characters.</p>
     *
     * <p>An entry with {@code unsure() == true} should be treated as
     * "atomic for now, revisit later" — its glyph, meaning, pinyin and
     * stroke count are best-effort, and its claim to standalone
     * singular status may not survive future analysis.</p>
     */
    default boolean unsure() { return false; }
}
