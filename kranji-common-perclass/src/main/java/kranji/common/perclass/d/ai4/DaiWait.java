// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.d.ai4;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.s.i4.SiTemple;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.tools.ToolsAndVessels;
import kranji.singular.tools.ToolsAndVessels.Chi_Step;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record DaiWait() implements ComposedZiT, LeftRightT<Chi_Step, SiTemple> {

    public static final DaiWait INSTANCE = new DaiWait();

    @Override public Chi_Step left() { return ToolsAndVessels.CHI_STEP; }
    @Override public SiTemple right() { return SiTemple.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u5F85"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.D, new Final(Head.OPEN, Body.A, Tail.VOWEL_I), Tone.FOURTH);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 60; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(ToolsAndVessels.CHI_STEP, SiTemple.INSTANCE);
    }
}
