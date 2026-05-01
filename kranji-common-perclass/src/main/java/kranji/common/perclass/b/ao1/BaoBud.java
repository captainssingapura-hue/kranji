// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.b.ao1;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.PlantFamily.CaoZiTou;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.TopBottomT;

public record BaoBud() implements ComposedZiT, TopBottomT<CaoZiTou, BaoWrap> {

    public static final BaoBud INSTANCE = new BaoBud();

    @Override public CaoZiTou top() { return BasicComponents.CAO_ZI_TOU; }
    @Override public BaoWrap bottom() { return BaoWrap.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u82DE"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.B, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.FIRST);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 140; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.CAO_ZI_TOU, BaoWrap.INSTANCE);
    }
}
