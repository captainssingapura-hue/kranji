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

/** Characters pronounced hui (tone 2). */
public final class Hui2 {
    private Hui2() {}

    /** 回 (hui2) — return. */
    public static final ChineseCharacterEntry 回_RETURN = entry("回")
            .py(H, U, Body.E, Tail.VOWEL_I, T2).strokes(6).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("口"))
            .compoundIndicative("return");

    public static final List<ChineseCharacterEntry> ALL = List.of(回_RETURN);
}
