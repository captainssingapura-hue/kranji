// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zero.iang4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.WoodFamily.Mu;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.animals.Animals;
import kranji.singular.animals.Animals.Yang;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record YangPattern() implements ComposedZiT, LeftRightT<Mu, Yang> {

    public static final YangPattern INSTANCE = new YangPattern();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Yang right() { return Animals.YANG; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6837"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZERO, new Final(Head.I, Body.A, Tail.NG), Tone.FOURTH);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, Animals.YANG);
    }
}
