// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.z.e2;

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
import kranji.singular.unsure.UnsureSingulars.Yi_HuntingNet;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZeSelect() implements ComposedZiT, LeftRightT<TiShouPang, Yi_HuntingNet> {

    public static final ZeSelect INSTANCE = new ZeSelect();

    @Override public TiShouPang left() { return BasicComponents.TI_SHOU_PANG; }
    @Override public Yi_HuntingNet right() { return UnsureSingulars.YI_HUNTINGNET; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u62E9"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.Z, new Final(Head.OPEN, Body.E, Tail.NONE), Tone.SECOND);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 64; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.TI_SHOU_PANG, UnsureSingulars.YI_HUNTINGNET);
    }
}
