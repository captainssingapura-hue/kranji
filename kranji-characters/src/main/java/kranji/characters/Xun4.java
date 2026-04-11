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

/** Characters pronounced xun (tone 4). */
public final class Xun4 {
    private Xun4() {}

    /** 训 (xun4) — train; lesson. */
    public static final ChineseCharacterEntry 训_TRAIN_LESSON = entry("训")
            .py(X, V, Body.E, Tail.N, T4).strokes(5).radical(149)
            .leftRight(YAN_ZI_PANG, zi("川"))
            .phonoSemantic(YAN_ZI_PANG, zi("川"));

    public static final List<ChineseCharacterEntry> ALL = List.of(训_TRAIN_LESSON);
}
