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

/** Characters pronounced ru (tone 2). */
public final class Ru2 {
    private Ru2() {}

    /** 如 (ru2) — if; like. */
    public static final ChineseCharacterEntry 如_IF_LIKE = entry("如")
            .py(R, U, Body.U, Tail.NONE, T2).strokes(6).radical(38)
            .leftRight(zi("女"), zi("口"))
            .phonoSemantic(zi("女"), zi("口"));

    public static final List<ChineseCharacterEntry> ALL = List.of(如_IF_LIKE);
}
