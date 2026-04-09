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

/** Characters pronounced qiong (tone 2). */
public final class Qiong2 {
    private Qiong2() {}

    /** 穷 (qiong2) — poor. */
    public static final ChineseCharacterEntry 穷_POOR = entry("穷")
            .py(Q, I, Body.O, Tail.NG, T2).strokes(7).radical(116)
            .topBottom(zi("穴"), zi("力"))
            .phonoSemantic(zi("穴"), zi("力"));

    public static final List<ChineseCharacterEntry> ALL = List.of(穷_POOR);
}
