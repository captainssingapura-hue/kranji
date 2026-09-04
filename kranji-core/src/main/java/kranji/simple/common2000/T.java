package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code t-}. Seed set. */
public final class T {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.T);

    private T() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(IAN, FIRST, "天"),
            D.syl(U, THIRD, "土")
    );
}
