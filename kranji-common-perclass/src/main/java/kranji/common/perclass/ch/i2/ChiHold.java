// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.ch.i2;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.s.i4.SiTemple;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.HandFamily.TiShouPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ChiHold() implements ComposedZiT, LeftRightT<TiShouPang, SiTemple> {

    public static final ChiHold INSTANCE = new ChiHold();

    @Override public TiShouPang left() { return BasicComponents.TI_SHOU_PANG; }
    @Override public SiTemple right() { return SiTemple.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6301"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.CH, new Final(Head.OPEN, Body.NULL, Tail.NONE), Tone.SECOND);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 64; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.TI_SHOU_PANG, SiTemple.INSTANCE);
    }
}
