// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.p.ao2;

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
import kranji.singular.body.BodyParts.Kou;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record PaoRoar() implements ComposedZiT, LeftRightT<Kou, BaoWrap> {

    public static final PaoRoar INSTANCE = new PaoRoar();

    @Override public Kou left() { return BodyParts.KOU; }
    @Override public BaoWrap right() { return BaoWrap.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u5486"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.P, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.SECOND);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 30; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BodyParts.KOU, BaoWrap.INSTANCE);
    }
}
