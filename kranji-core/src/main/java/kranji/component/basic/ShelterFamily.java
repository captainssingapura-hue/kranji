package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.TopBottom;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 宀部 / 冖部 — Shelter and cover-related components. */
public final class ShelterFamily {
    private ShelterFamily() {}

    /** 宀 — 宝盖头 (roof/shelter). */
    public record BaoGaiTou() implements BasicComponent {
        @Override public String glyph()      { return "宀"; }
        @Override public String name()       { return "宝盖头"; }
        @Override public String standalone() { return "宀"; }
        @Override public String meaning()    { return "roof"; }
        @Override public String pinyin()     { return "mián"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopBottom.Top) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    /** 冖 — 秃宝盖 (cover). */
    public record TuBaoGai() implements BasicComponent {
        @Override public String glyph()      { return "冖"; }
        @Override public String name()       { return "秃宝盖"; }
        @Override public String standalone() { return "冖"; }
        @Override public String meaning()    { return "cover"; }
        @Override public String pinyin()     { return "mì"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopBottom.Top) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    /** 亠 — 文字头 (lid). */
    public record WenZiTou() implements BasicComponent {
        @Override public String glyph()      { return "亠"; }
        @Override public String name()       { return "文字头"; }
        @Override public String standalone() { return "亠"; }
        @Override public String meaning()    { return "lid"; }
        @Override public String pinyin()     { return "tóu"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopBottom.Top) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    public static final BaoGaiTou BAO_GAI_TOU = new BaoGaiTou();
    public static final TuBaoGai TU_BAO_GAI = new TuBaoGai();
    public static final WenZiTou WEN_ZI_TOU = new WenZiTou();
}
