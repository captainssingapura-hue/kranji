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

/** Characters pronounced pan (tone 2). */
public final class Pan2 {
    private Pan2() {}

    /** 盘 (pan2) — plate; disc. */
    public static final ChineseCharacterEntry 盘_PLATE_DISC = entry("盘")
            .py(P, OPEN, Body.A, Tail.N, T2).strokes(11).radical(108)
            .topBottom(zi("舟"), zi("皿"))
            .phonoSemantic(zi("皿"), zi("般"));

    public static final List<ChineseCharacterEntry> ALL = List.of(盘_PLATE_DISC);
}
