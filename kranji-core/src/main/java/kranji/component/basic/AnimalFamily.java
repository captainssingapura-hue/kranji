package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 犬部 — Animal-related components. */
public final class AnimalFamily {
    private AnimalFamily() {}

    /** 犭 — 反犬旁 (dog/animal). Derives from 犬 quǎn. */
    public record FanQuanPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "犭"; }
        @Override public String name()       { return "反犬旁"; }
        @Override public String standalone() { return "犬"; }
        @Override public String meaning()    { return "dog"; }
        @Override public String pinyinText()     { return "quǎn"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final FanQuanPang FAN_QUAN_PANG = new FanQuanPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(FAN_QUAN_PANG);
}
