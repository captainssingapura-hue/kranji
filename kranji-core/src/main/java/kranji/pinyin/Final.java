package kranji.pinyin;

/**
 * 韵母 — The final of a Mandarin syllable, decomposed into head (medial),
 * body (nucleus), and tail (coda).
 *
 * @param head the medial / 韵头 (四呼)
 * @param body the nucleus / 韵腹
 * @param tail the coda / 韵尾
 */
public record Final(Head head, Body body, Tail tail) {

    /**
     * Returns the conventional pinyin spelling of this final in its
     * <em>initial-less</em> form — i.e. the key you'd look up in a finals
     * table (so {@code (U,U,NONE) → "u"}, {@code (V,V,NONE) → "ü"},
     * {@code (I,E_CARON,NONE) → "ie"}, {@code (U,E,VOWEL_I) → "uei"}).
     *
     * <p>The medial letter and the nucleus letter are collapsed when they
     * would otherwise duplicate — "uu" / "üü" are never written. Initial-
     * dependent orthography (j/q/x dropping the ü, zero-initial y-/w-
     * prefixes, and the ui/iu/un abbreviations) is applied on top of this
     * string by {@link PinyinSyllable#numberedTone()} and
     * {@link PinyinSyllable#toDiacritic()}, not here.</p>
     */
    public String spelling() {
        String medial = switch (head) {
            case I -> "i";
            case U -> "u";
            case V -> "ü";
            case OPEN -> "";
        };
        String nucleus = body.symbol();
        // Pinyin orthography quirk: the u-glide in "ao"/"iao" is written
        // as "o", not "u" — the Tail symbol is phonemic, not spelling.
        // Everywhere else (ou, iou) "u" is the right letter.
        String tailSym = (tail == Tail.VOWEL_U && body == Body.A) ? "o" : tail.symbol();
        // Collapse duplicate medial+nucleus: (U,U) → "u", (V,V) → "ü".
        // (No risk of eating an E_CARON because E_CARON now spells "e",
        //  never "i"/"u"/"ü".)
        if (!medial.isEmpty() && medial.equals(nucleus)) {
            return medial + tailSym;
        }
        return medial + nucleus + tailSym;
    }
}
