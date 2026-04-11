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

/** Characters pronounced bang (tone 1). */
public final class Bang1 {
    private Bang1() {}

    /** 帮 (bang1) — help; assist. */
    public static final ChineseCharacterEntry 帮_HELP_ASSIST = entry("帮")
            .py(B, OPEN, Body.A, Tail.NG, T1).strokes(9).radical(50)
            .topBottom(zi("邦"), zi("巾"))
            .phonoSemantic(zi("巾"), zi("邦"));

    public static final List<ChineseCharacterEntry> ALL = List.of(帮_HELP_ASSIST);
}
