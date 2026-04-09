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

/** Characters pronounced ge (tone 1). */
public final class Ge1 {
    private Ge1() {}

    /** 哥 (ge1) — older brother. */
    public static final ChineseCharacterEntry 哥_OLDER_BROTHER = entry("哥")
            .py(G, OPEN, Body.E, Tail.NONE, T1).strokes(10).radical(30)
            .topBottom(zi("可"), zi("可"))
            .compoundIndicative("older brother");

    public static final List<ChineseCharacterEntry> ALL = List.of(哥_OLDER_BROTHER);
}
