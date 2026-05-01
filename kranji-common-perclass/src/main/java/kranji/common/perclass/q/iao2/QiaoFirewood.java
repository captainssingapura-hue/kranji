// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.q.iao2;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.WoodFamily.Mu;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Jiao_Scorched;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record QiaoFirewood() implements ComposedZiT, LeftRightT<Mu, Jiao_Scorched> {

    public static final QiaoFirewood INSTANCE = new QiaoFirewood();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Jiao_Scorched right() { return UnsureSingulars.JIAO_SCORCHED; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6A35"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.Q, new Final(Head.I, Body.A, Tail.VOWEL_U), Tone.SECOND);
    }

    @Override public int strokes() { return 16; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, UnsureSingulars.JIAO_SCORCHED);
    }
}
