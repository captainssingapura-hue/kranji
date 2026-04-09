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

/** Characters pronounced zhun (tone 3). */
public final class Zhun3 {
    private Zhun3() {}

    /** 准 (zhun3) — allow; standard. */
    public static final ChineseCharacterEntry 准_ALLOW_STANDARD = entry("准")
            .py(ZH, U, Body.E, Tail.N, T3).strokes(10).radical(15)
            .leftRight(LIANG_DIAN_SHUI, zi("隹"))
            .phonoSemantic(LIANG_DIAN_SHUI, zi("隹"));

    public static final List<ChineseCharacterEntry> ALL = List.of(准_ALLOW_STANDARD);
}
