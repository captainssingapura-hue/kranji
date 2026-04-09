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

/** Characters pronounced chuan (tone 1). */
public final class Chuan1 {
    private Chuan1() {}

    /** 穿 (chuan1) — wear; through. */
    public static final ChineseCharacterEntry 穿_WEAR_THROUGH = entry("穿")
            .py(CH, U, Body.A, Tail.N, T1).strokes(9).radical(116)
            .topBottom(zi("穴"), zi("牙"))
            .compoundIndicative("wear; through");

    public static final List<ChineseCharacterEntry> ALL = List.of(穿_WEAR_THROUGH);
}
