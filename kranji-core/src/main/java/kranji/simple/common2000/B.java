package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code b-}. Seed set. */
public final class B {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.B);

    private B() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(A, FIRST, "八"),
            D.syl(AI, SECOND, "白"),
            D.syl(EI, THIRD, "北")
    );
}
