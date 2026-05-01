// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.r.ao2;

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
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Yao_Lofty;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record RaoOar() implements ComposedZiT, LeftRightT<Mu, Yao_Lofty> {

    public static final RaoOar INSTANCE = new RaoOar();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Yao_Lofty right() { return UnsureSingulars.YAO_LOFTY; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6861"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.R, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.SECOND);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, UnsureSingulars.YAO_LOFTY);
    }
}
