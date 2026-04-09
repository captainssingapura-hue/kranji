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

/** Characters pronounced mo (tone 4). */
public final class Mo4 {
    private Mo4() {}

    /** 末 (mo4) — end; tip. */
    public static final ChineseCharacterEntry 末_END_TIP = entry("末")
            .py(M, OPEN, Body.O, Tail.NONE, T4).strokes(5).radical(75)
            .singular()
            .indicative("end; tip");

    public static final List<ChineseCharacterEntry> ALL = List.of(末_END_TIP);
}
