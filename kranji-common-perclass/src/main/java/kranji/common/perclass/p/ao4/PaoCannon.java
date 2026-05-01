// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.p.ao4;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.b.ao1.BaoWrap;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.nature.NatureElements;
import kranji.singular.nature.NatureElements.Huo;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record PaoCannon() implements ComposedZiT, LeftRightT<Huo, BaoWrap> {

    public static final PaoCannon INSTANCE = new PaoCannon();

    @Override public Huo left() { return NatureElements.HUO; }
    @Override public BaoWrap right() { return BaoWrap.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u70AE"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.P, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.FOURTH);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 86; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.HUO, BaoWrap.INSTANCE);
    }
}
