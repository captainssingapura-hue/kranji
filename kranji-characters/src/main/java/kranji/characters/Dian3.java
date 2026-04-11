package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced dian (tone 3). */
public final class Dian3 {
    private Dian3() {}

    /** 点 (dian3) — point; dot. */
    public static final ChineseCharacterEntry 点_POINT_DOT = entry("点")
            .py(D, I, Body.A, Tail.N, T3).strokes(9).radical(86)
            .topBottom(zi("占"), SI_DIAN_DI)
            .phonoSemantic(SI_DIAN_DI, zi("占"));

    public static final List<ChineseCharacterEntry> ALL = List.of(点_POINT_DOT);
}
