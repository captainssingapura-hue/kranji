// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.j.i3;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.HandFamily.TiShouPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Qi_Even;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record JiSqueeze() implements ComposedZiT, LeftRightT<TiShouPang, Qi_Even> {

    public static final JiSqueeze INSTANCE = new JiSqueeze();

    @Override public TiShouPang left() { return BasicComponents.TI_SHOU_PANG; }
    @Override public Qi_Even right() { return UnsureSingulars.QI_EVEN; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6324"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.J, new Final(Head.OPEN, Body.I, Tail.NONE), Tone.THIRD);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 64; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.TI_SHOU_PANG, UnsureSingulars.QI_EVEN);
    }
}
