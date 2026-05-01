// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.an4;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.actions.ActionsAndStates.Li_Stand;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhanStand() implements ComposedZiT, LeftRightT<Li_Stand, ZhanOccupy> {

    public static final ZhanStand INSTANCE = new ZhanStand();

    @Override public Li_Stand left() { return ActionsAndStates.LI_STAND; }
    @Override public ZhanOccupy right() { return ZhanOccupy.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7AD9"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.OPEN, Body.A, Tail.N), Tone.FOURTH);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 117; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(ActionsAndStates.LI_STAND, ZhanOccupy.INSTANCE);
    }
}
