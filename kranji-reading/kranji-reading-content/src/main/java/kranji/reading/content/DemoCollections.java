package kranji.reading.content;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.CollectionId;

import java.util.List;

/**
 * The collections the app ships with.
 *
 * <p>Seven small sets rather than one large one, because a collection is the
 * unit of curation, review and licensing — and because mounting is atomic, so
 * a collection is also the smallest thing that can be arranged. Splitting by
 * form rather than by theme is what makes each set coherent enough to be
 * mounted whole.</p>
 *
 * <p>Each entry is one line: the local id, the title, the author, and the
 * resource holding the text. Metadata here, body there — so listing the whole
 * library opens no files.</p>
 *
 * <h2>Provenance</h2>
 *
 * <p>The Tang poems and the 文言 originals are ancient and free to use. The
 * fables and idiom stories are retellings written for this project from
 * 韩非子, 战国策 and 吕氏春秋. The rhymes, prose and expository pieces are
 * original. One exception is recorded on {@link #WEN_YAN}.</p>
 */
public final class DemoCollections {

    private DemoCollections() {}

    private record Bundle(CollectionId id, String title, String summary,
                          List<ArticleRef> articles) implements ArticleCollection {}

    private static ArticleCollection bundle(String id, String title, String summary,
                                            ArticleRef... articles) {
        return new Bundle(CollectionId.named("kranji.reader.demo." + id),
                title, summary, List.of(articles));
    }

    private static String res(String collection, String slug) {
        return "/articles/" + collection + "/" + slug + ".txt";
    }

    /** 唐诗启蒙 — the short poems a child meets first. */
    public static final ArticleCollection TANG_SHI = bundle("tangshi",
            "唐诗启蒙", "Short Tang poems, read aloud and learnt by heart.",
            ArticleRef.by("jing-ye-si", "静夜思", "李白", res("tangshi", "jing-ye-si")),
            ArticleRef.by("chun-xiao", "春晓", "孟浩然", res("tangshi", "chun-xiao")),
            ArticleRef.by("yong-e", "咏鹅", "骆宾王", res("tangshi", "yong-e")),
            ArticleRef.by("deng-guan-que-lou", "登鹳雀楼", "王之涣",
                    res("tangshi", "deng-guan-que-lou")),
            ArticleRef.by("min-nong", "悯农", "李绅", res("tangshi", "min-nong")));

    /** 儿歌 — short, rhymed, repetitive; the first things read alone. */
    public static final ArticleCollection ER_GE = bundle("erge",
            "儿歌", "Short rhymes, the first things read without help.",
            ArticleRef.of("xiao-yu-dian", "小雨点", res("erge", "xiao-yu-dian")),
            ArticleRef.of("xiao-xing-xing", "小星星", res("erge", "xiao-xing-xing")),
            ArticleRef.of("shu-ya-zi", "数鸭子", res("erge", "shu-ya-zi")));

    /** 寓言故事 — a short story that exists for its last sentence. */
    public static final ArticleCollection YU_YAN = bundle("yuyan",
            "寓言故事", "Fables retold in modern Chinese.",
            ArticleRef.of("shou-zhu-dai-tu", "守株待兔", res("yuyan", "shou-zhu-dai-tu")),
            ArticleRef.of("ba-miao-zhu-zhang", "拔苗助长", res("yuyan", "ba-miao-zhu-zhang")),
            ArticleRef.of("wang-yang-bu-lao", "亡羊补牢", res("yuyan", "wang-yang-bu-lao")));

    /** 成语故事 — the story a four-character idiom is short for. */
    public static final ArticleCollection CHENG_YU = bundle("chengyu",
            "成语故事", "The stories behind four-character idioms.",
            ArticleRef.of("hua-she-tian-zu", "画蛇添足", res("chengyu", "hua-she-tian-zu")),
            ArticleRef.of("zi-xiang-mao-dun", "自相矛盾", res("chengyu", "zi-xiang-mao-dun")),
            ArticleRef.of("ke-zhou-qiu-jian", "刻舟求剑", res("chengyu", "ke-zhou-qiu-jian")));

    /** 生活记叙 — prose that tells what happened, in the order it happened. */
    public static final ArticleCollection SHENG_HUO = bundle("shenghuo",
            "生活记叙", "Everyday scenes, told in order.",
            ArticleRef.of("chun-tian", "春天来了", res("shenghuo", "chun-tian")),
            ArticleRef.of("shang-xue-lu-shang", "上学路上", res("shenghuo", "shang-xue-lu-shang")),
            ArticleRef.of("wo-de-nai-nai", "我的奶奶", res("shenghuo", "wo-de-nai-nai")));

    /** 科普说明 — prose that explains a thing plainly. */
    public static final ArticleCollection KE_PU = bundle("kepu",
            "科普说明", "Short explanations of one thing at a time.",
            ArticleRef.of("xiong-mao", "大熊猫", res("kepu", "xiong-mao")),
            ArticleRef.of("shui", "水的三种样子", res("kepu", "shui")),
            ArticleRef.of("ma-yi", "蚂蚁", res("kepu", "ma-yi")));

    /**
     * 文言启蒙 — older grammar, denser characters, readings worth checking.
     *
     * <p>Note that 守株待兔 and 刻舟求剑 appear here as their classical originals
     * and elsewhere as modern retellings. The same local id in two collections
     * is legitimate — the collection is the uniqueness boundary — and reading
     * the two side by side is the point.</p>
     *
     * <p><b>施氏食狮史 is 赵元任's, and he died in 1982.</b> It is in copyright in
     * most jurisdictions until the 2050s. Fine as a local demo; it should not
     * ship in anything distributed, and this collection is the reason to check
     * before one is.</p>
     */
    public static final ArticleCollection WEN_YAN = bundle("wenyan",
            "文言启蒙", "Classical Chinese, short enough to read in one sitting.",
            ArticleRef.by("shou-zhu-dai-tu", "守株待兔", "韩非子",
                    res("wenyan", "shou-zhu-dai-tu")),
            ArticleRef.by("ke-zhou-qiu-jian", "刻舟求剑", "吕氏春秋",
                    res("wenyan", "ke-zhou-qiu-jian")),
            ArticleRef.by("shi-shi-shi-shi-shi", "施氏食狮史", "赵元任",
                    res("wenyan", "shi-shi-shi-shi-shi")));

    /** Every bundled collection, in no particular order — the tree arranges them. */
    public static List<ArticleCollection> all() {
        return List.of(TANG_SHI, ER_GE, YU_YAN, CHENG_YU, SHENG_HUO, KE_PU, WEN_YAN);
    }
}
