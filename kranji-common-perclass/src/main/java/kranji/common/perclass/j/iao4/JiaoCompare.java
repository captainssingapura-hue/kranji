// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.j.iao4;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.actions.ActionsAndStates.Jiao;
import kranji.singular.tools.ToolsAndVessels;
import kranji.singular.tools.ToolsAndVessels.Che;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record JiaoCompare() implements ComposedZiT, LeftRightT<Che, Jiao> {

    public static final JiaoCompare INSTANCE = new JiaoCompare();

    @Override public Che left() { return ToolsAndVessels.CHE; }
    @Override public Jiao right() { return ActionsAndStates.JIAO; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u8F83"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.J, new Final(Head.I, Body.A, Tail.VOWEL_U), Tone.FOURTH);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 159; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(ToolsAndVessels.CHE, ActionsAndStates.JIAO);
    }
}
