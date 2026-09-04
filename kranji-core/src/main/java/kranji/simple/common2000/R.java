package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code r-}. Seed set. */
public final class R {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.R);

    private R() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(EN, SECOND, "人"),
            D.syl(SYLLABIC, FOURTH, "日")
    );
}
