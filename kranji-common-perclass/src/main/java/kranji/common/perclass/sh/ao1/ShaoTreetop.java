// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.sh.ao1;

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
import kranji.singular.unsure.UnsureSingulars.Xiao_Resemble;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ShaoTreetop() implements ComposedZiT, LeftRightT<Mu, Xiao_Resemble> {

    public static final ShaoTreetop INSTANCE = new ShaoTreetop();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Xiao_Resemble right() { return UnsureSingulars.XIAO_RESEMBLE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u68A2"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.SH, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.FIRST);
    }

    @Override public int strokes() { return 11; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, UnsureSingulars.XIAO_RESEMBLE);
    }
}
