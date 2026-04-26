// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.an1;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.zh.an4.ZhanOccupy;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.singular.plants.PlantsAndAgriculture.Mi;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhanGlue() implements ComposedZiT, LeftRightT<Mi, ZhanOccupy> {

    public static final ZhanGlue INSTANCE = new ZhanGlue();

    @Override public Mi left() { return PlantsAndAgriculture.MI; }
    @Override public ZhanOccupy right() { return ZhanOccupy.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7C98"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.OPEN, Body.A, Tail.N), Tone.FIRST);
    }

    @Override public int strokes() { return 11; }
    @Override public int radicalNo() { return 119; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(PlantsAndAgriculture.MI, ZhanOccupy.INSTANCE);
    }
}
