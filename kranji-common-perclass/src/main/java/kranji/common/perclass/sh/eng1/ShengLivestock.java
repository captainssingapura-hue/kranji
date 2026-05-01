// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.sh.eng1;

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
import kranji.singular.animals.Animals;
import kranji.singular.animals.Animals.Niu;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ShengLivestock() implements ComposedZiT, LeftRightT<Niu, Sheng> {

    public static final ShengLivestock INSTANCE = new ShengLivestock();

    @Override public Niu left() { return Animals.NIU; }
    @Override public Sheng right() { return ActionsAndStates.SHENG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7272"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.SH, new Final(Head.OPEN, Body.E, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 93; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(Animals.NIU, ActionsAndStates.SHENG);
    }
}
