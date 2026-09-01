package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code p-}. Seed set. */
public final class P {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.P);

    private P() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(ENG, SECOND, "朋"),
            D.syl(AO, THIRD, "跑")
    );
}
