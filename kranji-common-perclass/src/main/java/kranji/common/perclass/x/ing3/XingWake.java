// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.x.ing3;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.x.ing1.XingStar;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.tools.ToolsAndVessels;
import kranji.singular.tools.ToolsAndVessels.You_Wine;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record XingWake() implements ComposedZiT, LeftRightT<You_Wine, XingStar> {

    public static final XingWake INSTANCE = new XingWake();

    @Override public You_Wine left() { return ToolsAndVessels.YOU_WINE; }
    @Override public XingStar right() { return XingStar.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u9192"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.X, new Final(Head.OPEN, Body.I, Tail.NG), Tone.THIRD);
    }

    @Override public int strokes() { return 16; }
    @Override public int radicalNo() { return 164; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(ToolsAndVessels.YOU_WINE, XingStar.INSTANCE);
    }
}
