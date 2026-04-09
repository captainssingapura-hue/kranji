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

/** Characters pronounced zu (tone 2). */
public final class Zu2 {
    private Zu2() {}

    /** 足 (zu2) — foot; enough. */
    public static final ChineseCharacterEntry 足_FOOT_ENOUGH = entry("足")
            .py(Z, U, Body.U, Tail.NONE, T2).strokes(7).radical(157)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(足_FOOT_ENOUGH);
}
