// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zero.i4;

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
import kranji.singular.unsure.UnsureSingulars.Yi_HuntingNet;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record YiTranslate() implements ComposedZiT, LeftRightT<YanZiPang, Yi_HuntingNet> {

    public static final YiTranslate INSTANCE = new YiTranslate();

    @Override public YanZiPang left() { return BasicComponents.YAN_ZI_PANG; }
    @Override public Yi_HuntingNet right() { return UnsureSingulars.YI_HUNTINGNET; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u8BD1"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZERO, new Final(Head.OPEN, Body.I, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 7; }
    @Override public int radicalNo() { return 149; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.YAN_ZI_PANG, UnsureSingulars.YI_HUNTINGNET);
    }
}
