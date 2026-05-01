// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.x.i1;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.HeartFamily.ShuXinPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Xi_Former;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record XiCherish() implements ComposedZiT, LeftRightT<ShuXinPang, Xi_Former> {

    public static final XiCherish INSTANCE = new XiCherish();

    @Override public ShuXinPang left() { return BasicComponents.SHU_XIN_PANG; }
    @Override public Xi_Former right() { return UnsureSingulars.XI_FORMER; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u60DC"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.X, new Final(Head.OPEN, Body.I, Tail.NONE), Tone.FIRST);
    }

    @Override public int strokes() { return 11; }
    @Override public int radicalNo() { return 61; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SHU_XIN_PANG, UnsureSingulars.XI_FORMER);
    }
}
