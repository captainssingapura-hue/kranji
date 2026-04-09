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

/** Characters pronounced du (tone 2). */
public final class Du2 {
    private Du2() {}

    /** 读 (du2) — read. */
    public static final ChineseCharacterEntry 读_READ = entry("读")
            .py(D, U, Body.U, Tail.NONE, T2).strokes(10).radical(149)
            .leftRight(YAN_ZI_PANG, zi("卖"))
            .phonoSemantic(YAN_ZI_PANG, zi("卖"));

    /** 毒 (du2) — poison; toxic. */
    public static final ChineseCharacterEntry 毒_POISON_TOXIC = entry("毒")
            .py(D, U, Body.U, Tail.NONE, T2).strokes(9).radical(80)
            .topBottom(zi("龶"), zi("母"))
            .compoundIndicative("poison; toxic");

    /** 独 (du2) — alone; single. */
    public static final ChineseCharacterEntry 独_ALONE_SINGLE = entry("独")
            .py(D, U, Body.U, Tail.NONE, T2).strokes(9).radical(94)
            .leftRight(FAN_QUAN_PANG, zi("虫"))
            .phonoSemantic(FAN_QUAN_PANG, zi("虫"));

    public static final List<ChineseCharacterEntry> ALL = List.of(读_READ, 毒_POISON_TOXIC, 独_ALONE_SINGLE);
}
