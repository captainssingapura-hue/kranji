// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.b.a1;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.radicals.RadicalComponents.Zhu_Bamboo;
import kranji.singular.structures.Structures;
import kranji.singular.structures.Structures.Ba_Cling;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.TopBottomT;

public record BaBamboo() implements ComposedZiT, TopBottomT<Zhu_Bamboo, Ba_Cling> {

    public static final BaBamboo INSTANCE = new BaBamboo();

    @Override public Zhu_Bamboo top() { return RadicalComponents.ZHU_BAMBOO; }
    @Override public Ba_Cling bottom() { return Structures.BA_CLING; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7B06"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.B, new Final(Head.OPEN, Body.A, Tail.NONE), Tone.FIRST);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 118; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(RadicalComponents.ZHU_BAMBOO, Structures.BA_CLING);
    }
}
