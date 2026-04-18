package kranji.common;

import kranji.classification.EtymologicalCategory;
import kranji.classification.EtymologicalCategory.CompoundIndicative;
import kranji.classification.EtymologicalCategory.PhonoSemantic;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.ComposedBlock.FullEnclosure;
import kranji.zi.ComposedBlock.LeftMiddleRight;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.zi.ComposedBlock.SemiEnclosureBottomLeft;
import kranji.zi.ComposedBlock.SemiEnclosureBottomThree;
import kranji.zi.ComposedBlock.SemiEnclosureLeftThree;
import kranji.zi.ComposedBlock.SemiEnclosureTopThree;
import kranji.zi.ComposedBlock.SemiEnclosureUpperLeft;
import kranji.zi.ComposedBlock.SemiEnclosureUpperRight;
import kranji.zi.ComposedBlock.TopBottom;
import kranji.zi.ComposedBlock.TopMiddleBottom;
import kranji.zi.ComposedZi;
import kranji.zi.ZiChar;

/**
 * Factory helpers that compress the two most common
 * {@link ComposedZi} construction patterns into one-liners:
 *
 * <ul>
 *   <li><b>{@code phono*}</b> — {@link PhonoSemantic} etymology, where the
 *       two child slots are the semantic and phonetic parts respectively.
 *       The {@code PhonoSemantic} record echoes the same two children as
 *       the structural composition, which the factory fills in from the
 *       same two arguments.</li>
 *   <li><b>{@code compound*}</b> — {@link CompoundIndicative} etymology,
 *       whose children are structural only. The factory takes an
 *       explanatory {@code hint} string in addition to the two slots.</li>
 * </ul>
 *
 * <p>One suffix per composition variant: {@code LR}, {@code TB},
 * {@code BL}, {@code UL}, {@code UR}, {@code T3}, {@code B3}, {@code L3},
 * {@code FE}, {@code LMR}, {@code TMB} — mirroring the sealed variants of
 * {@link ComposedBlock}. Rare style×etymology combinations (e.g.
 * {@code Pictogram}, {@code SelfIndicative}, multi-syllable pinyin) fall
 * back to constructing {@link ComposedZi} directly.</p>
 *
 * <p>Every factory:</p>
 * <ol>
 *   <li>Wraps the glyph in {@link ZiChar.Unicode} — runtime identical to
 *       {@link ZiChar.Literal}, which was only a source-code readability
 *       tag and is irrelevant once the call is a one-liner.</li>
 *   <li>Passes the {@link PinyinSyllable} through directly. Polysyllabic
 *       Zi (rare) should construct {@link ComposedZi} directly.</li>
 *   <li>Uses {@code ""} for the separate English-meaning field — every
 *       current record leaves this blank and derives meaning via the
 *       Zi's {@code meaning()} override.</li>
 * </ol>
 */
public final class ComposedFactories {

    private ComposedFactories() {}

    // ── Phono-semantic factories ─────────────────────────────────────

    /** {@code LeftRight(semantic, phonetic)} + {@code PhonoSemantic(semantic, phonetic)}. */
    public static ComposedZi phonoLR(String glyph, PinyinSyllable py, int strokes, int radical,
                                     BlockStructure semantic, BlockStructure phonetic) {
        return phono(glyph, py, strokes, radical,
                new LeftRight(semantic, phonetic), semantic, phonetic);
    }

    /** {@code TopBottom(semantic, phonetic)} + {@code PhonoSemantic(semantic, phonetic)}. */
    public static ComposedZi phonoTB(String glyph, PinyinSyllable py, int strokes, int radical,
                                     BlockStructure semantic, BlockStructure phonetic) {
        return phono(glyph, py, strokes, radical,
                new TopBottom(semantic, phonetic), semantic, phonetic);
    }

    /** {@code SemiEnclosureBottomLeft(semantic, phonetic)} + {@code PhonoSemantic(…)}. */
    public static ComposedZi phonoBL(String glyph, PinyinSyllable py, int strokes, int radical,
                                     BlockStructure semantic, BlockStructure phonetic) {
        return phono(glyph, py, strokes, radical,
                new SemiEnclosureBottomLeft(semantic, phonetic), semantic, phonetic);
    }

    /** {@code SemiEnclosureUpperLeft(semantic, phonetic)} + {@code PhonoSemantic(…)}. */
    public static ComposedZi phonoUL(String glyph, PinyinSyllable py, int strokes, int radical,
                                     BlockStructure semantic, BlockStructure phonetic) {
        return phono(glyph, py, strokes, radical,
                new SemiEnclosureUpperLeft(semantic, phonetic), semantic, phonetic);
    }

    /** {@code SemiEnclosureUpperRight(semantic, phonetic)} + {@code PhonoSemantic(…)}. */
    public static ComposedZi phonoUR(String glyph, PinyinSyllable py, int strokes, int radical,
                                     BlockStructure semantic, BlockStructure phonetic) {
        return phono(glyph, py, strokes, radical,
                new SemiEnclosureUpperRight(semantic, phonetic), semantic, phonetic);
    }

    /** {@code SemiEnclosureTopThree(semantic, phonetic)} + {@code PhonoSemantic(…)}. */
    public static ComposedZi phonoT3(String glyph, PinyinSyllable py, int strokes, int radical,
                                     BlockStructure semantic, BlockStructure phonetic) {
        return phono(glyph, py, strokes, radical,
                new SemiEnclosureTopThree(semantic, phonetic), semantic, phonetic);
    }

