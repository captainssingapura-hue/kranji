// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.u4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.MetalFamily.JinZiPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.concepts.AbstractConcepts.Shou_Long;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhuCast() implements ComposedZiT, LeftRightT<JinZiPang, Shou_Long> {

    public static final ZhuCast INSTANCE = new ZhuCast();

    @Override public JinZiPang left() { return BasicComponents.JIN_ZI_PANG; }
    @Override public Shou_Long right() { return AbstractConcepts.SHOU_LONG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u94F8"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.U, Body.U, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 12; }
    @Override public int radicalNo() { return 167; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.JIN_ZI_PANG, AbstractConcepts.SHOU_LONG);
    }
}
