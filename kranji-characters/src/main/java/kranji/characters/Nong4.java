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

/** Characters pronounced nong (tone 4). */
public final class Nong4 {
    private Nong4() {}

    /** 弄 (nong4) — play; handle. */
    public static final ChineseCharacterEntry 弄_PLAY_HANDLE = entry("弄")
            .py(N, OPEN, Body.O, Tail.NG, T4).strokes(7).radical(55)
            .topBottom(zi("王"), zi("廾"))
            .compoundIndicative("play; handle");

    public static final List<ChineseCharacterEntry> ALL = List.of(弄_PLAY_HANDLE);
}
