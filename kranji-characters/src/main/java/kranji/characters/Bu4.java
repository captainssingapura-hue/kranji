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

/** Characters pronounced bu (tone 4). */
public final class Bu4 {
    private Bu4() {}

    /** 不 (bu4) — not. */
    public static final ChineseCharacterEntry 不_NOT = entry("不")
            .py(B, U, Body.U, Tail.NONE, T4).strokes(4).radical(1)
            .singular()
            .indicative("not");

    /** 部 (bu4) — part; section. */
    public static final ChineseCharacterEntry 部_PART_SECTION = entry("部")
            .py(B, U, Body.U, Tail.NONE, T4).strokes(10).radical(163)
            .leftRight(zi("咅"), zi("阝"))
            .phonoSemantic(zi("阝"), zi("咅"));

    /** 步 (bu4) — step; walk. */
    public static final ChineseCharacterEntry 步_STEP_WALK = entry("步")
            .py(B, U, Body.U, Tail.NONE, T4).strokes(7).radical(77)
            .topBottom(zi("止"), zi("少"))
            .compoundIndicative("step; walk");

    public static final List<ChineseCharacterEntry> ALL = List.of(不_NOT, 部_PART_SECTION, 步_STEP_WALK);
}
