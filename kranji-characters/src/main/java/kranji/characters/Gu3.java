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

/** Characters pronounced gu (tone 3). */
public final class Gu3 {
    private Gu3() {}

    /** 古 (gu3) — ancient; old. */
    public static final ChineseCharacterEntry 古_ANCIENT_OLD = entry("古")
            .py(G, U, Body.U, Tail.NONE, T3).strokes(5).radical(30)
            .topBottom(zi("十"), zi("口"))
            .compoundIndicative("ancient; old");

    /** 鼓 (gu3) — drum; beat. */
    public static final ChineseCharacterEntry 鼓_DRUM_BEAT = entry("鼓")
            .py(G, U, Body.U, Tail.NONE, T3).strokes(13).radical(207)
            .leftRight(zi("壴"), zi("支"))
            .compoundIndicative("drum; beat");

    public static final List<ChineseCharacterEntry> ALL = List.of(古_ANCIENT_OLD, 鼓_DRUM_BEAT);
}
