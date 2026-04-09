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

/** Characters pronounced cu (tone 1). */
public final class Cu1 {
    private Cu1() {}

    /** 粗 (cu1) — thick; rough. */
    public static final ChineseCharacterEntry 粗_THICK_ROUGH = entry("粗")
            .py(C, U, Body.U, Tail.NONE, T1).strokes(11).radical(119)
            .leftRight(zi("米"), zi("且"))
            .phonoSemantic(zi("米"), zi("且"));

    public static final List<ChineseCharacterEntry> ALL = List.of(粗_THICK_ROUGH);
}
