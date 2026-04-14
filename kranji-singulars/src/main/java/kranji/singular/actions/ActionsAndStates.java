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
    public record Wang() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph()   { return "亡"; }
        @Override public String meaning() { return "perish, flee"; }
        @Override public String pinyin()  { return "wáng"; }
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

    public record Jiao() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "交"; }
        @Override public String meaning() { return "exchange, cross"; }
        @Override public String pinyin()  { return "jiāo"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiao JIAO = new Jiao();

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

    // --- New records ---

    public record Yu_Give() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "与"; }
        @Override public String meaning() { return "give/participate"; }
        @Override public String pinyin()  { return "yǔ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Give YU_GIVE = new Yu_Give();

    public record Zhuan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "专"; }
        @Override public String meaning() { return "exclusive/special"; }
        @Override public String pinyin()  { return "zhuān"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhuan ZHUAN = new Zhuan();

    public record Chuan_String() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "串"; }
        @Override public String meaning() { return "string together"; }
        @Override public String pinyin()  { return "chuàn"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chuan_String CHUAN_STRING = new Chuan_String();

    public record Wei_Do() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "为"; }
        @Override public String meaning() { return "do/act/for"; }
        @Override public String pinyin()  { return "wéi"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wei_Do WEI_DO = new Wei_Do();

    public record Xi_Practice() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "习"; }
        @Override public String meaning() { return "practice/study"; }
        @Override public String pinyin()  { return "xí"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xi_Practice XI_PRACTICE = new Xi_Practice();

    public record Shu_Write() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "书"; }
        @Override public String meaning() { return "book/write"; }
        @Override public String pinyin()  { return "shū"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shu_Write SHU_WRITE = new Shu_Write();

    public record Le() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "了"; }
        @Override public String meaning() { return "finish/complete"; }
        @Override public String pinyin()  { return "le"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Le LE = new Le();

    public record Yu_Bestow() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "予"; }
        @Override public String meaning() { return "bestow/give"; }
        @Override public String pinyin()  { return "yǔ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Bestow YU_BESTOW = new Yu_Bestow();

    public record Ban_Handle() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "办"; }
        @Override public String meaning() { return "handle/manage"; }
        @Override public String pinyin()  { return "bàn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ban_Handle BAN_HANDLE = new Ban_Handle();

    public record Ji_Strike() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "击"; }
        @Override public String meaning() { return "strike/hit"; }
        @Override public String pinyin()  { return "jī"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Strike JI_STRIKE = new Ji_Strike();

    public record Ji_Reach() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "及"; }
        @Override public String meaning() { return "reach/and"; }
        @Override public String pinyin()  { return "jí"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Reach JI_REACH = new Ji_Reach();

    public record Cheng_Bear() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "承"; }
        @Override public String meaning() { return "bear/undertake"; }
        @Override public String pinyin()  { return "chéng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cheng_Bear CHENG_BEAR = new Cheng_Bear();

    public record Chi_Repel() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "斥"; }
        @Override public String meaning() { return "repel/denounce"; }
        @Override public String pinyin()  { return "chì"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chi_Repel CHI_REPEL = new Chi_Repel();

    public record Ye_Drag() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "曳"; }
        @Override public String meaning() { return "drag/pull"; }
        @Override public String pinyin()  { return "yè"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ye_Drag YE_DRAG = new Ye_Drag();

    public record Geng() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "更"; }
        @Override public String meaning() { return "change/more"; }
        @Override public String pinyin()  { return "gēng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Geng GENG = new Geng();

    public record Qiu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "求"; }
        @Override public String meaning() { return "seek/request"; }
        @Override public String pinyin()  { return "qiú"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qiu QIU = new Qiu();

    public record Shuai() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "甩"; }
        @Override public String meaning() { return "fling/throw"; }
        @Override public String pinyin()  { return "shuǎi"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shuai SHUAI = new Shuai();

    public record Le_Joy() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "乐"; }
        @Override public String meaning() { return "joy/music"; }
        @Override public String pinyin()  { return "lè"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Le_Joy LE_JOY = new Le_Joy();

    public record Zai() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "再"; }
        @Override public String meaning() { return "again"; }
        @Override public String pinyin()  { return "zài"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zai ZAI = new Zai();

    public record Wu_Not() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "勿"; }
        @Override public String meaning() { return "do not"; }
        @Override public String pinyin()  { return "wù"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Not WU_NOT = new Wu_Not();

    public record Cong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "匆"; }
        @Override public String meaning() { return "hurried/hasty"; }
        @Override public String pinyin()  { return "cōng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cong CONG = new Cong();

    public record Sheng_Rise() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "升"; }
        @Override public String meaning() { return "rise/ascend"; }
        @Override public String pinyin()  { return "shēng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Sheng_Rise SHENG_RISE = new Sheng_Rise();

    public record Shi_Lose() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "失"; }
        @Override public String meaning() { return "lose"; }
        @Override public String pinyin()  { return "shī"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Lose SHI_LOSE = new Shi_Lose();

    public record Wo() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "我"; }
        @Override public String meaning() { return "I/me"; }
        @Override public String pinyin()  { return "wǒ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wo WO = new Wo();

    public record Jian_Combine() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "兼"; }
        @Override public String meaning() { return "combine/simultaneously"; }
        @Override public String pinyin()  { return "jiān"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jian_Combine JIAN_COMBINE = new Jian_Combine();

    public record Bing_Grasp() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "秉"; }
        @Override public String meaning() { return "grasp/hold"; }
        @Override public String pinyin()  { return "bǐng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bing_Grasp BING_GRASP = new Bing_Grasp();

    public record Chan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "产"; }
        @Override public String meaning() { return "produce/give birth"; }
        @Override public String pinyin()  { return "chǎn"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chan CHAN = new Chan();

    public record Zhong_Heavy() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "重"; }
        @Override public String meaning() { return "heavy/repeat"; }
        @Override public String pinyin()  { return "zhòng"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhong_Heavy ZHONG_HEAVY = new Zhong_Heavy();

    public record Qu_Bend() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "曲"; }
        @Override public String meaning() { return "bend/song"; }
        @Override public String pinyin()  { return "qū"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qu_Bend QU_BEND = new Qu_Bend();

    public record Zha() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "乍"; }
        @Override public String meaning() { return "suddenly"; }
        @Override public String pinyin()  { return "zhà"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zha ZHA = new Zha();

    public record Jia_Clamp() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "夹"; }
        @Override public String meaning() { return "clamp/pinch"; }
        @Override public String pinyin()  { return "jiā"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jia_Clamp JIA_CLAMP = new Jia_Clamp();

    public record Wu_Dont() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "毋"; }
        @Override public String meaning() { return "do not"; }
        @Override public String pinyin()  { return "wú"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Dont WU_DONT = new Wu_Dont();

    public record Guan_Pierce() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "毌"; }
        @Override public String meaning() { return "pierce through"; }
        @Override public String pinyin()  { return "guàn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Guan_Pierce GUAN_PIERCE = new Guan_Pierce();

    public record Mie() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "乜"; }
        @Override public String meaning() { return "squint"; }
        @Override public String pinyin()  { return "miē"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mie MIE = new Mie();

    public record Yi_Govern() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "\u4E42"; }
        @Override public String meaning() { return "govern, cut"; }
        @Override public String pinyin()  { return "yì"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Govern YI_GOVERN = new Yi_Govern();

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
            WU_DONT, GUAN_PIERCE, MIE, YI_GOVERN);
}
