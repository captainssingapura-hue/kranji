// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.b.ang4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.MetalFamily.JinZiPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Pang_Side;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record BangPound() implements ComposedZiT, LeftRightT<JinZiPang, Pang_Side> {

    public static final BangPound INSTANCE = new BangPound();

    @Override public JinZiPang left() { return BasicComponents.JIN_ZI_PANG; }
    @Override public Pang_Side right() { return UnsureSingulars.PANG_SIDE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u9551"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.B, new Final(Head.OPEN, Body.A, Tail.NG), Tone.FOURTH);
    }

    @Override public int strokes() { return 15; }
    @Override public int radicalNo() { return 167; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.JIN_ZI_PANG, UnsureSingulars.PANG_SIDE);
    }
}
