// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.ong1;

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
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.space.SpaceAndDirection.Zhong;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhongClock() implements ComposedZiT, LeftRightT<JinZiPang, Zhong> {

    public static final ZhongClock INSTANCE = new ZhongClock();

    @Override public JinZiPang left() { return BasicComponents.JIN_ZI_PANG; }
    @Override public Zhong right() { return SpaceAndDirection.ZHONG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u949F"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.OPEN, Body.O, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 167; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.JIN_ZI_PANG, SpaceAndDirection.ZHONG);
    }
}
