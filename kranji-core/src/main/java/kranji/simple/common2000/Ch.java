package kranji.simple.common2000;

import kranji.pinyin.Initial;
import kranji.simple.SyllableDecl;
import kranji.simple.SimpleZiDsl;

import java.util.List;

import static kranji.pinyin.Tone.*;
import static kranji.simple.Finals.*;

/** Characters whose default reading begins with {@code ch-}. Seed set. */
public final class Ch {

    private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.CH);

    private Ch() {}

    public static final List<SyllableDecl> DECLARATIONS = List.of(
            D.syl(E, FIRST, "车"),
            D.syl(ANG, SECOND, "长")
    );
}
