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

/** Characters pronounced hu (tone 4). */
public final class Hu4 {
    private Hu4() {}

    /** 护 (hu4) — protect; guard. */
    public static final ChineseCharacterEntry 护_PROTECT_GUARD = entry("护")
            .py(H, U, Body.U, Tail.NONE, T4).strokes(7).radical(64)
            .leftRight(TI_SHOU_PANG, zi("户"))
            .phonoSemantic(TI_SHOU_PANG, zi("户"));

    /** 互 (hu4) — mutual. */
    public static final ChineseCharacterEntry 互_MUTUAL = entry("互")
            .py(H, U, Body.U, Tail.NONE, T4).strokes(4).radical(7)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(护_PROTECT_GUARD, 互_MUTUAL);
}
