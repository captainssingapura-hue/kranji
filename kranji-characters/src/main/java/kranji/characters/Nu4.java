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

/** Characters pronounced nu (tone 4). */
public final class Nu4 {
    private Nu4() {}

    /** 怒 (nu4) — anger; rage. */
    public static final ChineseCharacterEntry 怒_ANGER_RAGE = entry("怒")
            .py(N, U, Body.U, Tail.NONE, T4).strokes(9).radical(61)
            .topBottom(zi("奴"), zi("心"))
            .phonoSemantic(zi("心"), zi("奴"));

    public static final List<ChineseCharacterEntry> ALL = List.of(怒_ANGER_RAGE);
}
