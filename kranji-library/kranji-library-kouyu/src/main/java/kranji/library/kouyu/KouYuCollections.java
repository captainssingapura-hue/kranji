package kranji.library.kouyu;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.CollectionId;

import java.util.List;

/**
 * 绕口令 and 顺口溜 — text that exists to be said aloud.
 *
 * <h2>绕口令 is filed by the sounds it works on</h2>
 *
 * <p>Which is unusual for a library and right for this one. A tongue-twister
 * is a drill: 四是四十是十 is about s against sh and nothing else, so a reader
 * who cannot hear that pair wants the whole shelf, and a reader who can wants
 * a different shelf. Filing them by theme would scatter the only property that
 * decides which one to read next.</p>
 *
 * <p>This is also the one group where the reader's phonic model earns its
 * keep: the shelves are named for the very contrasts {@code PhonicPartitions}
 * files the corpus under — 平舌 against 翘舌, n against l, front nasal against
 * back nasal.</p>
 *
 * <h2>顺口溜 is filed by what it is for</h2>
 *
 * <p>A 顺口溜 is a mnemonic wearing a rhyme — the numbers, the seasons, the
 * order of something worth remembering. Sound is incidental there; the subject
 * is the point.</p>
 */
public final class KouYuCollections {

    private KouYuCollections() {}

    private record Bundle(CollectionId id, String title, String summary,
                          List<ArticleRef> articles) implements ArticleCollection {}

    private static ArticleCollection bundle(String id, String title, String summary,
                                            ArticleRef... articles) {
        return new Bundle(CollectionId.named("kranji.library.kouyu." + id),
                title, summary, List.of(articles));
    }

    /** One piece: slug doubles as the local id and the file name. */
    private static ArticleRef k(String slug, String title) {
        return ArticleRef.of(slug, title, "/kranji/articles/kouyu/" + slug + ".txt");
    }

    // ── 绕口令, by the contrast it drills ──────────────────────────────

    public static final ArticleCollection S_SH = bundle("s-sh",
            "绕口令 · s 和 sh", "The pair most speakers merge, drilled hardest.",
            k("si-shi-si", "四是四"), k("shan-shang-si-si", "山上四十四"),
            k("shi-shi-shi", "石狮寺"), k("shu-shu-shu", "叔叔数树"),
            k("sha-shang-sha", "沙上晒纱"), k("san-shan-si-shui", "三山四水"));

    public static final ArticleCollection Z_ZH = bundle("z-zh",
            "绕口令 · z c s 和 zh ch sh", "Flat tongue against curled, one line at a time.",
            k("zi-zhi", "紫瓷汁"), k("chi-pu-tao", "吃葡萄"),
            k("zhang-zhuang-cang", "张庄和仓庄"), k("cai-chai", "菜和柴"),
            k("zao-zao-zao", "枣和澡"), k("chuan-chuan", "船和砖"));

    public static final ArticleCollection N_L = bundle("n-l",
            "绕口令 · n 和 l", "Nose against tongue-tip, which many dialects do not separate.",
            k("niu-he-liu", "牛和柳"), k("nan-nan-lan-lan", "南南和兰兰"),
            k("liu-nai-nai", "刘奶奶和牛奶奶"), k("nong-li", "农田和梨"),
            k("lan-hua-nan", "蓝花和南瓜"));

    public static final ArticleCollection B_P = bundle("b-p",
            "绕口令 · b p f", "Lips, with and without a puff of air.",
            k("bai-shi-ta", "白石塔"), k("pang-pang-he-pan-pan", "胖胖和盘盘"),
            k("bian-bian-pian", "扁担和板凳"), k("po-po-pao", "婆婆泡茶"),
            k("feng-fu", "风吹佛"));

    public static final ArticleCollection AN_ANG = bundle("an-ang",
            "绕口令 · 前鼻音和后鼻音", "an against ang, in against ing: the ending, not the start.",
            k("chang-chuang", "长窗"), k("ren-reng", "人和扔"),
            k("jin-jing", "金井"), k("ban-bang", "搬板凳"),
            k("qing-qin", "青琴"));

    public static final ArticleCollection J_Q_X = bundle("j-q-x",
            "绕口令 · j q x", "The three that need the tongue high and forward.",
            k("xiao-xi", "小溪"), k("qi-qi-qi", "七棋"),
            k("jiang-jiao", "姜和椒"), k("xue-xi", "学习"));

    // ── 顺口溜, by what it helps you remember ──────────────────────────

    public static final ArticleCollection SHU_ZI = bundle("shuzi",
            "顺口溜 · 数字", "Counting rhymes, which is where reading numbers starts.",
            k("yi-er-san", "一二三"), k("shu-shou-zhi", "数手指"),
            k("shu-ge-ge", "数哥哥"), k("yi-dao-shi", "一到十"),
            k("jia-jian", "加减歌"), k("shu-xing-xing", "数星星"));

    public static final ArticleCollection JIE_QI = bundle("jieqi",
            "顺口溜 · 节气与月份", "The year, in the order it happens.",
            k("si-ji", "四季歌"), k("shi-er-yue", "十二月"),
            k("jie-qi-ge", "节气歌"), k("shi-er-sheng-xiao", "十二生肖"),
            k("chun-xia-qiu-dong", "春夏秋冬"));

    public static final ArticleCollection SHENG_HUO = bundle("shenghuo",
            "顺口溜 · 生活", "Rhymes about washing, sleeping and getting to school.",
            k("zao-shang", "早上"), k("guo-ma-lu", "过马路"),
            k("xi-shou", "洗手歌"), k("shua-ya", "刷牙歌"),
            k("shou-gui-ju", "守规矩"), k("chi-fan-ge", "吃饭歌"),
            k("zao-shui-zao-qi", "早睡早起"));

    public static final ArticleCollection ZI_RAN = bundle("ziran",
            "顺口溜 · 自然", "Weather, animals and plants, in rhyme.",
            k("tian-qi-ge", "天气歌"), k("dong-wu-jiao", "动物叫"),
            k("zhong-shu-ge", "种树歌"), k("xia-yu-le", "下雨了"),
            k("hua-er-kai", "花儿开"));

    public static final ArticleCollection CHANG_JU = bundle("changju",
            "绕口令 · 长句", "Longer ones, for when the short shelves stop being hard.",
            k("hong-feng-huang", "红凤凰"), k("bian-dan-ban-deng", "扁担长板凳宽"),
            k("da-ba-ba", "八十八"), k("hua-hu", "画壶"),
            k("qi-jia-qi", "七加七"), k("shan-qian-si-shi", "山前山后"),
            k("bu-bu-bu", "补布裤"), k("zhi-zhu", "蜘蛛织网"));

    public static final ArticleCollection XUE_XI = bundle("xuexi",
            "顺口溜 · 学习", "Rhymes about writing, reading and sitting still.",
            k("xie-zi-ge", "写字歌"), k("du-shu-ge", "读书歌"),
            k("bi-shun", "笔顺歌"), k("zuo-zi-ge", "坐姿歌"),
            k("ting-jiang", "听讲歌"), k("fu-xi", "复习歌"),
            k("wen-wei-shen-me", "问为什么"), k("shou-hao-shu", "爱护书"));

    public static List<ArticleCollection> all() {
        return List.of(S_SH, Z_ZH, N_L, B_P, AN_ANG, J_Q_X,
                SHU_ZI, JIE_QI, SHENG_HUO, ZI_RAN, CHANG_JU, XUE_XI);
    }
}
