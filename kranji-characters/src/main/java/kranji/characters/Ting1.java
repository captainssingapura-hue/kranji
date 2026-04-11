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

/** Characters pronounced ting (tone 1). */
public final class Ting1 {
    private Ting1() {}

    /** 听 (ting1) — listen; hear. */
    public static final ChineseCharacterEntry 听_LISTEN_HEAR = entry("听")
            .py(T, OPEN, Body.I, Tail.NG, T1).strokes(7).radical(30)
            .leftRight(zi("口"), zi("斤"))
            .phonoSemantic(zi("口"), zi("斤"));

    /** 厅 (ting1) — hall; room. */
    public static final ChineseCharacterEntry 厅_HALL_ROOM = entry("厅")
            .py(T, OPEN, Body.I, Tail.NG, T1).strokes(4).radical(27)
            .semiEnclosureUL(zi("厂"), zi("丁"))
            .phonoSemantic(zi("厂"), zi("丁"));

    public static final List<ChineseCharacterEntry> ALL = List.of(听_LISTEN_HEAR, 厅_HALL_ROOM);
}
