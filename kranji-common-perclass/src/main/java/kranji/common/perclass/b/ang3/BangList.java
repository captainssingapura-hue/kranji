// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.b.ang3;

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
import kranji.singular.unsure.UnsureSingulars.Pang_Side;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record BangList() implements ComposedZiT, LeftRightT<Mu, Pang_Side> {

    public static final BangList INSTANCE = new BangList();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Pang_Side right() { return UnsureSingulars.PANG_SIDE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u699C"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.B, new Final(Head.OPEN, Body.A, Tail.NG), Tone.THIRD);
    }

    @Override public int strokes() { return 14; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, UnsureSingulars.PANG_SIDE);
    }
}
