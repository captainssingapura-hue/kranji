package kranji.stroke;

import java.util.List;

/**
 * A single stroke in a glyph definition: one or more Bézier segments
 * forming the stroke spine, plus a width profile and base width.
 *
 * <p>Compound strokes (e.g. 横折 HENG_ZHE) use multiple segments joined
 * end-to-end. Simple strokes use a single segment.</p>
 *
 * <p>All coordinates are in the glyph's normalized [0,1]² space.
 * The {@link kranji.stroke.AffineTransform2D} from the layout engine
 * maps these into final SVG coordinates.</p>
 *
 * @param type       the calligraphic stroke type
 * @param segments   ordered Bézier segments forming the spine
 * @param baseWidth  stroke width in normalized [0,1] space (typical: 0.04–0.08)
 * @param profile    width modulation profile (null → use type's default)
 */
public record StrokeInstance(
        StrokeType type,
        List<CubicBezier> segments,
        double baseWidth,
        WidthProfile profile
) {
    /** Use the stroke type's default width profile. */
    public StrokeInstance(StrokeType type, List<CubicBezier> segments, double baseWidth) {
        this(type, segments, baseWidth, null);
    }

    /** Single-segment convenience constructor. */
    public StrokeInstance(StrokeType type, CubicBezier spine, double baseWidth) {
        this(type, List.of(spine), baseWidth, null);
    }

    /** Resolve the effective width profile. */
    public WidthProfile effectiveProfile() {
        return profile != null ? profile : type.defaultProfile();
    }

    /** Apply an affine transform to all segments. */
    public StrokeInstance transform(AffineTransform2D m) {
        List<CubicBezier> transformed = segments.stream()
                .map(seg -> seg.transform(m))
                .toList();
        return new StrokeInstance(type, transformed, baseWidth * m.widthScale(), profile);
    }
}
