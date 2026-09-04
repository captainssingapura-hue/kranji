package kranji.pinyin;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 声调 — The five tones of Mandarin Chinese.
 */
public enum Tone {

    /** 阴平 (ˉ) — High level tone, e.g. mā. */
    FIRST(1, "ˉ", "阴平"),

    /** 阳平 (ˊ) — Rising tone, e.g. má. */
    SECOND(2, "ˊ", "阳平"),

    /** 上声 (ˇ) — Dipping tone, e.g. mǎ. */
    THIRD(3, "ˇ", "上声"),

    /** 去声 (ˋ) — Falling tone, e.g. mà. */
    FOURTH(4, "ˋ", "去声"),

    /**
     * 轻声 (·) — Neutral/unstressed tone, e.g. ma.
     *
     * <p><b>Zero, not five.</b> The tone number is part of the internal
     * representation of a syllable, where 0 reads as "no tone" rather than as
     * a fifth one - and Mandarin has four tones plus the absence of one.</p>
     */
    NEUTRAL(0, "·", "轻声");

    private final int number;
    private final String diacritic;
    private final String chinese;

    Tone(int number, String diacritic, String chinese) {
        this.number = number;
        this.diacritic = diacritic;
        this.chinese = chinese;
    }

    /** The tone number: 1-4, or 0 for neutral. */
    @JsonValue
    public int number() {
        return number;
    }

    /** Returns the diacritic mark for this tone. */
    public String diacritic() {
        return diacritic;
    }

    /** Returns the Chinese name for this tone. */
    public String chinese() {
        return chinese;
    }

    /**
     * Looks up a Tone by its number.
     *
     * <p>{ 5} is accepted as well as { 0} for the neutral tone: it
     * was the number this enum used before, and data written then - the
     * generated per-class corpus among it - still says 5. Only 0 is ever
     * emitted.</p>
     */
    public static Tone ofNumber(int n) {
        return switch (n) {
            case 1 -> FIRST;
            case 2 -> SECOND;
            case 3 -> THIRD;
            case 4 -> FOURTH;
            case 0, 5 -> NEUTRAL;
            default -> throw new IllegalArgumentException("Invalid tone number: " + n);
        };
    }
}
