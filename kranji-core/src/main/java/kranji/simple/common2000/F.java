package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code f-}. Seed set. */
public final class F {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.F);

    private F() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(ENG, FIRST, "风"),
            D.syl(EI, FIRST, "飞")
    );
}
