package kranji.singular.actions;

import kranji.component.SingularZi;

public final class ActionsAndStates {
    private ActionsAndStates() {}

    public record Zou() implements SingularZi {
        @Override public String glyph()   { return "走"; }
        @Override public String meaning() { return "walk, run"; }
        @Override public String pinyin()  { return "zǒu"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Zou ZOU = new Zou();

    public record Li_Stand() implements SingularZi {
        @Override public String glyph()   { return "立"; }
        @Override public String meaning() { return "stand"; }
        @Override public String pinyin()  { return "lì"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Li_Stand LI_STAND = new Li_Stand();

    public record Zhi_Stop() implements SingularZi {
        @Override public String glyph()   { return "止"; }
        @Override public String meaning() { return "stop"; }
        @Override public String pinyin()  { return "zhǐ"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Zhi_Stop ZHI_STOP = new Zhi_Stop();

    public record You_Again() implements SingularZi {
        @Override public String glyph()   { return "又"; }
        @Override public String meaning() { return "again, also"; }
        @Override public String pinyin()  { return "yòu"; }
        @Override public int strokes()    { return 2; }
    }
    public static final You_Again YOU_AGAIN = new You_Again();

    public record Jian_See() implements SingularZi {
        @Override public String glyph()   { return "见"; }
        @Override public String meaning() { return "see"; }
        @Override public String pinyin()  { return "jiàn"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Jian_See JIAN_SEE = new Jian_See();

    public record Yan_Speech() implements SingularZi {
        @Override public String glyph()   { return "言"; }
        @Override public String meaning() { return "speech, word"; }
        @Override public String pinyin()  { return "yán"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Yan_Speech YAN_SPEECH = new Yan_Speech();

    public record Chu() implements SingularZi {
        @Override public String glyph()   { return "出"; }
        @Override public String meaning() { return "go out"; }
        @Override public String pinyin()  { return "chū"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Chu CHU = new Chu();

    public record Ru() implements SingularZi {
        @Override public String glyph()   { return "入"; }
        @Override public String meaning() { return "enter"; }
        @Override public String pinyin()  { return "rù"; }
        @Override public int strokes()    { return 2; }
    }
    public static final Ru RU = new Ru();

    public record Sheng() implements SingularZi {
        @Override public String glyph()   { return "生"; }
        @Override public String meaning() { return "life, birth"; }
        @Override public String pinyin()  { return "shēng"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Sheng SHENG = new Sheng();

    public record Si_Die() implements SingularZi {
        @Override public String glyph()   { return "死"; }
        @Override public String meaning() { return "die, death"; }
        @Override public String pinyin()  { return "sǐ"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Si_Die SI_DIE = new Si_Die();

    public record Shi_Eat() implements SingularZi {
        @Override public String glyph()   { return "食"; }
        @Override public String meaning() { return "eat, food"; }
        @Override public String pinyin()  { return "shí"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Shi_Eat SHI_EAT = new Shi_Eat();

    public record Fei() implements SingularZi {
        @Override public String glyph()   { return "飞"; }
        @Override public String meaning() { return "fly"; }
        @Override public String pinyin()  { return "fēi"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Fei FEI = new Fei();

    public record Xing() implements SingularZi {
        @Override public String glyph()   { return "行"; }
        @Override public String meaning() { return "walk, travel"; }
        @Override public String pinyin()  { return "xíng"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Xing XING = new Xing();

    public record Da_Strike() implements SingularZi {
        @Override public String glyph()   { return "打"; }
        @Override public String meaning() { return "hit, strike"; }
        @Override public String pinyin()  { return "dǎ"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Da_Strike DA_STRIKE = new Da_Strike();

    public record Jiao() implements SingularZi {
        @Override public String glyph()   { return "交"; }
        @Override public String meaning() { return "exchange, cross"; }
        @Override public String pinyin()  { return "jiāo"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Jiao JIAO = new Jiao();

    public record Dui() implements SingularZi {
        @Override public String glyph()   { return "对"; }
        @Override public String meaning() { return "correct, face"; }
        @Override public String pinyin()  { return "duì"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Dui DUI = new Dui();

    public record Kai() implements SingularZi {
        @Override public String glyph()   { return "开"; }
        @Override public String meaning() { return "open"; }
        @Override public String pinyin()  { return "kāi"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Kai KAI = new Kai();

    public record Gong_Work() implements SingularZi {
        @Override public String glyph()   { return "工"; }
        @Override public String meaning() { return "work"; }
        @Override public String pinyin()  { return "gōng"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Gong_Work GONG_WORK = new Gong_Work();

    public record Yong() implements SingularZi {
        @Override public String glyph()   { return "用"; }
        @Override public String meaning() { return "use"; }
        @Override public String pinyin()  { return "yòng"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Yong YONG = new Yong();

    public record Zhi_Know() implements SingularZi {
        @Override public String glyph()   { return "知"; }
        @Override public String meaning() { return "know"; }
        @Override public String pinyin()  { return "zhī"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Zhi_Know ZHI_KNOW = new Zhi_Know();

    public record Neng() implements SingularZi {
        @Override public String glyph()   { return "能"; }
        @Override public String meaning() { return "can, able"; }
        @Override public String pinyin()  { return "néng"; }
        @Override public int strokes()    { return 10; }
    }
    public static final Neng NENG = new Neng();

    public record Qu() implements SingularZi {
        @Override public String glyph()   { return "去"; }
        @Override public String meaning() { return "go, leave"; }
        @Override public String pinyin()  { return "qù"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Qu QU = new Qu();

    public record Lai() implements SingularZi {
        @Override public String glyph()   { return "来"; }
        @Override public String meaning() { return "come"; }
        @Override public String pinyin()  { return "lái"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Lai LAI = new Lai();

    public record Ke() implements SingularZi {
        @Override public String glyph()   { return "可"; }
        @Override public String meaning() { return "can, may"; }
        @Override public String pinyin()  { return "kě"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Ke KE = new Ke();

    public record Shi_Yes() implements SingularZi {
        @Override public String glyph()   { return "是"; }
        @Override public String meaning() { return "is, yes"; }
        @Override public String pinyin()  { return "shì"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Shi_Yes SHI_YES = new Shi_Yes();

    public record Zuo_Sit() implements SingularZi {
        @Override public String glyph()   { return "坐"; }
        @Override public String meaning() { return "sit"; }
        @Override public String pinyin()  { return "zuò"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Zuo_Sit ZUO_SIT = new Zuo_Sit();

    public record She_Shoot() implements SingularZi {
        @Override public String glyph()   { return "射"; }
        @Override public String meaning() { return "shoot"; }
        @Override public String pinyin()  { return "shè"; }
        @Override public int strokes()    { return 10; }
    }
    public static final She_Shoot SHE_SHOOT = new She_Shoot();

    public record Zheng_Contest() implements SingularZi {
        @Override public String glyph()   { return "争"; }
        @Override public String meaning() { return "contend, strive"; }
        @Override public String pinyin()  { return "zhēng"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Zheng_Contest ZHENG_CONTEST = new Zheng_Contest();

    public record Cai() implements SingularZi {
        @Override public String glyph()   { return "采"; }
        @Override public String meaning() { return "gather, pick"; }
        @Override public String pinyin()  { return "cǎi"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Cai CAI = new Cai();

    public record Fang_Release() implements SingularZi {
        @Override public String glyph()   { return "放"; }
        @Override public String meaning() { return "release, let go"; }
        @Override public String pinyin()  { return "fàng"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Fang_Release FANG_RELEASE = new Fang_Release();
}
