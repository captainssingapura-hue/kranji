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

/** Characters pronounced sheng (tone 1). */
public final class Sheng1 {
    private Sheng1() {}

    /** 生 (sheng1) — life; born. */
    public static final ChineseCharacterEntry 生_LIFE_BORN = entry("生")
            .py(SH, OPEN, Body.E, Tail.NG, T1).strokes(5).radical(100)
            .singular()
            .pictograph();

    /** 声 (sheng1) — sound; voice. */
    public static final ChineseCharacterEntry 声_SOUND_VOICE = entry("声")
            .py(SH, OPEN, Body.E, Tail.NG, T1).strokes(7).radical(33)
            .topBottom(zi("士"), zi("声"))
            .phonoSemantic(zi("耳"), zi("殸"));

    /** 升 (sheng1) — rise; liter. */
    public static final ChineseCharacterEntry 升_RISE_LITER = entry("升")
            .py(SH, OPEN, Body.E, Tail.NG, T1).strokes(4).radical(24)
            .singular()
            .indicative("rise; liter");

    public static final List<ChineseCharacterEntry> ALL = List.of(生_LIFE_BORN, 声_SOUND_VOICE, 升_RISE_LITER);
}
