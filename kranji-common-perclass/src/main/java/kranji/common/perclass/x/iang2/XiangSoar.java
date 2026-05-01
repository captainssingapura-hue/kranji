// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.x.iang2;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.animals.Animals;
import kranji.singular.animals.Animals.Yang;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.radicals.RadicalComponents.Yu_Feather;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record XiangSoar() implements ComposedZiT, LeftRightT<Yang, Yu_Feather> {

    public static final XiangSoar INSTANCE = new XiangSoar();

    @Override public Yang left() { return Animals.YANG; }
    @Override public Yu_Feather right() { return RadicalComponents.YU_FEATHER; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7FD4"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.X, new Final(Head.I, Body.A, Tail.NG), Tone.SECOND);
    }

    @Override public int strokes() { return 12; }
    @Override public int radicalNo() { return 124; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(RadicalComponents.YU_FEATHER, Animals.YANG);
    }
}
