// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.u4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.WaterFamily.SanDianShui;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Lu_Mound;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LuStrain() implements ComposedZiT, LeftRightT<SanDianShui, Lu_Mound> {

    public static final LuStrain INSTANCE = new LuStrain();

    @Override public SanDianShui left() { return BasicComponents.SAN_DIAN_SHUI; }
    @Override public Lu_Mound right() { return UnsureSingulars.LU_MOUND; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6F09"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.U, Body.U, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 14; }
    @Override public int radicalNo() { return 85; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SAN_DIAN_SHUI, UnsureSingulars.LU_MOUND);
    }
}
