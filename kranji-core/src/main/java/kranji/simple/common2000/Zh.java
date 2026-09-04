package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code zh-}. Seed set. */
public final class Zh {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.ZH);

    private Zh() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(ONG, FIRST, "中"),
            D.syl(SYLLABIC, FIRST, "知")
    );
}
