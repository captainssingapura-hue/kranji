// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.h.ong2;

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
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.actions.ActionsAndStates.Gong_Work;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record HongRed() implements ComposedZiT, LeftRightT<JiaoSiPang, Gong_Work> {

    public static final HongRed INSTANCE = new HongRed();

    @Override public JiaoSiPang left() { return BasicComponents.JIAO_SI_PANG; }
    @Override public Gong_Work right() { return ActionsAndStates.GONG_WORK; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7EA2"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.H, new Final(Head.OPEN, Body.O, Tail.NG), Tone.SECOND);
    }

    @Override public int strokes() { return 6; }
    @Override public int radicalNo() { return 120; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.JIAO_SI_PANG, ActionsAndStates.GONG_WORK);
    }
}
