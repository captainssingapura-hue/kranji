// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.u3;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.HandFamily.TiShouPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.concepts.AbstractConcepts.Zhu_Lord;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhuLeanOn() implements ComposedZiT, LeftRightT<TiShouPang, Zhu_Lord> {

    public static final ZhuLeanOn INSTANCE = new ZhuLeanOn();

    @Override public TiShouPang left() { return BasicComponents.TI_SHOU_PANG; }
    @Override public Zhu_Lord right() { return AbstractConcepts.ZHU_LORD; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u62C4"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.U, Body.U, Tail.NONE), Tone.THIRD);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 64; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.TI_SHOU_PANG, AbstractConcepts.ZHU_LORD);
    }
}
