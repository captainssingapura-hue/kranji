package kranji.library.shici;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.CollectionId;

import java.util.List;

/**
 * 古诗词 — classical verse, filed by era, then by poet, then by form.
 *
 * <h2>Why the poet is the middle level</h2>
 *
 * <p>Era is how the anthologies divide and how a child is taught to place a
 * poem. Poet is the level a reader actually navigates by: having liked 静夜思
 * the next thing wanted is more 李白, not more 五言绝句. Form is last because
 * it is the level that matters least to a reader and most to a teacher, and it
 * only earns a shelf where a poet has enough poems for the distinction to
 * separate anything.</p>
 *
 * <p>So 李白, 杜甫 and 王维 divide by form and the rest do not. A tree that
 * split every poet would be mostly empty shelves, and an empty shelf looks
 * like a gap in the collection rather than a gap in the scheme.</p>
 *
 * <h2>Provenance</h2>
 *
 * <p>Every poem here is centuries old and out of copyright everywhere. The
 * texts are the standard ones; the titles, attributions and reading pins are
 * this project's. 李煜 is filed under 宋词 rather than 南唐, which is wrong
 * historically and right for a reader — his 词 belong beside 李清照's.</p>
 */
public final class ShiCiCollections {

    private ShiCiCollections() {}

    private record Bundle(CollectionId id, String title, String summary,
                          List<ArticleRef> articles) implements ArticleCollection {}

    private static ArticleCollection bundle(String id, String title, String summary,
                                            ArticleRef... articles) {
        return new Bundle(CollectionId.named("kranji.library.shici." + id),
                title, summary, List.of(articles));
    }

    private static String res(String slug) {
        return "/kranji/articles/shici/" + slug + ".txt";
    }

    /** One poem: slug doubles as the local id and the file name. */
    private static ArticleRef p(String slug, String title, String author) {
        return ArticleRef.by(slug, title, author, res(slug));
    }

    // ── 唐诗 · 李白 ────────────────────────────────────────────────────

    public static final ArticleCollection LI_BAI_WUYAN = bundle("libai-wuyan",
            "李白 · 五言", "Five-character lines: the shortest, and the first learnt.",
            p("libai-jing-ye-si", "静夜思", "李白"),
            p("libai-du-zuo-jing-ting-shan", "独坐敬亭山", "李白"),
            p("libai-yuan-qing", "怨情", "李白"),
            p("libai-ye-su-shan-si", "夜宿山寺", "李白"),
            p("libai-qiu-pu-ge", "秋浦歌", "李白"),
            p("libai-lao-lao-ting", "劳劳亭", "李白"),
            p("libai-yu-jie-yuan", "玉阶怨", "李白"),
            p("libai-song-you-ren", "送友人", "李白"));

    public static final ArticleCollection LI_BAI_QIYAN = bundle("libai-qiyan",
            "李白 · 七言", "Seven-character lines: longer breath, wider picture.",
            p("libai-wang-lu-shan-pu-bu", "望庐山瀑布", "李白"),
            p("libai-zao-fa-bai-di-cheng", "早发白帝城", "李白"),
            p("libai-zeng-wang-lun", "赠汪伦", "李白"),
            p("libai-wang-tian-men-shan", "望天门山", "李白"),
            p("libai-huang-he-lou-song", "黄鹤楼送孟浩然之广陵", "李白"),
            p("libai-ke-zhong-xing", "客中行", "李白"),
            p("libai-e-mei-shan-yue-ge", "峨眉山月歌", "李白"),
            p("libai-shan-zhong-wen-da", "山中问答", "李白"));

    // ── 唐诗 · 杜甫 ────────────────────────────────────────────────────

