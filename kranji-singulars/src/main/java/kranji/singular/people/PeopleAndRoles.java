package kranji.singular.people;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class PeopleAndRoles {
    private PeopleAndRoles() {}

    public record Ren() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "人"; }
        @Override public String meaning() { return "person"; }
        @Override public String pinyinText()  { return "rén"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ren REN = new Ren();

    public record Nv() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "女"; }
        @Override public String meaning() { return "woman"; }
        @Override public String pinyinText()  { return "nǚ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Nv NV = new Nv();

    public record Zi() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "子"; }
        @Override public String meaning() { return "child, son"; }
        @Override public String pinyinText()  { return "zǐ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zi ZI = new Zi();

    public record Da() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "大"; }
        @Override public String meaning() { return "big, great"; }
        @Override public String pinyinText()  { return "dà"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Da DA = new Da();

    public record Fu_Husband() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "夫"; }
        @Override public String meaning() { return "husband, man"; }
        @Override public String pinyinText()  { return "fū"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fu_Husband FU_HUSBAND = new Fu_Husband();

    public record Fu_Father() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "父"; }
        @Override public String meaning() { return "father"; }
        @Override public String pinyinText()  { return "fù"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fu_Father FU_FATHER = new Fu_Father();

    public record Mu_Mother() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "母"; }
        @Override public String meaning() { return "mother"; }
        @Override public String pinyinText()  { return "mǔ"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mu_Mother MU_MOTHER = new Mu_Mother();

    public record Xiong() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "兄"; }
        @Override public String meaning() { return "elder brother"; }
        @Override public String pinyinText()  { return "xiōng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xiong XIONG = new Xiong();

    public record Di() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "弟"; }
        @Override public String meaning() { return "younger brother"; }
        @Override public String pinyinText()  { return "dì"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Di DI = new Di();

    public record Wang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "王"; }
        @Override public String meaning() { return "king"; }
        @Override public String pinyinText()  { return "wáng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wang WANG = new Wang();

    public record Chen() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "臣"; }
        @Override public String meaning() { return "minister"; }
        @Override public String pinyinText()  { return "chén"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chen CHEN = new Chen();

    public record Shi() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "士"; }
        @Override public String meaning() { return "scholar"; }
        @Override public String pinyinText()  { return "shì"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi SHI = new Shi();

    public record Min() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "民"; }
        @Override public String meaning() { return "people"; }
        @Override public String pinyinText()  { return "mín"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Min MIN = new Min();

    public record Er_Child() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "儿"; }
        @Override public String meaning() { return "child"; }
        @Override public String pinyinText()  { return "ér"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Er_Child ER_CHILD = new Er_Child();

    public record You() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "友"; }
        @Override public String meaning() { return "friend"; }
        @Override public String pinyinText()  { return "yǒu"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final You YOU = new You();

    public record Jun() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "君"; }
        @Override public String meaning() { return "lord"; }
        @Override public String pinyinText()  { return "jūn"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jun JUN = new Jun();

    public record Guan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "官"; }
        @Override public String meaning() { return "official"; }
        @Override public String pinyinText()  { return "guān"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Guan GUAN = new Guan();

    public record Shi_Clan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "氏"; }
        @Override public String meaning() { return "clan"; }
        @Override public String pinyinText()  { return "shì"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Clan SHI_CLAN = new Shi_Clan();

    public record Xiao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "小"; }
        @Override public String meaning() { return "small"; }
        @Override public String pinyinText()  { return "xiǎo"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xiao XIAO = new Xiao();

    public record Shao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "少"; }
        @Override public String meaning() { return "few, young"; }
        @Override public String pinyinText()  { return "shǎo"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shao SHAO = new Shao();

    public record Wen() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "文"; }
        @Override public String meaning() { return "writing, culture"; }
        @Override public String pinyinText()  { return "wén"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wen WEN = new Wen();

    public record Gai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "丐"; }
        @Override public String meaning() { return "beggar"; }
        @Override public String pinyinText()  { return "gài"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gai GAI = new Gai();

    public record Ren_Ninth() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "壬"; }
        @Override public String meaning() { return "ninth Heavenly Stem"; }
        @Override public String pinyinText()  { return "rén"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ren_Ninth REN_NINTH = new Ren_Ninth();

    public record Yi_Barbarian() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "夷"; }
        @Override public String meaning() { return "foreigner/barbarian"; }
        @Override public String pinyinText()  { return "yí"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Barbarian YI_BARBARIAN = new Yi_Barbarian();

    public record Jie_Alone() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "孑"; }
        @Override public String meaning() { return "alone/solitary"; }
        @Override public String pinyinText()  { return "jié"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jie_Alone JIE_ALONE = new Jie_Alone();

    public record Jue_Larva() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "孓"; }
        @Override public String meaning() { return "mosquito larva"; }
        @Override public String pinyinText()  { return "jué"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jue_Larva JUE_LARVA = new Jue_Larva();

    public record Yin_Governor() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "尹"; }
        @Override public String meaning() { return "governor/administer"; }
        @Override public String pinyinText()  { return "yǐn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yin_Governor YIN_GOVERNOR = new Yin_Governor();

    public record Li_Official() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "吏"; }
        @Override public String meaning() { return "official/clerk"; }
        @Override public String pinyinText()  { return "lì"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Li_Official LI_OFFICIAL = new Li_Official();

    public record Shi_History() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "史"; }
        @Override public String meaning() { return "history/historian"; }
        @Override public String pinyinText()  { return "shǐ"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_History SHI_HISTORY = new Shi_History();

    public record Yu_Yu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "禹"; }
        @Override public String meaning() { return "Yu (legendary ruler)"; }
        @Override public String pinyinText()  { return "yǔ"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Yu YU_YU = new Yu_Yu();

    public record Yu_Monkey() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "禺"; }
        @Override public String meaning() { return "area (archaic)"; }
        @Override public String pinyinText()  { return "yú"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Monkey YU_MONKEY = new Yu_Monkey();

    public record You_Especially() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "尤"; }
        @Override public String meaning() { return "especially/fault"; }
        @Override public String pinyinText()  { return "yóu"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final You_Especially YOU_ESPECIALLY = new You_Especially();

    public record Su() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "肃"; }
        @Override public String meaning() { return "solemn/respectful"; }
        @Override public String pinyinText()  { return "sù"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Su SU = new Su();

    public record Er_And() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "而"; }
        @Override public String meaning() { return "and/but"; }
        @Override public String pinyinText()  { return "ér"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Er_And ER_AND = new Er_And();

    public record Mian_Hide() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "丏"; }
        @Override public String meaning() { return "hide/screen"; }
        @Override public String pinyinText()  { return "miǎn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mian_Hide MIAN_HIDE = new Mian_Hide();

    public record Tai_Great() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "太"; }
        @Override public String meaning() { return "great/supreme"; }
        @Override public String pinyinText()  { return "tài"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Tai_Great TAI_GREAT = new Tai_Great();

    public record Yao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "夭"; }
        @Override public String meaning() { return "young/premature death"; }
        @Override public String pinyinText()  { return "yāo"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yao YAO = new Yao();

    /** 帅 — commander, handsome. */
    public record Shuai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E05"; }
        @Override public String meaning() { return "commander, handsome"; }
        @Override public String pinyinText()  { return "shu\u00E0i"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shuai SHUAI = new Shuai();

    /** 司 — manage, department. */
    public record Si_Manage() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u53F8"; }
        @Override public String meaning() { return "manage, department"; }
        @Override public String pinyinText()  { return "s\u012B"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Si_Manage SI_MANAGE = new Si_Manage();

    /** \u5175 — soldier. */
    public record Bing() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5175"; }
        @Override public String meaning() { return "soldier"; }
        @Override public String pinyinText()  { return "b\u012Bng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bing BING = new Bing();

    public static final List<kranji.library.LibraryMember<BasicSet>> ALL = List.of(
            REN, NV, ZI, DA, FU_HUSBAND, FU_FATHER, MU_MOTHER, XIONG, DI,
            WANG, CHEN, SHI, MIN, ER_CHILD, YOU, JUN, GUAN, SHI_CLAN,
            XIAO, SHAO, WEN, GAI, REN_NINTH, YI_BARBARIAN,
            JIE_ALONE, JUE_LARVA, YIN_GOVERNOR, LI_OFFICIAL, SHI_HISTORY,
            YU_YU, YU_MONKEY, YOU_ESPECIALLY, SU, ER_AND, MIAN_HIDE,
            TAI_GREAT, YAO,
            SHUAI, SI_MANAGE,
            BING);
}
