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

/** Characters pronounced lv (tone 4). */
public final class Lv4 {
    private Lv4() {}

    /** 率 (lv4) — rate; lead. */
    public static final ChineseCharacterEntry 率_RATE_LEAD = entry("率")
            .py(L, V, Body.V, Tail.NONE, T4).strokes(11).radical(95)
            .singular()
            .compoundIndicative("rate; lead");

    /** 绿 (lv4) — green. */
    public static final ChineseCharacterEntry 绿_GREEN = entry("绿")
            .py(L, V, Body.V, Tail.NONE, T4).strokes(11).radical(120)
            .leftRight(JIAO_SI_PANG, zi("录"))
            .phonoSemantic(JIAO_SI_PANG, zi("录"));

    /** 虑 (lv4) — worry; consider. */
    public static final ChineseCharacterEntry 虑_WORRY_CONSIDER = entry("虑")
            .py(L, V, Body.V, Tail.NONE, T4).strokes(10).radical(141)
            .semiEnclosureUL(zi("虍"), zi("思"))
            .phonoSemantic(zi("虍"), zi("思"));

    public static final List<ChineseCharacterEntry> ALL = List.of(率_RATE_LEAD, 绿_GREEN, 虑_WORRY_CONSIDER);
}
