// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.sh.eng1;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.n.an2.NanMale;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.actions.ActionsAndStates.Sheng;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ShengNephew() implements ComposedZiT, LeftRightT<Sheng, NanMale> {

    public static final ShengNephew INSTANCE = new ShengNephew();

    @Override public Sheng left() { return ActionsAndStates.SHENG; }
    @Override public NanMale right() { return NanMale.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7525"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.SH, new Final(Head.OPEN, Body.E, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 12; }
    @Override public int radicalNo() { return 100; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NanMale.INSTANCE, ActionsAndStates.SHENG);
    }
}
