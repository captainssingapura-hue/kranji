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

/** Characters pronounced ren (tone 4). */
public final class Ren4 {
    private Ren4() {}

    /** 任 (ren4) — appoint; let. */
    public static final ChineseCharacterEntry 任_APPOINT_LET = entry("任")
            .py(R, OPEN, Body.E, Tail.N, T4).strokes(6).radical(9)
            .leftRight(DAN_REN_PANG, zi("壬"))
            .phonoSemantic(DAN_REN_PANG, zi("壬"));

    /** 认 (ren4) — recognize. */
    public static final ChineseCharacterEntry 认_RECOGNIZE = entry("认")
            .py(R, OPEN, Body.E, Tail.N, T4).strokes(4).radical(149)
            .leftRight(YAN_ZI_PANG, zi("人"))
            .phonoSemantic(YAN_ZI_PANG, zi("人"));

    public static final List<ChineseCharacterEntry> ALL = List.of(任_APPOINT_LET, 认_RECOGNIZE);
}
