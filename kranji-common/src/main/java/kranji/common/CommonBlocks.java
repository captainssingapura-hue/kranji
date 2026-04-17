package kranji.common;

import kranji.common.depth1.Depth1StrokesHigh;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.body.BodyParts;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.structures.Structures;
import kranji.singular.tools.ToolsAndVessels;
import kranji.zi.ComposedBlock.*;

import static kranji.component.basic.BasicComponents.*;

/**
 * Named structural sub-components reused across {@link kranji.zi.ComposedZi}
 * definitions.
 *
 * <p>Rather than building anonymous inline {@code new ComposedBlock(...)}
 * trees inside a character's constructor, extract every sub-structure here
 * so that each ComposedZi construction contains only named references.</p>
 */
public final class CommonBlocks {

    private CommonBlocks() {}

    // ── double-glyph patterns ──────────────────────────────────────────

    /** 口口 — double mouth, structural component of 品 and 器. */
    public static final LeftRight DOUBLE_KOU =
            new LeftRight(BodyParts.KOU, BodyParts.KOU);

    /** 虫虫 — double insect, bottom of 蠢. */
    public static final LeftRight DOUBLE_CHONG =
            new LeftRight(Animals.CHONG, Animals.CHONG);

    // ── ⺈ (knife-top) combinations ────────────────────────────────────

    /** ⺈力 — knife-top over strength, right side of 抛. */
    public static final TopBottom DAO_LI =
            new TopBottom(RadicalComponents.DAO_TOP, ToolsAndVessels.LI_PLOW);

    /** ⺈又 — knife-top over again, right side of 报. */
    public static final TopBottom DAO_YOU =
            new TopBottom(RadicalComponents.DAO_TOP, ActionsAndStates.YOU_AGAIN);

    /** ⺈小 — knife-top over small (≈ 尔), right side of 你. */
    public static final TopBottom DAO_XIAO =
            new TopBottom(RadicalComponents.DAO_TOP, PeopleAndRoles.XIAO);

    // ── other sub-structures ───────────────────────────────────────────

    /** 又刂 — again + knife, top of 坚. */
    public static final LeftRight YOU_DAO =
            new LeftRight(ActionsAndStates.YOU_AGAIN, LI_DAO_PANG);

    /** 人彡 — person over three-strokes, phonetic component of 诊. */
    public static final TopBottom REN_SHAN =
            new TopBottom(PeopleAndRoles.REN, RadicalComponents.SHAN_HAIR);

    /** 一工 — one over work, left side of 式. */
    public static final TopBottom YI_GONG =
            new TopBottom(NumbersAndMeasure.YI, ActionsAndStates.GONG_WORK);

    /** 林鬼 — forest over ghost, content of 魔. */
    public static final TopBottom LIN_GUI =
            new TopBottom(Depth1StrokesHigh.LIN.structure(), Animals.GUI_GHOST);

    /** 广林 — cliff wrapping forest, phonetic sub-component of 魔. */
    public static final SemiEnclosureUpperLeft GUANG_LIN =
            new SemiEnclosureUpperLeft(
                    Structures.GUANG, Depth1StrokesHigh.LIN.structure());
}
