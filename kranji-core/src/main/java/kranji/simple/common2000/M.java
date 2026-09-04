package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code m-}. Seed set. */
public final class M {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.M);

    private M() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(ING, SECOND, "明"),
            D.syl(A, THIRD, "马"),
            D.syl(U, FOURTH, "木")
    );
}