    public static final ArticleCollection DU_FU_WUYAN = bundle("dufu-wuyan",
            "杜甫 · 五言", "Five-character lines from the other half of 李杜.",
            p("dufu-jue-ju-chi-ri", "绝句 · 迟日江山丽", "杜甫"),
            p("dufu-ba-zhen-tu", "八阵图", "杜甫"),
            p("dufu-chun-wang", "春望", "杜甫"),
            p("dufu-wang-yue", "望岳", "杜甫"),
            p("dufu-lu-ye-shu-huai", "旅夜书怀", "杜甫"),
            p("dufu-yue-ye-yi-she-di", "月夜忆舍弟", "杜甫"));

    public static final ArticleCollection DU_FU_QIYAN = bundle("dufu-qiyan",
            "杜甫 · 七言", "Seven-character lines, including the one every child learns.",
            p("dufu-jue-ju-liang-ge", "绝句 · 两个黄鹂", "杜甫"),
            p("dufu-chun-ye-xi-yu", "春夜喜雨", "杜甫"),
            p("dufu-jiang-nan-feng-li", "江南逢李龟年", "杜甫"),
            p("dufu-wen-guan-jun", "闻官军收河南河北", "杜甫"),
            p("dufu-jiang-pan-du-bu", "江畔独步寻花", "杜甫"),
            p("dufu-deng-gao", "登高", "杜甫"));

    // ── 唐诗 · 王维 ────────────────────────────────────────────────────

    public static final ArticleCollection WANG_WEI_SHAN_SHUI = bundle("wangwei-shanshui",
            "王维 · 山水", "Hills, rain, and being alone in them.",
            p("wangwei-lu-chai", "鹿柴", "王维"),
            p("wangwei-zhu-li-guan", "竹里馆", "王维"),
            p("wangwei-shan-ju-qiu-ming", "山居秋暝", "王维"),
            p("wangwei-niao-ming-jian", "鸟鸣涧", "王维"),
            p("wangwei-shan-zhong", "山中", "王维"));

    public static final ArticleCollection WANG_WEI_SONG_BIE = bundle("wangwei-songbie",
            "王维 · 送别与怀人", "Partings, and the people not there.",
            p("wangwei-xiang-si", "相思", "王维"),
            p("wangwei-jiu-yue-jiu-ri", "九月九日忆山东兄弟", "王维"),
            p("wangwei-song-yuan-er", "送元二使安西", "王维"),
            p("wangwei-za-shi", "杂诗", "王维"),
            p("wangwei-song-bie", "送别", "王维"));

    // ── 唐诗 · one shelf each ─────────────────────────────────────────

    public static final ArticleCollection BAI_JU_YI = bundle("baijuyi",
            "白居易", "Written to be understood by anybody, which is why they last.",
            p("baijuyi-fu-de-gu-yuan-cao", "赋得古原草送别", "白居易"),
            p("baijuyi-chi-shang", "池上", "白居易"),
            p("baijuyi-mu-jiang-yin", "暮江吟", "白居易"),
            p("baijuyi-yi-jiang-nan", "忆江南", "白居易"),
            p("baijuyi-wen-liu-shi-jiu", "问刘十九", "白居易"),
            p("baijuyi-da-lin-si-tao-hua", "大林寺桃花", "白居易"),
            p("baijuyi-you-zi-yin", "邯郸冬至夜思家", "白居易"),
            p("baijuyi-cao-tang", "村夜", "白居易"));

    public static final ArticleCollection MENG_HAO_RAN = bundle("menghaoran",
            "孟浩然", "Mornings, rivers, and not getting up.",
            p("menghaoran-chun-xiao", "春晓", "孟浩然"),
            p("menghaoran-su-jian-de-jiang", "宿建德江", "孟浩然"),
            p("menghaoran-guo-gu-ren-zhuang", "过故人庄", "孟浩然"),
            p("menghaoran-wang-dong-ting", "望洞庭湖赠张丞相", "孟浩然"),
            p("menghaoran-xia-ri-nan-ting", "夏日南亭怀辛大", "孟浩然"));

