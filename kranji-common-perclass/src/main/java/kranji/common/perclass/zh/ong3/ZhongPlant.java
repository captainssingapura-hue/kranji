// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.ong3;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.singular.plants.PlantsAndAgriculture.He;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.space.SpaceAndDirection.Zhong;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhongPlant() implements ComposedZiT, LeftRightT<He, Zhong> {

    public static final ZhongPlant INSTANCE = new ZhongPlant();

    @Override public He left() { return PlantsAndAgriculture.HE; }
    @Override public Zhong right() { return SpaceAndDirection.ZHONG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u79CD"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.OPEN, Body.O, Tail.NG), Tone.THIRD);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 115; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(PlantsAndAgriculture.HE, SpaceAndDirection.ZHONG);
    }
}
