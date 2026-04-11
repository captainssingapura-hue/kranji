package kranji.component;

import kranji.component.Component.Part;

/**
 * Catalog of common Chinese character parts (偏旁).
 *
 * <p>These are the variant forms of radicals that appear only inside compound
 * characters, never as standalone glyphs. Each constant captures the part's
 * visual form, conventional name, the standalone character it derives from,
 * its semantic meaning, and the pronunciation of the standalone form.</p>
 *
 * @deprecated Use {@link kranji.component.basic.BasicComponents} instead.
 *             Each part is now a stateless record implementing
 *             {@link BasicComponent}, grouped by radical family.
 */
@Deprecated
public final class Parts {

    private Parts() {}

    // ── Left-side parts (左偏旁) ────────────────────────────────────────

    /** 亻 — 单人旁 (person). Derives from 人 rén. */
    public static final Part DAN_REN_PANG = new Part("亻", "单人旁", "人", "person", "rén");

    /** 氵 — 三点水 (water). Derives from 水 shuǐ. */
    public static final Part SAN_DIAN_SHUI = new Part("氵", "三点水", "水", "water", "shuǐ");

    /** 冫 — 两点水 (ice). Derives from 冰 bīng. */
    public static final Part LIANG_DIAN_SHUI = new Part("冫", "两点水", "冰", "ice", "bīng");

    /** 忄 — 竖心旁 (heart). Derives from 心 xīn. */
    public static final Part SHU_XIN_PANG = new Part("忄", "竖心旁", "心", "heart", "xīn");

    /** 扌 — 提手旁 (hand). Derives from 手 shǒu. */
    public static final Part TI_SHOU_PANG = new Part("扌", "提手旁", "手", "hand", "shǒu");

    /** 犭 — 反犬旁 (dog/animal). Derives from 犬 quǎn. */
    public static final Part FAN_QUAN_PANG = new Part("犭", "反犬旁", "犬", "dog", "quǎn");

    /** 讠 — 言字旁 (speech). Derives from 言 yán. */
    public static final Part YAN_ZI_PANG = new Part("讠", "言字旁", "言", "speech", "yán");

    /** 钅 — 金字旁 (metal/gold). Derives from 金 jīn. */
    public static final Part JIN_ZI_PANG = new Part("钅", "金字旁", "金", "metal", "jīn");

    /** 饣 — 食字旁 (food). Derives from 食 shí. */
    public static final Part SHI_ZI_PANG = new Part("饣", "食字旁", "食", "food", "shí");

    /** 纟 — 绞丝旁 (silk/thread). Derives from 丝 sī. */
    public static final Part JIAO_SI_PANG = new Part("纟", "绞丝旁", "丝", "silk", "sī");

    /** 礻 — 示字旁 (spirit/altar). Derives from 示 shì. */
    public static final Part SHI_ZI_PANG_SPIRIT = new Part("礻", "示字旁", "示", "spirit", "shì");

    /** 衤 — 衣字旁 (clothing). Derives from 衣 yī. */
    public static final Part YI_ZI_PANG = new Part("衤", "衣字旁", "衣", "clothing", "yī");

    /** 彳 — 双人旁 (step). Derives from 彳 chì. */
    public static final Part SHUANG_REN_PANG = new Part("彳", "双人旁", "彳", "step", "chì");

    /** 阝(left) — 左耳旁 (mound/hill). Derives from 阜 fù. */
    public static final Part ZUO_ER_PANG = new Part("阝", "左耳旁", "阜", "mound", "fù");

    // ── Right-side parts (右偏旁) ───────────────────────────────────────

    /** 刂 — 立刀旁 (knife). Derives from 刀 dāo. */
    public static final Part LI_DAO_PANG = new Part("刂", "立刀旁", "刀", "knife", "dāo");

    /** 阝(right) — 右耳旁 (city). Derives from 邑 yì. */
    public static final Part YOU_ER_PANG = new Part("阝", "右耳旁", "邑", "city", "yì");

    /** 攵 — 反文旁 (strike/tap). Derives from 攴 pū. */
    public static final Part FAN_WEN_PANG = new Part("攵", "反文旁", "攴", "strike", "pū");

    // ── Top parts (字头) ────────────────────────────────────────────────

    /** 艹 — 草字头 (grass/plant). Derives from 艸 cǎo. */
    public static final Part CAO_ZI_TOU = new Part("艹", "草字头", "艸", "grass", "cǎo");

    /** 宀 — 宝盖头 (roof/shelter). */
    public static final Part BAO_GAI_TOU = new Part("宀", "宝盖头", "宀", "roof", "mián");

    /** 冖 — 秃宝盖 (cover). */
    public static final Part TU_BAO_GAI = new Part("冖", "秃宝盖", "冖", "cover", "mì");

    /** 亠 — 文字头 (lid). */
    public static final Part WEN_ZI_TOU = new Part("亠", "文字头", "亠", "lid", "tóu");

    /** ⺮ — 竹字头 (bamboo). Derives from 竹 zhú. */
    public static final Part ZHU_ZI_TOU = new Part("⺮", "竹字头", "竹", "bamboo", "zhú");

    // ── Bottom parts (字底) ─────────────────────────────────────────────

    /** 灬 — 四点底 (fire). Derives from 火 huǒ. */
    public static final Part SI_DIAN_DI = new Part("灬", "四点底", "火", "fire", "huǒ");

    /** 辶 — 走之底 (walk/advance). Derives from 辵 chuò. */
    public static final Part ZOU_ZHI_DI = new Part("辶", "走之底", "辵", "walk", "chuò");

    /** 廴 — 建之旁 (long stride). */
    public static final Part JIAN_ZHI_PANG = new Part("廴", "建之旁", "廴", "long stride", "yǐn");

    // ── Enclosure parts (包围部件) ──────────────────────────────────────

    /** 囗 — 国字框 (enclosure). */
    public static final Part GUO_ZI_KUANG = new Part("囗", "国字框", "囗", "enclosure", "wéi");

    /** 疒 — 病字旁 (sickness). */
    public static final Part BING_ZI_PANG = new Part("疒", "病字旁", "疒", "sickness", "nè");

    /** 勹 — 包字头 (wrap). */
    public static final Part BAO_ZI_TOU = new Part("勹", "包字头", "勹", "wrap", "bāo");

    /** 匚 — 三框 (box/container). */
    public static final Part SAN_KUANG = new Part("匚", "三框", "匚", "box", "fāng");

    /** 凵 — 凶字框 (receptacle). */
    public static final Part XIONG_ZI_KUANG = new Part("凵", "凶字框", "凵", "receptacle", "kǎn");

    /** 冂 — 同字框 (border/boundary). */
    public static final Part TONG_ZI_KUANG = new Part("冂", "同字框", "冂", "border", "jiōng");
}
