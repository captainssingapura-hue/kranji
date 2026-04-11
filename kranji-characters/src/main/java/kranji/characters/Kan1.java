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

/** Characters pronounced kan (tone 1). */
public final class Kan1 {
    private Kan1() {}

    /** 刊 (kan1) — publish; print. */
    public static final ChineseCharacterEntry 刊_PUBLISH_PRINT = entry("刊")
            .py(K, OPEN, Body.A, Tail.N, T1).strokes(5).radical(18)
            .leftRight(zi("干"), LI_DAO_PANG)
            .phonoSemantic(LI_DAO_PANG, zi("干"));

    public static final List<ChineseCharacterEntry> ALL = List.of(刊_PUBLISH_PRINT);
}
