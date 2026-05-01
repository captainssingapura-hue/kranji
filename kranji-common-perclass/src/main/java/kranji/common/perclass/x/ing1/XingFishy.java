// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.x.ing1;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.nature.NatureElements;
import kranji.singular.nature.NatureElements.Yue;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record XingFishy() implements ComposedZiT, LeftRightT<Yue, XingStar> {

    public static final XingFishy INSTANCE = new XingFishy();

    @Override public Yue left() { return NatureElements.YUE; }
    @Override public XingStar right() { return XingStar.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u8165"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.X, new Final(Head.OPEN, Body.I, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 13; }
    @Override public int radicalNo() { return 130; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YUE, XingStar.INSTANCE);
    }
}
