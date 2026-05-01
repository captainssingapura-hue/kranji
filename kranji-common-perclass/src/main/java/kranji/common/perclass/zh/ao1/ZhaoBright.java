// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.zh.ao1;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.zh.ao4.ZhaoSummon;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.nature.NatureElements;
import kranji.singular.nature.NatureElements.Ri;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record ZhaoBright() implements ComposedZiT, LeftRightT<Ri, ZhaoSummon> {

    public static final ZhaoBright INSTANCE = new ZhaoBright();

    @Override public Ri left() { return NatureElements.RI; }
    @Override public ZhaoSummon right() { return ZhaoSummon.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u662D"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.ZH, new Final(Head.OPEN, Body.A, Tail.VOWEL_U), Tone.FIRST);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 72; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.RI, ZhaoSummon.INSTANCE);
    }
}
