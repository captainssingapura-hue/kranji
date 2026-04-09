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

/** Characters pronounced guang (tone 1). */
public final class Guang1 {
    private Guang1() {}

    /** 光 (guang1) — light; ray. */
    public static final ChineseCharacterEntry 光_LIGHT_RAY = entry("光")
            .py(G, U, Body.A, Tail.NG, T1).strokes(6).radical(10)
            .topBottom(zi("⺌"), zi("儿"))
            .compoundIndicative("light; ray");

    public static final List<ChineseCharacterEntry> ALL = List.of(光_LIGHT_RAY);
}
