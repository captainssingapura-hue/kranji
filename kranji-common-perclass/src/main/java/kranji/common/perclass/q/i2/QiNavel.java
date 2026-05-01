// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.q.i2;

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
import kranji.singular.unsure.UnsureSingulars.Qi_Even;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record QiNavel() implements ComposedZiT, LeftRightT<Yue, Qi_Even> {

    public static final QiNavel INSTANCE = new QiNavel();

    @Override public Yue left() { return NatureElements.YUE; }
    @Override public Qi_Even right() { return UnsureSingulars.QI_EVEN; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u8110"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.Q, new Final(Head.OPEN, Body.I, Tail.NONE), Tone.SECOND);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 130; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YUE, UnsureSingulars.QI_EVEN);
    }
}
