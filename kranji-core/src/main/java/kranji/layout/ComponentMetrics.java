package kranji.layout;

import java.util.HashMap;
import java.util.Map;

/**
 * Describes a component's preferred proportional weight for layout.
 *
 * <p>When a composition type (e.g. LeftRight) divides space among its
 * children, each child's weight determines what fraction of the
 * available width or height it receives.</p>
 *
 * <p>Example: in {@code LeftRight(氵, 青)}, 氵 has widthWeight=0.35
 * and 青 has widthWeight=1.0, so 氵 gets 0.35/(0.35+1.0) ≈ 26%
 * of the horizontal space.</p>
 *
 * <p>Weights are relative, not absolute — only the ratio between
 * siblings matters.</p>
 *
 * @param widthWeight   relative width in horizontal compositions (default 1.0)
 * @param heightWeight  relative height in vertical compositions (default 1.0)
 */
public record ComponentMetrics(double widthWeight, double heightWeight) {

    // ── Preset profiles ────────────────────────────────────────

    /** Default 1:1 proportions (e.g. 日, 月, 口, 木, 青). */
    public static final ComponentMetrics SQUARE = new ComponentMetrics(1.0, 1.0);

    /** Narrow left/right radicals (e.g. 亻, 氵, 忄, 讠, 刂). */
    public static final ComponentMetrics NARROW = new ComponentMetrics(0.45, 1.0);

    /** Wide-but-short top radicals (e.g. 艹, 宀, 冖, ⺮). */
    public static final ComponentMetrics SHORT_WIDE = new ComponentMetrics(1.0, 0.40);

    /** Short bottom radicals (e.g. 灬, 心 when used as bottom). */
    public static final ComponentMetrics SHORT_BOTTOM = new ComponentMetrics(1.0, 0.45);

    /** Wide bottom wrapper (e.g. 辶, 廴). */
    public static final ComponentMetrics WIDE_WRAP = new ComponentMetrics(1.0, 0.35);

    // ── Registries ──────────────────────────────────────────────

    private static final Map<String, ComponentMetrics> REGISTRY = new HashMap<>();

    /**
     * Role-aware registry: glyph → (role → metrics).
     * Checked first by {@link #forGlyph(String, BlockRole)}.
     */
    private static final Map<String, Map<BlockRole, ComponentMetrics>> ROLE_REGISTRY = new HashMap<>();

    static {
        // ── Narrow left-side radicals (左偏旁) ──
        register("亻", NARROW);
        register("氵", NARROW);
        register("冫", NARROW);
        register("忄", NARROW);
        register("扌", NARROW);
        register("犭", NARROW);
        register("讠", NARROW);
        register("钅", new ComponentMetrics(0.40, 1.0));
        register("饣", new ComponentMetrics(0.40, 1.0));
        register("纟", NARROW);
        register("礻", NARROW);
        register("衤", NARROW);
        register("彳", NARROW);
        register("阝", NARROW);  // 左耳旁 or 右耳旁

        // ── Narrow right-side radicals (右偏旁) ──
        register("刂", NARROW);
        register("攵", new ComponentMetrics(0.45, 1.0));

        // ── Short top radicals (字头) ──
        register("艹", SHORT_WIDE);
        register("宀", SHORT_WIDE);
        register("冖", SHORT_WIDE);
        register("亠", new ComponentMetrics(1.0, 0.30));
        register("⺮", SHORT_WIDE);
        register("穴", new ComponentMetrics(1.0, 0.50));

        // ── Short bottom radicals (字底) ──
        register("灬", SHORT_BOTTOM);
        register("心", SHORT_BOTTOM);  // when used as bottom component

        // ── Bottom wrappers ──
        register("辶", WIDE_WRAP);
        register("廴", WIDE_WRAP);

        // ── Enclosure radicals (proportions less relevant; handled by enclosure layout) ──
        register("囗", SQUARE);
        register("疒", new ComponentMetrics(0.45, 0.50));
        register("勹", new ComponentMetrics(0.50, 0.50));
        register("匚", new ComponentMetrics(0.30, 1.0));
        register("凵", new ComponentMetrics(1.0, 0.35));
        register("冂", SQUARE);

        // ── Common standalone Zi used as components ──
        register("幺", new ComponentMetrics(0.45, 1.0));

        // ── Role-specific overrides for standalone Zi (字) ──
        // These apply when a full character is used as a component
        // in a specific structural position.

        // Tall-narrow singulars (日, 口, 目, 田): significantly narrower as LEFT,
        // slightly scaled down (not too fat) as BOTTOM.
        register("日", BlockRole.LEFT, new ComponentMetrics(0.45, 1.0));
        register("日", BlockRole.BOTTOM, new ComponentMetrics(0.85, 0.80));
        register("口", BlockRole.LEFT, new ComponentMetrics(0.40, 1.0));
        register("口", BlockRole.BOTTOM, new ComponentMetrics(0.80, 0.80));
        register("目", BlockRole.LEFT, new ComponentMetrics(0.45, 1.0));
        register("目", BlockRole.BOTTOM, new ComponentMetrics(0.85, 0.80));
        register("田", BlockRole.LEFT, new ComponentMetrics(0.45, 1.0));
        register("田", BlockRole.BOTTOM, new ComponentMetrics(0.85, 0.80));

        // Other common Zi as left components
        register("月", BlockRole.LEFT, new ComponentMetrics(0.55, 1.0));
    }

    /**
     * Look up role-specific metrics for a component glyph.
     *
     * <p>Resolution order: role-specific entry → glyph-level entry → {@link #SQUARE}.</p>
     */
    public static ComponentMetrics forGlyph(String glyph, BlockRole role) {
        var roleMap = ROLE_REGISTRY.get(glyph);
        if (roleMap != null) {
            var m = roleMap.get(role);
            if (m != null) return m;
        }
        return forGlyph(glyph);
    }

    /**
     * Look up the metrics for a component glyph (role-agnostic).
     * Returns {@link #SQUARE} if no specific metrics are registered.
     */
    public static ComponentMetrics forGlyph(String glyph) {
        return REGISTRY.getOrDefault(glyph, SQUARE);
    }

    private static void register(String glyph, ComponentMetrics m) {
        REGISTRY.put(glyph, m);
    }

    private static void register(String glyph, BlockRole role, ComponentMetrics m) {
        ROLE_REGISTRY.computeIfAbsent(glyph, k -> new HashMap<>()).put(role, m);
    }
}
