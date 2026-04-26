// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.ang2;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.AnimalFamily.FanQuanPang;
import kranji.component.basic.BasicComponents;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.radicals.RadicalComponents.Liang_Good;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LangWolf() implements ComposedZiT, LeftRightT<FanQuanPang, Liang_Good> {

    public static final LangWolf INSTANCE = new LangWolf();

    @Override public FanQuanPang left() { return BasicComponents.FAN_QUAN_PANG; }
    @Override public Liang_Good right() { return RadicalComponents.LIANG_GOOD; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u72FC"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.OPEN, Body.A, Tail.NG), Tone.SECOND);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 94; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.FAN_QUAN_PANG, RadicalComponents.LIANG_GOOD);
    }
}