    public static final ArticleCollection DU_MU = bundle("dumu",
            "杜牧", "Autumn, wine, and a sharp last line.",
            p("dumu-shan-xing", "山行", "杜牧"),
            p("dumu-qing-ming", "清明", "杜牧"),
            p("dumu-jiang-nan-chun", "江南春", "杜牧"),
            p("dumu-qiu-xi", "秋夕", "杜牧"),
            p("dumu-bo-qin-huai", "泊秦淮", "杜牧"),
            p("dumu-chi-bi", "赤壁", "杜牧"),
            p("dumu-zeng-bie", "赠别", "杜牧"),
            p("dumu-guo-hua-qing-gong", "过华清宫", "杜牧"));

    public static final ArticleCollection LI_SHANG_YIN = bundle("lishangyin",
            "李商隐", "Beautiful, and not always explainable.",
            p("lishangyin-le-you-yuan", "登乐游原", "李商隐"),
            p("lishangyin-ye-yu-ji-bei", "夜雨寄北", "李商隐"),
            p("lishangyin-wu-ti", "无题 · 相见时难", "李商隐"),
            p("lishangyin-chang-e", "嫦娥", "李商隐"),
            p("lishangyin-jin-se", "锦瑟", "李商隐"),
            p("lishangyin-luo-hua", "落花", "李商隐"));

    public static final ArticleCollection LIU_YU_XI = bundle("liuyuxi",
            "刘禹锡", "Cheerful about things other poets are sad about.",
            p("liuyuxi-wu-yi-xiang", "乌衣巷", "刘禹锡"),
            p("liuyuxi-qiu-ci", "秋词", "刘禹锡"),
            p("liuyuxi-wang-dong-ting", "望洞庭", "刘禹锡"),
            p("liuyuxi-zhu-zhi-ci", "竹枝词", "刘禹锡"),
            p("liuyuxi-lang-tao-sha", "浪淘沙", "刘禹锡"),
            p("liuyuxi-shi-tou-cheng", "石头城", "刘禹锡"));

    public static final ArticleCollection WANG_CHANG_LING = bundle("wangchangling",
            "王昌龄", "The frontier, at night, from a long way from home.",
            p("wangchangling-chu-sai", "出塞", "王昌龄"),
            p("wangchangling-fu-rong-lou", "芙蓉楼送辛渐", "王昌龄"),
            p("wangchangling-cong-jun-xing", "从军行", "王昌龄"),
            p("wangchangling-cai-lian-qu", "采莲曲", "王昌龄"),
            p("wangchangling-gui-yuan", "闺怨", "王昌龄"));

    public static final ArticleCollection BIAN_SAI = bundle("biansai",
            "边塞诗", "Snow, horses, and men who are not coming back.",
            p("wangzhihuan-liang-zhou-ci", "凉州词", "王之涣"),
            p("wanghan-liang-zhou-ci", "凉州词 · 葡萄美酒", "王翰"),
            p("censhen-feng-ru-jing", "逢入京使", "岑参"),
            p("censhen-bai-xue-ge", "白雪歌送武判官归京", "岑参"),
            p("gaoshi-bie-dong-da", "别董大", "高适"),
            p("lulun-sai-xia-qu", "塞下曲", "卢纶"));

    public static final ArticleCollection TANG_QI_TA = bundle("tang-qita",
            "唐诗 · 其他", "One famous poem each, from poets a child meets once.",
            p("wangzhihuan-deng-guan-que-lou", "登鹳雀楼", "王之涣"),
            p("luobinwang-yong-e", "咏鹅", "骆宾王"),
            p("lishen-min-nong", "悯农", "李绅"),
            p("hezhizhang-hui-xiang-ou-shu", "回乡偶书", "贺知章"),
            p("hezhizhang-yong-liu", "咏柳", "贺知章"),
            p("liuzongyuan-jiang-xue", "江雪", "柳宗元"),
            p("zhangji-feng-qiao-ye-bo", "枫桥夜泊", "张继"),
            p("hanyu-chun-xue", "早春呈水部张十八员外", "韩愈"),
            p("zhangzhihe-yu-ge-zi", "渔歌子", "张志和"),
            p("wangjian-shi-wu-ye", "十五夜望月", "王建"),
            p("zujing-zhong-nan", "终南望余雪", "祖咏"),
            p("jiadao-xun-yin-zhe", "寻隐者不遇", "贾岛"));

