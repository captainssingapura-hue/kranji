package kranji.json.bridge;

import kranji.classification.EtymologicalCategory;
import kranji.json.dto.BlockRefJson;
import kranji.json.dto.ComposedBlockJson;
import kranji.json.dto.ComposedZiJson;
import kranji.json.dto.EtymologyJson;
import kranji.json.dto.PinyinJson;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;
import kranji.json.dto.ZiCharForm;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.CompositionLayout;
import kranji.zi.ComposedZi;
import kranji.zi.SingularBlock;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;
import kranji.zi.ZiChar;

import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * One-way converters from the typed runtime hierarchy ({@code kranji-core})
 * into the untyped JSON DTO layer ({@code kranji-json}).
 *
 * <p>All methods are pure. No mutable state.</p>
 *
 * <h2>BlockStructure → BlockRefJson policy</h2>
 * A typed {@link BlockStructure} occupying a slot is serialized as:
 * <ul>
 *   <li>{@link SingularBlock} (including both {@link SingularZi} and
 *       {@link SingularPart}) → {@link BlockRefJson.GlyphRef}, i.e. a
 *       bare glyph string. The receiving side must carry the referenced
 *       glyph in its singular pool.</li>
 *   <li>{@link ComposedBlock} → {@link BlockRefJson.InlineComposed},
 *       expanding the sub-composition recursively.</li>
 * </ul>
 * Callers that want to inline a {@link SingularPart} payload (rather than
 * rely on registry lookup) can do so manually via {@link #singularPart}
 * and wrap the result in {@link BlockRefJson.InlineSingular}.
 *
 * <h2>Reference-context (depth-2 and above)</h2>
 * Depth-2+ Zi carry {@link ComposedBlock} children that represent named
 * composed Zi from a lower depth (e.g. a depth-2 Zi whose right slot is
 * 元 — itself a depth-1 {@code ComposedZi}). Inlining the entire
 * sub-composition would discard the by-reference intent and bloat the
 * JSON.
 *
 * <p>Pass a {@link RefContext} mapping {@link ComposedBlock} instances to
 * their reference glyphs to override {@link BlockRefJson.InlineComposed}
 * with {@link BlockRefJson.GlyphRef} for each registered structure. The
 * legacy single-argument methods are equivalent to passing
 * {@link RefContext#EMPTY} and remain the right choice for depth-1
 * (where no ComposedBlock child is itself a named Zi).</p>
 */
public final class TypedToUntyped {

    private TypedToUntyped() {}

    /**
     * Identity map of {@link ComposedBlock} structures that should be
     * serialized as {@link BlockRefJson.GlyphRef} (by glyph) instead of
     * {@link BlockRefJson.InlineComposed} (full sub-tree).
     *
     * <p>Comparison is by reference identity — a {@link ComposedBlock}
     * matches only when the exact same instance was registered. This
     * is intentional: two structurally equal but distinct instances
     * represent two records and should not collapse onto one glyph.</p>
     */
    public static final class RefContext {
        public static final RefContext EMPTY = new RefContext(new IdentityHashMap<>());

        private final IdentityHashMap<ComposedBlock, String> byIdentity;

        public RefContext(IdentityHashMap<ComposedBlock, String> byIdentity) {
            this.byIdentity = Objects.requireNonNull(byIdentity, "byIdentity");
        }

        /** Returns the registered glyph for {@code block}, or {@code null}. */
        String glyphOf(ComposedBlock block) {
            return byIdentity.get(block);
        }

        boolean isEmpty() { return byIdentity.isEmpty(); }
    }

    // ── Leaf types ────────────────────────────────────────────────────

    /** Convert a typed {@link SingularZi} to its JSON wire form. */
    public static SingularZiJson singularZi(SingularZi sz) {
        Objects.requireNonNull(sz, "sz");
        return new SingularZiJson(
                sz.glyph(),
                codepointOf(sz.glyph()),
                sz.name(),
                sz.strokes(),
                sz.radicalNo() == 0 ? null : sz.radicalNo(),
                emptyToNull(sz.pinyinText()),
                sz.pinyin() == null ? null : pinyin(sz.pinyin()),
                emptyToNull(sz.meaning()),
                etymology(sz.etymology())
        );
    }

    /** Convert a typed {@link SingularPart} to its JSON wire form. */
    public static SingularPartJson singularPart(SingularPart sp) {
        Objects.requireNonNull(sp, "sp");
        String derivedFromGlyph = sp.derivedFrom() != null
                ? sp.derivedFrom().character()
                : (sp.standalone().equals(sp.glyph()) ? null : sp.standalone());
        return new SingularPartJson(
                sp.glyph(),
                sp.name().equals(sp.glyph()) ? null : sp.name(),
                sp.strokes(),
                emptyToNull(sp.pinyinText()),
                emptyToNull(sp.meaning()),
                derivedFromGlyph
        );
    }

    // ── Composition tree ──────────────────────────────────────────────

    /** Emit a {@link BlockRefJson} for a typed block, per the class-level policy. */
    public static BlockRefJson blockRef(BlockStructure block) {
        return blockRef(block, RefContext.EMPTY);
    }

    /**
     * Emit a {@link BlockRefJson} for a typed block, consulting
     * {@code ctx} so that {@link ComposedBlock}s registered as named
     * lower-depth Zi serialize as {@link BlockRefJson.GlyphRef} instead
     * of {@link BlockRefJson.InlineComposed}.
     */
    public static BlockRefJson blockRef(BlockStructure block, RefContext ctx) {
        Objects.requireNonNull(block, "block");
        Objects.requireNonNull(ctx, "ctx");
        if (block instanceof ComposedBlock cb) {
            String knownGlyph = ctx.glyphOf(cb);
            if (knownGlyph != null) {
                return new BlockRefJson.GlyphRef(knownGlyph);
            }
            return new BlockRefJson.InlineComposed(composedBlock(cb.composition(), ctx));
        }
        // SingularBlock → compact glyph string reference
        return new BlockRefJson.GlyphRef(block.glyph());
    }

    /** Convert a typed {@link CompositionLayout} to its JSON wire form. */
    public static ComposedBlockJson composedBlock(CompositionLayout layout) {
        return composedBlock(layout, RefContext.EMPTY);
    }

    /**
     * Convert a typed {@link CompositionLayout} to its JSON wire form, with
     * children resolved against {@code ctx}.
     */
    public static ComposedBlockJson composedBlock(CompositionLayout layout, RefContext ctx) {
        Objects.requireNonNull(layout, "layout");
        Objects.requireNonNull(ctx, "ctx");
        return switch (layout) {
            case CompositionLayout.LeftRight lr -> of("left_right", orderedMap(
                    "left",  blockRef(lr.left(), ctx),
                    "right", blockRef(lr.right(), ctx)));
            case CompositionLayout.TopBottom tb -> of("top_bottom", orderedMap(
                    "top",    blockRef(tb.top(), ctx),
                    "bottom", blockRef(tb.bottom(), ctx)));
            case CompositionLayout.LeftMiddleRight lmr -> of("left_middle_right", orderedMap(
                    "left",   blockRef(lmr.left(), ctx),
                    "middle", blockRef(lmr.middle(), ctx),
                    "right",  blockRef(lmr.right(), ctx)));
            case CompositionLayout.TopMiddleBottom tmb -> of("top_middle_bottom", orderedMap(
                    "top",    blockRef(tmb.top(), ctx),
                    "middle", blockRef(tmb.middle(), ctx),
                    "bottom", blockRef(tmb.bottom(), ctx)));
            case CompositionLayout.FullEnclosure fe -> of("full_enclosure", orderedMap(
                    "outer", blockRef(fe.outer(), ctx),
                    "inner", blockRef(fe.inner(), ctx)));
            case CompositionLayout.SemiEnclosureUpperLeft  ul -> semi("semi_enclosure_upper_left",  ul.wrapper(), ul.content(), ctx);
            case CompositionLayout.SemiEnclosureUpperRight ur -> semi("semi_enclosure_upper_right", ur.wrapper(), ur.content(), ctx);
            case CompositionLayout.SemiEnclosureBottomLeft bl -> semi("semi_enclosure_bottom_left", bl.wrapper(), bl.content(), ctx);
            case CompositionLayout.SemiEnclosureTopThree   t3 -> semi("semi_enclosure_top_three",   t3.wrapper(), t3.content(), ctx);
            case CompositionLayout.SemiEnclosureBottomThree b3 -> semi("semi_enclosure_bottom_three", b3.wrapper(), b3.content(), ctx);
            case CompositionLayout.SemiEnclosureLeftThree  l3 -> semi("semi_enclosure_left_three",  l3.wrapper(), l3.content(), ctx);
        };
    }

    /** Convert a typed {@link ComposedZi} to its JSON wire form. */
    public static ComposedZiJson composedZi(ComposedZi cz) {
        return composedZi(cz, RefContext.EMPTY);
    }

    /**
     * Convert a typed {@link ComposedZi} to its JSON wire form, resolving
     * inline references against {@code ctx} (see class javadoc).
     */
    public static ComposedZiJson composedZi(ComposedZi cz, RefContext ctx) {
        Objects.requireNonNull(cz, "cz");
        Objects.requireNonNull(ctx, "ctx");
        ZiCharForm form = switch (cz.ziChar()) {
            case ZiChar.Literal ignored -> ZiCharForm.LITERAL;
            case ZiChar.Unicode ignored -> ZiCharForm.UNICODE;
        };
        return new ComposedZiJson(
                cz.character(),
                form,
                cz.strokes() == 0 ? null : cz.strokes(),
                cz.radicalNo() == 0 ? null : cz.radicalNo(),
                cz.pinyin() == null ? null : pinyin(cz.pinyin()),
                emptyToNull(cz.meaning()),
                composedBlock(cz.composition(), ctx),
                etymology(cz.etymology(), ctx)
        );
    }

    // ── Etymology ─────────────────────────────────────────────────────

    /** Convert {@link EtymologicalCategory} to {@link EtymologyJson}. */
    public static EtymologyJson etymology(EtymologicalCategory cat) {
        return etymology(cat, RefContext.EMPTY);
    }

    /** Convert {@link EtymologicalCategory} to {@link EtymologyJson}, with refs resolved against {@code ctx}. */
    public static EtymologyJson etymology(EtymologicalCategory cat, RefContext ctx) {
        if (cat == null) return null;
        Objects.requireNonNull(ctx, "ctx");
        return switch (cat) {
            case EtymologicalCategory.Pictograph p         -> new EtymologyJson.Pictograph();
            case EtymologicalCategory.SimpleIndicative s   -> new EtymologyJson.SimpleIndicative(s.indicatorDescription());
            case EtymologicalCategory.CompoundIndicative c -> new EtymologyJson.CompoundIndicative(c.meaningHint());
            case EtymologicalCategory.DerivativeCognate d  -> new EtymologyJson.DerivativeCognate(d.cognateGlyph());
            case EtymologicalCategory.PhoneticLoan l       -> new EtymologyJson.PhoneticLoan(l.originalMeaning());
            case EtymologicalCategory.PhonoSemantic ps     -> new EtymologyJson.PhonoSemantic(
                    blockRef(ps.semanticPart(), ctx),
                    blockRef(ps.phoneticPart(), ctx));
        };
    }

    // ── Pinyin ────────────────────────────────────────────────────────

    /** Convert a typed {@link PinyinSyllable} to its JSON wire form. */
    public static PinyinJson pinyin(PinyinSyllable syllable) {
        Objects.requireNonNull(syllable, "syllable");
        return new PinyinJson(
                syllable.initial().pinyin(),
                syllable.fin().spelling(),
                syllable.tone().number()
        );
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private static ComposedBlockJson semi(String style, BlockStructure wrapper, BlockStructure content, RefContext ctx) {
        return of(style, orderedMap(
                "wrapper", blockRef(wrapper, ctx),
                "content", blockRef(content, ctx)));
    }

    private static ComposedBlockJson of(String style, Map<String, BlockRefJson> slots) {
        return new ComposedBlockJson(style, slots);
    }

    /** Two-key ordered map preserving insertion order for predictable output. */
    private static Map<String, BlockRefJson> orderedMap(
            String k1, BlockRefJson v1,
            String k2, BlockRefJson v2) {
        Map<String, BlockRefJson> m = new LinkedHashMap<>();
        m.put(k1, v1); m.put(k2, v2);
        return m;
    }

    /** Three-key ordered map preserving insertion order for predictable output. */
    private static Map<String, BlockRefJson> orderedMap(
            String k1, BlockRefJson v1,
            String k2, BlockRefJson v2,
            String k3, BlockRefJson v3) {
        Map<String, BlockRefJson> m = new LinkedHashMap<>();
        m.put(k1, v1); m.put(k2, v2); m.put(k3, v3);
        return m;
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isEmpty()) ? null : s;
    }

    private static String codepointOf(String glyph) {
        if (glyph == null || glyph.isEmpty()) return null;
        return "U+" + String.format("%04X", glyph.codePointAt(0));
    }
}
