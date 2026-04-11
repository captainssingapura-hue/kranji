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

/** Characters pronounced guan (tone 1). */
public final class Guan1 {
    private Guan1() {}

    /** 关 (guan1) — close; related. */
    public static final ChineseCharacterEntry 关_CLOSE_RELATED = entry("关")
            .py(G, U, Body.A, Tail.N, T1).strokes(6).radical(12)
            .topBottom(zi("丷"), zi("天"))
            .compoundIndicative("close; related");

    /** 观 (guan1) — view; observe. */
    public static final ChineseCharacterEntry 观_VIEW_OBSERVE = entry("观")
            .py(G, U, Body.A, Tail.N, T1).strokes(6).radical(147)
            .leftRight(zi("又"), zi("见"))
            .phonoSemantic(zi("见"), zi("又"));

    public static final List<ChineseCharacterEntry> ALL = List.of(关_CLOSE_RELATED, 观_VIEW_OBSERVE);
}
