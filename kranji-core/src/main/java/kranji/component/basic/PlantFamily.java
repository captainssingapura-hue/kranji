package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.TopBottom;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 艸部 / 竹部 — Plant-related components. */
public final class PlantFamily {
    private PlantFamily() {}

    /** 艹 — 草字头 (grass/plant). Derives from 艸 cǎo. */
    public record CaoZiTou() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "艹"; }
        @Override public String name()       { return "草字头"; }
        @Override public String standalone() { return "艸"; }
        @Override public String meaning()    { return "grass"; }
        @Override public String pinyin()     { return "cǎo"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopBottom.Top) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** ⺮ — 竹字头 (bamboo). Derives from 竹 zhú. */
    public record ZhuZiTou() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "⺮"; }
        @Override public String name()       { return "竹字头"; }
        @Override public String standalone() { return "竹"; }
        @Override public String meaning()    { return "bamboo"; }
        @Override public String pinyin()     { return "zhú"; }
        @Override public int strokes()       { return 6; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopBottom.Top) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final CaoZiTou CAO_ZI_TOU = new CaoZiTou();
    public static final ZhuZiTou ZHU_ZI_TOU = new ZhuZiTou();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(CAO_ZI_TOU, ZHU_ZI_TOU);
}
