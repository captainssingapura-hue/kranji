// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.q.iao2;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.PersonFamily.DanRenPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Qiao_Tall;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record QiaoOverseas() implements ComposedZiT, LeftRightT<DanRenPang, Qiao_Tall> {

    public static final QiaoOverseas INSTANCE = new QiaoOverseas();

    @Override public DanRenPang left() { return BasicComponents.DAN_REN_PANG; }
    @Override public Qiao_Tall right() { return UnsureSingulars.QIAO_TALL; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u4FA8"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.Q, new Final(Head.I, Body.A, Tail.VOWEL_U), Tone.SECOND);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 9; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.DAN_REN_PANG, UnsureSingulars.QIAO_TALL);
    }
}
