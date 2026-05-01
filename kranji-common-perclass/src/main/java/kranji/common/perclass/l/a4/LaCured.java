// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.a4;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.nature.NatureElements;
import kranji.singular.nature.NatureElements.Yue;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Xi_Former;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LaCured() implements ComposedZiT, LeftRightT<Yue, Xi_Former> {

    public static final LaCured INSTANCE = new LaCured();

    @Override public Yue left() { return NatureElements.YUE; }
    @Override public Xi_Former right() { return UnsureSingulars.XI_FORMER; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u814A"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.OPEN, Body.A, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 12; }
    @Override public int radicalNo() { return 130; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YUE, UnsureSingulars.XI_FORMER);
    }
}
