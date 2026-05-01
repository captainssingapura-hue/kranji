// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.j.ing1;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.body.BodyParts;
import kranji.singular.body.BodyParts.Mu;
import kranji.singular.materials.Materials;
import kranji.singular.materials.Materials.Qing;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record JingEyeball() implements ComposedZiT, LeftRightT<Mu, Qing> {

    public static final JingEyeball INSTANCE = new JingEyeball();

    @Override public Mu left() { return BodyParts.MU; }
    @Override public Qing right() { return Materials.QING; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u775B"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.J, new Final(Head.OPEN, Body.I, Tail.NG), Tone.FIRST);
    }

    @Override public int strokes() { return 13; }
    @Override public int radicalNo() { return 109; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(BodyParts.MU, Materials.QING);
    }
}
