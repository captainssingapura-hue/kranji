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

/** Characters pronounced lv (tone 3). */
public final class Lv3 {
    private Lv3() {}

    /** 旅 (lv3) — travel; trip. */
    public static final ChineseCharacterEntry 旅_TRAVEL_TRIP = entry("旅")
            .py(L, V, Body.V, Tail.NONE, T3).strokes(10).radical(70)
            .leftRight(zi("方"), zi("㫃"))
            .compoundIndicative("travel; trip");

    public static final List<ChineseCharacterEntry> ALL = List.of(旅_TRAVEL_TRIP);
}
