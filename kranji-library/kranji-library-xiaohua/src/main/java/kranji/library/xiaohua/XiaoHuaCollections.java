package kranji.library.xiaohua;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.CollectionId;

import java.util.List;

/**
 * 一般笑话 — short jokes, filed by where they happen.
 *
 * <p>Setting rather than kind, because a child picks a joke by recognising the
 * situation. "School" and "at home" are places a reader has been; "pun" and
 * "misunderstanding" are how a joke works, which is the last thing anybody
 * wants to be told before hearing one. 谐音 is the one exception and earns it:
 * a pun on sound is the one kind where knowing in advance is the point, and it
 * is the shelf a reader goes to when they want to practise hearing tones.</p>
 *
 * <h2>Why jokes are worth a module</h2>
 *
 * <p>They are the shortest prose a beginner will re-read voluntarily, and
 * re-reading is the whole mechanism. A joke also fails loudly when a character
 * is misread, which makes it an unusually honest test of whether the gloss set
 * is doing its job.</p>
 *
 * <p>Written for this project, and deliberately mild — the group most likely
 * to be replaced wholesale by somebody with better taste, which is another
 * reason it is its own module.</p>
 */
public final class XiaoHuaCollections {

    private XiaoHuaCollections() {}

    private record Bundle(CollectionId id, String title, String summary,
                          List<ArticleRef> articles) implements ArticleCollection {}

    private static ArticleCollection bundle(String id, String title, String summary,
                                            ArticleRef... articles) {
        return new Bundle(CollectionId.named("kranji.library.xiaohua." + id),
                title, summary, List.of(articles));
    }

    private static ArticleRef j(String slug, String title) {
        return ArticleRef.of(slug, title, "/kranji/articles/xiaohua/" + slug + ".txt");
    }

    public static final ArticleCollection XUE_XIAO = bundle("xuexiao",
            "学校笑话", "Classrooms, homework, and answers that are technically correct.",
            j("zuo-ye", "作业"), j("shu-xue-ke", "数学课"), j("kao-shi", "考试"),
            j("ju-shou", "举手"), j("zuo-wen", "作文"), j("chi-dao", "迟到"),
            j("di-li-ke", "地理课"), j("ti-yu-ke", "体育课"),
            j("zi-dian", "查字典"), j("jia-zhang-hui", "家长会"),
            j("tong-zhuo", "同桌"), j("bi-ji", "笔记"),
            j("xiang-pi", "橡皮"), j("shui-xie-de", "谁写的"),
            j("chang-ge", "唱歌"));

    public static final ArticleCollection JIA_LI = bundle("jiali",
            "家里的笑话", "Parents, dinner, and being sent to bed.",
            j("chi-fan", "吃饭"), j("shui-jiao", "睡觉"), j("bang-mang", "帮忙"),
            j("sao-di", "扫地"), j("xi-wan", "洗碗"), j("kan-dian-shi", "看电视"),
            j("mai-dong-xi", "买东西"), j("sheng-ri", "生日"),
            j("chuang-huo", "闯祸"), j("zhao-dong-xi", "找东西"),
            j("ye-ye-de-hua", "爷爷的话"), j("di-di", "弟弟"),
            j("qi-chuang", "起床"), j("xi-zao", "洗澡"),
            j("ling-hua-qian", "零花钱"));

    public static final ArticleCollection DONG_WU = bundle("dongwu",
            "动物笑话", "Animals who talk, and are no wiser for it.",
            j("xiao-gou", "小狗"), j("mao-he-yu", "猫和鱼"), j("da-xiang", "大象"),
            j("wu-gui", "乌龟"), j("xiao-niao", "小鸟"), j("qi-e", "企鹅"),
            j("jin-yu", "金鱼"), j("mian-yang", "绵羊"),
            j("ying-wu", "鹦鹉"), j("zhang-yu", "章鱼"),
            j("wo-niu", "蜗牛"), j("pang-xie", "螃蟹"), j("bian-fu", "蝙蝠"));

    public static final ArticleCollection XIE_YIN = bundle("xieyin",
            "谐音笑话", "Jokes that only work if you hear the tone.",
            j("mai-mai", "买卖"), j("shi-shi", "十四和四十"), j("shui-jiao-2", "水饺和睡觉"),
            j("ma-ma", "妈妈骂马"), j("bei-zi", "杯子和被子"),
            j("li-zi", "梨子和栗子"), j("yan-jing-2", "眼镜和眼睛"),
            j("bao-zi", "包子和抱"), j("lao-shi-he-shi-zi", "老师和狮子"),
            j("tang-he-tang", "汤和糖"));

    public static final ArticleCollection LENG = bundle("leng",
            "冷笑话", "The ones that work by not being funny.",
            j("shu-ya-zi-leng", "数鸭子"), j("men", "门"), j("xiang-jiao", "香蕉"),
            j("shi-tou", "石头"), j("dian-ti", "电梯"), j("wen-lu", "问路"),
            j("shu-mian", "书面"), j("deng", "等"),
            j("ying-zi", "影子"), j("jing-zi", "镜子"), j("shi-jian", "时间"));

    public static final ArticleCollection ZHI_YE = bundle("zhiye",
            "职业笑话", "Doctors, waiters and barbers, being unhelpful.",
            j("yi-sheng", "医生"), j("fu-wu-yuan", "服务员"), j("li-fa", "理发"),
            j("si-ji", "司机"), j("hua-jia", "画家"), j("jing-cha", "警察"),
            j("mu-jiang", "木匠"), j("lao-ban", "老板"),
            j("chu-shi", "厨师"), j("you-di-yuan", "邮递员"),
            j("xiu-biao", "修表的"));

    public static List<ArticleCollection> all() {
        return List.of(XUE_XIAO, JIA_LI, DONG_WU, XIE_YIN, LENG, ZHI_YE);
    }
}
