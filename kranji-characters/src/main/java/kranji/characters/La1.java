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

/** Characters pronounced la (tone 1). */
public final class La1 {
    private La1() {}

    /** 拉 (la1) — pull; drag. */
    public static final ChineseCharacterEntry 拉_PULL_DRAG = entry("拉")
            .py(L, OPEN, Body.A, Tail.NONE, T1).strokes(8).radical(64)
            .leftRight(TI_SHOU_PANG, zi("立"))
            .phonoSemantic(TI_SHOU_PANG, zi("立"));

    public static final List<ChineseCharacterEntry> ALL = List.of(拉_PULL_DRAG);
}
