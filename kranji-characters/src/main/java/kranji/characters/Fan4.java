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

/** Characters pronounced fan (tone 4). */
public final class Fan4 {
    private Fan4() {}

    /** 范 (fan4) — model; scope. */
    public static final ChineseCharacterEntry 范_MODEL_SCOPE = entry("范")
            .py(F, OPEN, Body.A, Tail.N, T4).strokes(8).radical(140)
            .topBottom(CAO_ZI_TOU, zi("范"))
            .phonoSemantic(CAO_ZI_TOU, zi("范"));

    public static final List<ChineseCharacterEntry> ALL = List.of(范_MODEL_SCOPE);
}
