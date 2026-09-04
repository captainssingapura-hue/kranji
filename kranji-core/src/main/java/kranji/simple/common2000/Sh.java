package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code sh-}. Seed set. */
public final class Sh {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.SH);

    private Sh() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(AN, FIRST, "山"),
            D.syl(UEI, THIRD, "水"),
            D.syl(SYLLABIC, FOURTH, "是")
    );
}
