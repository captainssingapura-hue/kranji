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

/** Characters pronounced xiao (tone 1). */
public final class Xiao1 {
    private Xiao1() {}

    /** 消 (xiao1) — eliminate; news. */
    public static final ChineseCharacterEntry 消_ELIMINATE_NEWS = entry("消")
            .py(X, I, Body.A, Tail.VOWEL_U, T1).strokes(10).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("肖"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("肖"));

    public static final List<ChineseCharacterEntry> ALL = List.of(消_ELIMINATE_NEWS);
}
