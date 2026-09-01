package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code d-}. Seed set. */
public final class D {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.D);

    private D() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(ONG, FIRST, "东"),
            D.syl(A, FOURTH, "大"),
            D.syl(UO, FIRST, "多")
    );
}
