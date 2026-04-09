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

/** Characters pronounced gui (tone 4). */
public final class Gui4 {
    private Gui4() {}

    /** 跪 (gui4) — kneel. */
    public static final ChineseCharacterEntry 跪_KNEEL = entry("跪")
            .py(G, U, Body.E, Tail.VOWEL_I, T4).strokes(13).radical(157)
            .leftRight(zi("足"), zi("危"))
            .phonoSemantic(zi("足"), zi("危"));

    public static final List<ChineseCharacterEntry> ALL = List.of(跪_KNEEL);
}
