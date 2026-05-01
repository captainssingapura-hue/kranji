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
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.radicals.RadicalComponents.Zhu_Bamboo;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.TopBottomT;

public record ShengReed() implements ComposedZiT, TopBottomT<Zhu_Bamboo, Sheng> {

    public static final ShengReed INSTANCE = new ShengReed();

    @Override public Zhu_Bamboo top() { return RadicalComponents.ZHU_BAMBOO; }
    @Override public Sheng bottom() { return ActionsAndStates.SHENG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7B19"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.SH, new Final(Head.OPEN, Body.E, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 11; }
    @Override public int radicalNo() { return 118; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(RadicalComponents.ZHU_BAMBOO, ActionsAndStates.SHENG);
    }
}
