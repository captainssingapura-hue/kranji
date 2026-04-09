package kranji.layout;

/**
 * A per-position layout override for a component.
 *
 * <p>Carried by {@link HintedComponent} to override the default
 * {@link ComponentMetrics} for a specific {@link BlockRole}.</p>
 *
 * @param widthWeight  override width weight ({@code NaN} = no override)
 * @param heightWeight override height weight ({@code NaN} = no override)
 * @param glyph        alternate glyph to render ({@code null} = use component's own glyph)
 */
public record LayoutHint(double widthWeight, double heightWeight, String glyph) {

    /** Override scaling only. */
    public static LayoutHint scale(double widthWeight, double heightWeight) {
        return new LayoutHint(widthWeight, heightWeight, null);
    }

    /** Override glyph only. */
    public static LayoutHint glyph(String glyph) {
        return new LayoutHint(Double.NaN, Double.NaN, glyph);
    }

    /** Override both glyph and scaling. */
    public static LayoutHint glyphAndScale(String glyph, double widthWeight, double heightWeight) {
        return new LayoutHint(widthWeight, heightWeight, glyph);
    }
}
