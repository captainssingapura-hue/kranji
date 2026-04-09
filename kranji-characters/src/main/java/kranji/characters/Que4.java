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

/** Characters pronounced que (tone 4). */
public final class Que4 {
    private Que4() {}

    /** 确 (que4) — sure; indeed. */
    public static final ChineseCharacterEntry 确_SURE_INDEED = entry("确")
            .py(Q, V, Body.E_CARON, Tail.NONE, T4).strokes(12).radical(112)
            .leftRight(zi("石"), zi("角"))
            .phonoSemantic(zi("石"), zi("角"));

    public static final List<ChineseCharacterEntry> ALL = List.of(确_SURE_INDEED);
}
