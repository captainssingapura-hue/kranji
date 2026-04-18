package kranji.common.depth2;

import kranji.classification.EtymologicalCategory.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.body.BodyParts;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.nature.NatureElements;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.tools.ToolsAndVessels;
import kranji.singular.people.PeopleAndRoles;
import kranji.common.depth1.Depth1Strokes4;
import kranji.common.depth1.Depth1Strokes5;
import kranji.common.depth1.Depth1StrokesHigh;
import kranji.component.basic.WoodFamily;
import kranji.singular.materials.Materials;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import kranji.common.CommonBlocks;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

public final class Depth2Strokes6 {

    private Depth2Strokes6() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
    private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }

    // ── 6 strokes ──────────────────────────────────────────────────────

    /** 式 (shì) — style/formula. LeftRight: TopBottom(一, 工) on left + 弋 on right. */
    public static final ComposedZi SHI_STYLE = new ComposedZi(
            lit("式"),
            py(Initial.SH, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH),
            6, 56, "",
            new LeftRight(CommonBlocks.YI_GONG, ToolsAndVessels.YI_SHOOT),
            new PhonoSemantic(ActionsAndStates.GONG_WORK, ToolsAndVessels.YI_SHOOT)
    );

    public static final List<ComposedZi> ALL = List.of(
            SHI_STYLE
    );
}
