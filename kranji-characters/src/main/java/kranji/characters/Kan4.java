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

/** Characters pronounced kan (tone 4). */
public final class Kan4 {
    private Kan4() {}

    /** 看 (kan4) — look; see. */
    public static final ChineseCharacterEntry 看_LOOK_SEE = entry("看")
            .py(K, OPEN, Body.A, Tail.N, T4).strokes(9).radical(109)
            .topBottom(zi("手"), zi("目"))
            .compoundIndicative("look; see");

    public static final List<ChineseCharacterEntry> ALL = List.of(看_LOOK_SEE);
}
