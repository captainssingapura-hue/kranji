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

/** Characters pronounced yun (tone 4). */
public final class Yun4 {
    private Yun4() {}

    /** 运 (yun4) — transport; luck. */
    public static final ChineseCharacterEntry 运_TRANSPORT_LUCK = entry("运")
            .py(ZERO, V, Body.E, Tail.N, T4).strokes(7).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("云"))
            .phonoSemantic(ZOU_ZHI_DI, zi("云"));

    public static final List<ChineseCharacterEntry> ALL = List.of(运_TRANSPORT_LUCK);
}
