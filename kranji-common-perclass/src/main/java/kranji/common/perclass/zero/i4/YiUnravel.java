// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zero.i4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.SilkFamily.JiaoSiPang;
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

public record YiUnravel() implements ComposedZiT, LeftRightT<JiaoSiPang, Yi_HuntingNet> {

    public static final YiUnravel INSTANCE = new YiUnravel();

    @Override public JiaoSiPang left() { return BasicComponents.JIAO_SI_PANG; }
    @Override public Yi_HuntingNet right() { return UnsureSingulars.YI_HUNTINGNET; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7ECE"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZERO, new Final(Head.OPEN, Body.I, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 120; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.JIAO_SI_PANG, UnsureSingulars.YI_HUNTINGNET);
    }
}
