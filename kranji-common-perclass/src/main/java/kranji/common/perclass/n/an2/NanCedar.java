// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.n.an2;

import kranji.classification.EtymologicalCategory;
import kranji.component.basic.BasicComponents;
import kranji.component.basic.WoodFamily.Mu;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.space.SpaceAndDirection.Nan;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record NanCedar() implements ComposedZiT, LeftRightT<Mu, Nan> {

    public static final NanCedar INSTANCE = new NanCedar();

    @Override public Mu left() { return BasicComponents.MU; }
    @Override public Nan right() { return SpaceAndDirection.NAN; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u6960"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.N, new Final(Head.OPEN, Body.A, Tail.N), Tone.SECOND);
    }

    @Override public int strokes() { return 13; }
    @Override public int radicalNo() { return 75; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BasicComponents.MU, SpaceAndDirection.NAN);
    }
}
