// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.b.an4;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.SilkFamily.JiaoSiPang;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.numbers.NumbersAndMeasure.Ban;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record BanTrip() implements ComposedZiT, LeftRightT<JiaoSiPang, Ban> {

    public static final BanTrip INSTANCE = new BanTrip();

    @Override public JiaoSiPang left() { return BasicComponents.JIAO_SI_PANG; }
    @Override public Ban right() { return NumbersAndMeasure.BAN; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u7ECA"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.B, new Final(Head.OPEN, Body.A, Tail.N), Tone.FOURTH);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 120; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.JIAO_SI_PANG, NumbersAndMeasure.BAN);
    }
}
