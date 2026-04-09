package kranji.stroke;

/**
 * Fundamental Chinese calligraphy stroke types.
 *
 * <p>Each type carries a default {@link WidthProfile} that approximates
 * the traditional brush behavior for that stroke shape.</p>
 */
public enum StrokeType {

    // ── Basic strokes ──────────────────────────────────────────────
    /** 横 héng — horizontal stroke */
    HENG(WidthProfile.UNIFORM),

    /** 竖 shù — vertical stroke */
    SHU(WidthProfile.UNIFORM),

    /** 撇 piě — left-falling stroke */
    PIE(WidthProfile.TAPER_END),

    /** 捺 nà — right-falling stroke */
    NA(WidthProfile.SWELL_END),

    /** 点 diǎn — dot */
    DIAN(WidthProfile.DOT),

    /** 提 tí — rising stroke */
    TI(WidthProfile.RISING),

    // ── Compound strokes ───────────────────────────────────────────
    /** 横折 héng zhé — horizontal then turn down */
    HENG_ZHE(WidthProfile.UNIFORM),

    /** 竖钩 shù gōu — vertical with hook */
    SHU_GOU(WidthProfile.UNIFORM),

    /** 横折钩 héng zhé gōu — horizontal, turn down, hook */
    HENG_ZHE_GOU(WidthProfile.UNIFORM),

    /** 竖折 shù zhé — vertical then turn right */
    SHU_ZHE(WidthProfile.UNIFORM),

    /** 撇折 piě zhé — left-falling then turn right */
    PIE_ZHE(WidthProfile.TAPER_END),

    /** 弯钩 wān gōu — curved hook */
    WAN_GOU(WidthProfile.UNIFORM),

    /** 斜钩 xié gōu — slanting hook */
    XIE_GOU(WidthProfile.UNIFORM);

    private final WidthProfile defaultProfile;

    StrokeType(WidthProfile defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public WidthProfile defaultProfile() {
        return defaultProfile;
    }
}
