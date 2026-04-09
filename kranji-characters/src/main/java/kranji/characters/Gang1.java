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

/** Characters pronounced gang (tone 1). */
public final class Gang1 {
    private Gang1() {}

    /** 刚 (gang1) — just now; firm. */
    public static final ChineseCharacterEntry 刚_JUST_NOW_FIRM = entry("刚")
            .py(G, OPEN, Body.A, Tail.NG, T1).strokes(6).radical(18)
            .leftRight(zi("冈"), LI_DAO_PANG)
            .phonoSemantic(LI_DAO_PANG, zi("冈"));

    public static final List<ChineseCharacterEntry> ALL = List.of(刚_JUST_NOW_FIRM);
}
