package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code l-}. Seed set. */
public final class L {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.L);

    private L() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(AI, SECOND, "来"),
            D.syl(U, FOURTH, "路"),
            D.syl(V, FOURTH, "绿")
    );
}
