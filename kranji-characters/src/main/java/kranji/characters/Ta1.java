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

/** Characters pronounced ta (tone 1). */
public final class Ta1 {
    private Ta1() {}

    /** 他 (ta1) — he; him. */
    public static final ChineseCharacterEntry 他_HE_HIM = entry("他")
            .py(T, OPEN, Body.A, Tail.NONE, T1).strokes(5).radical(9)
            .leftRight(DAN_REN_PANG, zi("也"))
            .phonoSemantic(DAN_REN_PANG, zi("也"));

    /** 她 (ta1) — she; her. */
    public static final ChineseCharacterEntry 她_SHE_HER = entry("她")
            .py(T, OPEN, Body.A, Tail.NONE, T1).strokes(6).radical(38)
            .leftRight(zi("女"), zi("也"))
            .phonoSemantic(zi("女"), zi("也"));

    public static final List<ChineseCharacterEntry> ALL = List.of(他_HE_HIM, 她_SHE_HER);
}
