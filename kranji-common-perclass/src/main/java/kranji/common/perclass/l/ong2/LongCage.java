// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.ong2;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.animals.Animals;
import kranji.singular.animals.Animals.Long;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.radicals.RadicalComponents.Zhu_Bamboo;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.TopBottomT;

public record LongCage() implements ComposedZiT, TopBottomT<Zhu_Bamboo, Long> {

    public static final LongCage INSTANCE = new LongCage();

    @Override public Zhu_Bamboo top() { return RadicalComponents.ZHU_BAMBOO; }
    @Override public Long bottom() { return Animals.LONG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7B3C"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.OPEN, Body.O, Tail.NG), Tone.SECOND);
    }

    @Override public int strokes() { return 11; }
    @Override public int radicalNo() { return 118; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(RadicalComponents.ZHU_BAMBOO, Animals.LONG);
    }
}
