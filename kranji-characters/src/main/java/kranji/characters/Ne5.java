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

/** Characters pronounced ne (tone 5). */
public final class Ne5 {
    private Ne5() {}

    /** 呢 (ne5) — modal particle. */
    public static final ChineseCharacterEntry 呢_MODAL_PARTICLE = entry("呢")
            .py(N, OPEN, Body.E, Tail.NONE, T5).strokes(8).radical(30)
            .leftRight(zi("口"), zi("尼"))
            .phonoSemantic(zi("口"), zi("尼"));

    public static final List<ChineseCharacterEntry> ALL = List.of(呢_MODAL_PARTICLE);
}
