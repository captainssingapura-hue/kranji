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

/** Characters pronounced tiao (tone 4). */
public final class Tiao4 {
    private Tiao4() {}

    /** 跳 (tiao4) — jump; skip. */
    public static final ChineseCharacterEntry 跳_JUMP_SKIP = entry("跳")
            .py(T, I, Body.A, Tail.VOWEL_U, T4).strokes(13).radical(157)
            .leftRight(zi("足"), zi("兆"))
            .phonoSemantic(zi("足"), zi("兆"));

    public static final List<ChineseCharacterEntry> ALL = List.of(跳_JUMP_SKIP);
}
