package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code q-}. Seed set. */
public final class Q {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.Q);

    private Q() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(ING, FIRST, "青"),
            D.syl(V, FOURTH, "去")
    );
}
