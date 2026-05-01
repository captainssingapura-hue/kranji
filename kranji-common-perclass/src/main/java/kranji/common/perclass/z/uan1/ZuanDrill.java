// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.z.uan1;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.zh.an4.ZhanOccupy;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.MetalFamily.JinZiPang;
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

public record ZuanDrill() implements ComposedZiT, LeftRightT<JinZiPang, ZhanOccupy> {

    public static final ZuanDrill INSTANCE = new ZuanDrill();

    @Override public JinZiPang left() { return BasicComponents.JIN_ZI_PANG; }
    @Override public ZhanOccupy right() { return ZhanOccupy.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u94BB"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.Z, new Final(Head.U, Body.A, Tail.N), Tone.FIRST);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 167; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.JIN_ZI_PANG, ZhanOccupy.INSTANCE);
    }
}
