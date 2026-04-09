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

/** Characters pronounced you (tone 4). */
public final class You4 {
    private You4() {}

    /** 又 (you4) — again; also. */
    public static final ChineseCharacterEntry 又_AGAIN_ALSO = entry("又")
            .py(ZERO, I, Body.O, Tail.VOWEL_U, T4).strokes(2).radical(29)
            .singular()
            .pictograph();

    /** 右 (you4) — right (side). */
    public static final ChineseCharacterEntry 右_RIGHT_SIDE = entry("右")
            .py(ZERO, I, Body.O, Tail.VOWEL_U, T4).strokes(5).radical(30)
            .topBottom(zi("𠂇"), zi("口"))
            .compoundIndicative("right (side)");

    public static final List<ChineseCharacterEntry> ALL = List.of(又_AGAIN_ALSO, 右_RIGHT_SIDE);
}
