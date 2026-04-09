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

/** Characters pronounced he (tone 1). */
public final class He1 {
    private He1() {}

    /** 喝 (he1) — drink. */
    public static final ChineseCharacterEntry 喝_DRINK = entry("喝")
            .py(H, OPEN, Body.E, Tail.NONE, T1).strokes(12).radical(30)
            .leftRight(zi("口"), zi("曷"))
            .phonoSemantic(zi("口"), zi("曷"));

    public static final List<ChineseCharacterEntry> ALL = List.of(喝_DRINK);
}
