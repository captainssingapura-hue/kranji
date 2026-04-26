// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.g.u1;

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
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.concepts.AbstractConcepts.Gu;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record GuBuy() implements ComposedZiT, LeftRightT<SanDianShui, Gu> {

    public static final GuBuy INSTANCE = new GuBuy();

    @Override public SanDianShui left() { return BasicComponents.SAN_DIAN_SHUI; }
    @Override public Gu right() { return AbstractConcepts.GU; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6CBD"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.G, new Final(Head.U, Body.U, Tail.NONE), Tone.FIRST);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 85; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SAN_DIAN_SHUI, AbstractConcepts.GU);
    }
}
