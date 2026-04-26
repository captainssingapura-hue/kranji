// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.k.u1;

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
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.concepts.AbstractConcepts.Gu;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record KuWither() implements ComposedZiT, LeftRightT<Mu, Gu> {

    public static final KuWither INSTANCE = new KuWither();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Gu right() { return AbstractConcepts.GU; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u67AF"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.K, new Final(Head.U, Body.U, Tail.NONE), Tone.FIRST);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, AbstractConcepts.GU);
    }
}
