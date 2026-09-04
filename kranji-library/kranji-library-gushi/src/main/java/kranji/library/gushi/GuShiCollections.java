package kranji.library.gushi;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.CollectionId;

import java.util.List;

/**
 * 寓言故事, 成语故事 and 神话 — the three kinds of story told for their point.
 *
 * <h2>Why they share a module and not a shelf</h2>
 *
 * <p>All three are short, all are told to make something stick, and a child
 * meets them in the same year. They divide differently, though, and that is
 * why the tree has three branches. A 寓言 is filed by <b>where it comes
 * from</b> — 韩非子 and 庄子 and 伊索 are different traditions with different
 * manners — and a 成语故事 by <b>what it is for</b>, because nobody reaches for
 * 掩耳盗铃 by remembering which book it is in. 神话 is filed by neither: it is
 * one shelf, because it is one body of stories about how things began.</p>
 *
 * <h2>Provenance</h2>
 *
 * <p>The sources are ancient and free. Every text here is a retelling written
 * for this project in modern Chinese, short enough for a first reading — not a
 * transcription of the classical original, which is a different collection and
 * a different reading age.</p>
 */
public final class GuShiCollections {

    private GuShiCollections() {}

    private record Bundle(CollectionId id, String title, String summary,
                          List<ArticleRef> articles) implements ArticleCollection {}

    private static ArticleCollection bundle(String id, String title, String summary,
                                            ArticleRef... articles) {
        return new Bundle(CollectionId.named("kranji.library.gushi." + id),
                title, summary, List.of(articles));
    }

    /** One story: slug doubles as the local id and the file name. */
    private static ArticleRef s(String slug, String title) {
        return ArticleRef.of(slug, title, "/kranji/articles/gushi/" + slug + ".txt");
    }

    // ── 寓言故事, by where it comes from ───────────────────────────────

    public static final ArticleCollection HAN_FEI = bundle("hanfei",
            "韩非子", "Fables from a book about how not to govern.",
            s("shou-zhu-dai-tu", "守株待兔"),
            s("zi-xiang-mao-dun", "自相矛盾"),
            s("lan-yu-chong-shu", "滥竽充数"),
            s("zheng-ren-mai-lu", "郑人买履"),
            s("mai-du-huan-zhu", "买椟还珠"),
            s("lao-ma-shi-tu", "老马识途"),
            s("hui-ji-ji-yi", "讳疾忌医"),
            s("zeng-zi-sha-zhu", "曾子杀猪"));

    public static final ArticleCollection ZHUANG_ZI = bundle("zhuangzi",
            "庄子与列子", "Fables that are funny first and unsettling afterwards.",
            s("jing-di-zhi-wa", "井底之蛙"),
            s("han-dan-xue-bu", "邯郸学步"),
            s("tang-bi-dang-che", "螳臂当车"),
            s("zhao-san-mu-si", "朝三暮四"),
            s("wang-yang-xing-tan", "望洋兴叹"),
            s("dong-shi-xiao-pin", "东施效颦"),
            s("qi-ren-you-tian", "杞人忧天"),
            s("yi-lin-dao-fu", "疑邻盗斧"),
            s("liang-xiao-er-bian-ri", "两小儿辩日"),
            s("ji-chang-xue-she", "纪昌学射"));

    public static final ArticleCollection ZHAN_GUO = bundle("zhanguo",
            "战国策", "Arguments won by telling a story instead.",
            s("hua-she-tian-zu", "画蛇添足"),
            s("hu-jia-hu-wei", "狐假虎威"),
            s("nan-yuan-bei-zhe", "南辕北辙"),
            s("wang-yang-bu-lao", "亡羊补牢"),
            s("yu-bang-xiang-zheng", "鹬蚌相争"),
            s("jing-gong-zhi-niao", "惊弓之鸟"),
            s("san-ren-cheng-hu", "三人成虎"),
            s("hua-long-dian-jing", "画龙点睛"));

    public static final ArticleCollection YI_SUO = bundle("yisuo",
            "伊索寓言", "The Greek ones, which travelled and stayed.",
            s("gui-tu-sai-pao", "龟兔赛跑"),
            s("lang-lai-le", "狼来了"),
            s("bei-feng-he-tai-yang", "北风和太阳"),
            s("hu-li-he-pu-tao", "狐狸和葡萄"),
            s("nong-fu-he-she", "农夫和蛇"),
            s("wu-ya-he-shui", "乌鸦喝水"),
            s("shi-zi-he-lao-shu", "狮子和老鼠"),
            s("ma-yi-he-xi-shuai", "蚂蚁和蟋蟀"),
            s("jin-fu-tou", "金斧头"),
            s("xia-jin-dan-de-e", "下金蛋的鹅"));

    // ── 成语故事, by what it is for ────────────────────────────────────

    public static final ArticleCollection QIN_XUE = bundle("qinxue",
            "成语 · 勤学", "Idioms about working at something until it gives.",
            s("xuan-liang-ci-gu", "悬梁刺股"),
            s("zao-bi-tou-guang", "凿壁偷光"),
            s("wen-ji-qi-wu", "闻鸡起舞"),
            s("nang-ying-ying-xue", "囊萤映雪"),
            s("wei-bian-san-jue", "韦编三绝"),
            s("tie-chu-cheng-zhen", "铁杵成针"),
            s("cheng-men-li-xue", "程门立雪"),
            s("shou-bu-shi-juan", "手不释卷"));

