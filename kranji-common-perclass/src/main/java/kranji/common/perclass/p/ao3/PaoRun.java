// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.p.ao3;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.b.ao1.BaoWrap;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.body.BodyParts;
import kranji.singular.body.BodyParts.Zu;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record PaoRun() implements ComposedZiT, LeftRightT<Zu, BaoWrap> {

    public static final PaoRun INSTANCE = new PaoRun();

    @Override public Zu left() { return BodyParts.ZU; }
    @Override public BaoWrap right() { return BaoWrap.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u8DD1"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.P, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.THIRD);
    }

    @Override public int strokes() { return 12; }
    @Override public int radicalNo() { return 157; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BodyParts.ZU, BaoWrap.INSTANCE);
    }
}
