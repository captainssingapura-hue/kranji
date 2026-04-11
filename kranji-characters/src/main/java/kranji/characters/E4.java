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

/** Characters pronounced e (tone 4). */
public final class E4 {
    private E4() {}

    /** 饿 (e4) — hungry. */
    public static final ChineseCharacterEntry 饿_HUNGRY = entry("饿")
            .py(ZERO, OPEN, Body.E, Tail.NONE, T4).strokes(10).radical(184)
            .leftRight(SHI_ZI_PANG, zi("我"))
            .phonoSemantic(SHI_ZI_PANG, zi("我"));

    public static final List<ChineseCharacterEntry> ALL = List.of(饿_HUNGRY);
}
