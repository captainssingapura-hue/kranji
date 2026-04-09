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

/** Characters pronounced can (tone 2). */
public final class Can2 {
    private Can2() {}

    /** 残 (can2) — cruel; remain. */
    public static final ChineseCharacterEntry 残_CRUEL_REMAIN = entry("残")
            .py(C, OPEN, Body.A, Tail.N, T2).strokes(9).radical(78)
            .leftRight(zi("歹"), zi("戋"))
            .phonoSemantic(zi("歹"), zi("戋"));

    public static final List<ChineseCharacterEntry> ALL = List.of(残_CRUEL_REMAIN);
}
