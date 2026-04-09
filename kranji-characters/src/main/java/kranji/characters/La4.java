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

/** Characters pronounced la (tone 4). */
public final class La4 {
    private La4() {}

    /** 辣 (la4) — spicy; hot. */
    public static final ChineseCharacterEntry 辣_SPICY_HOT = entry("辣")
            .py(L, OPEN, Body.A, Tail.NONE, T4).strokes(14).radical(160)
            .leftRight(zi("辛"), zi("束"))
            .phonoSemantic(zi("辛"), zi("束"));

    public static final List<ChineseCharacterEntry> ALL = List.of(辣_SPICY_HOT);
}
