package kranji.singular.actions;

import kranji.classification.BlockRole;
import kranji.layout.Politeness;
import kranji.zi.ComposedBlock.TopBottom;
import kranji.zi.ComposedBlock.TopMiddleBottom;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class ActionsAndStates {
    private ActionsAndStates() {}

    /** 亡 — perish/flee. Yielding as top/middle in vertical stacks. */
    public record Wang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph()   { return "亡"; }
        @Override public String meaning() { return "perish, flee"; }
        @Override public String pinyinText()  { return "wáng"; }
        @Override public int strokes()    { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopMiddleBottom.Top)    return Politeness.YIELDING;
            if (role instanceof TopMiddleBottom.Middle) return Politeness.YIELDING;
            if (role instanceof TopBottom.Top)          return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }
    public static final Wang WANG = new Wang();

    public record Zou() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "走"; }
        @Override public String meaning() { return "walk, run"; }
        @Override public String pinyinText()  { return "zǒu"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zou ZOU = new Zou();

    public record Li_Stand() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "立"; }
        @Override public String meaning() { return "stand"; }
        @Override public String pinyinText()  { return "lì"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Li_Stand LI_STAND = new Li_Stand();

    public record Zhi_Stop() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "止"; }
        @Override public String meaning() { return "stop"; }
        @Override public String pinyinText()  { return "zhǐ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Stop ZHI_STOP = new Zhi_Stop();

    public record You_Again() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "又"; }
        @Override public String meaning() { return "again, also"; }
        @Override public String pinyinText()  { return "yòu"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final You_Again YOU_AGAIN = new You_Again();

    public record Jian_See() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "见"; }
        @Override public String meaning() { return "see"; }
        @Override public String pinyinText()  { return "jiàn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jian_See JIAN_SEE = new Jian_See();

    public record Yan_Speech() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "言"; }
        @Override public String meaning() { return "speech, word"; }
        @Override public String pinyinText()  { return "yán"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yan_Speech YAN_SPEECH = new Yan_Speech();

    public record Chu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "出"; }
        @Override public String meaning() { return "go out"; }
        @Override public String pinyinText()  { return "chū"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chu CHU = new Chu();

    public record Ru() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "入"; }
        @Override public String meaning() { return "enter"; }
        @Override public String pinyinText()  { return "rù"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ru RU = new Ru();

    public record Sheng() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "生"; }
        @Override public String meaning() { return "life, birth"; }
        @Override public String pinyinText()  { return "shēng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Sheng SHENG = new Sheng();

    public record Shi_Eat() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "食"; }
        @Override public String meaning() { return "eat, food"; }
        @Override public String pinyinText()  { return "shí"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Eat SHI_EAT = new Shi_Eat();

    public record Fei() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "飞"; }
        @Override public String meaning() { return "fly"; }
        @Override public String pinyinText()  { return "fēi"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fei FEI = new Fei();

    public record Jiao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "交"; }
        @Override public String meaning() { return "exchange, cross"; }
        @Override public String pinyinText()  { return "jiāo"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiao JIAO = new Jiao();

    public record Kai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "开"; }
        @Override public String meaning() { return "open"; }
        @Override public String pinyinText()  { return "kāi"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Kai KAI = new Kai();

    public record Gong_Work() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "工"; }
        @Override public String meaning() { return "work"; }
        @Override public String pinyinText()  { return "gōng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gong_Work GONG_WORK = new Gong_Work();

    public record Yong() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "用"; }
        @Override public String meaning() { return "use"; }
        @Override public String pinyinText()  { return "yòng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yong YONG = new Yong();

    public record Neng() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "能"; }
        @Override public String meaning() { return "can, able"; }
        @Override public String pinyinText()  { return "néng"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Neng NENG = new Neng();

    public record Qu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "去"; }
        @Override public String meaning() { return "go, leave"; }
        @Override public String pinyinText()  { return "qù"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qu QU = new Qu();

    public record Lai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "来"; }
        @Override public String meaning() { return "come"; }
        @Override public String pinyinText()  { return "lái"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Lai LAI = new Lai();

    public record Ke() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "可"; }
        @Override public String meaning() { return "can, may"; }
        @Override public String pinyinText()  { return "kě"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ke KE = new Ke();

    public record Shi_Yes() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "是"; }
        @Override public String meaning() { return "is, yes"; }
        @Override public String pinyinText()  { return "shì"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Yes SHI_YES = new Shi_Yes();

    public record Zuo_Sit() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "坐"; }
        @Override public String meaning() { return "sit"; }
        @Override public String pinyinText()  { return "zuò"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zuo_Sit ZUO_SIT = new Zuo_Sit();

    public record Zheng_Contest() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "争"; }
        @Override public String meaning() { return "contend, strive"; }
        @Override public String pinyinText()  { return "zhēng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zheng_Contest ZHENG_CONTEST = new Zheng_Contest();

    public record Cai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "采"; }
        @Override public String meaning() { return "gather, pick"; }
        @Override public String pinyinText()  { return "cǎi"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cai CAI = new Cai();

    // --- New records ---

    public record Yu_Give() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "与"; }
        @Override public String meaning() { return "give/participate"; }
        @Override public String pinyinText()  { return "yǔ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Give YU_GIVE = new Yu_Give();

    public record Zhuan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "专"; }
        @Override public String meaning() { return "exclusive/special"; }
        @Override public String pinyinText()  { return "zhuān"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhuan ZHUAN = new Zhuan();

    public record Chuan_String() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "串"; }
        @Override public String meaning() { return "string together"; }
        @Override public String pinyinText()  { return "chuàn"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chuan_String CHUAN_STRING = new Chuan_String();

    public record Wei_Do() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "为"; }
        @Override public String meaning() { return "do/act/for"; }
        @Override public String pinyinText()  { return "wéi"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wei_Do WEI_DO = new Wei_Do();

    public record Xi_Practice() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "习"; }
        @Override public String meaning() { return "practice/study"; }
        @Override public String pinyinText()  { return "xí"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xi_Practice XI_PRACTICE = new Xi_Practice();

    public record Shu_Write() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "书"; }
        @Override public String meaning() { return "book/write"; }
        @Override public String pinyinText()  { return "shū"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shu_Write SHU_WRITE = new Shu_Write();

    public record Le() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "了"; }
        @Override public String meaning() { return "finish/complete"; }
        @Override public String pinyinText()  { return "le"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Le LE = new Le();

    public record Yu_Bestow() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "予"; }
        @Override public String meaning() { return "bestow/give"; }
        @Override public String pinyinText()  { return "yǔ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Bestow YU_BESTOW = new Yu_Bestow();

    public record Ban_Handle() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "办"; }
        @Override public String meaning() { return "handle/manage"; }
        @Override public String pinyinText()  { return "bàn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ban_Handle BAN_HANDLE = new Ban_Handle();

    public record Ji_Strike() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "击"; }
        @Override public String meaning() { return "strike/hit"; }
        @Override public String pinyinText()  { return "jī"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Strike JI_STRIKE = new Ji_Strike();

    public record Ji_Reach() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "及"; }
        @Override public String meaning() { return "reach/and"; }
        @Override public String pinyinText()  { return "jí"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Reach JI_REACH = new Ji_Reach();

    public record Cheng_Bear() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "承"; }
        @Override public String meaning() { return "bear/undertake"; }
        @Override public String pinyinText()  { return "chéng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cheng_Bear CHENG_BEAR = new Cheng_Bear();

    public record Chi_Repel() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "斥"; }
        @Override public String meaning() { return "repel/denounce"; }
        @Override public String pinyinText()  { return "chì"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chi_Repel CHI_REPEL = new Chi_Repel();

    public record Ye_Drag() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "曳"; }
        @Override public String meaning() { return "drag/pull"; }
        @Override public String pinyinText()  { return "yè"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ye_Drag YE_DRAG = new Ye_Drag();

    public record Geng() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "更"; }
        @Override public String meaning() { return "change/more"; }
        @Override public String pinyinText()  { return "gēng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Geng GENG = new Geng();

    public record Qiu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "求"; }
        @Override public String meaning() { return "seek/request"; }
        @Override public String pinyinText()  { return "qiú"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qiu QIU = new Qiu();

    public record Shuai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "甩"; }
        @Override public String meaning() { return "fling/throw"; }
        @Override public String pinyinText()  { return "shuǎi"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shuai SHUAI = new Shuai();

    public record Le_Joy() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "乐"; }
        @Override public String meaning() { return "joy/music"; }
        @Override public String pinyinText()  { return "lè"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Le_Joy LE_JOY = new Le_Joy();

    public record Zai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "再"; }
        @Override public String meaning() { return "again"; }
        @Override public String pinyinText()  { return "zài"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zai ZAI = new Zai();

    public record Wu_Not() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "勿"; }
        @Override public String meaning() { return "do not"; }
        @Override public String pinyinText()  { return "wù"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Not WU_NOT = new Wu_Not();

    public record Cong() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "匆"; }
        @Override public String meaning() { return "hurried/hasty"; }
        @Override public String pinyinText()  { return "cōng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cong CONG = new Cong();

    public record Sheng_Rise() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "升"; }
        @Override public String meaning() { return "rise/ascend"; }
        @Override public String pinyinText()  { return "shēng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Sheng_Rise SHENG_RISE = new Sheng_Rise();

    public record Shi_Lose() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "失"; }
        @Override public String meaning() { return "lose"; }
        @Override public String pinyinText()  { return "shī"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Lose SHI_LOSE = new Shi_Lose();

    public record Wo() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "我"; }
        @Override public String meaning() { return "I/me"; }
        @Override public String pinyinText()  { return "wǒ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wo WO = new Wo();

    public record Jian_Combine() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "兼"; }
        @Override public String meaning() { return "combine/simultaneously"; }
        @Override public String pinyinText()  { return "jiān"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jian_Combine JIAN_COMBINE = new Jian_Combine();

    public record Bing_Grasp() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "秉"; }
        @Override public String meaning() { return "grasp/hold"; }
        @Override public String pinyinText()  { return "bǐng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bing_Grasp BING_GRASP = new Bing_Grasp();

    public record Chan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "产"; }
        @Override public String meaning() { return "produce/give birth"; }
        @Override public String pinyinText()  { return "chǎn"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chan CHAN = new Chan();

    public record Zhong_Heavy() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "重"; }
        @Override public String meaning() { return "heavy/repeat"; }
        @Override public String pinyinText()  { return "zhòng"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhong_Heavy ZHONG_HEAVY = new Zhong_Heavy();

    public record Qu_Bend() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "曲"; }
        @Override public String meaning() { return "bend/song"; }
        @Override public String pinyinText()  { return "qū"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qu_Bend QU_BEND = new Qu_Bend();

    public record Zha() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "乍"; }
        @Override public String meaning() { return "suddenly"; }
        @Override public String pinyinText()  { return "zhà"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zha ZHA = new Zha();

    public record Jia_Clamp() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "夹"; }
        @Override public String meaning() { return "clamp/pinch"; }
        @Override public String pinyinText()  { return "jiā"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jia_Clamp JIA_CLAMP = new Jia_Clamp();

    public record Wu_Dont() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "毋"; }
        @Override public String meaning() { return "do not"; }
        @Override public String pinyinText()  { return "wú"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Dont WU_DONT = new Wu_Dont();

    public record Guan_Pierce() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "毌"; }
        @Override public String meaning() { return "pierce through"; }
        @Override public String pinyinText()  { return "guàn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Guan_Pierce GUAN_PIERCE = new Guan_Pierce();

    public record Mie() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "乜"; }
        @Override public String meaning() { return "squint"; }
        @Override public String pinyinText()  { return "miē"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mie MIE = new Mie();

    public record Yi_Govern() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E42"; }
        @Override public String meaning() { return "govern, cut"; }
        @Override public String pinyinText()  { return "yì"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Govern YI_GOVERN = new Yi_Govern();

    /** 化 — change, transform. */
    public record Hua_Change() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5316"; }
        @Override public String meaning() { return "change, transform"; }
        @Override public String pinyinText()  { return "hu\u00E0"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hua_Change HUA_CHANGE = new Hua_Change();

    /** 敖 — roam, ramble. */
    public record Ao_Roam() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u6556"; }
        @Override public String meaning() { return "roam, ramble"; }
        @Override public String pinyinText()  { return "\u00E1o"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ao_Roam AO_ROAM = new Ao_Roam();

    /** 乞 — beg, plead. */
    public record Qi_Beg() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E5E"; }
        @Override public String meaning() { return "beg, plead"; }
        @Override public String pinyinText()  { return "q\u01D0"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qi_Beg QI_BEG = new Qi_Beg();

    /** 支 — branch, support. */
    public record Zhi_Branch() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u652F"; }
        @Override public String meaning() { return "branch, support"; }
        @Override public String pinyinText()  { return "zh\u012B"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Branch ZHI_BRANCH = new Zhi_Branch();

    /** 比 — compare, contrast. */
    public record Bi_Compare() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u6BD4"; }
        @Override public String meaning() { return "compare, contrast"; }
        @Override public String pinyinText()  { return "b\u01D0"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bi_Compare BI_COMPARE = new Bi_Compare();

    /** 勾 — hook, tick mark. */
    public record Gou() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u52FE"; }
        @Override public String meaning() { return "hook, tick mark"; }
        @Override public String pinyinText()  { return "g\u014Du"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gou GOU = new Gou();

    /** 归 — return. */
    public record Gui() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5F52"; }
        @Override public String meaning() { return "return"; }
        @Override public String pinyinText()  { return "gu\u012B"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gui GUI = new Gui();

    /** 叫 — call, shout. */
    public record Jiao_Call() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u53EB"; }
        @Override public String meaning() { return "call, shout"; }
        @Override public String pinyinText()  { return "ji\u00E0o"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiao_Call JIAO_CALL = new Jiao_Call();

    /** 令 — command, order. */
    public record Ling() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4EE4"; }
        @Override public String meaning() { return "command, order"; }
        @Override public String pinyinText()  { return "l\u00ECng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ling LING = new Ling();

    /** 犯 — violate, criminal. */
    public record Fan_Violate() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u72AF"; }
        @Override public String meaning() { return "violate, criminal"; }
        @Override public String pinyinText()  { return "f\u00E0n"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fan_Violate FAN_VIOLATE = new Fan_Violate();

    /** 处 — place, deal with. */
    public record Chu_Place() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5904"; }
        @Override public String meaning() { return "place, deal with"; }
        @Override public String pinyinText()  { return "ch\u01D4"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chu_Place CHU_PLACE = new Chu_Place();

    /** 务 — affair, business. */
    public record Wu_Affair() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u52A1"; }
        @Override public String meaning() { return "affair, business"; }
        @Override public String pinyinText()  { return "w\u00F9"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Affair WU_AFFAIR = new Wu_Affair();

    /** 汇 — converge, collect. */
    public record Hui_Converge() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u6C47"; }
        @Override public String meaning() { return "converge, collect"; }
        @Override public String pinyinText()  { return "hu\u00EC"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hui_Converge HUI_CONVERGE = new Hui_Converge();

    /** 讯 — message, interrogate. */
    public record Xun_Message() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u8BAF"; }
        @Override public String meaning() { return "message, interrogate"; }
        @Override public String pinyinText()  { return "x\u00F9n"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xun_Message XUN_MESSAGE = new Xun_Message();

    /** 发 — send, emit. */
    public record Fa_Send() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u53D1"; }
        @Override public String meaning() { return "send, emit"; }
        @Override public String pinyinText()  { return "f\u0101"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fa_Send FA_SEND = new Fa_Send();

    /** 孕 — pregnant. */
    public record Yun_Pregnant() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5B55"; }
        @Override public String meaning() { return "pregnant"; }
        @Override public String pinyinText()  { return "y\u00F9n"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yun_Pregnant YUN_PREGNANT = new Yun_Pregnant();

    /** 纠 — entangle, correct. */
    public record Jiu_Entangle() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u7EA0"; }
        @Override public String meaning() { return "entangle, correct"; }
        @Override public String pinyinText()  { return "ji\u016B"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiu_Entangle JIU_ENTANGLE = new Jiu_Entangle();

    /** \u514B — overcome. */
    public record Ke_Overcome() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u514B"; }
        @Override public String meaning() { return "overcome"; }
        @Override public String pinyinText()  { return "k\u00E8"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ke_Overcome KE_OVERCOME = new Ke_Overcome();

    /** \u514D — avoid/exempt. */
    public record Mian() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u514D"; }
        @Override public String meaning() { return "avoid, exempt"; }
        @Override public String pinyinText()  { return "mi\u01CEn"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mian MIAN = new Mian();

    /** \u6B65 — step/walk. */
    public record Bu_Step() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u6B65"; }
        @Override public String meaning() { return "step, walk"; }
        @Override public String pinyinText()  { return "b\u00F9"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bu_Step BU_STEP = new Bu_Step();

    /** \u4E71 — chaos/disorder. */
    public record Luan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E71"; }
        @Override public String meaning() { return "chaos, disorder"; }
        @Override public String pinyinText()  { return "lu\u00E0n"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Luan LUAN = new Luan();

    /** \u5E94 — should/respond. */
    public record Ying() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E94"; }
        @Override public String meaning() { return "should, respond"; }
        @Override public String pinyinText()  { return "y\u012Bng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ying YING = new Ying();

    /** \u5F03 — abandon. */
    public record Qi_Abandon() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5F03"; }
        @Override public String meaning() { return "abandon"; }
        @Override public String pinyinText()  { return "q\u00EC"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qi_Abandon QI_ABANDON = new Qi_Abandon();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            WANG, ZOU, LI_STAND, ZHI_STOP, YOU_AGAIN, JIAN_SEE, YAN_SPEECH,
            CHU, RU, SHENG, SHI_EAT, FEI, JIAO, KAI,
            GONG_WORK, YONG, NENG, QU, LAI, KE,
            SHI_YES, ZUO_SIT, ZHENG_CONTEST, CAI,
            YU_GIVE, ZHUAN, CHUAN_STRING, WEI_DO, XI_PRACTICE, SHU_WRITE,
            LE, YU_BESTOW, BAN_HANDLE, JI_STRIKE, JI_REACH, CHENG_BEAR,
            CHI_REPEL, YE_DRAG, GENG, QIU, SHUAI, LE_JOY, ZAI,
            WU_NOT, CONG, SHENG_RISE, SHI_LOSE, WO, JIAN_COMBINE,
            BING_GRASP, CHAN, ZHONG_HEAVY, QU_BEND, ZHA, JIA_CLAMP,
            WU_DONT, GUAN_PIERCE, MIE, YI_GOVERN,
            HUA_CHANGE, AO_ROAM,
            QI_BEG, ZHI_BRANCH, BI_COMPARE, GOU,
            GUI, JIAO_CALL, LING, FAN_VIOLATE, CHU_PLACE, WU_AFFAIR,
            HUI_CONVERGE, XUN_MESSAGE, FA_SEND, YUN_PREGNANT, JIU_ENTANGLE,
            KE_OVERCOME, MIAN, BU_STEP, LUAN, YING, QI_ABANDON);
}
