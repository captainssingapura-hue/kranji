// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.u4;

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
import kranji.singular.concepts.AbstractConcepts.Zhu_Lord;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhuPillar() implements ComposedZiT, LeftRightT<Mu, Zhu_Lord> {

    public static final ZhuPillar INSTANCE = new ZhuPillar();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Zhu_Lord right() { return AbstractConcepts.ZHU_LORD; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u67F1"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.U, Body.U, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, AbstractConcepts.ZHU_LORD);
    }
}
