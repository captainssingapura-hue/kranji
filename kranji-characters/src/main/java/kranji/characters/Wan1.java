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

/** Characters pronounced wan (tone 1). */
public final class Wan1 {
    private Wan1() {}

    /** 湾 (wan1) — bay; gulf. */
    public static final ChineseCharacterEntry 湾_BAY_GULF = entry("湾")
            .py(ZERO, U, Body.A, Tail.N, T1).strokes(12).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("弯"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("弯"));

    /** 弯 (wan1) — bend; curve. */
    public static final ChineseCharacterEntry 弯_BEND_CURVE = entry("弯")
            .py(ZERO, U, Body.A, Tail.N, T1).strokes(9).radical(57)
            .topBottom(zi("亦"), zi("弓"))
            .phonoSemantic(zi("弓"), zi("亦"));

    public static final List<ChineseCharacterEntry> ALL = List.of(湾_BAY_GULF, 弯_BEND_CURVE);
}
