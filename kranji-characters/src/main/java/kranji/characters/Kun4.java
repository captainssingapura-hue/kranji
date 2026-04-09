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

/** Characters pronounced kun (tone 4). */
public final class Kun4 {
    private Kun4() {}

    /** 困 (kun4) — sleepy; trapped. */
    public static final ChineseCharacterEntry 困_SLEEPY_TRAPPED = entry("困")
            .py(K, U, Body.E, Tail.N, T4).strokes(7).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("木"))
            .compoundIndicative("sleepy; trapped");

    public static final List<ChineseCharacterEntry> ALL = List.of(困_SLEEPY_TRAPPED);
}
