// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.x.iang4;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.actions.ActionsAndStates.Gong_Work;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.space.SpaceAndDirection.Ye_Page;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record XiangNeck() implements ComposedZiT, LeftRightT<Gong_Work, Ye_Page> {

    public static final XiangNeck INSTANCE = new XiangNeck();

    @Override public Gong_Work left() { return ActionsAndStates.GONG_WORK; }
    @Override public Ye_Page right() { return SpaceAndDirection.YE_PAGE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u9879"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.X, new Final(Head.I, Body.A, Tail.NG), Tone.FOURTH);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 181; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(SpaceAndDirection.YE_PAGE, ActionsAndStates.GONG_WORK);
    }
}
