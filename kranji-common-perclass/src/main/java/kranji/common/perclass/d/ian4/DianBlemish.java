// Hand-authored typed record. See docs/batch-additions-workflow.md §2A.
package kranji.common.perclass.d.ian4;

import kranji.classification.EtymologicalCategory;
import kranji.common.perclass.zh.an4.ZhanOccupy;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.people.PeopleAndRoles.Wang;
import kranji.zi.ComposedZiT;
import kranji.zi.CompositionLayoutT;
import kranji.zi.LeftRightT;

public record DianBlemish() implements ComposedZiT, LeftRightT<Wang, ZhanOccupy> {

    public static final DianBlemish INSTANCE = new DianBlemish();

    @Override public Wang left() { return PeopleAndRoles.WANG; }
    @Override public ZhanOccupy right() { return ZhanOccupy.INSTANCE; }

    @Override public CompositionLayoutT layout() { return this; }

    @Override public String glyph() { return "\u73B7"; }

    @Override public PinyinSyllable pinyin() {
        return new PinyinSyllable(Initial.D, new Final(Head.I, Body.A, Tail.N), Tone.FOURTH);
    }

    @Override public int strokes() { return 9; }
    @Override public int radicalNo() { return 96; }
    @Override public String meaning() { return ""; }

    @Override public EtymologicalCategory etymology() {
        return new EtymologicalCategory.PhonoSemantic(PeopleAndRoles.WANG, ZhanOccupy.INSTANCE);
    }
}
