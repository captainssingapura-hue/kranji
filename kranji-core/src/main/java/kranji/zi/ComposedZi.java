package kranji.zi;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.PinyinSyllable;

import java.util.List;

/**
 * A composed Chinese character — both a {@link Zi} (semantic identity)
 * and a composition of sub-components.
 *
 * <p>Unlike {@link SingularZi} which is its own structure, a ComposedZi
 * delegates its structural decomposition to an inner {@link ComposedBlock}.</p>
 *
 * @param ziChar      the glyph wrapped as {@link ZiChar} for source readability
 * @param pinyin      Mandarin reading(s)
 * @param strokes     total stroke count
 * @param radicalNo   Kangxi radical number (1–214)
 * @param meaning     English semantic meaning
 * @param composition the structural decomposition (e.g. LeftRight, TopBottom)
 * @param etymology   etymological classification (六书)
 */
public record ComposedZi(
        ZiChar ziChar,
        List<PinyinSyllable> pinyin,
        int strokes,
        int radicalNo,
        String meaning,
        ComposedBlock composition,
        EtymologicalCategory etymology
) implements Zi {

    @Override
    public String character() { return ziChar.value(); }

    @Override
    public BlockStructure structure() { return composition; }
}
