// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.j.ing3;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.nature.NatureElements;
import kranji.singular.nature.NatureElements.Ri;
import kranji.singular.structures.Structures;
import kranji.singular.structures.Structures.Jing_Capital;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.TopBottomT;

public record JingScene() implements ComposedZiT, TopBottomT<Ri, Jing_Capital> {

    public static final JingScene INSTANCE = new JingScene();

    @Override public Ri top() { return NatureElements.RI; }
    @Override public Jing_Capital bottom() { return Structures.JING_CAPITAL; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u666F"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.J, new Final(Head.OPEN, Body.I, Tail.NG), Tone.THIRD);
    }

    @Override public int strokes() { return 12; }
    @Override public int radicalNo() { return 72; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.RI, Structures.JING_CAPITAL);
    }
}
