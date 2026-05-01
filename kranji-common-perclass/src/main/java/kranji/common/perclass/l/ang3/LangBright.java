// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.ang3;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.nature.NatureElements;
import kranji.singular.nature.NatureElements.Yue;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.radicals.RadicalComponents.Liang_Good;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LangBright() implements ComposedZiT, LeftRightT<Liang_Good, Yue> {

    public static final LangBright INSTANCE = new LangBright();

    @Override public Liang_Good left() { return RadicalComponents.LIANG_GOOD; }
    @Override public Yue right() { return NatureElements.YUE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6717"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.OPEN, Body.A, Tail.NG), Tone.THIRD);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 74; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YUE, RadicalComponents.LIANG_GOOD);
    }
}
