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

/** Characters pronounced jing (tone 4). */
public final class Jing4 {
    private Jing4() {}

    /** 静 (jing4) — quiet; still. */
    public static final ChineseCharacterEntry 静_QUIET_STILL = entry("静")
            .py(J, OPEN, Body.I, Tail.NG, T4).strokes(14).radical(174)
            .leftRight(zi("青"), zi("争"))
            .phonoSemantic(zi("青"), zi("争"));

    /** 境 (jing4) — boundary; state. */
    public static final ChineseCharacterEntry 境_BOUNDARY_STATE = entry("境")
            .py(J, OPEN, Body.I, Tail.NG, T4).strokes(14).radical(32)
            .leftRight(zi("土"), zi("竟"))
            .phonoSemantic(zi("土"), zi("竟"));

    public static final List<ChineseCharacterEntry> ALL = List.of(静_QUIET_STILL, 境_BOUNDARY_STATE);
}
