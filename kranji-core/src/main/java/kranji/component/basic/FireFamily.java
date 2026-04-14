package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.TopBottom;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 火部 — Fire-related components. */
public final class FireFamily {
    private FireFamily() {}

    /** 灬 — 四点底 (fire). Derives from 火 huǒ. */
    public record SiDianDi() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "灬"; }
        @Override public String name()       { return "四点底"; }
        @Override public String standalone() { return "火"; }
        @Override public String meaning()    { return "fire"; }
        @Override public String pinyin()     { return "huǒ"; }
        @Override public int strokes()       { return 4; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopBottom.Bottom) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final SiDianDi SI_DIAN_DI = new SiDianDi();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(SI_DIAN_DI);
}
