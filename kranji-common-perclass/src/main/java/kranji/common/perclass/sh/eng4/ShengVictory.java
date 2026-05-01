// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.sh.eng4;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.actions.ActionsAndStates.Sheng;
import kranji.singular.nature.NatureElements;
import kranji.singular.nature.NatureElements.Yue;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ShengVictory() implements ComposedZiT, LeftRightT<Yue, Sheng> {

    public static final ShengVictory INSTANCE = new ShengVictory();

    @Override public Yue left() { return NatureElements.YUE; }
    @Override public Sheng right() { return ActionsAndStates.SHENG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u80DC"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.SH, new Final(Head.OPEN, Body.E, Tail.NG), Tone.FOURTH);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 130; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YUE, ActionsAndStates.SHENG);
    }
}
