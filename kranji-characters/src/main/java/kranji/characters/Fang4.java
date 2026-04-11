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

/** Characters pronounced fang (tone 4). */
public final class Fang4 {
    private Fang4() {}

    /** 放 (fang4) — put; release. */
    public static final ChineseCharacterEntry 放_PUT_RELEASE = entry("放")
            .py(F, OPEN, Body.A, Tail.NG, T4).strokes(8).radical(66)
            .leftRight(zi("方"), FAN_WEN_PANG)
            .phonoSemantic(FAN_WEN_PANG, zi("方"));

    public static final List<ChineseCharacterEntry> ALL = List.of(放_PUT_RELEASE);
}
