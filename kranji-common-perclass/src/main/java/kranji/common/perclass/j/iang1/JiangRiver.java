// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.j.iang1;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.WaterFamily.SanDianShui;
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

public record JiangRiver() implements ComposedZiT, LeftRightT<SanDianShui, Gong_Work> {

    public static final JiangRiver INSTANCE = new JiangRiver();

    @Override public SanDianShui left() { return BasicComponents.SAN_DIAN_SHUI; }
    @Override public Gong_Work right() { return ActionsAndStates.GONG_WORK; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6C5F"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.J, new Final(Head.I, Body.A, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 6; }
    @Override public int radicalNo() { return 85; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SAN_DIAN_SHUI, ActionsAndStates.GONG_WORK);
    }
}
