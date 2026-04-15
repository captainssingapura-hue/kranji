package kranji.demos;

import kranji.classification.EtymologicalCategory.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.body.BodyParts;
import kranji.singular.nature.NatureElements;
import kranji.component.basic.WoodFamily;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

/**
 * Depth-2 composed characters: at least one direct component is itself
 * a depth-1 composition (i.e. a {@link ComposedBlock} of singular blocks).
 *
 * <p>Depth is defined recursively as {@code max(depth of components) + 1},
 * where singular blocks have depth 0.</p>
 */
public final class Depth2Examples {

    private Depth2Examples() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    // ── LeftRight / Phono-Semantic ──────────────────────────────────────

    /** 雅 (yǎ) — elegant/refined. LeftRight: 牙(phonetic) + 佳(semantic, decomposed as 亻+圭). */
    public static final ComposedZi YA = new ComposedZi(
            "雅",
            List.of(py(Initial.ZERO, Head.I, Body.A, Tail.NONE, Tone.THIRD)),
            12, 172, "",
            new LeftRight(SingularBlock.plain("牙"),
                    new LeftRight(DAN_REN_PANG, SingularBlock.plain("圭"))),
            new PhonoSemantic(SingularBlock.plain("佳"), SingularBlock.plain("牙"))
    );

    // ── TopMiddleBottom / Compound Indicative ───────────────────────────

    /** 器 (qì) — vessel/utensil. TMB: 口口(top) + 犬(middle) + 口口(bottom). */
    public static final ComposedZi QI_VESSEL = new ComposedZi(
            "器",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            16, 30, "",
            new TopMiddleBottom(
                    new LeftRight(SingularBlock.plain("口"), SingularBlock.plain("口")),
                    SingularBlock.plain("犬"),
                    new LeftRight(SingularBlock.plain("口"), SingularBlock.plain("口"))
            ),
            new CompoundIndicative("四口(four mouths/openings) + 犬(dog as guard) → vessel, instrument")
    );

    // ── TopBottom / Compound Indicative ─────────────────────────────────

    /** 品 (pǐn) — product/taste. TopBottom: 口(top) + 口口(bottom). */
    public static final ComposedZi PIN = new ComposedZi(
            "品",
            List.of(py(Initial.P, Head.OPEN, Body.I, Tail.N, Tone.THIRD)),
            9, 30, "",
            new TopBottom(BodyParts.KOU, new LeftRight(SingularBlock.plain("口"), SingularBlock.plain("口"))),
            new CompoundIndicative("口(mouth) × 3 → many mouths → tasting, judging quality")
    );

    /** 森 (sēn) — dense forest. TopBottom: 木(top) + 木木(bottom). */
    public static final ComposedZi SEN = new ComposedZi(
            "森",
            List.of(py(Initial.S, Head.OPEN, Body.E, Tail.N, Tone.FIRST)),
            12, 75, "",
            new TopBottom(WoodFamily.MU, new LeftRight(SingularBlock.plain("木"), SingularBlock.plain("木"))),
            new CompoundIndicative("木(tree) × 3 → dense forest")
    );

    // ── LeftRight / Phono-Semantic (additional) ────────────────────────

    // ── LeftRight / Phono-Semantic (additional) ────────────────────────

    /** 式 (shì) — style/formula. LeftRight: TopBottom(一, 工) on left + 弋 on right. */
    public static final ComposedZi SHI_STYLE = new ComposedZi(
            "式",
            List.of(py(Initial.SH, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 56, "",
            new LeftRight(
                    new TopBottom(SingularBlock.plain("一"), ActionsAndStates.GONG_WORK),
                    SingularBlock.plain("弋")),
            new PhonoSemantic(ActionsAndStates.GONG_WORK, SingularBlock.plain("弋"))
    );

    /** All depth-2 examples. */
    public static final List<ComposedZi> ALL = List.of(
            YA, QI_VESSEL, PIN, SEN, SHI_STYLE
    );
}
