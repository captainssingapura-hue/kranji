package kranji.simple.gloss;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.Objects;

/**
 * A meaning, as a phrase of English and nothing else.
 *
 * <p>Used at both levels: what a character means when read a given way, and
 * what a phrase means. They are the same thing - a concise English phrase with
 * a size limit - so they are the same type, and the rule and its constant live
 * in one place rather than being restated wherever an English gloss appears.</p>
 *
 * <p>A pure value: two meanings with the same text ARE the same meaning, so
 * uniqueness within a reading is the container's property rather than a check.
 * Rank and examples live beside it in {@link Sense}, because they are facts
 * about where a meaning sits, not about what it says.</p>
 *
 * <p>No registry, unlike examples. Phrases genuinely repeat across characters -
 * 银行 teaches both 银 and 行 - whereas meanings measured 568 distinct out of
 * 579 in the existing hand-curated set. A lookup tier for something that never
 * repeats is indirection with nothing on the other end; equality is enough.</p>
 *
 * <p>Concise deliberately. The reader is a child mid-story who has stopped at a
 * character and wants enough to keep going. Senses are separated by {@code ;}
 * and near-synonyms within a sense by {@code ,} - the convention the existing
 * component glosses already use.</p>
 */
public record Meaning(String text) implements ValueObject {

    /** Longer than this and it has stopped being concise. */
    public static final int MAX_LENGTH = 60;

    public Meaning {
        Objects.requireNonNull(text, "text");
        String trimmed = text.strip();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(
                    "an empty meaning - omit it rather than assert nothing");
        }
        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "\"" + trimmed + "\" is " + trimmed.length() + " characters; the limit is "
                  + MAX_LENGTH + " because a child mid-story will not read a paragraph");
        }
        text = trimmed;
    }

    public static Meaning of(String text) { return new Meaning(text); }

    @Override public String toString() { return text; }
}
