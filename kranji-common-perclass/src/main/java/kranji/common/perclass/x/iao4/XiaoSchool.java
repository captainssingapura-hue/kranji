// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.x.iao4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.WoodFamily.Mu;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.actions.ActionsAndStates.Jiao;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record XiaoSchool() implements ComposedZiT, LeftRightT<Mu, Jiao> {

    public static final XiaoSchool INSTANCE = new XiaoSchool();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Jiao right() { return ActionsAndStates.JIAO; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6821"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.X, new Final(Head.I, Body.A, Tail.VOWEL_U), Tone.FOURTH);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, ActionsAndStates.JIAO);
    }
}
