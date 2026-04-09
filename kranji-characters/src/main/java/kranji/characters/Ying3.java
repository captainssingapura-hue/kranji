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

/** Characters pronounced ying (tone 3). */
public final class Ying3 {
    private Ying3() {}

    /** 影 (ying3) — shadow; film. */
    public static final ChineseCharacterEntry 影_SHADOW_FILM = entry("影")
            .py(ZERO, OPEN, Body.I, Tail.NG, T3).strokes(15).radical(59)
            .leftRight(zi("景"), zi("彡"))
            .phonoSemantic(zi("彡"), zi("景"));

    public static final List<ChineseCharacterEntry> ALL = List.of(影_SHADOW_FILM);
}
