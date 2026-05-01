// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.j.ing1;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.materials.Materials;
import kranji.singular.materials.Materials.Qing;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.singular.plants.PlantsAndAgriculture.Mi;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record JingEssence() implements ComposedZiT, LeftRightT<Mi, Qing> {

    public static final JingEssence INSTANCE = new JingEssence();

    @Override public Mi left() { return PlantsAndAgriculture.MI; }
    @Override public Qing right() { return Materials.QING; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7CBE"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.J, new Final(Head.OPEN, Body.I, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 14; }
    @Override public int radicalNo() { return 119; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(PlantsAndAgriculture.MI, Materials.QING);
    }
}
