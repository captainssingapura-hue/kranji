package kranji.json.bridge;

import kranji.classification.EtymologicalCategory;
import kranji.json.dto.BlockRefJson;
import kranji.json.dto.ComposedBlockJson;
import kranji.json.dto.ComposedZiJson;
import kranji.json.dto.EtymologyJson;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;
import kranji.json.dto.ZiCharForm;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.ComposedPart;
import kranji.zi.ComposedZi;
import kranji.zi.CompositionLayout;
import kranji.zi.SingularBlock;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;
import kranji.zi.ZiChar;

import java.util.Map;
import java.util.Objects;

/**
 * One-way converters from the untyped JSON DTO layer ({@code kranji-json})
 * into the typed runtime hierarchy ({@code kranji-core}).
 *
 * <p>All methods are pure and side-effect free. No mutable state.</p>
 *
 * <h2>Pinyin fidelity</h2>
 * The JSON wire format stores pinyin as flat strings ({@code initial},
 * {@code final}, {@code tone}) while the core hierarchy uses structured
 * {@link PinyinSyllable} with {@code Initial} / {@code Final} / {@code Tone}.
 * For {@link SingularZi}, the typed adapter derives structured pinyin
 * on demand from {@code pinyinText} via
 * {@link PinyinSyllable#parse(String)}. For {@link ComposedZi}, the
 * structured {@code PinyinJson} (if present) is reconstructed into a
 * {@link PinyinSyllable} via numbered-form concatenation + parse.
 */
public final class UntypedToTyped {

    private UntypedToTyped() {}

    // ── Leaf types ────────────────────────────────────────────────────

    /**
     * Convert a {@link SingularZiJson} into a typed {@link SingularZi}
     * ({@link JsonSingularZi} adapter).
     */
    public static SingularZi singularZi(SingularZiJson json) {
        Objects.requireNonNull(json, "json");
        return new JsonSingularZi(
                json.glyph(),
                json.pinyinText(),
                json.strokes() == null ? 0 : json.strokes(),
                json.radicalNo() == null ? 0 : json.radicalNo(),
                json.meaning(),
                etymology(json.etymology(), null)
        );
    }

    /**
     * Convert a {@link SingularPartJson} into a typed {@link SingularPart}
     * with {@code derivedFrom} left unresolved ({@code null}).
     */
    public static SingularPart singularPart(SingularPartJson json) {
        return singularPart(json, null);
    }

    /**
     * Convert a {@link SingularPartJson} into a typed {@link SingularPart},
     * binding the supplied {@code derivedFrom} (may be {@code null}).
     */
    public static SingularPart singularPart(SingularPartJson json, SingularZi derivedFrom) {
        Objects.requireNonNull(json, "json");
        return new JsonSingularPart(
                json.glyph(),
                json.name(),
                json.strokes() == null ? 0 : json.strokes(),
                json.pinyinText(),
                json.meaning(),
                derivedFrom
        );
    }

    // ── Composition tree ──────────────────────────────────────────────

    /**
     * Resolve a {@link BlockRefJson} to a typed {@link BlockStructure}.
     *
     * <ul>
     *   <li>{@code GlyphRef} → looked up via {@code resolver}; throws
     *       {@link IllegalStateException} if the glyph is unknown.</li>
     *   <li>{@code InlineSingular} → {@link SingularPart} adapter.</li>
     *   <li>{@code InlineComposed} → recursive {@link ComposedBlock}.</li>
     * </ul>
     */
    public static BlockStructure blockStructure(BlockRefJson ref, BlockResolver resolver) {
        Objects.requireNonNull(ref, "ref");
        Objects.requireNonNull(resolver, "resolver");
        return switch (ref) {
            case BlockRefJson.GlyphRef(String glyph) -> {
                SingularBlock resolved = resolver.resolve(glyph);
                if (resolved == null) {
                    throw new IllegalStateException(
                            "Unresolved glyph reference: \"" + glyph + "\"");
                }
                yield resolved;
            }
            case BlockRefJson.InlineSingular(SingularPartJson part) ->
                    singularPart(part);
            case BlockRefJson.InlineComposed(ComposedBlockJson block) ->
                    new ComposedPart(composedBlock(block, resolver));
        };
    }

