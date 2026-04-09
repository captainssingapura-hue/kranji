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

/** Characters pronounced ti (tone 2). */
public final class Ti2 {
    private Ti2() {}

    /** 提 (ti2) — raise; mention. */
    public static final ChineseCharacterEntry 提_RAISE_MENTION = entry("提")
            .py(T, OPEN, Body.I, Tail.NONE, T2).strokes(12).radical(64)
            .leftRight(TI_SHOU_PANG, zi("是"))
            .phonoSemantic(TI_SHOU_PANG, zi("是"));

    /** 题 (ti2) — topic; problem. */
    public static final ChineseCharacterEntry 题_TOPIC_PROBLEM = entry("题")
            .py(T, OPEN, Body.I, Tail.NONE, T2).strokes(15).radical(181)
            .leftRight(zi("是"), zi("页"))
            .phonoSemantic(zi("页"), zi("是"));

    public static final List<ChineseCharacterEntry> ALL = List.of(提_RAISE_MENTION, 题_TOPIC_PROBLEM);
}
