// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.z.e2;

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
import kranji.singular.unsure.UnsureSingulars.Yi_HuntingNet;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZeMarsh() implements ComposedZiT, LeftRightT<SanDianShui, Yi_HuntingNet> {

    public static final ZeMarsh INSTANCE = new ZeMarsh();

    @Override public SanDianShui left() { return BasicComponents.SAN_DIAN_SHUI; }
    @Override public Yi_HuntingNet right() { return UnsureSingulars.YI_HUNTINGNET; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6CFD"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.Z, new Final(Head.OPEN, Body.E, Tail.NONE), Tone.SECOND);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 85; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SAN_DIAN_SHUI, UnsureSingulars.YI_HUNTINGNET);
    }
}
