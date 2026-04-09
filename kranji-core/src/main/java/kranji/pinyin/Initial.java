package kranji.pinyin;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 声母 — The onset consonant of a Mandarin syllable.
 *
 * <p>21 consonant initials plus a zero initial for vowel-starting syllables.</p>
 */
public enum Initial {

    // @formatter:off
    ZERO ("",   "零声母"),

    // Bilabial (双唇音)
    B    ("b",  "双唇音"),
    P    ("p",  "双唇音"),
    M    ("m",  "双唇音"),

    // Labiodental (唇齿音)
    F    ("f",  "唇齿音"),

    // Alveolar (舌尖中音)
    D    ("d",  "舌尖中音"),
    T    ("t",  "舌尖中音"),
    N    ("n",  "舌尖中音"),
    L    ("l",  "舌尖中音"),

    // Velar (舌根音)
    G    ("g",  "舌根音"),
    K    ("k",  "舌根音"),
    H    ("h",  "舌根音"),

    // Palatal (舌面音)
    J    ("j",  "舌面音"),
    Q    ("q",  "舌面音"),
    X    ("x",  "舌面音"),

    // Retroflex (翘舌音)
    ZH   ("zh", "翘舌音"),
    CH   ("ch", "翘舌音"),
    SH   ("sh", "翘舌音"),
    R    ("r",  "翘舌音"),

    // Dental sibilant (平舌音)
    Z    ("z",  "平舌音"),
    C    ("c",  "平舌音"),
    S    ("s",  "平舌音");
    // @formatter:on

    private final String pinyin;
    private final String placeOfArticulation;

    Initial(String pinyin, String placeOfArticulation) {
        this.pinyin = pinyin;
        this.placeOfArticulation = placeOfArticulation;
    }

    /** Returns the pinyin spelling of this initial (empty string for zero initial). */
    @JsonValue
    public String pinyin() {
        return pinyin;
    }

    /** Returns the Chinese name for the place of articulation. */
    public String placeOfArticulation() {
        return placeOfArticulation;
    }

    /** Parses an Initial from its pinyin spelling. */
    public static Initial fromPinyin(String s) {
        for (Initial i : values()) {
            if (i.pinyin.equals(s)) return i;
        }
        throw new IllegalArgumentException("Unknown initial: " + s);
    }
}
