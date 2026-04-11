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

/** Characters pronounced biang (tone 2). */
public final class Biang2 {
    private Biang2() {}

    /** 𰻝 (biang) — biang noodles. SemiEnclosureBL: deeply recursive. Compound indicative. */
    public static final ChineseCharacterEntry 𰻝_BIANG = entry("𰻝")
            .py(B, I, Body.A, Tail.NG, T2).strokes(58).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI,
                    tmb(zi("穴"),
                            lmr(zi("月"),
                                    tb(lmr(zi("幺"), zi("言"), zi("幺")),
                                            lmr(zi("长"), zi("马"), zi("长"))),
                                    LI_DAO_PANG),
                            zi("心")))
            .compoundIndicative("folk-coined character for the sound biang — "
                    + "the slapping noise of noodle dough pulled and struck against a board");

    public static final List<ChineseCharacterEntry> ALL = List.of(𰻝_BIANG);
}
