package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code z-}. Seed set. */
public final class Z {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.Z);

    private Z() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(OU, THIRD, "走"),
            D.syl(SYLLABIC, FOURTH, "字")
    );
}
