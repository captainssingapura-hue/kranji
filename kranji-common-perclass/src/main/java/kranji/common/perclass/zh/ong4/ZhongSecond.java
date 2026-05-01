// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.ong4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.PersonFamily.DanRenPang;
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

public record ZhongSecond() implements ComposedZiT, LeftRightT<DanRenPang, Zhong> {

    public static final ZhongSecond INSTANCE = new ZhongSecond();

    @Override public DanRenPang left() { return BasicComponents.DAN_REN_PANG; }
    @Override public Zhong right() { return SpaceAndDirection.ZHONG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u4EF2"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.OPEN, Body.O, Tail.NG), Tone.FOURTH);
    }

    @Override public int strokes() { return 6; }
    @Override public int radicalNo() { return 9; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.DAN_REN_PANG, SpaceAndDirection.ZHONG);
    }
}
