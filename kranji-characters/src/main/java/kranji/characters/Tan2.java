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

/** Characters pronounced tan (tone 2). */
public final class Tan2 {
    private Tan2() {}

    /** 谈 (tan2) — talk; discuss. */
    public static final ChineseCharacterEntry 谈_TALK_DISCUSS = entry("谈")
            .py(T, OPEN, Body.A, Tail.N, T2).strokes(10).radical(149)
            .leftRight(YAN_ZI_PANG, zi("炎"))
            .phonoSemantic(YAN_ZI_PANG, zi("炎"));

    /** 弹 (tan2) — play; bounce. */
    public static final ChineseCharacterEntry 弹_PLAY_BOUNCE = entry("弹")
            .py(T, OPEN, Body.A, Tail.N, T2).strokes(11).radical(57)
            .leftRight(zi("弓"), zi("单"))
            .phonoSemantic(zi("弓"), zi("单"));

    public static final List<ChineseCharacterEntry> ALL = List.of(谈_TALK_DISCUSS, 弹_PLAY_BOUNCE);
}
