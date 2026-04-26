// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.ang3;

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
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.space.SpaceAndDirection.Chang;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhangSwell() implements ComposedZiT, LeftRightT<SanDianShui, Chang> {

    public static final ZhangSwell INSTANCE = new ZhangSwell();

    @Override public SanDianShui left() { return BasicComponents.SAN_DIAN_SHUI; }
    @Override public Chang right() { return SpaceAndDirection.CHANG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6DA8"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.OPEN, Body.A, Tail.NG), Tone.THIRD);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 85; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SAN_DIAN_SHUI, SpaceAndDirection.CHANG);
    }
}
