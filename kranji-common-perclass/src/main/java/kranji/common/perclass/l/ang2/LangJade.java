// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.ang2;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.materials.Materials;
import kranji.singular.materials.Materials.Yu_Jade;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.radicals.RadicalComponents.Liang_Good;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LangJade() implements ComposedZiT, LeftRightT<Yu_Jade, Liang_Good> {

    public static final LangJade INSTANCE = new LangJade();

    @Override public Yu_Jade left() { return Materials.YU_JADE; }
    @Override public Liang_Good right() { return RadicalComponents.LIANG_GOOD; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7405"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.OPEN, Body.A, Tail.NG), Tone.SECOND);
    }

    @Override public int strokes() { return 11; }
    @Override public int radicalNo() { return 96; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(Materials.YU_JADE, RadicalComponents.LIANG_GOOD);
    }
}
