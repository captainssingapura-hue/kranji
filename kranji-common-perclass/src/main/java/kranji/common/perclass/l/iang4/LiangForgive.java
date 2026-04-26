// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.iang4;

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
import kranji.singular.structures.Structures;
import kranji.singular.structures.Structures.Jing_Capital;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LiangForgive() implements ComposedZiT, LeftRightT<YanZiPang, Jing_Capital> {

    public static final LiangForgive INSTANCE = new LiangForgive();

    @Override public YanZiPang left() { return BasicComponents.YAN_ZI_PANG; }
    @Override public Jing_Capital right() { return Structures.JING_CAPITAL; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u8C05"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.I, Body.A, Tail.NG), Tone.FOURTH);
    }

    @Override public int strokes() { return 11; }
    @Override public int radicalNo() { return 149; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.YAN_ZI_PANG, Structures.JING_CAPITAL);
    }
}
