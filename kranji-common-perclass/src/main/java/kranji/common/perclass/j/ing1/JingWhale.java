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
import kranji.singular.animals.Animals;
import kranji.singular.animals.Animals.Yu;
import kranji.singular.structures.Structures;
import kranji.singular.structures.Structures.Jing_Capital;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record JingWhale() implements ComposedZiT, LeftRightT<Yu, Jing_Capital> {

    public static final JingWhale INSTANCE = new JingWhale();

    @Override public Yu left() { return Animals.YU; }
    @Override public Jing_Capital right() { return Structures.JING_CAPITAL; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u9CB8"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.J, new Final(Head.OPEN, Body.I, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 16; }
    @Override public int radicalNo() { return 195; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(Animals.YU, Structures.JING_CAPITAL);
    }
}