    public static final ArticleCollection ZHI_HUI = bundle("zhihui",
            "成语 · 机智", "Idioms about somebody who thought of it first.",
            s("cao-chong-cheng-xiang", "曹冲称象"),
            s("si-ma-guang-za-gang", "司马光砸缸"),
            s("kong-rong-rang-li", "孔融让梨"),
            s("wang-mei-zhi-ke", "望梅止渴"),
            s("tian-ji-sai-ma", "田忌赛马"),
            s("wan-bi-gui-zhao", "完璧归赵"),
            s("kong-cheng-ji", "空城计"),
            s("cao-chuan-jie-jian", "草船借箭"));

    public static final ArticleCollection YU_CHUN = bundle("yuchun",
            "成语 · 糊涂", "Idioms about doing the wrong thing carefully.",
            s("yan-er-dao-ling", "掩耳盗铃"),
            s("ba-miao-zhu-zhang", "拔苗助长"),
            s("ke-zhou-qiu-jian", "刻舟求剑"),
            s("bei-gong-she-ying", "杯弓蛇影"),
            s("xue-zu-shi-lu", "削足适履"),
            s("hua-bing-chong-ji", "画饼充饥"),
            s("yuan-mu-qiu-yu", "缘木求鱼"),
            s("bao-xin-jiu-huo", "抱薪救火"),
            s("yin-ye-fei-shi", "因噎废食"),
            s("mang-ren-mo-xiang", "盲人摸象"));

    public static final ArticleCollection PIN_GE = bundle("pinge",
            "成语 · 志气", "Idioms about deciding something and not letting go.",
            s("wo-xin-chang-dan", "卧薪尝胆"),
            s("po-fu-chen-zhou", "破釜沉舟"),
            s("san-gu-mao-lu", "三顾茅庐"),
            s("li-mu-wei-xin", "立木为信"),
            s("yi-nuo-qian-jin", "一诺千金"),
            s("fu-shui-nan-shou", "覆水难收"),
            s("bei-shui-yi-zhan", "背水一战"),
            s("shi-bie-san-ri", "士别三日"));

    // ── 神话 ───────────────────────────────────────────────────────────

    public static final ArticleCollection SHEN_HUA = bundle("shenhua",
            "神话传说", "How the sky, the rivers and the sun got the way they are.",
            s("pan-gu-kai-tian", "盘古开天"),
            s("nv-wa-bu-tian", "女娲补天"),
            s("hou-yi-she-ri", "后羿射日"),
            s("chang-e-ben-yue", "嫦娥奔月"),
            s("jing-wei-tian-hai", "精卫填海"),
            s("kua-fu-zhui-ri", "夸父追日"),
            s("yu-gong-yi-shan", "愚公移山"),
            s("da-yu-zhi-shui", "大禹治水"),
            s("niu-lang-zhi-nv", "牛郎织女"),
            s("shen-nong-chang-cao", "神农尝百草"));

    // ── 民间故事 ─────────────────────────────────────────────────────

    public static final ArticleCollection MIN_JIAN = bundle("minjian",
            "民间故事", "Told at home for centuries before anybody wrote them down.",
            s("meng-jiang-nv", "孟姜女"),
            s("bai-she-zhuan", "白蛇传"),
            s("liang-zhu", "梁山伯与祝英台"),
            s("tian-xian-pei", "天仙配"),
            s("a-fan-ti-mai-lv", "阿凡提卖驴"),
            s("ma-liang", "神笔马良"),
            s("bao-lian-deng", "宝莲灯"),
            s("lu-ban-xue-yi", "鲁班学艺"),
            s("tian-shu", "田螺姑娘"),
            s("wu-tou-de-gu-shi", "老鼠嫁女"),
            s("shi-er-sheng-xiao-gu-shi", "十二生肖的来历"),
            s("nian-de-gu-shi", "年的故事"));

    public static final ArticleCollection WAI_GUO = bundle("waiguo",
            "外国童话", "Stories from elsewhere that travelled the same way.",
            s("hui-gu-niang", "灰姑娘"),
            s("xiao-hong-mao", "小红帽"),
            s("chou-xiao-ya", "丑小鸭"),
            s("mai-huo-chai-de-xiao-nv-hai", "卖火柴的小女孩"),
            s("guo-wang-de-xin-yi", "皇帝的新装"),
            s("san-zhi-xiao-zhu", "三只小猪"),
            s("jie-mu-niao", "金鹅"),
            s("bai-xue-gong-zhu", "白雪公主"),
            s("mu-ma", "木偶奇遇记"),
            s("jie-ke-yu-dou-jing", "杰克与豆茎"),
            s("shui-jing-xie", "水晶鞋的另一半"),
            s("da-mu-zhi", "大拇指"),
            s("lv-ye-xian-zong", "绿野仙踪"));

    public static List<ArticleCollection> all() {
        return List.of(HAN_FEI, ZHUANG_ZI, ZHAN_GUO, YI_SUO,
                QIN_XUE, ZHI_HUI, YU_CHUN, PIN_GE, SHEN_HUA, MIN_JIAN, WAI_GUO);
    }
}
