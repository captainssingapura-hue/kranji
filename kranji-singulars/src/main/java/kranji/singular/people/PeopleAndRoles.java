package kranji.singular.people;

import kranji.component.SingularZi;

public final class PeopleAndRoles {
    private PeopleAndRoles() {}

    public record Ren() implements SingularZi {
        @Override public String glyph()   { return "人"; }
        @Override public String meaning() { return "person"; }
        @Override public String pinyin()  { return "rén"; }
        @Override public int strokes()    { return 2; }
    }
    public static final Ren REN = new Ren();

    public record Nv() implements SingularZi {
        @Override public String glyph()   { return "女"; }
        @Override public String meaning() { return "woman"; }
        @Override public String pinyin()  { return "nǚ"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Nv NV = new Nv();

    public record Zi() implements SingularZi {
        @Override public String glyph()   { return "子"; }
        @Override public String meaning() { return "child, son"; }
        @Override public String pinyin()  { return "zǐ"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Zi ZI = new Zi();

    public record Da() implements SingularZi {
        @Override public String glyph()   { return "大"; }
        @Override public String meaning() { return "big, great"; }
        @Override public String pinyin()  { return "dà"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Da DA = new Da();

    public record Fu_Husband() implements SingularZi {
        @Override public String glyph()   { return "夫"; }
        @Override public String meaning() { return "husband, man"; }
        @Override public String pinyin()  { return "fū"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Fu_Husband FU_HUSBAND = new Fu_Husband();

    public record Fu_Father() implements SingularZi {
        @Override public String glyph()   { return "父"; }
        @Override public String meaning() { return "father"; }
        @Override public String pinyin()  { return "fù"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Fu_Father FU_FATHER = new Fu_Father();

    public record Mu_Mother() implements SingularZi {
        @Override public String glyph()   { return "母"; }
        @Override public String meaning() { return "mother"; }
        @Override public String pinyin()  { return "mǔ"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Mu_Mother MU_MOTHER = new Mu_Mother();

    public record Xiong() implements SingularZi {
        @Override public String glyph()   { return "兄"; }
        @Override public String meaning() { return "elder brother"; }
        @Override public String pinyin()  { return "xiōng"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Xiong XIONG = new Xiong();

    public record Di() implements SingularZi {
        @Override public String glyph()   { return "弟"; }
        @Override public String meaning() { return "younger brother"; }
        @Override public String pinyin()  { return "dì"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Di DI = new Di();

    public record Wang() implements SingularZi {
        @Override public String glyph()   { return "王"; }
        @Override public String meaning() { return "king"; }
        @Override public String pinyin()  { return "wáng"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Wang WANG = new Wang();

    public record Chen() implements SingularZi {
        @Override public String glyph()   { return "臣"; }
        @Override public String meaning() { return "minister"; }
        @Override public String pinyin()  { return "chén"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Chen CHEN = new Chen();

    public record Shi() implements SingularZi {
        @Override public String glyph()   { return "士"; }
        @Override public String meaning() { return "scholar"; }
        @Override public String pinyin()  { return "shì"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Shi SHI = new Shi();

    public record Min() implements SingularZi {
        @Override public String glyph()   { return "民"; }
        @Override public String meaning() { return "people"; }
        @Override public String pinyin()  { return "mín"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Min MIN = new Min();

    public record Er_Child() implements SingularZi {
        @Override public String glyph()   { return "儿"; }
        @Override public String meaning() { return "child"; }
        @Override public String pinyin()  { return "ér"; }
        @Override public int strokes()    { return 2; }
    }
    public static final Er_Child ER_CHILD = new Er_Child();

    public record You() implements SingularZi {
        @Override public String glyph()   { return "友"; }
        @Override public String meaning() { return "friend"; }
        @Override public String pinyin()  { return "yǒu"; }
        @Override public int strokes()    { return 4; }
    }
    public static final You YOU = new You();

    public record Jun() implements SingularZi {
        @Override public String glyph()   { return "君"; }
        @Override public String meaning() { return "lord"; }
        @Override public String pinyin()  { return "jūn"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Jun JUN = new Jun();

    public record Guan() implements SingularZi {
        @Override public String glyph()   { return "官"; }
        @Override public String meaning() { return "official"; }
        @Override public String pinyin()  { return "guān"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Guan GUAN = new Guan();

    public record Shi_Clan() implements SingularZi {
        @Override public String glyph()   { return "氏"; }
        @Override public String meaning() { return "clan"; }
        @Override public String pinyin()  { return "shì"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Shi_Clan SHI_CLAN = new Shi_Clan();

    public record Xiao() implements SingularZi {
        @Override public String glyph()   { return "小"; }
        @Override public String meaning() { return "small"; }
        @Override public String pinyin()  { return "xiǎo"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Xiao XIAO = new Xiao();

    public record Shao() implements SingularZi {
        @Override public String glyph()   { return "少"; }
        @Override public String meaning() { return "few, young"; }
        @Override public String pinyin()  { return "shǎo"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Shao SHAO = new Shao();

    public record Lao() implements SingularZi {
        @Override public String glyph()   { return "老"; }
        @Override public String meaning() { return "old"; }
        @Override public String pinyin()  { return "lǎo"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Lao LAO = new Lao();

    public record Wen() implements SingularZi {
        @Override public String glyph()   { return "文"; }
        @Override public String meaning() { return "writing, culture"; }
        @Override public String pinyin()  { return "wén"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Wen WEN = new Wen();

    public record Wu() implements SingularZi {
        @Override public String glyph()   { return "武"; }
        @Override public String meaning() { return "martial"; }
        @Override public String pinyin()  { return "wǔ"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Wu WU = new Wu();

    public record Shi_Scholar() implements SingularZi {
        @Override public String glyph()   { return "师"; }
        @Override public String meaning() { return "teacher"; }
        @Override public String pinyin()  { return "shī"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Shi_Scholar SHI_SCHOLAR = new Shi_Scholar();

    public record Nv_Slave() implements SingularZi {
        @Override public String glyph()   { return "奴"; }
        @Override public String meaning() { return "slave"; }
        @Override public String pinyin()  { return "nú"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Nv_Slave NV_SLAVE = new Nv_Slave();
}
