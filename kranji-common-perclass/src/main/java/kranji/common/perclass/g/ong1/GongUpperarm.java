// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.g.ong1;

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
import kranji.singular.unsure.UnsureSingulars;
import kranji.singular.unsure.UnsureSingulars.Gong_Forearm;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record GongUpperarm() implements ComposedZiT, LeftRightT<Yue, Gong_Forearm> {

    public static final GongUpperarm INSTANCE = new GongUpperarm();

    @Override public Yue left() { return NatureElements.YUE; }
    @Override public Gong_Forearm right() { return UnsureSingulars.GONG_FOREARM; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u80B1"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.G, new Final(Head.OPEN, Body.O, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 8; }
    @Override public int radicalNo() { return 130; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(NatureElements.YUE, UnsureSingulars.GONG_FOREARM);
    }
}
