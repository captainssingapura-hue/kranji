package kranji.zi;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.PinyinSyllable;

/**
 * A composed Chinese character — both a {@link Zi} (semantic identity)
 * and a {@link ComposedBlock} (composed structural block with identity).
 *
 * <p>Because {@code ComposedZi} is itself a {@link BlockStructure}, it
 * can fill a slot directly in another character's composition without
 * needing a {@code .structure()} bridge:</p>
 *
 * <pre>
 * new LeftRight(BasicComponents.DAN_REN_PANG, ER_YOU_ARCHAIC)
 * </pre>
 *
 * <p>The inner {@link CompositionLayout} carries the spatial grammar
 * (one of {@code LeftRight}, {@code TopBottom}, etc.).</p>
 *
 * @param ziChar      the glyph wrapped as {@link ZiChar} for source readability
 * @param pinyin      the Mandarin reading — one syllable per character
 * @param strokes     total stroke count
 * @param radicalNo   Kangxi radical number (1–214)
 * @param meaning     English semantic meaning
 * @param composition the structural decomposition (one of the 11 layout variants)
 * @param etymology   etymological classification (六书)
 */
public record ComposedZi(
        ZiChar ziChar,
        PinyinSyllable pinyin,
        int strokes,
        int radicalNo,
        String meaning,
        CompositionLayout composition,
        EtymologicalCategory etymology
) implements ComposedBlock, Zi {

    @Override public String character() { return ziChar.value(); }

    @Override public String glyph()     { return ziChar.value(); }

    /** A ComposedZi <em>is</em> its own structure. */
    @Override public BlockStructure structure() { return this; }
}