    // ── 宋 ─────────────────────────────────────────────────────────────

    public static final ArticleCollection SU_SHI = bundle("sushi",
            "苏轼", "词 and 诗 from the most quotable man of the 宋.",
            p("sushi-shui-diao-ge-tou", "水调歌头 · 明月几时有", "苏轼"),
            p("sushi-ti-xi-lin-bi", "题西林壁", "苏轼"),
            p("sushi-yin-hu-shang", "饮湖上初晴后雨", "苏轼"),
            p("sushi-hui-chong-chun-jiang", "惠崇春江晚景", "苏轼"),
            p("sushi-zeng-liu-jing-wen", "赠刘景文", "苏轼"),
            p("sushi-nian-nu-jiao", "念奴娇 · 赤壁怀古", "苏轼"),
            p("sushi-ding-feng-bo", "定风波 · 莫听穿林", "苏轼"),
            p("sushi-liu-yue-er-qi", "六月二十七日望湖楼醉书", "苏轼"));

    public static final ArticleCollection LI_QING_ZHAO = bundle("liqingzhao",
            "李清照", "The 宋词 a reader remembers for its sound before its sense.",
            p("liqingzhao-ru-meng-ling", "如梦令 · 常记溪亭日暮", "李清照"),
            p("liqingzhao-xia-ri-jue-ju", "夏日绝句", "李清照"),
            p("liqingzhao-ru-meng-ling-2", "如梦令 · 昨夜雨疏风骤", "李清照"),
            p("liqingzhao-yi-jian-mei", "一剪梅 · 红藕香残", "李清照"),
            p("liqingzhao-sheng-sheng-man", "声声慢 · 寻寻觅觅", "李清照"),
            p("liqingzhao-wu-ling-chun", "武陵春 · 风住尘香", "李清照"));

    public static final ArticleCollection XIN_QI_JI = bundle("xinqiji",
            "辛弃疾", "Loud, plain-spoken, and fond of villages.",
            p("xinqiji-qing-ping-le", "清平乐 · 村居", "辛弃疾"),
            p("xinqiji-xi-jiang-yue", "西江月 · 夜行黄沙道中", "辛弃疾"),
            p("xinqiji-chou-nu-er", "丑奴儿 · 少年不识愁滋味", "辛弃疾"),
            p("xinqiji-po-zhen-zi", "破阵子 · 醉里挑灯看剑", "辛弃疾"),
            p("xinqiji-qing-yu-an", "青玉案 · 元夕", "辛弃疾"),
            p("xinqiji-nan-xiang-zi", "南乡子 · 何处望神州", "辛弃疾"));

    public static final ArticleCollection LU_YOU = bundle("luyou",
            "陆游", "Rain on the roof, and one country on his mind.",
            p("luyou-shi-er", "示儿", "陆游"),
            p("luyou-you-shan-xi-cun", "游山西村", "陆游"),
            p("luyou-shi-yi-yue-si-ri", "十一月四日风雨大作", "陆游"),
            p("luyou-qiu-ye-jiang-xiao", "秋夜将晓出篱门迎凉有感", "陆游"),
            p("luyou-bu-suan-zi", "卜算子 · 咏梅", "陆游"),
            p("luyou-dong-ye-du-shu", "冬夜读书示子聿", "陆游"));

