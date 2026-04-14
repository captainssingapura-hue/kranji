package kranji.singular.people;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class PeopleAndRoles {
    private PeopleAndRoles() {}

    public record Ren() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "人"; }
        @Override public String meaning() { return "person"; }
        @Override public String pinyin()  { return "rén"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ren REN = new Ren();

    public record Nv() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "女"; }
        @Override public String meaning() { return "woman"; }
        @Override public String pinyin()  { return "nǚ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Nv NV = new Nv();

    public record Zi() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "子"; }
        @Override public String meaning() { return "child, son"; }
        @Override public String pinyin()  { return "zǐ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zi ZI = new Zi();

    public record Da() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "大"; }
        @Override public String meaning() { return "big, great"; }
        @Override public String pinyin()  { return "dà"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Da DA = new Da();

    public record Fu_Husband() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "夫"; }
        @Override public String meaning() { return "husband, man"; }
        @Override public String pinyin()  { return "fū"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fu_Husband FU_HUSBAND = new Fu_Husband();

    public record Fu_Father() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "父"; }
        @Override public String meaning() { return "father"; }
        @Override public String pinyin()  { return "fù"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fu_Father FU_FATHER = new Fu_Father();

    public record Mu_Mother() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "母"; }
        @Override public String meaning() { return "mother"; }
        @Override public String pinyin()  { return "mǔ"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mu_Mother MU_MOTHER = new Mu_Mother();

    public record Xiong() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "兄"; }
        @Override public String meaning() { return "elder brother"; }
        @Override public String pinyin()  { return "xiōng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xiong XIONG = new Xiong();

    public record Di() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "弟"; }
        @Override public String meaning() { return "younger brother"; }
        @Override public String pinyin()  { return "dì"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Di DI = new Di();

    public record Wang() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "王"; }
        @Override public String meaning() { return "king"; }
        @Override public String pinyin()  { return "wáng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wang WANG = new Wang();

    public record Chen() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "臣"; }
        @Override public String meaning() { return "minister"; }
        @Override public String pinyin()  { return "chén"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chen CHEN = new Chen();

    public record Shi() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "士"; }
        @Override public String meaning() { return "scholar"; }
        @Override public String pinyin()  { return "shì"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi SHI = new Shi();

    public record Min() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "民"; }
        @Override public String meaning() { return "people"; }
        @Override public String pinyin()  { return "mín"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Min MIN = new Min();

    public record Er_Child() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "儿"; }
        @Override public String meaning() { return "child"; }
        @Override public String pinyin()  { return "ér"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Er_Child ER_CHILD = new Er_Child();

    public record You() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "友"; }
        @Override public String meaning() { return "friend"; }
        @Override public String pinyin()  { return "yǒu"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final You YOU = new You();

    public record Jun() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "君"; }
        @Override public String meaning() { return "lord"; }
        @Override public String pinyin()  { return "jūn"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jun JUN = new Jun();

    public record Guan() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "官"; }
        @Override public String meaning() { return "official"; }
        @Override public String pinyin()  { return "guān"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Guan GUAN = new Guan();

    public record Shi_Clan() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "氏"; }
        @Override public String meaning() { return "clan"; }
        @Override public String pinyin()  { return "shì"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Clan SHI_CLAN = new Shi_Clan();

    public record Xiao() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "小"; }
        @Override public String meaning() { return "small"; }
        @Override public String pinyin()  { return "xiǎo"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xiao XIAO = new Xiao();

    public record Shao() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "少"; }
        @Override public String meaning() { return "few, young"; }
        @Override public String pinyin()  { return "shǎo"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shao SHAO = new Shao();

    public record Wen() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "文"; }
        @Override public String meaning() { return "writing, culture"; }
        @Override public String pinyin()  { return "wén"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wen WEN = new Wen();

    public record Gai() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "丐"; }
        @Override public String meaning() { return "beggar"; }
        @Override public String pinyin()  { return "gài"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gai GAI = new Gai();

    public record Ren_Ninth() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "壬"; }
        @Override public String meaning() { return "ninth Heavenly Stem"; }
        @Override public String pinyin()  { return "rén"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ren_Ninth REN_NINTH = new Ren_Ninth();

    public record Yi_Barbarian() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "夷"; }
        @Override public String meaning() { return "foreigner/barbarian"; }
        @Override public String pinyin()  { return "yí"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Barbarian YI_BARBARIAN = new Yi_Barbarian();

    public record Jie_Alone() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "孑"; }
        @Override public String meaning() { return "alone/solitary"; }
        @Override public String pinyin()  { return "jié"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jie_Alone JIE_ALONE = new Jie_Alone();

    public record Jue_Larva() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "孓"; }
        @Override public String meaning() { return "mosquito larva"; }
        @Override public String pinyin()  { return "jué"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jue_Larva JUE_LARVA = new Jue_Larva();

    public record Yin_Governor() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "尹"; }
        @Override public String meaning() { return "governor/administer"; }
        @Override public String pinyin()  { return "yǐn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yin_Governor YIN_GOVERNOR = new Yin_Governor();

    public record Li_Official() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "吏"; }
        @Override public String meaning() { return "official/clerk"; }
        @Override public String pinyin()  { return "lì"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Li_Official LI_OFFICIAL = new Li_Official();

    public record Shi_History() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "史"; }
        @Override public String meaning() { return "history/historian"; }
        @Override public String pinyin()  { return "shǐ"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_History SHI_HISTORY = new Shi_History();

    public record Yu_Yu() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "禹"; }
        @Override public String meaning() { return "Yu (legendary ruler)"; }
        @Override public String pinyin()  { return "yǔ"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Yu YU_YU = new Yu_Yu();

    public record Yu_Monkey() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "禺"; }
        @Override public String meaning() { return "area (archaic)"; }
        @Override public String pinyin()  { return "yú"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Monkey YU_MONKEY = new Yu_Monkey();

    public record You_Especially() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "尤"; }
        @Override public String meaning() { return "especially/fault"; }
        @Override public String pinyin()  { return "yóu"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final You_Especially YOU_ESPECIALLY = new You_Especially();

    public record Su() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "肃"; }
        @Override public String meaning() { return "solemn/respectful"; }
        @Override public String pinyin()  { return "sù"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Su SU = new Su();

    public record Er_And() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "而"; }
        @Override public String meaning() { return "and/but"; }
        @Override public String pinyin()  { return "ér"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Er_And ER_AND = new Er_And();

    public record Mian_Hide() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "丏"; }
        @Override public String meaning() { return "hide/screen"; }
        @Override public String pinyin()  { return "miǎn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mian_Hide MIAN_HIDE = new Mian_Hide();

    public record Tai_Great() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "太"; }
        @Override public String meaning() { return "great/supreme"; }
        @Override public String pinyin()  { return "tài"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Tai_Great TAI_GREAT = new Tai_Great();

    public record Yao() implements kranji.library.LibraryMember<BasicSet> {
        @Override public String glyph()   { return "夭"; }
        @Override public String meaning() { return "young/premature death"; }
        @Override public String pinyin()  { return "yāo"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yao YAO = new Yao();

    public static final List<kranji.library.LibraryMember<BasicSet>> ALL = List.of(
            REN, NV, ZI, DA, FU_HUSBAND, FU_FATHER, MU_MOTHER, XIONG, DI,
            WANG, CHEN, SHI, MIN, ER_CHILD, YOU, JUN, GUAN, SHI_CLAN,
            XIAO, SHAO, WEN, GAI, REN_NINTH, YI_BARBARIAN,
            JIE_ALONE, JUE_LARVA, YIN_GOVERNOR, LI_OFFICIAL, SHI_HISTORY,
            YU_YU, YU_MONKEY, YOU_ESPECIALLY, SU, ER_AND, MIAN_HIDE,
            TAI_GREAT, YAO);
}
