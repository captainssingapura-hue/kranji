package kranji.common.base;

import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.body.BodyParts;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.tools.ToolsAndVessels;
import kranji.zi.ComposedPart;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.zi.CompositionLayout.TopBottom;

import static kranji.component.basic.BasicComponents.LI_DAO_PANG;

/**
 * Named structural sub-components (all built from singular blocks only)
 * reused across {@link kranji.zi.ComposedZi} definitions.
 *
 * <p>Lives in {@code kranji-common-base} so every depth module can reference
 * these shared shapes. Sub-components that themselves contain a depth-1+
 * composed Zi (e.g. forest inside 魔) live in the first depth module that
 * consumes them, not here.</p>
 */
public final class CommonBlocks {

    private CommonBlocks() {}

    // ── double-glyph patterns ──────────────────────────────────────────

    /** 口口 — double mouth, structural component of 品 and 器. */
    public static final ComposedPart DOUBLE_KOU =
            new ComposedPart(new LeftRight(BodyParts.KOU, BodyParts.KOU));

    /** 虫虫 — double insect, bottom of 蠢. */
    public static final ComposedPart DOUBLE_CHONG =
            new ComposedPart(new LeftRight(Animals.CHONG, Animals.CHONG));

    // ── ⺈ (knife-top) combinations ────────────────────────────────────

    /** ⺈力 — knife-top over strength, right side of 抛. */
    public static final ComposedPart DAO_LI =
            new ComposedPart(new TopBottom(RadicalComponents.DAO_TOP, ToolsAndVessels.LI_PLOW));

    /** ⺈又 — knife-top over again, right side of 报. */
    public static final ComposedPart DAO_YOU =
            new ComposedPart(new TopBottom(RadicalComponents.DAO_TOP, ActionsAndStates.YOU_AGAIN));

    /** ⺈小 — knife-top over small (≈ 尔), right side of 你. */
    public static final ComposedPart DAO_XIAO =
            new ComposedPart(new TopBottom(RadicalComponents.DAO_TOP, PeopleAndRoles.XIAO));

    // ── other sub-structures ───────────────────────────────────────────

    /** 又刂 — again + knife, top of 坚. */
    public static final ComposedPart YOU_DAO =
            new ComposedPart(new LeftRight(ActionsAndStates.YOU_AGAIN, LI_DAO_PANG));

    /** 人彡 — person over three-strokes, phonetic component of 诊. */
    public static final ComposedPart REN_SHAN =
            new ComposedPart(new TopBottom(PeopleAndRoles.REN, RadicalComponents.SHAN_HAIR));

    /** 一工 — one over work, left side of 式. */
    public static final ComposedPart YI_GONG =
            new ComposedPart(new TopBottom(NumbersAndMeasure.YI, ActionsAndStates.GONG_WORK));
}
