package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 犬部 — Animal-related components. */
public final class AnimalFamily {
    private AnimalFamily() {}

    /** 犭 — 反犬旁 (dog/animal). Derives from 犬 quǎn. */
    public record FanQuanPang() implements BasicComponent {
        @Override public String glyph()      { return "犭"; }
        @Override public String name()       { return "反犬旁"; }
        @Override public String standalone() { return "犬"; }
        @Override public String meaning()    { return "dog"; }
        @Override public String pinyin()     { return "quǎn"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    public static final FanQuanPang FAN_QUAN_PANG = new FanQuanPang();
}
