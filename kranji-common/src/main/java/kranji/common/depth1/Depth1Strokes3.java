package kranji.common.depth1;

import kranji.classification.EtymologicalCategory.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.body.BodyParts;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.materials.Materials;
import kranji.singular.nature.NatureElements;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.tools.ToolsAndVessels;
import kranji.singular.animals.Animals;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.component.basic.WoodFamily;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

public final class Depth1Strokes3 {

    private Depth1Strokes3() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
    private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }

    // ── 3 strokes ──────────────────────────────────────────────────────

    /** 亿 (yì) — hundred million. LeftRight: 亻(semantic) + 乙(phonetic). */
    public static final ComposedZi YI_HUNDRED_MILLION = new ComposedZi(
            uni("\u4EBF"),
            List.of(py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            3, 9, "",
            new LeftRight(DAN_REN_PANG, AbstractConcepts.YI_SECOND),
            new PhonoSemantic(DAN_REN_PANG, AbstractConcepts.YI_SECOND)
    );

    public static final List<ComposedZi> ALL = List.of(
            YI_HUNDRED_MILLION
    );
}
