// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.t.e4;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.s.i4.SiTemple;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.animals.Animals;
import kranji.singular.animals.Animals.Niu;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record TeSpecial() implements ComposedZiT, LeftRightT<Niu, SiTemple> {

    public static final TeSpecial INSTANCE = new TeSpecial();

    @Override public Niu left() { return Animals.NIU; }
    @Override public SiTemple right() { return SiTemple.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7279"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.T, new Final(Head.OPEN, Body.E, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 93; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(Animals.NIU, SiTemple.INSTANCE);
    }
}
