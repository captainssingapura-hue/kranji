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

/** Characters pronounced yin (tone 1). */
public final class Yin1 {
    private Yin1() {}

    /** 因 (yin1) — because. */
    public static final ChineseCharacterEntry 因_BECAUSE = entry("因")
            .py(ZERO, OPEN, Body.I, Tail.N, T1).strokes(6).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("大"))
            .compoundIndicative("because");

    /** 音 (yin1) — sound; music. */
    public static final ChineseCharacterEntry 音_SOUND_MUSIC = entry("音")
            .py(ZERO, OPEN, Body.I, Tail.N, T1).strokes(9).radical(180)
            .singular()
            .compoundIndicative("sound; music");

    /** 阴 (yin1) — cloudy; dark. */
    public static final ChineseCharacterEntry 阴_CLOUDY_DARK = entry("阴")
            .py(ZERO, OPEN, Body.I, Tail.N, T1).strokes(6).radical(170)
            .leftRight(zi("阝"), zi("月"))
            .phonoSemantic(zi("阝"), zi("月"));

    public static final List<ChineseCharacterEntry> ALL = List.of(因_BECAUSE, 音_SOUND_MUSIC, 阴_CLOUDY_DARK);
}
