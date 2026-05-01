// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.b.ang3;

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
import kranji.singular.unsure.UnsureSingulars.Pang_Side;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record BangShoulder() implements ComposedZiT, LeftRightT<Yue, Pang_Side> {

    public static final BangShoulder INSTANCE = new BangShoulder();

    @Override public Yue left() { return NatureElements.YUE; }
    @Override public Pang_Side right() { return UnsureSingulars.PANG_SIDE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u8180"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.B, new Final(Head.OPEN, Body.A, Tail.NG), Tone.THIRD);
    }

    @Override public int strokes() { return 14; }
    @Override public int radicalNo() { return 130; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YUE, UnsureSingulars.PANG_SIDE);
    }
}
