package kranji.library.kepu;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.CollectionId;

import java.util.List;

/**
 * 科普读物 — short expository pieces, filed by subject.
 *
 * <p>Subject and not reading level, though level is what a teacher would pick.
 * A child who has just read about ants wants the bees, not the next hardest
 * thing; curiosity travels sideways. Level is a property of the piece and can
 * be shown on it, where subject has to be the shape of the tree or it is not
 * navigable at all.</p>
 *
 * <p>Ten subjects rather than six, and 动物 divides again — into 昆虫, 鸟 and
 * 兽 — because it is the shelf a child goes to first and the one that gets
 * long enough to need dividing. The others are one shelf each until they earn
 * more.</p>
 *
 * <p>Written for this project. Nothing here is translated or adapted from a
 * source, which is why this module carries no third-party licence — and why
 * the facts are the plain ones a reader can check.</p>
 */
public final class KePuCollections {

    private KePuCollections() {}

    private record Bundle(CollectionId id, String title, String summary,
                          List<ArticleRef> articles) implements ArticleCollection {}

    private static ArticleCollection bundle(String id, String title, String summary,
                                            ArticleRef... articles) {
        return new Bundle(CollectionId.named("kranji.library.kepu." + id),
                title, summary, List.of(articles));
    }

    private static ArticleRef a(String slug, String title) {
        return ArticleRef.of(slug, title, "/kranji/articles/kepu/" + slug + ".txt");
    }

    public static final ArticleCollection KUN_CHONG = bundle("kunchong",
            "昆虫", "Six legs, and more of them than everything else put together.",
            a("ma-yi", "蚂蚁"), a("mi-feng", "蜜蜂"), a("hu-die", "蝴蝶"),
            a("qing-ting", "蜻蜓"), a("zhi-liao", "知了"), a("ying-huo-chong", "萤火虫"),
            a("piao-chong", "瓢虫"), a("can", "蚕"));

    public static final ArticleCollection NIAO = bundle("niao",
            "鸟", "Feathers, and what they are for besides flying.",
            a("yan-zi", "燕子"), a("qi-e", "企鹅"), a("mao-tou-ying", "猫头鹰"),
            a("zhuo-mu-niao", "啄木鸟"), a("da-yan", "大雁"), a("kong-que", "孔雀"));

    public static final ArticleCollection SHOU = bundle("shou",
            "兽", "Animals with fur, milk and warm blood.",
            a("xiong-mao", "熊猫"), a("da-xiang", "大象"), a("jing", "鲸"),
            a("bian-fu", "蝙蝠"), a("luo-tuo", "骆驼"), a("song-shu", "松鼠"),
            a("lao-hu", "老虎"), a("hai-tun", "海豚"));

    public static final ArticleCollection ZHI_WU = bundle("zhiwu",
            "植物", "Plants that do something a reader can go outside and see.",
            a("xiang-ri-kui", "向日葵"), a("zhu-zi", "竹子"), a("pu-gong-ying", "蒲公英"),
            a("he-hua", "荷花"), a("xian-ren-zhang", "仙人掌"), a("shu-nian-lun", "树的年轮"),
            a("zhong-zi", "种子"), a("ye-zi", "叶子"));

    public static final ArticleCollection TIAN_WEN = bundle("tianwen",
            "天文", "The sky, which is the first thing anybody wonders about.",
            a("yue-liang", "月亮"), a("tai-yang", "太阳"), a("xing-xing", "星星"),
            a("di-qiu", "地球"), a("liu-xing", "流星"), a("cai-hong", "彩虹"),
            a("ri-shi", "日食"), a("si-ji-cheng-yin", "四季是怎么来的"));

    public static final ArticleCollection DI_LI = bundle("dili",
            "地理", "How the ground got the shape it has.",
            a("huo-shan", "火山"), a("he-liu", "河流"), a("sha-mo", "沙漠"),
            a("di-zhen", "地震"), a("hai-yang", "海洋"), a("shan", "山是怎么来的"),
            a("bing-chuan", "冰川"));

    public static final ArticleCollection REN_TI = bundle("renti",
            "人体", "The reader's own body, which is nearer than the stars.",
            a("xin-zang", "心脏"), a("yan-jing", "眼睛"), a("gu-tou", "骨头"),
            a("da-nao", "大脑"), a("hu-xi", "呼吸"), a("ya-chi", "牙齿"),
            a("pi-fu", "皮肤"), a("shui-mian", "睡觉"));

    public static final ArticleCollection WU_ZHI = bundle("wuzhi",
            "物质世界", "Water, air and magnets: experiments you can do at a sink.",
            a("shui", "水"), a("kong-qi", "空气"), a("ci-tie", "磁铁"),
            a("guang", "光"), a("sheng-yin", "声音"), a("re", "热"),
            a("fu-li", "浮力"), a("yan", "盐"));

    public static final ArticleCollection TIAN_QI = bundle("tianqi",
            "天气", "Why the sky does what it does.",
            a("yu", "雨"), a("xue", "雪"), a("feng", "风"),
            a("yun", "云"), a("lei-dian", "雷电"), a("wu", "雾"));

    public static final ArticleCollection JI_SHU = bundle("jishu",
            "身边的科技", "Ordinary machines, explained once.",
            a("dian", "电"), a("lun-zi", "轮子"), a("qiao-gan", "杠杆"),
            a("zhi-nan-zhen", "指南针"), a("fei-ji", "飞机为什么能飞"),
            a("bing-xiang", "冰箱"));

    public static final ArticleCollection SHI_WU = bundle("shiwu",
            "食物与农业", "Where dinner comes from, and what happens to it on the way.",
            a("mi-fan", "米"), a("mian-bao", "面包"), a("niu-nai", "牛奶"),
            a("ji-dan", "鸡蛋"), a("tang", "糖"), a("cha", "茶"),
            a("dou-fu", "豆腐"), a("shui-guo", "水果为什么甜"),
            a("fa-jiao", "发酵"), a("bao-xian", "食物为什么会坏"));

    public static final ArticleCollection HUAN_JING = bundle("huanjing",
            "环境", "What the world is short of, and why it matters here.",
            a("la-ji", "垃圾"), a("hui-shou", "回收"), a("sen-lin", "森林"),
            a("shui-zi-yuan", "水从哪里来"), a("kong-qi-wu-ran", "空气脏了"),
            a("qi-hou", "天气在变"), a("wu-zhong", "消失的动物"));

    public static final ArticleCollection SHU_XING = bundle("shuxing",
            "数与形", "The mathematics that is visible before it is taught.",
            a("dui-cheng", "对称"), a("yuan", "圆"), a("liu-jiao-xing", "蜂窝为什么是六角形"),
            a("da-xiao", "大和小"), a("cheng-liang", "怎么量"));

    public static List<ArticleCollection> all() {
        return List.of(KUN_CHONG, NIAO, SHOU, ZHI_WU, TIAN_WEN, DI_LI,
                REN_TI, WU_ZHI, TIAN_QI, JI_SHU, SHI_WU, HUAN_JING, SHU_XING);
    }
}
