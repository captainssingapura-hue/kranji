// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.x.ing1;

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
import kranji.singular.nature.NatureElements.Ri;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.TopBottomT;

public record XingStar() implements ComposedZiT, TopBottomT<Ri, Sheng> {

    public static final XingStar INSTANCE = new XingStar();

    @Override public Ri top() { return NatureElements.RI; }
    @Override public Sheng bottom() { return ActionsAndStates.SHENG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u661F"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.X, new Final(Head.OPEN, Body.I, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 72; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.RI, ActionsAndStates.SHENG);
    }
}
