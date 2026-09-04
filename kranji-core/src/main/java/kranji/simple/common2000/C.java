package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code c-}. Seed set. */
public final class C {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.C);

    private C() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(AI, SECOND, "才"),
            D.syl(AO, THIRD, "草")
    );
}
