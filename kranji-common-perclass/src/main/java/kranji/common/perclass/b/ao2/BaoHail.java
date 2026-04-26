// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.b.ao2;

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
import kranji.singular.nature.NatureElements.Yu;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.TopBottomT;

public record BaoHail() implements ComposedZiT, TopBottomT<Yu, BaoWrap> {

    public static final BaoHail INSTANCE = new BaoHail();

    @Override public Yu top() { return NatureElements.YU; }
    @Override public BaoWrap bottom() { return BaoWrap.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u96F9"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.B, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.SECOND);
    }

    @Override public int strokes() { return 13; }
    @Override public int radicalNo() { return 173; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YU, BaoWrap.INSTANCE);
    }
}
