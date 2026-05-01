// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.ang4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.WaterFamily.SanDianShui;
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

public record LangWave() implements ComposedZiT, LeftRightT<SanDianShui, Liang_Good> {

    public static final LangWave INSTANCE = new LangWave();

    @Override public SanDianShui left() { return BasicComponents.SAN_DIAN_SHUI; }
    @Override public Liang_Good right() { return RadicalComponents.LIANG_GOOD; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6D6A"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.OPEN, Body.A, Tail.NG), Tone.FOURTH);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 85; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SAN_DIAN_SHUI, RadicalComponents.LIANG_GOOD);
    }
}
