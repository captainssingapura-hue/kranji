package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 衣部 / 示部 — Clothing and spirit-related components. */
public final class ClothingFamily {
    private ClothingFamily() {}

    /** 礻 — 示字旁 (spirit/altar). Derives from 示 shì. */
    public record ShiZiPangSpirit() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "礻"; }
        @Override public String name()       { return "示字旁"; }
        @Override public String standalone() { return "示"; }
        @Override public String meaning()    { return "spirit"; }
        @Override public String pinyinText()     { return "shì"; }
        @Override public int strokes()       { return 4; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 衤 — 衣字旁 (clothing). Derives from 衣 yī. */
    public record YiZiPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "衤"; }
        @Override public String name()       { return "衣字旁"; }
        @Override public String standalone() { return "衣"; }
        @Override public String meaning()    { return "clothing"; }
        @Override public String pinyinText()     { return "yī"; }
        @Override public int strokes()       { return 5; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final ShiZiPangSpirit SHI_ZI_PANG_SPIRIT = new ShiZiPangSpirit();
    public static final YiZiPang YI_ZI_PANG = new YiZiPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(SHI_ZI_PANG_SPIRIT, YI_ZI_PANG);
}
