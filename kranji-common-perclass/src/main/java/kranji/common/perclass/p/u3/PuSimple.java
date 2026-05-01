// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.p.u3;

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
import kranji.singular.tools.ToolsAndVessels;
import kranji.singular.tools.ToolsAndVessels.Bu_Divination;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record PuSimple() implements ComposedZiT, LeftRightT<Mu, Bu_Divination> {

    public static final PuSimple INSTANCE = new PuSimple();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Bu_Divination right() { return ToolsAndVessels.BU_DIVINATION; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6734"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.P, new Final(Head.U, Body.U, Tail.NONE), Tone.THIRD);
    }

    @Override public int strokes() { return 6; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, ToolsAndVessels.BU_DIVINATION);
    }
}
