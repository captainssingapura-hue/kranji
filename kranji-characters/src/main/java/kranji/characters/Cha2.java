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

/** Characters pronounced cha (tone 2). */
public final class Cha2 {
    private Cha2() {}

    /** 茶 (cha2) — tea. */
    public static final ChineseCharacterEntry 茶_TEA = entry("茶")
            .py(CH, OPEN, Body.A, Tail.NONE, T2).strokes(9).radical(140)
            .topBottom(CAO_ZI_TOU, zi("余"))
            .phonoSemantic(CAO_ZI_TOU, zi("余"));

    /** 察 (cha2) — inspect; observe. */
    public static final ChineseCharacterEntry 察_INSPECT_OBSERVE = entry("察")
            .py(CH, OPEN, Body.A, Tail.NONE, T2).strokes(14).radical(40)
            .topBottom(BAO_GAI_TOU, zi("祭"))
            .phonoSemantic(BAO_GAI_TOU, zi("祭"));

    public static final List<ChineseCharacterEntry> ALL = List.of(茶_TEA, 察_INSPECT_OBSERVE);
}
