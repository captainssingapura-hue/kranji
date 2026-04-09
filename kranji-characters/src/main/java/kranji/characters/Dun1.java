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

/** Characters pronounced dun (tone 1). */
public final class Dun1 {
    private Dun1() {}

    /** 蹲 (dun1) — squat; crouch. */
    public static final ChineseCharacterEntry 蹲_SQUAT_CROUCH = entry("蹲")
            .py(D, U, Body.E, Tail.N, T1).strokes(19).radical(157)
            .leftRight(zi("足"), zi("尊"))
            .phonoSemantic(zi("足"), zi("尊"));

    public static final List<ChineseCharacterEntry> ALL = List.of(蹲_SQUAT_CROUCH);
}
