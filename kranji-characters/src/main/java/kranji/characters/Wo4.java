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

/** Characters pronounced wo (tone 4). */
public final class Wo4 {
    private Wo4() {}

    /** 握 (wo4) — grip; hold. */
    public static final ChineseCharacterEntry 握_GRIP_HOLD = entry("握")
            .py(ZERO, U, Body.O, Tail.NONE, T4).strokes(12).radical(64)
            .leftRight(TI_SHOU_PANG, zi("屋"))
            .phonoSemantic(TI_SHOU_PANG, zi("屋"));

    public static final List<ChineseCharacterEntry> ALL = List.of(握_GRIP_HOLD);
}
