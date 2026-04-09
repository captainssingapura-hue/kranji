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

/** Characters pronounced shuang (tone 1). */
public final class Shuang1 {
    private Shuang1() {}

    /** 双 (shuang1) — double; pair. */
    public static final ChineseCharacterEntry 双_DOUBLE_PAIR = entry("双")
            .py(SH, U, Body.A, Tail.NG, T1).strokes(4).radical(29)
            .leftRight(zi("又"), zi("又"))
            .compoundIndicative("double; pair");

    public static final List<ChineseCharacterEntry> ALL = List.of(双_DOUBLE_PAIR);
}
