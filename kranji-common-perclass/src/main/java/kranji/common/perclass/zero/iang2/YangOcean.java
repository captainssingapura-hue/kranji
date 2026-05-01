// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zero.iang2;

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
import kranji.singular.animals.Animals;
import kranji.singular.animals.Animals.Yang;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record YangOcean() implements ComposedZiT, LeftRightT<SanDianShui, Yang> {

    public static final YangOcean INSTANCE = new YangOcean();

    @Override public SanDianShui left() { return BasicComponents.SAN_DIAN_SHUI; }
    @Override public Yang right() { return Animals.YANG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6D0B"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZERO, new Final(Head.I, Body.A, Tail.NG), Tone.SECOND);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 85; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SAN_DIAN_SHUI, Animals.YANG);
    }
}
