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

/** Characters pronounced che (tone 1). */
public final class Che1 {
    private Che1() {}

    /** 车 (che1) — car; vehicle. */
    public static final ChineseCharacterEntry 车_CAR_VEHICLE = entry("车")
            .py(CH, OPEN, Body.E, Tail.NONE, T1).strokes(4).radical(159)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(车_CAR_VEHICLE);
}
