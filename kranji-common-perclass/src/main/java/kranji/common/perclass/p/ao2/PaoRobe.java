// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.p.ao2;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.b.ao1.BaoWrap;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.ClothingFamily.YiZiPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record PaoRobe() implements ComposedZiT, LeftRightT<YiZiPang, BaoWrap> {

    public static final PaoRobe INSTANCE = new PaoRobe();

    @Override public YiZiPang left() { return BasicComponents.YI_ZI_PANG; }
    @Override public BaoWrap right() { return BaoWrap.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u888D"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.P, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.SECOND);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 145; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.YI_ZI_PANG, BaoWrap.INSTANCE);
    }
}
