package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code no initial (zero-initial syllables)}. Seed set. */
public final class Zero {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.ZERO);

    private Zero() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(I, FIRST, "一"),
            D.syl(VE, FOURTH, "月"),
            D.syl(VN, SECOND, "云")
    );
}
