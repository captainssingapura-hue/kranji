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

/** Characters pronounced cun (tone 2). */
public final class Cun2 {
    private Cun2() {}

    /** 存 (cun2) — exist; store. */
    public static final ChineseCharacterEntry 存_EXIST_STORE = entry("存")
            .py(C, U, Body.E, Tail.N, T2).strokes(6).radical(39)
            .semiEnclosureUL(zi("才"), zi("子"))
            .phonoSemantic(zi("子"), zi("才"));

    public static final List<ChineseCharacterEntry> ALL = List.of(存_EXIST_STORE);
}
