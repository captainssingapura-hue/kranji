// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.l.iang2;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.WaterFamily.LiangDianShui;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.structures.Structures;
import kranji.singular.structures.Structures.Jing_Capital;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record LiangCool() implements ComposedZiT, LeftRightT<LiangDianShui, Jing_Capital> {

    public static final LiangCool INSTANCE = new LiangCool();

    @Override public LiangDianShui left() { return BasicComponents.LIANG_DIAN_SHUI; }
    @Override public Jing_Capital right() { return Structures.JING_CAPITAL; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u51C9"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.L, new Final(Head.I, Body.A, Tail.NG), Tone.SECOND);
    }

    @Override public int strokes() { return 10; }
    @Override public int radicalNo() { return 15; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.LIANG_DIAN_SHUI, Structures.JING_CAPITAL);
    }
}
