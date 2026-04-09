package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced gong (tone 1). */
public final class Gong1 {
    private Gong1() {}

    /** 工 (gong1) — work; craft. */
    public static final ChineseCharacterEntry 工_WORK_CRAFT = entry("工")
            .py(G, OPEN, Body.O, Tail.NG, T1).strokes(3).radical(48)
            .singular()
            .pictograph();

    /** 公 (gong1) — public. */
    public static final ChineseCharacterEntry 公_PUBLIC = entry("公")
            .py(G, OPEN, Body.O, Tail.NG, T1).strokes(4).radical(12)
            .topBottom(zi("八"), zi("厶"))
            .compoundIndicative("public");

    /** 供 (gong1) — supply; offer. */
    public static final ChineseCharacterEntry 供_SUPPLY_OFFER = entry("供")
            .py(G, OPEN, Body.O, Tail.NG, T1).strokes(8).radical(9)
            .leftRight(DAN_REN_PANG, zi("共"))
            .phonoSemantic(DAN_REN_PANG, zi("共"));

    /** 功 (gong1) — merit; skill. */
    public static final ChineseCharacterEntry 功_MERIT_SKILL = entry("功")
            .py(G, OPEN, Body.O, Tail.NG, T1).strokes(5).radical(19)
            .leftRight(zi("工"), zi("力"))
            .phonoSemantic(zi("力"), zi("工"));

    /** 弓 (gong1) — bow. */
    public static final ChineseCharacterEntry 弓_BOW = entry("弓")
            .py(G, OPEN, Body.O, Tail.NG, T1).strokes(3).radical(57)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(工_WORK_CRAFT, 公_PUBLIC, 供_SUPPLY_OFFER, 功_MERIT_SKILL, 弓_BOW);
}
