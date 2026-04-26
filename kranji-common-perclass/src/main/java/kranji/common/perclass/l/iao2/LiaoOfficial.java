// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.iao2;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.PersonFamily.DanRenPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Liao_Torch;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LiaoOfficial() implements ComposedZiT, LeftRightT<DanRenPang, Liao_Torch> {

    public static final LiaoOfficial INSTANCE = new LiaoOfficial();

    @Override public DanRenPang left() { return BasicComponents.DAN_REN_PANG; }
    @Override public Liao_Torch right() { return UnsureSingulars.LIAO_TORCH; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u50DA"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.I, Body.A, Tail.VOWEL_U), Tone.SECOND);
    }

    @Override public int strokes() { return 14; }
    @Override public int radicalNo() { return 9; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.DAN_REN_PANG, UnsureSingulars.LIAO_TORCH);
    }
}