    /** {@code SemiEnclosureBottomThree(semantic, phonetic)} + {@code PhonoSemantic(…)}. */
    public static ComposedZi phonoB3(String glyph, PinyinSyllable py, int strokes, int radical,
                                     BlockStructure semantic, BlockStructure phonetic) {
        return phono(glyph, py, strokes, radical,
                new SemiEnclosureBottomThree(semantic, phonetic), semantic, phonetic);
    }

    /** {@code SemiEnclosureLeftThree(semantic, phonetic)} + {@code PhonoSemantic(…)}. */
    public static ComposedZi phonoL3(String glyph, PinyinSyllable py, int strokes, int radical,
                                     BlockStructure semantic, BlockStructure phonetic) {
        return phono(glyph, py, strokes, radical,
                new SemiEnclosureLeftThree(semantic, phonetic), semantic, phonetic);
    }

    /** {@code FullEnclosure(outer, inner)} + {@code PhonoSemantic(outer, inner)}. */
    public static ComposedZi phonoFE(String glyph, PinyinSyllable py, int strokes, int radical,
                                     BlockStructure outer, BlockStructure inner) {
        return phono(glyph, py, strokes, radical,
                new FullEnclosure(outer, inner), outer, inner);
    }

    // ── Compound-indicative factories ────────────────────────────────

    /** {@code LeftRight(a, b)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundLR(String glyph, PinyinSyllable py, int strokes, int radical,
                                        BlockStructure left, BlockStructure right, String hint) {
        return compound(glyph, py, strokes, radical,
                new LeftRight(left, right), hint);
    }

    /** {@code TopBottom(a, b)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundTB(String glyph, PinyinSyllable py, int strokes, int radical,
                                        BlockStructure top, BlockStructure bottom, String hint) {
        return compound(glyph, py, strokes, radical,
                new TopBottom(top, bottom), hint);
    }

    /** {@code SemiEnclosureBottomLeft(wrapper, content)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundBL(String glyph, PinyinSyllable py, int strokes, int radical,
                                        BlockStructure wrapper, BlockStructure content, String hint) {
        return compound(glyph, py, strokes, radical,
                new SemiEnclosureBottomLeft(wrapper, content), hint);
    }

    /** {@code SemiEnclosureUpperLeft(wrapper, content)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundUL(String glyph, PinyinSyllable py, int strokes, int radical,
                                        BlockStructure wrapper, BlockStructure content, String hint) {
        return compound(glyph, py, strokes, radical,
                new SemiEnclosureUpperLeft(wrapper, content), hint);
    }

    /** {@code SemiEnclosureUpperRight(wrapper, content)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundUR(String glyph, PinyinSyllable py, int strokes, int radical,
                                        BlockStructure wrapper, BlockStructure content, String hint) {
        return compound(glyph, py, strokes, radical,
                new SemiEnclosureUpperRight(wrapper, content), hint);
    }

    /** {@code SemiEnclosureTopThree(wrapper, content)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundT3(String glyph, PinyinSyllable py, int strokes, int radical,
                                        BlockStructure wrapper, BlockStructure content, String hint) {
        return compound(glyph, py, strokes, radical,
                new SemiEnclosureTopThree(wrapper, content), hint);
    }

    /** {@code SemiEnclosureBottomThree(wrapper, content)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundB3(String glyph, PinyinSyllable py, int strokes, int radical,
                                        BlockStructure wrapper, BlockStructure content, String hint) {
        return compound(glyph, py, strokes, radical,
                new SemiEnclosureBottomThree(wrapper, content), hint);
    }

    /** {@code SemiEnclosureLeftThree(wrapper, content)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundL3(String glyph, PinyinSyllable py, int strokes, int radical,
                                        BlockStructure wrapper, BlockStructure content, String hint) {
        return compound(glyph, py, strokes, radical,
                new SemiEnclosureLeftThree(wrapper, content), hint);
    }

    /** {@code FullEnclosure(outer, inner)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundFE(String glyph, PinyinSyllable py, int strokes, int radical,
                                        BlockStructure outer, BlockStructure inner, String hint) {
        return compound(glyph, py, strokes, radical,
                new FullEnclosure(outer, inner), hint);
    }

    /** {@code LeftMiddleRight(…)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundLMR(String glyph, PinyinSyllable py, int strokes, int radical,
                                         BlockStructure left, BlockStructure middle, BlockStructure right,
                                         String hint) {
        return compound(glyph, py, strokes, radical,
                new LeftMiddleRight(left, middle, right), hint);
    }

    /** {@code TopMiddleBottom(…)} + {@code CompoundIndicative(hint)}. */
    public static ComposedZi compoundTMB(String glyph, PinyinSyllable py, int strokes, int radical,
                                         BlockStructure top, BlockStructure middle, BlockStructure bottom,
                                         String hint) {
        return compound(glyph, py, strokes, radical,
                new TopMiddleBottom(top, middle, bottom), hint);
    }

    // ── Internal shared builders ─────────────────────────────────────

    private static ComposedZi phono(String glyph, PinyinSyllable py, int strokes, int radical,
                                    ComposedBlock composition,
                                    BlockStructure semantic, BlockStructure phonetic) {
        return build(glyph, py, strokes, radical, composition,
                new PhonoSemantic(semantic, phonetic));
    }

    private static ComposedZi compound(String glyph, PinyinSyllable py, int strokes, int radical,
                                       ComposedBlock composition, String hint) {
        return build(glyph, py, strokes, radical, composition,
                new CompoundIndicative(hint));
    }

    private static ComposedZi build(String glyph, PinyinSyllable py, int strokes, int radical,
                                    ComposedBlock composition, EtymologicalCategory etymology) {
        return new ComposedZi(
                new ZiChar.Unicode(glyph),
                py,
                strokes, radical, "",
                composition,
                etymology);
    }
}
