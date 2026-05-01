// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.t.ao1;

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
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.concepts.AbstractConcepts.Shou_Long;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record TaoWave() implements ComposedZiT, LeftRightT<SanDianShui, Shou_Long> {

    public static final TaoWave INSTANCE = new TaoWave();

    @Override public SanDianShui left() { return BasicComponents.SAN_DIAN_SHUI; }
    @Override public Shou_Long right() { return AbstractConcepts.SHOU_LONG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6D9B"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.T, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.FIRST);
    }

    @Override public int strokes() { return 11; }
    @Override public int radicalNo() { return 85; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SAN_DIAN_SHUI, AbstractConcepts.SHOU_LONG);
    }
}
