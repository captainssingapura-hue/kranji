package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.TopBottom;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 火部 — Fire-related components. */
public final class FireFamily {
    private FireFamily() {}

    /** 灬 — 四点底 (fire). Derives from 火 huǒ. */
    public record SiDianDi() implements BasicComponent {
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
    }

    public static final SiDianDi SI_DIAN_DI = new SiDianDi();
}
