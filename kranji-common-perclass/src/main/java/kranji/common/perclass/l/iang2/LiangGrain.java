// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.iang2;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.singular.plants.PlantsAndAgriculture.Mi;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.radicals.RadicalComponents.Liang_Good;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LiangGrain() implements ComposedZiT, LeftRightT<Mi, Liang_Good> {

    public static final LiangGrain INSTANCE = new LiangGrain();

    @Override public Mi left() { return PlantsAndAgriculture.MI; }
    @Override public Liang_Good right() { return RadicalComponents.LIANG_GOOD; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7CAE"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.I, Body.A, Tail.NG), Tone.SECOND);
    }

    @Override public int strokes() { return 13; }
    @Override public int radicalNo() { return 119; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(PlantsAndAgriculture.MI, RadicalComponents.LIANG_GOOD);
    }
}
