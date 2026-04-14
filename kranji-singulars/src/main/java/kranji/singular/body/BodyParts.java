package kranji.singular.body;

import kranji.classification.BlockRole;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;
import kranji.zi.ComposedBlock.FullEnclosure;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.zi.ComposedBlock.TopBottom;
import kranji.zi.ComposedBlock.TopMiddleBottom;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class BodyParts {
    private BodyParts() {}

    /** 口 — mouth. Yielding in side/bottom/middle positions, inner-scaled as bottom/enclosure. */
    public record Kou() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph()   { return "口"; }
        @Override public String meaning() { return "mouth"; }
        @Override public String pinyin()  { return "kǒu"; }
        @Override public int strokes()    { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left)          return Politeness.YIELDING;
            if (role instanceof LeftRight.Right)         return Politeness.YIELDING;
            if (role instanceof TopBottom.Bottom)        return Politeness.YIELDING;
            if (role instanceof TopMiddleBottom.Top)     return Politeness.YIELDING;
            if (role instanceof TopMiddleBottom.Middle)  return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }

        @Override
        public LayoutHint hintFor(BlockRole role) {
            if (role instanceof TopBottom.Bottom)      return LayoutHint.innerScale(0.80, 0.80);
            if (role instanceof FullEnclosure.Inner)   return LayoutHint.innerScale(0.55, 0.55);
            return null;
        }
    }

    /** 目 — eye. Yielding as left/bottom, inner-scaled as bottom. */
    public record Mu() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph()   { return "目"; }
        @Override public String meaning() { return "eye"; }
        @Override public String pinyin()  { return "mù"; }
        @Override public int strokes()    { return 5; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left)   return Politeness.YIELDING;
            if (role instanceof TopBottom.Bottom) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }

        @Override
        public LayoutHint hintFor(BlockRole role) {
            if (role instanceof TopBottom.Bottom) return LayoutHint.innerScale(0.85, 0.80);
            return null;
        }
    }

    public record Er() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u8033"; }
        @Override public String meaning() { return "ear"; }
        @Override public String pinyin()  { return "\u011Br"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Kou KOU = new Kou();
    public static final Mu  MU  = new Mu();
    public static final Er  ER  = new Er();

    public record Shou() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u624B"; }
        @Override public String meaning() { return "hand"; }
        @Override public String pinyin()  { return "sh\u01D2u"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shou SHOU = new Shou();

    public record Zu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u8DB3"; }
        @Override public String meaning() { return "foot"; }
        @Override public String pinyin()  { return "z\u00FA"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zu ZU = new Zu();

    public record Xin() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u5FC3"; }
        @Override public String meaning() { return "heart"; }
        @Override public String pinyin()  { return "x\u012Bn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xin XIN = new Xin();

    public record Tou() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u5934"; }
        @Override public String meaning() { return "head"; }
        @Override public String pinyin()  { return "t\u00F3u"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Tou TOU = new Tou();

    public record Mian() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u9762"; }
        @Override public String meaning() { return "face"; }
        @Override public String pinyin()  { return "mi\u00E0n"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mian MIAN = new Mian();

    public record Shen() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u8EAB"; }
        @Override public String meaning() { return "body"; }
        @Override public String pinyin()  { return "sh\u0113n"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shen SHEN = new Shen();

    public record Shou_Head() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u9996"; }
        @Override public String meaning() { return "head (formal)"; }
        @Override public String pinyin()  { return "sh\u01D2u"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shou_Head SHOU_HEAD = new Shou_Head();

    public record Ya() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u7259"; }
        @Override public String meaning() { return "tooth"; }
        @Override public String pinyin()  { return "y\u00E1"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ya YA = new Ya();

    public record She() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u820C"; }
        @Override public String meaning() { return "tongue"; }
        @Override public String pinyin()  { return "sh\u00E9"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final She SHE = new She();

    public record Pi() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u76AE"; }
        @Override public String meaning() { return "skin"; }
        @Override public String pinyin()  { return "p\u00ED"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Pi PI = new Pi();

    public record Mao() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u6BDB"; }
        @Override public String meaning() { return "hair, fur"; }
        @Override public String pinyin()  { return "m\u00E1o"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mao MAO = new Mao();

    public record Rou() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u8089"; }
        @Override public String meaning() { return "meat, flesh"; }
        @Override public String pinyin()  { return "r\u00F2u"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Rou ROU = new Rou();

    public record Xue() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u8840"; }
        @Override public String meaning() { return "blood"; }
        @Override public String pinyin()  { return "xu\u00E8"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xue XUE = new Xue();

    public record Wei() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u80C3"; }
        @Override public String meaning() { return "stomach"; }
        @Override public String pinyin()  { return "w\u00E8i"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wei WEI = new Wei();

    public record Zi_Self() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "自"; }
        @Override public String meaning() { return "self/from"; }
        @Override public String pinyin()  { return "zì"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zi_Self ZI_SELF = new Zi_Self();

    public record Jiu_Mortar() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "臼"; }
        @Override public String meaning() { return "mortar/joint socket"; }
        @Override public String pinyin()  { return "jiù"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiu_Mortar JIU_MORTAR = new Jiu_Mortar();

    public record Yu_Moment() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "臾"; }
        @Override public String meaning() { return "hands pulling body"; }
        @Override public String pinyin()  { return "yú"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Moment YU_MOMENT = new Yu_Moment();

    public record Zhua() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "爪"; }
        @Override public String meaning() { return "claw/talon"; }
        @Override public String pinyin()  { return "zhǎo"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhua ZHUA = new Zhua();

    public record Xin_Fontanel() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "囟"; }
        @Override public String meaning() { return "fontanel/skull"; }
        @Override public String pinyin()  { return "xìn"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xin_Fontanel XIN_FONTANEL = new Xin_Fontanel();

    public record Gen_Tough() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "艮"; }
        @Override public String meaning() { return "tough/blunt (trigram)"; }
        @Override public String pinyin()  { return "gèn"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gen_Tough GEN_TOUGH = new Gen_Tough();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            KOU, MU, ER, SHOU, ZU, XIN, TOU, MIAN, SHEN, SHOU_HEAD, YA,
            SHE, PI, MAO, ROU, XUE, WEI,
            ZI_SELF, JIU_MORTAR, YU_MOMENT, ZHUA, XIN_FONTANEL, GEN_TOUGH);
}
