// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.k.ua4;

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
import kranji.singular.unsure.UnsureSingulars.Kua_Boast;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record KuaHip() implements ComposedZiT, LeftRightT<Yue, Kua_Boast> {

    public static final KuaHip INSTANCE = new KuaHip();

    @Override public Yue left() { return NatureElements.YUE; }
    @Override public Kua_Boast right() { return UnsureSingulars.KUA_BOAST; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u80EF"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.K, new Final(Head.U, Body.A, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 130; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YUE, UnsureSingulars.KUA_BOAST);
    }
}