    public static final ArticleCollection YANG_WAN_LI = bundle("yangwanli",
            "杨万里", "Small things, looked at closely and cheerfully.",
            p("yangwanli-xiao-chi", "小池", "杨万里"),
            p("yangwanli-xiao-chu-jing-ci-si", "晓出净慈寺送林子方", "杨万里"),
            p("yangwanli-su-xin-shi-xu-gong-dian", "宿新市徐公店", "杨万里"),
            p("yangwanli-zhou-guo-an-ren", "舟过安仁", "杨万里"),
            p("yangwanli-gui-lai", "闲居初夏午睡起", "杨万里"),
            p("yangwanli-han-ye", "稚子弄冰", "杨万里"));

    public static final ArticleCollection WANG_AN_SHI = bundle("wanganshi",
            "王安石", "A politician's poems: short, exact, and about time passing.",
            p("wanganshi-mei-hua", "梅花", "王安石"),
            p("wanganshi-yuan-ri", "元日", "王安石"),
            p("wanganshi-bo-chuan-gua-zhou", "泊船瓜洲", "王安石"),
            p("wanganshi-shu-hu-yin-xian-sheng-bi", "书湖阴先生壁", "王安石"),
            p("wanganshi-deng-fei-lai-feng", "登飞来峰", "王安石"));

    public static final ArticleCollection LI_YU = bundle("liyu",
            "李煜", "A king who lost everything and wrote it down.",
            p("liyu-yu-mei-ren", "虞美人 · 春花秋月", "李煜"),
            p("liyu-lang-tao-sha", "浪淘沙令 · 帘外雨潺潺", "李煜"),
            p("liyu-xiang-jian-huan", "相见欢 · 无言独上西楼", "李煜"),
            p("liyu-qing-ping-le", "清平乐 · 别来春半", "李煜"),
            p("liyu-wang-jiang-nan", "望江南 · 多少恨", "李煜"));

    public static final ArticleCollection SONG_QI_TA = bundle("song-qita",
            "宋 · 其他", "One or two each from the poets beside the famous few.",
            p("zhuxi-chun-ri", "春日", "朱熹"),
            p("zhuxi-guan-shu-you-gan", "观书有感", "朱熹"),
            p("fanzhongyan-jiang-shang-yu-zhe", "江上渔者", "范仲淹"),
            p("fanzhongyan-su-mu-zhe", "苏幕遮 · 碧云天", "范仲淹"),
            p("ouyangxiu-sheng-cha-zi", "生查子 · 元夕", "欧阳修"),
            p("yanshu-huan-xi-sha", "浣溪沙 · 一曲新词", "晏殊"),
            p("yuefei-man-jiang-hong", "满江红 · 怒发冲冠", "岳飞"),
            p("wentianxiang-guo-ling-ding-yang", "过零丁洋", "文天祥"),
            p("linsheng-ti-lin-an-di", "题临安邸", "林升"),
            p("yekaishao-you-yuan-bu-zhi", "游园不值", "叶绍翁"),
            p("zenggong-cheng-nan", "城南", "曾巩"),
            p("qinguan-que-qiao-xian", "鹊桥仙 · 纤云弄巧", "秦观"));

    /** Every collection here, for a test that wants to walk them all. */
    public static List<ArticleCollection> all() {
        return List.of(LI_BAI_WUYAN, LI_BAI_QIYAN, DU_FU_WUYAN, DU_FU_QIYAN,
                WANG_WEI_SHAN_SHUI, WANG_WEI_SONG_BIE,
                BAI_JU_YI, MENG_HAO_RAN, DU_MU, LI_SHANG_YIN, LIU_YU_XI,
                WANG_CHANG_LING, BIAN_SAI, TANG_QI_TA,
                SU_SHI, LI_QING_ZHAO, XIN_QI_JI, LU_YOU, YANG_WAN_LI,
                WANG_AN_SHI, LI_YU, SONG_QI_TA);
    }
}
