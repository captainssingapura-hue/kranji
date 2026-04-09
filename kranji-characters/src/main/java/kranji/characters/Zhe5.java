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

/** Characters pronounced zhe (tone 5). */
public final class Zhe5 {
    private Zhe5() {}

    /** 着 (zhe5) — aspect marker. */
    public static final ChineseCharacterEntry 着_ASPECT_MARKER = entry("着")
            .py(ZH, OPEN, Body.E, Tail.NONE, T5).strokes(11).radical(109)
            .topBottom(zi("羊"), zi("目"))
            .compoundIndicative("aspect marker");

    public static final List<ChineseCharacterEntry> ALL = List.of(着_ASPECT_MARKER);
}
