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

/** Characters pronounced ming (tone 2). */
public final class Ming2 {
    private Ming2() {}

    /** 明 (ming) — bright. LeftRight: 日 + 月. Compound indicative. */
    public static final ChineseCharacterEntry 明_BRIGHT = entry("明")
            .py(M, OPEN, Body.I, Tail.NG, T2).strokes(8).radical(72)
            .leftRight(zi("日"), zi("月"))
            .compoundIndicative("日(sun) + 月(moon) → bright");

    public static final List<ChineseCharacterEntry> ALL = List.of(明_BRIGHT);
}
