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

/** Characters pronounced tu (tone 2). */
public final class Tu2 {
    private Tu2() {}

    /** 图 (tu2) — picture; map. */
    public static final ChineseCharacterEntry 图_PICTURE_MAP = entry("图")
            .py(T, U, Body.U, Tail.NONE, T2).strokes(8).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("冬"))
            .phonoSemantic(GUO_ZI_KUANG, zi("冬"));

    public static final List<ChineseCharacterEntry> ALL = List.of(图_PICTURE_MAP);
}