    /** Convert a {@link ComposedBlockJson} into a typed {@link CompositionLayout}. */
    public static CompositionLayout composedBlock(ComposedBlockJson json, BlockResolver resolver) {
        Objects.requireNonNull(json, "json");
        Map<String, BlockRefJson> slots = json.slots();
        String style = json.style();
        return switch (style) {
            case "left_right" -> new CompositionLayout.LeftRight(
                    block(slots, "left",  resolver),
                    block(slots, "right", resolver));
            case "top_bottom" -> new CompositionLayout.TopBottom(
                    block(slots, "top",    resolver),
                    block(slots, "bottom", resolver));
            case "left_middle_right" -> new CompositionLayout.LeftMiddleRight(
                    block(slots, "left",   resolver),
                    block(slots, "middle", resolver),
                    block(slots, "right",  resolver));
            case "top_middle_bottom" -> new CompositionLayout.TopMiddleBottom(
                    block(slots, "top",    resolver),
                    block(slots, "middle", resolver),
                    block(slots, "bottom", resolver));
            case "full_enclosure" -> new CompositionLayout.FullEnclosure(
                    block(slots, "outer", resolver),
                    block(slots, "inner", resolver));
            case "semi_enclosure_upper_left" -> new CompositionLayout.SemiEnclosureUpperLeft(
                    block(slots, "wrapper", resolver),
                    block(slots, "content", resolver));
            case "semi_enclosure_upper_right" -> new CompositionLayout.SemiEnclosureUpperRight(
                    block(slots, "wrapper", resolver),
                    block(slots, "content", resolver));
            case "semi_enclosure_bottom_left" -> new CompositionLayout.SemiEnclosureBottomLeft(
                    block(slots, "wrapper", resolver),
                    block(slots, "content", resolver));
            case "semi_enclosure_top_three" -> new CompositionLayout.SemiEnclosureTopThree(
                    block(slots, "wrapper", resolver),
                    block(slots, "content", resolver));
            case "semi_enclosure_bottom_three" -> new CompositionLayout.SemiEnclosureBottomThree(
                    block(slots, "wrapper", resolver),
                    block(slots, "content", resolver));
            case "semi_enclosure_left_three" -> new CompositionLayout.SemiEnclosureLeftThree(
                    block(slots, "wrapper", resolver),
                    block(slots, "content", resolver));
            default -> throw new IllegalArgumentException(
                    "Unknown composition style: \"" + style + "\"");
        };
    }

    /** Convert a {@link ComposedZiJson} into a typed {@link ComposedZi}. */
    public static ComposedZi composedZi(ComposedZiJson json, BlockResolver resolver) {
        Objects.requireNonNull(json, "json");
        Objects.requireNonNull(resolver, "resolver");
        CompositionLayout composition = composedBlock(json.composition(), resolver);
        return new ComposedZi(
                ziChar(json.glyph(), json.ziCharForm()),
                pinyinFrom(json.pinyin()),
                json.strokes()   == null ? 0 : json.strokes(),
                json.radicalNo() == null ? 0 : json.radicalNo(),
                json.meaning() == null ? "" : json.meaning(),
                composition,
                etymology(json.etymology(), resolver)
        );
    }

    /**
     * Reconstruct a {@link PinyinSyllable} from the flat JSON form. Returns
     * {@code null} if no pinyin is supplied; otherwise concatenates the
     * {@code initial}, {@code final}, and {@code tone} into the numbered
     * display form and delegates to {@link PinyinSyllable#parse}.
     */
    public static PinyinSyllable pinyinFrom(kranji.json.dto.PinyinJson py) {
        if (py == null) return null;
        String initial = py.initial() == null ? "" : py.initial();
        String fin     = py.fin()     == null ? "" : py.fin();
        int    tone    = py.tone()    == null ? 5  : py.tone();
        return PinyinSyllable.parse(initial + fin + tone);
    }

    // ── Etymology ─────────────────────────────────────────────────────

    /**
     * Convert {@link EtymologyJson} to {@link EtymologicalCategory}.
     *
     * <p>{@code resolver} is consulted only for the {@code PhonoSemantic}
     * variant (to resolve its {@code BlockRefJson} sides). It may be
     * {@code null} if the caller guarantees no PhonoSemantic references
     * appear; such references then trigger an exception.</p>
     */
    public static EtymologicalCategory etymology(EtymologyJson json, BlockResolver resolver) {
        if (json == null) return new EtymologicalCategory.Pictograph();
        return switch (json) {
            case EtymologyJson.Pictograph p              -> new EtymologicalCategory.Pictograph();
            case EtymologyJson.SimpleIndicative s        -> new EtymologicalCategory.SimpleIndicative(s.indicatorDescription());
            case EtymologyJson.CompoundIndicative c      -> new EtymologicalCategory.CompoundIndicative(c.meaningHint());
            case EtymologyJson.DerivativeCognate d       -> new EtymologicalCategory.DerivativeCognate(d.cognateGlyph());
            case EtymologyJson.PhoneticLoan l            -> new EtymologicalCategory.PhoneticLoan(l.originalMeaning());
            case EtymologyJson.PhonoSemantic ps -> {
                if (resolver == null) {
                    throw new IllegalStateException(
                            "PhonoSemantic etymology requires a non-null BlockResolver");
                }
                yield new EtymologicalCategory.PhonoSemantic(
                        blockStructure(ps.semanticPart(), resolver),
                        blockStructure(ps.phoneticPart(), resolver));
            }
        };
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private static BlockStructure block(Map<String, BlockRefJson> slots,
                                        String key,
                                        BlockResolver resolver) {
        BlockRefJson ref = slots.get(key);
        if (ref == null) {
            throw new IllegalArgumentException("Missing slot: \"" + key + "\"");
        }
        return blockStructure(ref, resolver);
    }

    private static ZiChar ziChar(String glyph, ZiCharForm form) {
        return form == ZiCharForm.UNICODE
                ? new ZiChar.Unicode(glyph)
                : new ZiChar.Literal(glyph);
    }
}
