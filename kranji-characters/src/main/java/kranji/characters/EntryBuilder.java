package kranji.characters;

import kranji.entry.ChineseCharacterEntry;
import kranji.pinyin.*;
import kranji.zi.ComposedZi;
import kranji.zi.ComposedZi.*;
import kranji.zi.SingularZi;
import kranji.zi.Zi;

import java.util.ArrayList;
import java.util.List;

/**
 * Fluent step-builder for {@link ChineseCharacterEntry}.
 *
 * <p>Usage pattern (static import {@code entry} from this class,
 * everything else from {@link Comp}):</p>
 *
 * <pre>{@code
 * public static final ChineseCharacterEntry 清_CLEAR = entry("清")
 *     .py(Q, OPEN, I, NG, T1).strokes(11).radical(85)
 *     .leftRight(SAN_DIAN_SHUI, zi("青"))
 *     .phonoSemantic(SAN_DIAN_SHUI, zi("青"));
 * }</pre>
 *
 * <p>The builder enforces a fixed ordering via return types:</p>
 * <ol>
 *   <li>{@code entry(glyph)} → {@code EntryBuilder} — set metadata</li>
 *   <li>{@code .leftRight(...)} → {@link CompositionSet} — composition locked</li>
 *   <li>{@code .phonoSemantic(...)} → {@link ChineseCharacterEntry} — done</li>
 * </ol>
 */
public final class EntryBuilder {

    private final String character;
    private final String codepoint;
    private final List<PinyinSyllable> pinyin = new ArrayList<>();
    private int strokes;
    private int radicalNo;

    private EntryBuilder(String glyph) {
        this.character = glyph;
        this.codepoint = ChineseCharacterEntry.toCodepoint(glyph);
    }

    /** Start building a character entry for the given glyph. */
    public static EntryBuilder entry(String glyph) {
        return new EntryBuilder(glyph);
    }

    // ── Metadata (chainable) ───────────────────────────────────────────

    /** Add a pinyin reading (can be called multiple times for 多音字). */
    public EntryBuilder py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        pinyin.add(new PinyinSyllable(ini, new Final(h, b, t), tone));
        return this;
    }

    /** Set the total stroke count. */
    public EntryBuilder strokes(int n) {
        this.strokes = n;
        return this;
    }

    /** Set the Kangxi radical number (1–214). */
    public EntryBuilder radical(int n) {
        this.radicalNo = n;
        return this;
    }

    // ── Composition setters (each returns CompositionSet) ──────────────

    /** 独体字 — Singular (no sub-components). */
    public CompositionSet singular() {
        return withStructure(SingularZi.plain(character));
    }

    /** 独体字 — Singular with a specific SingularZi. */
    public CompositionSet singular(SingularZi zi) {
        return withStructure(zi);
    }

    /** 左右结构 — Left-Right. */
    public CompositionSet leftRight(Zi left, Zi right) {
        return withStructure(new LeftRight(left, right));
    }

    /** 上下结构 — Top-Bottom. */
    public CompositionSet topBottom(Zi top, Zi bottom) {
        return withStructure(new TopBottom(top, bottom));
    }

    /** 左中右结构 — Left-Middle-Right. */
    public CompositionSet leftMiddleRight(Zi left, Zi middle, Zi right) {
        return withStructure(new LeftMiddleRight(left, middle, right));
    }

    /** 上中下结构 — Top-Middle-Bottom. */
    public CompositionSet topMiddleBottom(Zi top, Zi middle, Zi bottom) {
        return withStructure(new TopMiddleBottom(top, middle, bottom));
    }

    /** 全包围 — Full Enclosure. */
    public CompositionSet fullEnclosure(Zi outer, Zi inner) {
        return withStructure(new FullEnclosure(outer, inner));
    }

    /** 左上包围 — Semi-enclosure upper-left. */
    public CompositionSet semiEnclosureUL(Zi wrapper, Zi content) {
        return withStructure(new SemiEnclosureUpperLeft(wrapper, content));
    }

    /** 右上包围 — Semi-enclosure upper-right. */
    public CompositionSet semiEnclosureUR(Zi wrapper, Zi content) {
        return withStructure(new SemiEnclosureUpperRight(wrapper, content));
    }

    /** 左下包围 — Semi-enclosure bottom-left. */
    public CompositionSet semiEnclosureBL(Zi wrapper, Zi content) {
        return withStructure(new SemiEnclosureBottomLeft(wrapper, content));
    }

    /** 上三包围 — Semi-enclosure top-three. */
    public CompositionSet semiEnclosureT3(Zi wrapper, Zi content) {
        return withStructure(new SemiEnclosureTopThree(wrapper, content));
    }

    /** 下三包围 — Semi-enclosure bottom-three. */
    public CompositionSet semiEnclosureB3(Zi wrapper, Zi content) {
        return withStructure(new SemiEnclosureBottomThree(wrapper, content));
    }

    /** 左三包围 — Semi-enclosure left-three. */
    public CompositionSet semiEnclosureL3(Zi wrapper, Zi content) {
        return withStructure(new SemiEnclosureLeftThree(wrapper, content));
    }

    /** 品字结构 — Mosaic (three identical elements). */
    public CompositionSet mosaic(Zi element) {
        return withStructure(new Mosaic(element));
    }

    private CompositionSet withStructure(Zi structure) {
        return new CompositionSet(character, codepoint, List.copyOf(pinyin),
                strokes, radicalNo, structure);
    }
}
