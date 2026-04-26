// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.b.ang4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.SpeechFamily.YanZiPang;
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

public record BangSlander() implements ComposedZiT, LeftRightT<YanZiPang, Pang_Side> {

    public static final BangSlander INSTANCE = new BangSlander();

    @Override public YanZiPang left() { return BasicComponents.YAN_ZI_PANG; }
    @Override public Pang_Side right() { return UnsureSingulars.PANG_SIDE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u8C24"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.B, new Final(Head.OPEN, Body.A, Tail.NG), Tone.FOURTH);
    }

    @Override public int strokes() { return 12; }
    @Override public int radicalNo() { return 149; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.YAN_ZI_PANG, UnsureSingulars.PANG_SIDE);
    }
}
