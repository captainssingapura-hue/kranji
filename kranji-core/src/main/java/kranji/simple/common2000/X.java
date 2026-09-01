package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code x-}. Seed set. */
public final class X {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.X);

    private X() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(IAO, THIRD, "小"),
            D.syl(VE, THIRD, "雪")
    );
}
