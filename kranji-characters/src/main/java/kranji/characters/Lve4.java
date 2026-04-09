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

/** Characters pronounced lve (tone 4). */
public final class Lve4 {
    private Lve4() {}

    /** 略 (lve4) — brief; plan. */
    public static final ChineseCharacterEntry 略_BRIEF_PLAN = entry("略")
            .py(L, V, Body.E_CARON, Tail.NONE, T4).strokes(11).radical(102)
            .leftRight(zi("田"), zi("各"))
            .phonoSemantic(zi("田"), zi("各"));

    public static final List<ChineseCharacterEntry> ALL = List.of(略_BRIEF_PLAN);
}
