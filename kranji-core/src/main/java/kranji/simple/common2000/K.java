package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code k-}. Seed set. */
public final class K {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.K);

    private K() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(AI, FIRST, "开"),
            D.syl(AN, FOURTH, "看")
    );
}
