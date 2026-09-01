package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code g-}. Seed set. */
public final class G {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.G);

    private G() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(AO, FIRST, "高"),
            D.syl(E, FIRST, "歌")
    );
}
