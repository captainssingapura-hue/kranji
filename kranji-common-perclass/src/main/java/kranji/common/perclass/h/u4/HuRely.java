// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.h.u4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.HeartFamily.ShuXinPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.concepts.AbstractConcepts.Gu;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record HuRely() implements ComposedZiT, LeftRightT<ShuXinPang, Gu> {

    public static final HuRely INSTANCE = new HuRely();

    @Override public ShuXinPang left() { return BasicComponents.SHU_XIN_PANG; }
    @Override public Gu right() { return AbstractConcepts.GU; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6019"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.H, new Final(Head.U, Body.U, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 61; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SHU_XIN_PANG, AbstractConcepts.GU);
    }
}
