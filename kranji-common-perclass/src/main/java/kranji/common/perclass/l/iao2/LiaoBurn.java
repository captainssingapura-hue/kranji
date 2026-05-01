// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.iao2;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.nature.NatureElements;
import kranji.singular.nature.NatureElements.Huo;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Liao_Torch;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LiaoBurn() implements ComposedZiT, LeftRightT<Huo, Liao_Torch> {

    public static final LiaoBurn INSTANCE = new LiaoBurn();

    @Override public Huo left() { return NatureElements.HUO; }
    @Override public Liao_Torch right() { return UnsureSingulars.LIAO_TORCH; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u71CE"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.I, Body.A, Tail.VOWEL_U), Tone.SECOND);
    }

    @Override public int strokes() { return 16; }
    @Override public int radicalNo() { return 86; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.HUO, UnsureSingulars.LIAO_TORCH);
    }
}
