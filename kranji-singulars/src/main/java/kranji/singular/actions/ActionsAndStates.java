package kranji.singular.actions;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class ActionsAndStates {
    private ActionsAndStates() {}

    public record Zou() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "走"; }
        @Override public String meaning() { return "walk, run"; }
        @Override public String pinyin()  { return "zǒu"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zou ZOU = new Zou();

    public record Li_Stand() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "立"; }
        @Override public String meaning() { return "stand"; }
        @Override public String pinyin()  { return "lì"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Li_Stand LI_STAND = new Li_Stand();

    public record Zhi_Stop() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "止"; }
        @Override public String meaning() { return "stop"; }
        @Override public String pinyin()  { return "zhǐ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Stop ZHI_STOP = new Zhi_Stop();

    public record You_Again() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "又"; }
        @Override public String meaning() { return "again, also"; }
        @Override public String pinyin()  { return "yòu"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final You_Again YOU_AGAIN = new You_Again();

    public record Jian_See() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "见"; }
        @Override public String meaning() { return "see"; }
        @Override public String pinyin()  { return "jiàn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jian_See JIAN_SEE = new Jian_See();

    public record Yan_Speech() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "言"; }
        @Override public String meaning() { return "speech, word"; }
        @Override public String pinyin()  { return "yán"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yan_Speech YAN_SPEECH = new Yan_Speech();

    public record Chu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "出"; }
        @Override public String meaning() { return "go out"; }
        @Override public String pinyin()  { return "chū"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chu CHU = new Chu();

    public record Ru() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "入"; }
        @Override public String meaning() { return "enter"; }
        @Override public String pinyin()  { return "rù"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ru RU = new Ru();

    public record Sheng() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "生"; }
        @Override public String meaning() { return "life, birth"; }
        @Override public String pinyin()  { return "shēng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Sheng SHENG = new Sheng();

    public record Si_Die() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "死"; }
        @Override public String meaning() { return "die, death"; }
        @Override public String pinyin()  { return "sǐ"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Si_Die SI_DIE = new Si_Die();

    public record Shi_Eat() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "食"; }
        @Override public String meaning() { return "eat, food"; }
        @Override public String pinyin()  { return "shí"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Eat SHI_EAT = new Shi_Eat();

    public record Fei() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "飞"; }
        @Override public String meaning() { return "fly"; }
        @Override public String pinyin()  { return "fēi"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fei FEI = new Fei();

    public record Xing() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "行"; }
        @Override public String meaning() { return "walk, travel"; }
        @Override public String pinyin()  { return "xíng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xing XING = new Xing();

    public record Da_Strike() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "打"; }
        @Override public String meaning() { return "hit, strike"; }
        @Override public String pinyin()  { return "dǎ"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Da_Strike DA_STRIKE = new Da_Strike();

    public record Jiao() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "交"; }
        @Override public String meaning() { return "exchange, cross"; }
        @Override public String pinyin()  { return "jiāo"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiao JIAO = new Jiao();

    public record Dui() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "对"; }
        @Override public String meaning() { return "correct, face"; }
        @Override public String pinyin()  { return "duì"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dui DUI = new Dui();

    public record Kai() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "开"; }
        @Override public String meaning() { return "open"; }
        @Override public String pinyin()  { return "kāi"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Kai KAI = new Kai();

    public record Gong_Work() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "工"; }
        @Override public String meaning() { return "work"; }
        @Override public String pinyin()  { return "gōng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gong_Work GONG_WORK = new Gong_Work();

    public record Yong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "用"; }
        @Override public String meaning() { return "use"; }
        @Override public String pinyin()  { return "yòng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yong YONG = new Yong();

    public record Zhi_Know() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "知"; }
        @Override public String meaning() { return "know"; }
        @Override public String pinyin()  { return "zhī"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Know ZHI_KNOW = new Zhi_Know();

    public record Neng() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "能"; }
        @Override public String meaning() { return "can, able"; }
        @Override public String pinyin()  { return "néng"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Neng NENG = new Neng();

    public record Qu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "去"; }
        @Override public String meaning() { return "go, leave"; }
        @Override public String pinyin()  { return "qù"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qu QU = new Qu();

    public record Lai() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "来"; }
        @Override public String meaning() { return "come"; }
        @Override public String pinyin()  { return "lái"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Lai LAI = new Lai();

    public record Ke() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "可"; }
        @Override public String meaning() { return "can, may"; }
        @Override public String pinyin()  { return "kě"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ke KE = new Ke();

    public record Shi_Yes() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "是"; }
        @Override public String meaning() { return "is, yes"; }
        @Override public String pinyin()  { return "shì"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Yes SHI_YES = new Shi_Yes();

    public record Zuo_Sit() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "坐"; }
        @Override public String meaning() { return "sit"; }
        @Override public String pinyin()  { return "zuò"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zuo_Sit ZUO_SIT = new Zuo_Sit();

    public record She_Shoot() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "射"; }
        @Override public String meaning() { return "shoot"; }
        @Override public String pinyin()  { return "shè"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final She_Shoot SHE_SHOOT = new She_Shoot();

    public record Zheng_Contest() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "争"; }
        @Override public String meaning() { return "contend, strive"; }
        @Override public String pinyin()  { return "zhēng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zheng_Contest ZHENG_CONTEST = new Zheng_Contest();

    public record Cai() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "采"; }
        @Override public String meaning() { return "gather, pick"; }
        @Override public String pinyin()  { return "cǎi"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cai CAI = new Cai();

    public record Fang_Release() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "放"; }
        @Override public String meaning() { return "release, let go"; }
        @Override public String pinyin()  { return "fàng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fang_Release FANG_RELEASE = new Fang_Release();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            ZOU, LI_STAND, ZHI_STOP, YOU_AGAIN, JIAN_SEE, YAN_SPEECH,
            CHU, RU, SHENG, SI_DIE, SHI_EAT, FEI, XING, DA_STRIKE,
            JIAO, DUI, KAI, GONG_WORK, YONG, ZHI_KNOW, NENG,
            QU, LAI, KE, SHI_YES, ZUO_SIT, SHE_SHOOT, ZHENG_CONTEST,
            CAI, FANG_RELEASE);
}
