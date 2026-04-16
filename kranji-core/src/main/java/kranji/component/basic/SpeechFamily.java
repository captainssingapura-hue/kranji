package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 言部 — Speech-related components. */
public final class SpeechFamily {
    private SpeechFamily() {}

    /** 讠 — 言字旁 (speech). Derives from 言 yán. */
    public record YanZiPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "讠"; }
        @Override public String name()       { return "言字旁"; }
        @Override public String standalone() { return "言"; }
        @Override public String meaning()    { return "speech"; }
        @Override public String pinyinText()     { return "yán"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final YanZiPang YAN_ZI_PANG = new YanZiPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(YAN_ZI_PANG);
}
