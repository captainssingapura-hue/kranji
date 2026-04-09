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

/** Characters pronounced zu (tone 3). */
public final class Zu3 {
    private Zu3() {}

    /** 组 (zu3) — group; organize. */
    public static final ChineseCharacterEntry 组_GROUP_ORGANIZE = entry("组")
            .py(Z, U, Body.U, Tail.NONE, T3).strokes(8).radical(120)
            .leftRight(JIAO_SI_PANG, zi("且"))
            .phonoSemantic(JIAO_SI_PANG, zi("且"));

    public static final List<ChineseCharacterEntry> ALL = List.of(组_GROUP_ORGANIZE);
}
