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

/** Characters pronounced na (tone 4). */
public final class Na4 {
    private Na4() {}

    /** 那 (na4) — that. */
    public static final ChineseCharacterEntry 那_THAT = entry("那")
            .py(N, OPEN, Body.A, Tail.NONE, T4).strokes(6).radical(163)
            .leftRight(zi("冄"), YOU_ER_PANG)
            .phonoSemantic(YOU_ER_PANG, zi("冄"));

    /** 纳 (na4) — accept; pay. */
    public static final ChineseCharacterEntry 纳_ACCEPT_PAY = entry("纳")
            .py(N, OPEN, Body.A, Tail.NONE, T4).strokes(7).radical(120)
            .leftRight(JIAO_SI_PANG, zi("内"))
            .phonoSemantic(JIAO_SI_PANG, zi("内"));

    /** 捺 (na4) — right-falling. */
    public static final ChineseCharacterEntry 捺_RIGHT_FALLING = entry("捺")
            .py(N, OPEN, Body.A, Tail.NONE, T4).strokes(11).radical(64)
            .leftRight(TI_SHOU_PANG, zi("奈"))
            .phonoSemantic(TI_SHOU_PANG, zi("奈"));

    public static final List<ChineseCharacterEntry> ALL = List.of(那_THAT, 纳_ACCEPT_PAY, 捺_RIGHT_FALLING);
}
