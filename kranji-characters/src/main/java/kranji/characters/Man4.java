package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced man (tone 4). */
public final class Man4 {
    private Man4() {}

    /** 慢 (man4) — slow. */
    public static final ChineseCharacterEntry 慢_SLOW = entry("慢")
            .py(M, OPEN, Body.A, Tail.N, T4).strokes(14).radical(61)
            .leftRight(SHU_XIN_PANG, zi("曼"))
            .phonoSemantic(SHU_XIN_PANG, zi("曼"));

    public static final List<ChineseCharacterEntry> ALL = List.of(慢_SLOW);
}
