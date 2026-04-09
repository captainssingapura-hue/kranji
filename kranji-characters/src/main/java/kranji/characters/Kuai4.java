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

/** Characters pronounced kuai (tone 4). */
public final class Kuai4 {
    private Kuai4() {}

    /** 快 (kuai4) — fast; happy. */
    public static final ChineseCharacterEntry 快_FAST_HAPPY = entry("快")
            .py(K, U, Body.A, Tail.VOWEL_I, T4).strokes(7).radical(61)
            .leftRight(SHU_XIN_PANG, zi("夬"))
            .phonoSemantic(SHU_XIN_PANG, zi("夬"));

    /** 块 (kuai4) — piece; chunk. */
    public static final ChineseCharacterEntry 块_PIECE_CHUNK = entry("块")
            .py(K, U, Body.A, Tail.VOWEL_I, T4).strokes(7).radical(32)
            .leftRight(zi("土"), zi("夬"))
            .phonoSemantic(zi("土"), zi("夬"));

    /** 筷 (kuai4) — chopstick. */
    public static final ChineseCharacterEntry 筷_CHOPSTICK = entry("筷")
            .py(K, U, Body.A, Tail.VOWEL_I, T4).strokes(13).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("快"))
            .phonoSemantic(ZHU_ZI_TOU, zi("快"));

    public static final List<ChineseCharacterEntry> ALL = List.of(快_FAST_HAPPY, 块_PIECE_CHUNK, 筷_CHOPSTICK);
}
