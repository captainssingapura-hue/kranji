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

/** Characters pronounced zhuang (tone 1). */
public final class Zhuang1 {
    private Zhuang1() {}

    /** 庄 (zhuang1) — village; manor. */
    public static final ChineseCharacterEntry 庄_VILLAGE_MANOR = entry("庄")
            .py(ZH, U, Body.A, Tail.NG, T1).strokes(6).radical(53)
            .semiEnclosureUL(zi("广"), zi("土"))
            .phonoSemantic(zi("广"), zi("土"));

    public static final List<ChineseCharacterEntry> ALL = List.of(庄_VILLAGE_MANOR);
}
