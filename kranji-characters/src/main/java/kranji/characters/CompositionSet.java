package kranji.characters;

import kranji.classification.EtymologicalCategory.*;
import kranji.entry.ChineseCharacterEntry;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.SingularZi;
import kranji.zi.Zi;

import java.util.List;

/**
 * Step-builder stage: composition has been set, etymology is required next.
 *
 * <p>Every method on this class is a <em>terminal</em> — it sets the etymology
 * and returns the finished {@link ChineseCharacterEntry}. This guarantees at
 * the type level that etymology is never forgotten.</p>
 */
public final class CompositionSet {

    private final String character;
    private final String codepoint;
    private final List<PinyinSyllable> pinyin;
    private final int strokes;
    private final int radicalNo;
    private final Zi structure;

    CompositionSet(String character, String codepoint, List<PinyinSyllable> pinyin,
                   int strokes, int radicalNo, Zi structure) {
        this.character = character;
        this.codepoint = codepoint;
        this.pinyin = pinyin;
        this.strokes = strokes;
        this.radicalNo = radicalNo;
        this.structure = structure;
    }

    // ── Etymology terminals ────────────────────────────────────────────

    /** 象形 — Pictograph (stylized drawing). */
    public ChineseCharacterEntry pictograph() {
        return build(new Pictograph());
    }

    /** 指事 — Simple indicative (abstract symbol with indicator). */
    public ChineseCharacterEntry indicative(String description) {
        return build(new SimpleIndicative(description));
    }

    /** 会意 — Compound indicative (meaning from combined parts). */
    public ChineseCharacterEntry compoundIndicative(String meaningHint) {
        return build(new CompoundIndicative(meaningHint));
    }

    /** 形声 — Phono-semantic (semantic part + phonetic part). */
    public ChineseCharacterEntry phonoSemantic(SingularZi semanticPart, SingularZi phoneticPart) {
        return build(new PhonoSemantic(semanticPart, phoneticPart));
    }

    /** 转注 — Derivative cognate (shares etymological root). */
    public ChineseCharacterEntry cognate(String cognateGlyph) {
        return build(new DerivativeCognate(cognateGlyph));
    }

    /** 假借 — Phonetic loan (borrowed for sound). */
    public ChineseCharacterEntry loan(String originalMeaning) {
        return build(new PhoneticLoan(originalMeaning));
    }

    private ChineseCharacterEntry build(kranji.classification.EtymologicalCategory etymology) {
        return new ChineseCharacterEntry(character, codepoint, pinyin, strokes, radicalNo,
                structure, etymology);
    }
}
