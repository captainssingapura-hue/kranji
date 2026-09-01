package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code n-}. Seed set. */
public final class N {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.N);

    private N() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(I, THIRD, "你"),
            D.syl(V, THIRD, "女")
    );
}
