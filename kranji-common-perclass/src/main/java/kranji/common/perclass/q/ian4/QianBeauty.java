// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.q.ian4;

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
import kranji.singular.materials.Materials;
import kranji.singular.materials.Materials.Qing;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record QianBeauty() implements ComposedZiT, LeftRightT<DanRenPang, Qing> {

    public static final QianBeauty INSTANCE = new QianBeauty();

    @Override public DanRenPang left() { return BasicComponents.DAN_REN_PANG; }
    @Override public Qing right() { return Materials.QING; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u5029"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.Q, new Final(Head.I, Body.A, Tail.N), Tone.FOURTH);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 9; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.DAN_REN_PANG, Materials.QING);
    }
}
