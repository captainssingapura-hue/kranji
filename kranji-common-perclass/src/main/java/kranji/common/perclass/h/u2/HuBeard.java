// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.h.u2;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.concepts.AbstractConcepts.Gu;
import kranji.singular.nature.NatureElements;
import kranji.singular.nature.NatureElements.Yue;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record HuBeard() implements ComposedZiT, LeftRightT<Gu, Yue> {

    public static final HuBeard INSTANCE = new HuBeard();

    @Override public Gu left() { return AbstractConcepts.GU; }
    @Override public Yue right() { return NatureElements.YUE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u80E1"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.H, new Final(Head.U, Body.U, Tail.NONE), Tone.SECOND);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 130; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YUE, AbstractConcepts.GU);
    }
}
