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

/** Characters pronounced ya (tone 5). */
public final class Ya5 {
    private Ya5() {}

    /** 呀 (ya5) — particle. */
    public static final ChineseCharacterEntry 呀_PARTICLE = entry("呀")
            .py(ZERO, I, Body.A, Tail.NONE, T5).strokes(7).radical(30)
            .leftRight(zi("口"), zi("牙"))
            .phonoSemantic(zi("口"), zi("牙"));

    public static final List<ChineseCharacterEntry> ALL = List.of(呀_PARTICLE);
}
