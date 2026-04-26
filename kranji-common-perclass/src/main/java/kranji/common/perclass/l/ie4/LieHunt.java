// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.ie4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.AnimalFamily.FanQuanPang;
import kranji.component.basic.BasicComponents;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Xi_Former;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LieHunt() implements ComposedZiT, LeftRightT<FanQuanPang, Xi_Former> {

    public static final LieHunt INSTANCE = new LieHunt();

    @Override public FanQuanPang left() { return BasicComponents.FAN_QUAN_PANG; }
    @Override public Xi_Former right() { return UnsureSingulars.XI_FORMER; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u730E"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.I, Body.E_CARON, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 11; }
    @Override public int radicalNo() { return 94; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.FAN_QUAN_PANG, UnsureSingulars.XI_FORMER);
    }
}
