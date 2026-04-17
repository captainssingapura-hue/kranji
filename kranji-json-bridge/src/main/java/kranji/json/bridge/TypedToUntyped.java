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
import kranji.zi.ComposedZi;
import kranji.zi.SingularBlock;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;
import kranji.zi.ZiChar;

import java.util.LinkedHashMap;
import java.util.List;
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
 */
public final class TypedToUntyped {

    private TypedToUntyped() {}

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
                pinyinList(sz.pinyin()),
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
        Objects.requireNonNull(block, "block");
        if (block instanceof ComposedBlock cb) {
            return new BlockRefJson.InlineComposed(composedBlock(cb));
        }
        // SingularBlock → compact glyph string reference
        return new BlockRefJson.GlyphRef(block.glyph());
    }

    /** Convert a typed {@link ComposedBlock} to its JSON wire form. */
    public static ComposedBlockJson composedBlock(ComposedBlock block) {
        Objects.requireNonNull(block, "block");
        return switch (block) {
            case ComposedBlock.LeftRight lr -> of("left_right", Map.of(
                    "left",  blockRef(lr.left()),
                    "right", blockRef(lr.right())));
            case ComposedBlock.TopBottom tb -> of("top_bottom", Map.of(
                    "top",    blockRef(tb.top()),
                    "bottom", blockRef(tb.bottom())));
            case ComposedBlock.LeftMiddleRight lmr -> of("left_middle_right", orderedMap(
                    "left",   blockRef(lmr.left()),
                    "middle", blockRef(lmr.middle()),
                    "right",  blockRef(lmr.right())));
            case ComposedBlock.TopMiddleBottom tmb -> of("top_middle_bottom", orderedMap(
                    "top",    blockRef(tmb.top()),
                    "middle", blockRef(tmb.middle()),
                    "bottom", blockRef(tmb.bottom())));
            case ComposedBlock.FullEnclosure fe -> of("full_enclosure", Map.of(
                    "outer", blockRef(fe.outer()),
                    "inner", blockRef(fe.inner())));
            case ComposedBlock.SemiEnclosureUpperLeft  ul -> semi("semi_enclosure_upper_left",  ul.wrapper(), ul.content());
            case ComposedBlock.SemiEnclosureUpperRight ur -> semi("semi_enclosure_upper_right", ur.wrapper(), ur.content());
            case ComposedBlock.SemiEnclosureBottomLeft bl -> semi("semi_enclosure_bottom_left", bl.wrapper(), bl.content());
            case ComposedBlock.SemiEnclosureTopThree   t3 -> semi("semi_enclosure_top_three",   t3.wrapper(), t3.content());
            case ComposedBlock.SemiEnclosureBottomThree b3 -> semi("semi_enclosure_bottom_three", b3.wrapper(), b3.content());
            case ComposedBlock.SemiEnclosureLeftThree  l3 -> semi("semi_enclosure_left_three",  l3.wrapper(), l3.content());
        };
    }

    /** Convert a typed {@link ComposedZi} to its JSON wire form. */
    public static ComposedZiJson composedZi(ComposedZi cz) {
        Objects.requireNonNull(cz, "cz");
        ZiCharForm form = switch (cz.ziChar()) {
            case ZiChar.Literal ignored -> ZiCharForm.LITERAL;
            case ZiChar.Unicode ignored -> ZiCharForm.UNICODE;
        };
        return new ComposedZiJson(
                cz.character(),
                form,
                cz.strokes() == 0 ? null : cz.strokes(),
                cz.radicalNo() == 0 ? null : cz.radicalNo(),
                pinyinList(cz.pinyin()),
                emptyToNull(cz.meaning()),
                composedBlock(cz.composition()),
                etymology(cz.etymology())
        );
    }

    // ── Etymology ─────────────────────────────────────────────────────

    /** Convert {@link EtymologicalCategory} to {@link EtymologyJson}. */
    public static EtymologyJson etymology(EtymologicalCategory cat) {
        if (cat == null) return null;
        return switch (cat) {
            case EtymologicalCategory.Pictograph p         -> new EtymologyJson.Pictograph();
            case EtymologicalCategory.SimpleIndicative s   -> new EtymologyJson.SimpleIndicative(s.indicatorDescription());
            case EtymologicalCategory.CompoundIndicative c -> new EtymologyJson.CompoundIndicative(c.meaningHint());
            case EtymologicalCategory.DerivativeCognate d  -> new EtymologyJson.DerivativeCognate(d.cognateGlyph());
            case EtymologicalCategory.PhoneticLoan l       -> new EtymologyJson.PhoneticLoan(l.originalMeaning());
            case EtymologicalCategory.PhonoSemantic ps     -> new EtymologyJson.PhonoSemantic(
                    blockRef(ps.semanticPart()),
                    blockRef(ps.phoneticPart()));
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

    private static ComposedBlockJson semi(String style, BlockStructure wrapper, BlockStructure content) {
        return of(style, Map.of(
                "wrapper", blockRef(wrapper),
                "content", blockRef(content)));
    }

    private static ComposedBlockJson of(String style, Map<String, BlockRefJson> slots) {
        return new ComposedBlockJson(style, slots);
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

    private static List<PinyinJson> pinyinList(List<PinyinSyllable> typed) {
        if (typed == null || typed.isEmpty()) return List.of();
        return typed.stream().map(TypedToUntyped::pinyin).toList();
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isEmpty()) ? null : s;
    }

    private static String codepointOf(String glyph) {
        if (glyph == null || glyph.isEmpty()) return null;
        return "U+" + String.format("%04X", glyph.codePointAt(0));
    }
}
