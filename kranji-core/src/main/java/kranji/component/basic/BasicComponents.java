package kranji.component.basic;

/**
 * Re-export hub for all {@link kranji.component.BasicComponent} singletons.
 *
 * <p>Constant names match the legacy {@link kranji.component.Parts} class
 * for drop-in replacement via a single import change:</p>
 * <pre>{@code
 * // Before: import static kranji.component.basic.BasicComponents.*;
 * // After:  import static kranji.component.basic.BasicComponents.*;
 * }</pre>
 */
public final class BasicComponents {

    private BasicComponents() {}

    // ── Person ──────────────────────────────────────────────────────────
    public static final PersonFamily.DanRenPang      DAN_REN_PANG      = PersonFamily.DAN_REN_PANG;
    public static final PersonFamily.ShuangRenPang   SHUANG_REN_PANG   = PersonFamily.SHUANG_REN_PANG;

    // ── Water ───────────────────────────────────────────────────────────
    public static final WaterFamily.SanDianShui      SAN_DIAN_SHUI     = WaterFamily.SAN_DIAN_SHUI;
    public static final WaterFamily.LiangDianShui    LIANG_DIAN_SHUI   = WaterFamily.LIANG_DIAN_SHUI;

    // ── Heart ───────────────────────────────────────────────────────────
    public static final HeartFamily.ShuXinPang       SHU_XIN_PANG      = HeartFamily.SHU_XIN_PANG;

    // ── Hand ────────────────────────────────────────────────────────────
    public static final HandFamily.TiShouPang        TI_SHOU_PANG      = HandFamily.TI_SHOU_PANG;

    // ── Animal ──────────────────────────────────────────────────────────
    public static final AnimalFamily.FanQuanPang     FAN_QUAN_PANG     = AnimalFamily.FAN_QUAN_PANG;

    // ── Speech ──────────────────────────────────────────────────────────
    public static final SpeechFamily.YanZiPang       YAN_ZI_PANG       = SpeechFamily.YAN_ZI_PANG;

    // ── Metal ───────────────────────────────────────────────────────────
    public static final MetalFamily.JinZiPang        JIN_ZI_PANG       = MetalFamily.JIN_ZI_PANG;

    // ── Food ────────────────────────────────────────────────────────────
    public static final FoodFamily.ShiZiPang         SHI_ZI_PANG       = FoodFamily.SHI_ZI_PANG;

    // ── Silk ────────────────────────────────────────────────────────────
    public static final SilkFamily.JiaoSiPang        JIAO_SI_PANG      = SilkFamily.JIAO_SI_PANG;

    // ── Clothing / Spirit ───────────────────────────────────────────────
    public static final ClothingFamily.ShiZiPangSpirit SHI_ZI_PANG_SPIRIT = ClothingFamily.SHI_ZI_PANG_SPIRIT;
    public static final ClothingFamily.YiZiPang      YI_ZI_PANG        = ClothingFamily.YI_ZI_PANG;

    // ── Knife ───────────────────────────────────────────────────────────
    public static final KnifeFamily.LiDaoPang        LI_DAO_PANG       = KnifeFamily.LI_DAO_PANG;

    // ── Strike ──────────────────────────────────────────────────────────
    public static final StrikeFamily.FanWenPang      FAN_WEN_PANG      = StrikeFamily.FAN_WEN_PANG;

    // ── Ear ─────────────────────────────────────────────────────────────
    public static final EarFamily.ZuoErPang          ZUO_ER_PANG       = EarFamily.ZUO_ER_PANG;
    public static final EarFamily.YouErPang          YOU_ER_PANG       = EarFamily.YOU_ER_PANG;

    // ── Plant ───────────────────────────────────────────────────────────
    public static final PlantFamily.CaoZiTou         CAO_ZI_TOU        = PlantFamily.CAO_ZI_TOU;
    public static final PlantFamily.ZhuZiTou         ZHU_ZI_TOU        = PlantFamily.ZHU_ZI_TOU;

    // ── Shelter ─────────────────────────────────────────────────────────
    public static final ShelterFamily.BaoGaiTou      BAO_GAI_TOU       = ShelterFamily.BAO_GAI_TOU;
    public static final ShelterFamily.TuBaoGai       TU_BAO_GAI        = ShelterFamily.TU_BAO_GAI;
    public static final ShelterFamily.WenZiTou       WEN_ZI_TOU        = ShelterFamily.WEN_ZI_TOU;

    // ── Fire ────────────────────────────────────────────────────────────
    public static final FireFamily.SiDianDi          SI_DIAN_DI        = FireFamily.SI_DIAN_DI;

    // ── Movement ────────────────────────────────────────────────────────
    public static final MovementFamily.ZouZhiDi      ZOU_ZHI_DI        = MovementFamily.ZOU_ZHI_DI;
    public static final MovementFamily.JianZhiPang   JIAN_ZHI_PANG     = MovementFamily.JIAN_ZHI_PANG;

    // ── Wood ────────────────────────────────────────────────────────────
    public static final WoodFamily.Mu                MU                = WoodFamily.MU;

    // ── Enclosure ───────────────────────────────────────────────────────
    public static final EnclosureFamily.GuoZiKuang   GUO_ZI_KUANG      = EnclosureFamily.GUO_ZI_KUANG;
    public static final EnclosureFamily.BingZiPang   BING_ZI_PANG      = EnclosureFamily.BING_ZI_PANG;
    public static final EnclosureFamily.BaoZiTou     BAO_ZI_TOU        = EnclosureFamily.BAO_ZI_TOU;
    public static final EnclosureFamily.SanKuang     SAN_KUANG         = EnclosureFamily.SAN_KUANG;
    public static final EnclosureFamily.XiongZiKuang XIONG_ZI_KUANG    = EnclosureFamily.XIONG_ZI_KUANG;
    public static final EnclosureFamily.TongZiKuang  TONG_ZI_KUANG     = EnclosureFamily.TONG_ZI_KUANG;
}
