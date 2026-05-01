package kranji.singular.radicals;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

/**
 * Radical and stroke components (部首/偏旁/笔画部件) that serve as
 * indivisible building blocks in character composition.
 *
 * <p>These are NOT standalone characters in modern usage — they are
 * structural atoms used inside composed characters. Each is a genuine
 * singular block that cannot be further decomposed.</p>
 */
public final class RadicalComponents {
    private RadicalComponents() {}

    // ══════════════════════════════════════════════════════════════════
    //  1. Basic strokes and stroke components
    // ══════════════════════════════════════════════════════════════════

    public record Gun() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E28"; }
        @Override public String meaning() { return "vertical stroke"; }
        @Override public String pinyinText()  { return "g\u01D4n"; }
        @Override public int strokes()    { return 1; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gun GUN = new Gun();

    public record Pie() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E3F"; }
        @Override public String meaning() { return "left-falling stroke"; }
        @Override public String pinyinText()  { return "pi\u011B"; }
        @Override public int strokes()    { return 1; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Pie PIE = new Pie();

    public record Yi_Hook() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E5A"; }
        @Override public String meaning() { return "turning stroke"; }
        @Override public String pinyinText()  { return "y\u01D0"; }
        @Override public int strokes()    { return 1; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Hook YI_HOOK = new Yi_Hook();

    public record Kao() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E02"; }
        @Override public String meaning() { return "breath, crooked"; }
        @Override public String pinyinText()  { return "k\u01CEo"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Kao KAO = new Kao();

    public record Jiu_Twist() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E29"; }
        @Override public String meaning() { return "twist, entangle"; }
        @Override public String pinyinText()  { return "ji\u016B"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiu_Twist JIU_TWIST = new Jiu_Twist();

    public record Ba_Top() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E37"; }
        @Override public String meaning() { return "eight top variant"; }
        @Override public String pinyinText()  { return "b\u0101"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ba_Top BA_TOP = new Ba_Top();

    public record Yin_Hook() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\uD840\uDCCC"; }
        @Override public String meaning() { return "hook turn"; }
        @Override public String pinyinText()  { return "y\u01D0n"; }
        @Override public int strokes()    { return 1; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yin_Hook YIN_HOOK = new Yin_Hook();

    // ══════════════════════════════════════════════════════════════════
    //  2. Kangxi radical variants (top/left forms)
    // ══════════════════════════════════════════════════════════════════

    public record Dao_Top() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u2E88"; }
        @Override public String meaning() { return "knife radical top"; }
        @Override public String pinyinText()  { return "d\u0101o"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dao_Top DAO_TOP = new Dao_Top();

    public record Xiao_Top() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u2E8C"; }
        @Override public String meaning() { return "small radical top"; }
        @Override public String pinyinText()  { return "xi\u01CEo"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xiao_Top XIAO_TOP = new Xiao_Top();

    public record Shang_Dot() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u2E8D"; }
        @Override public String meaning() { return "small dot top variant"; }
        @Override public String pinyinText()  { return "xi\u01CEo"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shang_Dot SHANG_DOT = new Shang_Dot();

    public record Qiang_Left() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E2C"; }
        @Override public String meaning() { return "half-tree left radical"; }
        @Override public String pinyinText()  { return "qi\u00E1ng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qiang_Left QIANG_LEFT = new Qiang_Left();

    public record Niu_Left() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u725C"; }
        @Override public String meaning() { return "ox left radical"; }
        @Override public String pinyinText()  { return "ni\u00FA"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Niu_Left NIU_LEFT = new Niu_Left();

    public record Mu_Variant() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u6729"; }
        @Override public String meaning() { return "wood variant"; }
        @Override public String pinyinText()  { return "m\u00F9"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mu_Variant MU_VARIANT = new Mu_Variant();

    public record Tu_Old() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5721"; }
        @Override public String meaning() { return "earth old form"; }
        @Override public String pinyinText()  { return "t\u01D4"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Tu_Old TU_OLD = new Tu_Old();

    // ══════════════════════════════════════════════════════════════════
    //  3. CJK compatibility / rare radical components
    // ══════════════════════════════════════════════════════════════════

    public record Wu_Cross() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u3405"; }
        @Override public String meaning() { return "cross mark"; }
        @Override public String pinyinText()  { return "w\u01D4"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Cross WU_CROSS = new Wu_Cross();

    public record Yan_Marsh() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u3563"; }
        @Override public String meaning() { return "marsh, swamp"; }
        @Override public String pinyinText()  { return "y\u01CEn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yan_Marsh YAN_MARSH = new Yan_Marsh();

    public record Yan_Flag() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u3AC3"; }
        @Override public String meaning() { return "flag radical"; }
        @Override public String pinyinText()  { return "y\u01CEn"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yan_Flag YAN_FLAG = new Yan_Flag();

    public record Gan_Shield() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u3AD7"; }
        @Override public String meaning() { return "shield component"; }
        @Override public String pinyinText()  { return "g\u0101n"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gan_Shield GAN_SHIELD = new Gan_Shield();

    // ══════════════════════════════════════════════════════════════════
    //  4. Semantic radicals (部首) used as composition atoms
    // ══════════════════════════════════════════════════════════════════

    public record Jie_Seal() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5369"; }
        @Override public String meaning() { return "seal radical"; }
        @Override public String pinyinText()  { return "ji\u00E9"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jie_Seal JIE_SEAL = new Jie_Seal();

    public record Si_Private() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u53B6"; }
        @Override public String meaning() { return "private, personal"; }
        @Override public String pinyinText()  { return "s\u012B"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Si_Private SI_PRIVATE = new Si_Private();

    public record Chu_Step() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E8D"; }
        @Override public String meaning() { return "step with right foot"; }
        @Override public String pinyinText()  { return "ch\u00F9"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chu_Step CHU_STEP = new Chu_Step();

    public record Zhi_Slow() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5902"; }
        @Override public String meaning() { return "go slowly, follow"; }
        @Override public String pinyinText()  { return "zh\u01D0"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Slow ZHI_SLOW = new Zhi_Slow();

    public record Xiang_Descend() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5905"; }
        @Override public String meaning() { return "descend"; }
        @Override public String pinyinText()  { return "xi\u00E1ng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xiang_Descend XIANG_DESCEND = new Xiang_Descend();

    public record Che_Sprout() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5C6E"; }
        @Override public String meaning() { return "sprout, bud"; }
        @Override public String pinyinText()  { return "ch\u00E8"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Che_Sprout CHE_SPROUT = new Che_Sprout();

    public record Gong_Clasp() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5EFE"; }
        @Override public String meaning() { return "two hands clasped"; }
        @Override public String pinyinText()  { return "g\u01D2ng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gong_Clasp GONG_CLASP = new Gong_Clasp();

    public record Ji_Snout() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5F50"; }
        @Override public String meaning() { return "pig snout radical"; }
        @Override public String pinyinText()  { return "j\u00EC"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Snout JI_SNOUT = new Ji_Snout();

    public record Shan_Hair() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5F61"; }
        @Override public String meaning() { return "hair, bristles"; }
        @Override public String pinyinText()  { return "sh\u0101n"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shan_Hair SHAN_HAIR = new Shan_Hair();

    public record Shu_Lance() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u6BB3"; }
        @Override public String meaning() { return "lance, weapon"; }
        @Override public String pinyinText()  { return "sh\u016B"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shu_Lance SHU_LANCE = new Shu_Lance();

    public record Lao_Top() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u8002"; }
        @Override public String meaning() { return "old radical top"; }
        @Override public String pinyinText()  { return "l\u01CEo"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Lao_Top LAO_TOP = new Lao_Top();

    public record Hu_Tiger() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u864D"; }
        @Override public String meaning() { return "tiger radical top"; }
        @Override public String pinyinText()  { return "h\u01D4"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hu_Tiger HU_TIGER = new Hu_Tiger();

    public record Xi_Cover() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u8980"; }
        @Override public String meaning() { return "west, cover radical"; }
        @Override public String pinyinText()  { return "x\u012B"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xi_Cover XI_COVER = new Xi_Cover();

    public record Zhao_Claw() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u722B"; }
        @Override public String meaning() { return "claw radical top"; }
        @Override public String pinyinText()  { return "zh\u01CEo"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhao_Claw ZHAO_CLAW = new Zhao_Claw();

    public record Bian_Pick() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u91C6"; }
        @Override public String meaning() { return "distinguish, pick"; }
        @Override public String pinyinText()  { return "bi\u00E0n"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bian_Pick BIAN_PICK = new Bian_Pick();

    public record Mi_Silk() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u7CF8"; }
        @Override public String meaning() { return "silk, fine thread"; }
        @Override public String pinyinText()  { return "m\u00EC"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mi_Silk MI_SILK = new Mi_Silk();

    public record Mao_Cap() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5183"; }
        @Override public String meaning() { return "cap, hat cover"; }
        @Override public String pinyinText()  { return "m\u00E0o"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mao_Cap MAO_CAP = new Mao_Cap();

    public record Ran_Whiskers() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5184"; }
        @Override public String meaning() { return "whiskers, tender"; }
        @Override public String pinyinText()  { return "r\u01CEn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ran_Whiskers RAN_WHISKERS = new Ran_Whiskers();

    public record Yin_Waver() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5198"; }
        @Override public String meaning() { return "waver, hesitate"; }
        @Override public String pinyinText()  { return "y\u00EDn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yin_Waver YIN_WAVER = new Yin_Waver();

    // ══════════════════════════════════════════════════════════════════
    //  5. Phonetic/semantic component radicals (常用部件)
    // ══════════════════════════════════════════════════════════════════

    public record Zhu_Drum() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u58F4"; }
        @Override public String meaning() { return "drum, display"; }
        @Override public String pinyinText()  { return "zh\u00F9"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhu_Drum ZHU_DRUM = new Zhu_Drum();

    public record Jing_Stream() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5DE0"; }
        @Override public String meaning() { return "stream, underground water"; }
        @Override public String pinyinText()  { return "j\u012Bng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jing_Stream JING_STREAM = new Jing_Stream();

    public record Jian_Firm() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u81E4"; }
        @Override public String meaning() { return "firm, hard"; }
        @Override public String pinyinText()  { return "ji\u0101n"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jian_Firm JIAN_FIRM = new Jian_Firm();

    public record Xian_Pit() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u81FD"; }
        @Override public String meaning() { return "pit, pitfall"; }
        @Override public String pinyinText()  { return "xi\u00E0n"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xian_Pit XIAN_PIT = new Xian_Pit();

    public record Cha_Spade() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u81FF"; }
        @Override public String meaning() { return "spade, insert"; }
        @Override public String pinyinText()  { return "ch\u0101"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cha_Spade CHA_SPADE = new Cha_Spade();

    public record Fu_Full() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u7590"; }
        @Override public String meaning() { return "full, abundant"; }
        @Override public String pinyinText()  { return "f\u00FA"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fu_Full FU_FULL = new Fu_Full();

    public record Shen_Deep() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u7F59"; }
        @Override public String meaning() { return "deep, profound"; }
        @Override public String pinyinText()  { return "sh\u0113n"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shen_Deep SHEN_DEEP = new Shen_Deep();

    public record Shu_Erect() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u5C0C"; }
        @Override public String meaning() { return "erect, standing tree"; }
        @Override public String pinyinText()  { return "sh\u00F9"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shu_Erect SHU_ERECT = new Shu_Erect();

    public record Ci_Thorn() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u673F"; }
        @Override public String meaning() { return "thorn, prick"; }
        @Override public String pinyinText()  { return "c\u00EC"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ci_Thorn CI_THORN = new Ci_Thorn();

    public record Yang_Bright() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u661C"; }
        @Override public String meaning() { return "bright, yang"; }
        @Override public String pinyinText()  { return "y\u00E1ng"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yang_Bright YANG_BRIGHT = new Yang_Bright();

    public record Jun_Talent() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u590B"; }
        @Override public String meaning() { return "talented, superior"; }
        @Override public String pinyinText()  { return "j\u00F9n"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jun_Talent JUN_TALENT = new Jun_Talent();

    public record Duan_Upright() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u8011"; }
        @Override public String meaning() { return "upright, erect"; }
        @Override public String pinyinText()  { return "du\u0101n"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Duan_Upright DUAN_UPRIGHT = new Duan_Upright();

    public record Qing_Chime() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u6BB8"; }
        @Override public String meaning() { return "sound, chime"; }
        @Override public String pinyinText()  { return "q\u00ECng"; }
        @Override public int strokes()    { return 11; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qing_Chime QING_CHIME = new Qing_Chime();

    public record Ran_Flesh() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u80B0"; }
        @Override public String meaning() { return "dog meat, so"; }
        @Override public String pinyinText()  { return "r\u00E1n"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ran_Flesh RAN_FLESH = new Ran_Flesh();

    public record Ye_Leaf() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u67BC"; }
        @Override public String meaning() { return "leaf, page component"; }
        @Override public String pinyinText()  { return "y\u00E8"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ye_Leaf YE_LEAF = new Ye_Leaf();

    public record Qi_Lacquer() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u687C"; }
        @Override public String meaning() { return "lacquer tree"; }
        @Override public String pinyinText()  { return "q\u012B"; }
        @Override public int strokes()    { return 11; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qi_Lacquer QI_LACQUER = new Qi_Lacquer();

    // ══════════════════════════════════════════════════════════════════
    //  6. Top/upper component fragments (龶 龹 龺 etc.)
    // ══════════════════════════════════════════════════════════════════

    public record Zhu_Top() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u9FB6"; }
        @Override public String meaning() { return "top of \u4E3B/\u751F"; }
        @Override public String pinyinText()  { return "zh\u01D4"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhu_Top ZHU_TOP = new Zhu_Top();

    public record Quan_Upper() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u9FB9"; }
        @Override public String meaning() { return "curved upper component"; }
        @Override public String pinyinText()  { return "qu\u01CEn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Quan_Upper QUAN_UPPER = new Quan_Upper();

    public record Gan_Upper() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u9FBA"; }
        @Override public String meaning() { return "upper component of \u5E72"; }
        @Override public String pinyinText()  { return "g\u0101n"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gan_Upper GAN_UPPER = new Gan_Upper();

    // ══════════════════════════════════════════════════════════════════
    //  7. CJK Ext-B stroke components (supplementary plane)
    // ══════════════════════════════════════════════════════════════════

    public record Zuo_Hand() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\uD840\uDC87"; }
        @Override public String meaning() { return "left hand"; }
        @Override public String pinyinText()  { return "zu\u01D2"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zuo_Hand ZUO_HAND = new Zuo_Hand();

    public record Hua_Stroke() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\uD840\uDC89"; }
        @Override public String meaning() { return "slanting stroke"; }
        @Override public String pinyinText()  { return "hu\u00E0"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hua_Stroke HUA_STROKE = new Hua_Stroke();

    public record Xia_Hook() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\uD840\uDC8B"; }
        @Override public String meaning() { return "downward hook"; }
        @Override public String pinyinText()  { return "xi\u00E0"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xia_Hook XIA_HOOK = new Xia_Hook();

    public record Ji_Person() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\uD840\uDC8E"; }
        @Override public String meaning() { return "person variant"; }
        @Override public String pinyinText()  { return "j\u00ED"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Person JI_PERSON = new Ji_Person();

    public record Pai_Spread() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\uD840\uDCA2"; }
        @Override public String meaning() { return "spreading lines"; }
        @Override public String pinyinText()  { return "p\u00E0i"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Pai_Spread PAI_SPREAD = new Pai_Spread();

    public record Dui_Mound() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\uD840\uDCA4"; }
        @Override public String meaning() { return "mound, heap"; }
        @Override public String pinyinText()  { return "du\u012B"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dui_Mound DUI_MOUND = new Dui_Mound();

    // ══════════════════════════════════════════════════════════════════
    //  8. Common standalone 独体字 used as components
    //     (these are valid characters AND atomic building blocks)
    // ══════════════════════════════════════════════════════════════════

    public record Zhi_Arrive() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u81F3"; }
        @Override public String meaning() { return "arrive, reach"; }
        @Override public String pinyinText()  { return "zh\u00EC"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Arrive ZHI_ARRIVE = new Zhi_Arrive();

    public record Yu_Feather() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u7FBD"; }
        @Override public String meaning() { return "feather, plume"; }
        @Override public String pinyinText()  { return "y\u01D4"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Feather YU_FEATHER = new Yu_Feather();

    public record Zhu_Bamboo() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u7AF9"; }
        @Override public String meaning() { return "bamboo"; }
        @Override public String pinyinText()  { return "zh\u00FA"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhu_Bamboo ZHU_BAMBOO = new Zhu_Bamboo();

    public record Shi_Show() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u793A"; }
        @Override public String meaning() { return "show, reveal"; }
        @Override public String pinyinText()  { return "sh\u00EC"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Show SHI_SHOW = new Shi_Show();

    public record Liang_Good() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u826F"; }
        @Override public String meaning() { return "good, fine"; }
        @Override public String pinyinText()  { return "li\u00E1ng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Liang_Good LIANG_GOOD = new Liang_Good();

    public record Fei_Wrong() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u975E"; }
        @Override public String meaning() { return "wrong, not"; }
        @Override public String pinyinText()  { return "f\u0113i"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fei_Wrong FEI_WRONG = new Fei_Wrong();

    public record Yin_Sound() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u97F3"; }
        @Override public String meaning() { return "sound, tone"; }
        @Override public String pinyinText()  { return "y\u012Bn"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yin_Sound YIN_SOUND = new Yin_Sound();

    public record Qian_Owe() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u6B20"; }
        @Override public String meaning() { return "owe, lack, yawn"; }
        @Override public String pinyinText()  { return "qi\u00E0n"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qian_Owe QIAN_OWE = new Qian_Owe();

    public record Mei_Each() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u6BCF"; }
        @Override public String meaning() { return "each, every"; }
        @Override public String pinyinText()  { return "m\u011Bi"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mei_Each MEI_EACH = new Mei_Each();

    // ══════════════════════════════════════════════════════════════════
    //  9. Additional stroke/radical atoms (from decomposition passes)
    // ══════════════════════════════════════════════════════════════════

    public record Niu_Top() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u2EA7"; }
        @Override public String meaning() { return "ox radical top"; }
        @Override public String pinyinText()  { return "ni\u00FA"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Niu_Top NIU_TOP = new Niu_Top();

    public record Dian() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E36"; }
        @Override public String meaning() { return "dot stroke"; }
        @Override public String pinyinText()  { return "zh\u01D4"; }
        @Override public int strokes()    { return 1; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dian DIAN = new Dian();

    public record Yi_Turn() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4E5B"; }
        @Override public String meaning() { return "turning stroke"; }
        @Override public String pinyinText()  { return "y\u01D0"; }
        @Override public int strokes()    { return 1; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Turn YI_TURN = new Yi_Turn();

    public record Shui_Variant() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u6C3A"; }
        @Override public String meaning() { return "water radical variant"; }
        @Override public String pinyinText()  { return "shu\u01D0"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shui_Variant SHUI_VARIANT = new Shui_Variant();

    public record Wang_Net() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u7F52"; }
        @Override public String meaning() { return "net radical top"; }
        @Override public String pinyinText()  { return "w\u01CEng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wang_Net WANG_NET = new Wang_Net();

    public record Ji_Not() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u65E1"; }
        @Override public String meaning() { return "choke, breathless"; }
        @Override public String pinyinText()  { return "j\u00EC"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Not JI_NOT = new Ji_Not();

    public record Xuan_Dark() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u7384"; }
        @Override public String meaning() { return "dark, profound"; }
        @Override public String pinyinText()  { return "xu\u00E1n"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xuan_Dark XUAN_DARK = new Xuan_Dark();

    public record Liu_Flow() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u3A6C"; }
        @Override public String meaning() { return "flow component"; }
        @Override public String pinyinText()  { return "li\u00FA"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Liu_Flow LIU_FLOW = new Liu_Flow();

    // San_Piao (彡) was a duplicate of Shan_Hair — removed; callers should
    // use SHAN_HAIR.
    // Shu_Weapon (殳) was a duplicate of Shu_Lance — removed; callers should
    // use SHU_LANCE.

    public record Kang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u4EA2"; }
        @Override public String meaning() { return "high, overbearing"; }
        @Override public String pinyinText()  { return "k\u00E0ng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Kang KANG = new Kang();

    // Guai (夬, U+592C) was a SingularPart duplicate of the SingularZi
    // AbstractConcepts.GUAI — removed; callers should use
    // AbstractConcepts.GUAI. 夬 is a semantically valid standalone Zi so the
    // atomic SingularZi form is canonical per the "Zi over Part when a
    // valid Zi exists" rule.

    // Ne_Zi_Pang (疒) was a duplicate of EnclosureFamily.BingZiPang —
    // removed; callers should use BasicComponents.BING_ZI_PANG.
    // Tou_Zi_Tou (亠) was a duplicate of ShelterFamily.WenZiTou —
    // removed; callers should use BasicComponents.WEN_ZI_TOU.

    public record Yao_Lines() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u723B"; }
        @Override public String meaning() { return "hexagram lines"; }
        @Override public String pinyinText()  { return "y\u00E1o"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yao_Lines YAO_LINES = new Yao_Lines();

    public record Ang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "\u536C"; }
        @Override public String meaning() { return "raise, lift"; }
        @Override public String pinyinText()  { return "\u00E1ng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ang ANG = new Ang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            // Basic strokes
            GUN, PIE, YI_HOOK, KAO, JIU_TWIST, BA_TOP, YIN_HOOK,
            // Kangxi radical variants
            DAO_TOP, XIAO_TOP, SHANG_DOT, QIANG_LEFT, NIU_LEFT, MU_VARIANT, TU_OLD,
            // CJK compat
            WU_CROSS, YAN_MARSH, YAN_FLAG, GAN_SHIELD,
            // Semantic radicals
            JIE_SEAL, SI_PRIVATE, CHU_STEP, ZHI_SLOW, XIANG_DESCEND,
            CHE_SPROUT, GONG_CLASP, JI_SNOUT, SHAN_HAIR, SHU_LANCE,
            LAO_TOP, HU_TIGER, XI_COVER, ZHAO_CLAW, BIAN_PICK, MI_SILK,
            MAO_CAP, RAN_WHISKERS, YIN_WAVER,
            // Phonetic/semantic components
            ZHU_DRUM, JING_STREAM, JIAN_FIRM, XIAN_PIT, CHA_SPADE,
            FU_FULL, SHEN_DEEP, SHU_ERECT, CI_THORN, YANG_BRIGHT,
            JUN_TALENT, DUAN_UPRIGHT, QING_CHIME, RAN_FLESH,
            YE_LEAF, QI_LACQUER,
            // Upper fragments
            ZHU_TOP, QUAN_UPPER, GAN_UPPER,
            // CJK Ext-B
            ZUO_HAND, HUA_STROKE, XIA_HOOK, JI_PERSON, PAI_SPREAD, DUI_MOUND,
            // Standalone 独体字 components
            ZHI_ARRIVE, YU_FEATHER, ZHU_BAMBOO, SHI_SHOW, LIANG_GOOD, FEI_WRONG,
            YIN_SOUND, QIAN_OWE, MEI_EACH,
            // Additional atoms from decomposition passes
            NIU_TOP, DIAN, YI_TURN, SHUI_VARIANT, WANG_NET, JI_NOT,
            XUAN_DARK, LIU_FLOW,
            // 7-stroke batch components
            KANG, YAO_LINES, ANG);
}
