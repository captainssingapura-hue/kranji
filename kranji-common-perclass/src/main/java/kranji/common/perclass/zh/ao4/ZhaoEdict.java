// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.ao4;

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
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhaoEdict() implements ComposedZiT, LeftRightT<YanZiPang, ZhaoSummon> {

    public static final ZhaoEdict INSTANCE = new ZhaoEdict();

    @Override public YanZiPang left() { return BasicComponents.YAN_ZI_PANG; }
    @Override public ZhaoSummon right() { return ZhaoSummon.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u8BCF"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.FOURTH);
    }

    @Override public int strokes() { return 7; }
    @Override public int radicalNo() { return 149; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.YAN_ZI_PANG, ZhaoSummon.INSTANCE);
    }
}
