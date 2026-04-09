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

/** Characters pronounced ruo (tone 4). */
public final class Ruo4 {
    private Ruo4() {}

    /** 弱 (ruo4) — weak. */
    public static final ChineseCharacterEntry 弱_WEAK = entry("弱")
            .py(R, U, Body.O, Tail.NONE, T4).strokes(10).radical(57)
            .leftRight(zi("弓"), zi("弓"))
            .compoundIndicative("weak");

    public static final List<ChineseCharacterEntry> ALL = List.of(弱_WEAK);
}
