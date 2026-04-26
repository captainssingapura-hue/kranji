// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.sh.ao4;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.zh.ao4.ZhaoSummon;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.EarFamily.ErPang;
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

public record ShaoCity() implements ComposedZiT, LeftRightT<ZhaoSummon, ErPang> {

    public static final ShaoCity INSTANCE = new ShaoCity();

    @Override public ZhaoSummon left() { return ZhaoSummon.INSTANCE; }
    @Override public ErPang right() { return BasicComponents.ER_PANG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u90B5"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.SH, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.FOURTH);
    }

    @Override public int strokes() { return 7; }
    @Override public int radicalNo() { return 163; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.ER_PANG, ZhaoSummon.INSTANCE);
    }
}
