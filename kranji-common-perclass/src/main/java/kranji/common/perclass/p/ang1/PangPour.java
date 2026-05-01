// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.p.ang1;

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
import kranji.singular.unsure.UnsureSingulars.Pang_Side;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record PangPour() implements ComposedZiT, LeftRightT<SanDianShui, Pang_Side> {

    public static final PangPour INSTANCE = new PangPour();

    @Override public SanDianShui left() { return BasicComponents.SAN_DIAN_SHUI; }
    @Override public Pang_Side right() { return UnsureSingulars.PANG_SIDE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6EC2"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.P, new Final(Head.OPEN, Body.A, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 13; }
    @Override public int radicalNo() { return 85; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SAN_DIAN_SHUI, UnsureSingulars.PANG_SIDE);
    }
}
