package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.CompositionLayout.TopBottom;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 宀部 / 冖部 — Shelter and cover-related components. */
public final class ShelterFamily {
    private ShelterFamily() {}

    /** 宀 — 宝盖头 (roof/shelter). */
    public record BaoGaiTou() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "宀"; }
        @Override public String name()       { return "宝盖头"; }
        @Override public String standalone() { return "宀"; }
        @Override public String meaning()    { return "roof"; }
        @Override public String pinyinText()     { return "mián"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopBottom.Top) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 冖 — 秃宝盖 (cover). */
    public record TuBaoGai() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "冖"; }
        @Override public String name()       { return "秃宝盖"; }
        @Override public String standalone() { return "冖"; }
        @Override public String meaning()    { return "cover"; }
        @Override public String pinyinText()     { return "mì"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopBottom.Top) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 亠 — 文字头 (lid). */
    public record WenZiTou() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "亠"; }
        @Override public String name()       { return "文字头"; }
        @Override public String standalone() { return "亠"; }
        @Override public String meaning()    { return "lid"; }
        @Override public String pinyinText()     { return "tóu"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopBottom.Top) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final BaoGaiTou BAO_GAI_TOU = new BaoGaiTou();
    public static final TuBaoGai TU_BAO_GAI = new TuBaoGai();
    public static final WenZiTou WEN_ZI_TOU = new WenZiTou();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(BAO_GAI_TOU, TU_BAO_GAI, WEN_ZI_TOU);
}
