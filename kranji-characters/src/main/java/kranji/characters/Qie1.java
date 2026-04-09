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

/** Characters pronounced qie (tone 1). */
public final class Qie1 {
    private Qie1() {}

    /** 切 (qie1) — cut; all. */
    public static final ChineseCharacterEntry 切_CUT_ALL = entry("切")
            .py(Q, I, Body.E_CARON, Tail.NONE, T1).strokes(4).radical(18)
            .leftRight(zi("七"), zi("刀"))
            .phonoSemantic(zi("刀"), zi("七"));

    public static final List<ChineseCharacterEntry> ALL = List.of(切_CUT_ALL);
}
