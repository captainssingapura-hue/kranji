// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.x.ian1;

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
import kranji.singular.animals.Animals.Yu;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record XianFresh() implements ComposedZiT, LeftRightT<Yu, Yang> {

    public static final XianFresh INSTANCE = new XianFresh();

    @Override public Yu left() { return Animals.YU; }
    @Override public Yang right() { return Animals.YANG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u9C9C"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.X, new Final(Head.I, Body.A, Tail.N), Tone.FIRST);
    }

    @Override public int strokes() { return 14; }
    @Override public int radicalNo() { return 195; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.CompoundIndicative("鱼(fish) + 羊(sheep) → fresh");
    }
}
