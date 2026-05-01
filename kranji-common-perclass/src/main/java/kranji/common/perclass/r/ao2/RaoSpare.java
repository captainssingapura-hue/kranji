// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.r.ao2;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.FoodFamily.ShiZiPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Yao_Lofty;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record RaoSpare() implements ComposedZiT, LeftRightT<ShiZiPang, Yao_Lofty> {

    public static final RaoSpare INSTANCE = new RaoSpare();

    @Override public ShiZiPang left() { return BasicComponents.SHI_ZI_PANG; }
    @Override public Yao_Lofty right() { return UnsureSingulars.YAO_LOFTY; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u9976"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.R, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.SECOND);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 184; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.SHI_ZI_PANG, UnsureSingulars.YAO_LOFTY);
    }
}
