// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.k.ua4;

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
import kranji.singular.unsure.UnsureSingulars.Kua_Boast;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record KuaCarry() implements ComposedZiT, LeftRightT<TiShouPang, Kua_Boast> {

    public static final KuaCarry INSTANCE = new KuaCarry();

    @Override public TiShouPang left() { return BasicComponents.TI_SHOU_PANG; }
    @Override public Kua_Boast right() { return UnsureSingulars.KUA_BOAST; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u630E"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.K, new Final(Head.U, Body.A, Tail.NONE), Tone.FOURTH);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 64; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.TI_SHOU_PANG, UnsureSingulars.KUA_BOAST);
    }
}
