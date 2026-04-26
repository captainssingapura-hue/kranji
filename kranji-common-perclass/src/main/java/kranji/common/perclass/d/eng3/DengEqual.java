// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.d.eng3;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.s.i4.SiTemple;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.radicals.RadicalComponents.Zhu_Bamboo;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.TopBottomT;

public record DengEqual() implements ComposedZiT, TopBottomT<Zhu_Bamboo, SiTemple> {

    public static final DengEqual INSTANCE = new DengEqual();

    @Override public Zhu_Bamboo top() { return RadicalComponents.ZHU_BAMBOO; }
    @Override public SiTemple bottom() { return SiTemple.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7B49"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.D, new Final(Head.OPEN, Body.E, Tail.NG), Tone.THIRD);
    }

    @Override public int strokes() { return 12; }
    @Override public int radicalNo() { return 118; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(RadicalComponents.ZHU_BAMBOO, SiTemple.INSTANCE);
    }
}
