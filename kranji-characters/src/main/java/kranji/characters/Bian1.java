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

/** Characters pronounced bian (tone 1). */
public final class Bian1 {
    private Bian1() {}

    /** 边 (bian1) — side; edge. */
    public static final ChineseCharacterEntry 边_SIDE_EDGE = entry("边")
            .py(B, I, Body.A, Tail.N, T1).strokes(5).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("力"))
            .phonoSemantic(ZOU_ZHI_DI, zi("力"));

    public static final List<ChineseCharacterEntry> ALL = List.of(边_SIDE_EDGE);
}
