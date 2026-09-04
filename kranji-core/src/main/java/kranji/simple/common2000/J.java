package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code j-}. Seed set. */
public final class J {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.J);

    private J() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(IA, FIRST, "家"),
            D.syl(IN, FIRST, "金")
    );
}
