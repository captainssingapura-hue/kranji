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

/** Characters pronounced gai (tone 4). */
public final class Gai4 {
    private Gai4() {}

    /** 概 (gai4) — general; rough. */
    public static final ChineseCharacterEntry 概_GENERAL_ROUGH = entry("概")
            .py(G, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(13).radical(75)
            .leftRight(MU, zi("既"))
            .phonoSemantic(MU, zi("既"));

    /** 盖 (gai4) — cover; lid. */
    public static final ChineseCharacterEntry 盖_COVER_LID = entry("盖")
            .py(G, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(11).radical(108)
            .topBottom(zi("盍"), zi("皿"))
            .phonoSemantic(zi("皿"), zi("盍"));

    public static final List<ChineseCharacterEntry> ALL = List.of(概_GENERAL_ROUGH, 盖_COVER_LID);
}
