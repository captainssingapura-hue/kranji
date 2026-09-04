package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SimpleZiDsl;
import kranji.simple.SyllableDecl;
import kranji.simple.ZiDecl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/**
 * Characters whose reading begins with {@code h-}.
 *
 * <p>Declared syllable-first: a final and tone once, then the characters read
 * that way. A polyphonic character simply appears under each of its readings —
 * emphasised at the one that is principal, and {@link ZiDecl#alt} elsewhere.
 * Its alternates may well live in another partition file, since a character's
 * readings need not share an initial.</p>
 */
public final class H {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.H);

    private H() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(A,    FIRST,  "哈"),
            D.syl(AI,   SECOND, "还"),
            D.syl(AI,   THIRD,  "海"),
            D.syl(AI,   FOURTH, "害"),
            D.syl(AN,   SECOND, "含", "寒"),
            D.syl(AN,   FOURTH, "汉"),
            D.syl(ANG,  SECOND, "航"),
            D.syl(AO,   THIRD,  "好"),
            D.syl(AO,   FOURTH, ZiDecl.alt("好")),          // 好 is principally hǎo
            D.syl(E,    FIRST,  "喝"),
            D.syl(E,    SECOND, "河", "何", "合", "和"),
            D.syl(E,    FOURTH, ZiDecl.alt("和")),          // 和 is principally hé
            D.syl(EI,   FIRST,  "黑"),
            D.syl(EN,   THIRD,  "很"),
            D.syl(ONG,  SECOND, "红"),
            D.syl(OU,   FOURTH, "后"),
            D.syl(U,    FIRST,  "呼"),
            D.syl(U,    SECOND, "湖", "胡"),
            D.syl(U,    FOURTH, "户"),
            D.syl(UA,   FIRST,  "花"),
            D.syl(UA,   FOURTH, "话", "画"),
            D.syl(UAN,  FIRST,  "欢"),
            D.syl(UAN,  SECOND, ZiDecl.alt("还")),          // 还 is principally hái
            D.syl(UANG, SECOND, "黄"),
            D.syl(UEI,  SECOND, "回"),
            D.syl(UEI,  FOURTH, "会"),
            D.syl(UO,   SECOND, ZiDecl.alt("和")),          // 和 also reads huó
            D.syl(UO,   THIRD,  "火"),
            D.syl(UO,   FOURTH, "或"));
}
