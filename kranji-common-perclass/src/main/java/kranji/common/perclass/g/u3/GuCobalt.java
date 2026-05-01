// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.g.u3;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.MetalFamily.JinZiPang;
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

public record GuCobalt() implements ComposedZiT, LeftRightT<JinZiPang, Gu> {

    public static final GuCobalt INSTANCE = new GuCobalt();

    @Override public JinZiPang left() { return BasicComponents.JIN_ZI_PANG; }
    @Override public Gu right() { return AbstractConcepts.GU; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u94B4"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.G, new Final(Head.U, Body.U, Tail.NONE), Tone.THIRD);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 167; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.JIN_ZI_PANG, AbstractConcepts.GU);
    }
}
