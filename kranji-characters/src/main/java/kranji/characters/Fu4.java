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

/** Characters pronounced fu (tone 4). */
public final class Fu4 {
    private Fu4() {}

    /** 复 (fu4) — again; return. */
    public static final ChineseCharacterEntry 复_AGAIN_RETURN = entry("复")
            .py(F, U, Body.U, Tail.NONE, T4).strokes(9).radical(34)
            .topBottom(zi("𠂢"), zi("日夂"))
            .compoundIndicative("again; return");

    /** 父 (fu4) — father. */
    public static final ChineseCharacterEntry 父_FATHER = entry("父")
            .py(F, U, Body.U, Tail.NONE, T4).strokes(4).radical(88)
            .singular()
            .pictograph();

    /** 附 (fu4) — attach; near. */
    public static final ChineseCharacterEntry 附_ATTACH_NEAR = entry("附")
            .py(F, U, Body.U, Tail.NONE, T4).strokes(7).radical(170)
            .leftRight(zi("阝"), zi("付"))
            .phonoSemantic(zi("阝"), zi("付"));

    /** 富 (fu4) — rich; wealthy. */
    public static final ChineseCharacterEntry 富_RICH_WEALTHY = entry("富")
            .py(F, U, Body.U, Tail.NONE, T4).strokes(12).radical(40)
            .topBottom(BAO_GAI_TOU, zi("畐"))
            .phonoSemantic(BAO_GAI_TOU, zi("畐"));

    /** 副 (fu4) — deputy; pair. */
    public static final ChineseCharacterEntry 副_DEPUTY_PAIR = entry("副")
            .py(F, U, Body.U, Tail.NONE, T4).strokes(11).radical(18)
            .leftRight(zi("畐"), LI_DAO_PANG)
            .phonoSemantic(LI_DAO_PANG, zi("畐"));

    /** 付 (fu4) — pay. */
    public static final ChineseCharacterEntry 付_PAY = entry("付")
            .py(F, U, Body.U, Tail.NONE, T4).strokes(5).radical(9)
            .leftRight(DAN_REN_PANG, zi("寸"))
            .phonoSemantic(DAN_REN_PANG, zi("寸"));

    /** 妇 (fu4) — woman; wife. */
    public static final ChineseCharacterEntry 妇_WOMAN_WIFE = entry("妇")
            .py(F, U, Body.U, Tail.NONE, T4).strokes(6).radical(38)
            .leftRight(zi("女"), zi("彐"))
            .compoundIndicative("woman; wife");

    /** 腹 (fu4) — abdomen; belly. */
    public static final ChineseCharacterEntry 腹_ABDOMEN_BELLY = entry("腹")
            .py(F, U, Body.U, Tail.NONE, T4).strokes(13).radical(130)
            .leftRight(zi("月"), zi("复"))
            .phonoSemantic(zi("月"), zi("复"));

    public static final List<ChineseCharacterEntry> ALL = List.of(复_AGAIN_RETURN, 父_FATHER, 附_ATTACH_NEAR, 富_RICH_WEALTHY, 副_DEPUTY_PAIR, 付_PAY, 妇_WOMAN_WIFE, 腹_ABDOMEN_BELLY);
}
