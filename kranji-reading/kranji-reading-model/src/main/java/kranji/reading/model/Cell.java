package kranji.reading.model;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.Objects;

/**
 * One square of the page.
 *
 * <p>A {@link Token} is what the text <em>is</em>; a cell is what gets written
 * in one box. They differ in exactly one way: punctuation does not get a box of
 * its own — it rides in the corner of the character it belongs to.</p>
 *
 * <p>That is 禁则 handled structurally rather than by a line-breaking rule. A
 * row cannot break inside a cell, so 。 can never begin a line and 「 can never
 * end one, without any code deciding where lines break.</p>
 *
 * <p>The character case is split so the punctuation-bearing cell is visible in
 * the type. A renderer must acknowledge it to compile, which is the point —
 * punctuation needs different placement and a lighter weight, and a cell that
 * carries it should not be mistaken for a plain one.</p>
 */
public sealed interface Cell extends ValueObject permits Cell.WithCharacter, Cell.Plain {

    /** A cell that holds a character, with or without punctuation beside it. */
    sealed interface WithCharacter extends Cell permits Cell.Char, Cell.CharWithPunctuation {
        Token.Zi character();
    }

    /** The ordinary case: one character, nothing else. */
    record Char(Token.Zi character) implements WithCharacter {
        public Char {
            Objects.requireNonNull(character, "character");
        }
    }

    /**
     * A character with punctuation glued to it.
     *
     * @param leading  an opening mark that must not end a line — 「（《
     * @param trailing a closing mark that must not begin one — 。，、！？
     */
    record CharWithPunctuation(Token.Zi character, String leading, String trailing)
            implements WithCharacter {

        public CharWithPunctuation {
            Objects.requireNonNull(character, "character");
            leading = leading == null ? "" : leading;
            trailing = trailing == null ? "" : trailing;
            if (leading.isEmpty() && trailing.isEmpty()) {
                throw new IllegalArgumentException(
                        "a cell with no punctuation is a Char: " + character.text());
            }
        }

        public boolean hasLeading()  { return !leading.isEmpty(); }
        public boolean hasTrailing() { return !trailing.isEmpty(); }
    }

    /** Text with no character in it — Latin, digits, stray marks. */
    record Plain(String text) implements Cell {
        public Plain {
            Objects.requireNonNull(text, "text");
            if (text.isBlank()) {
                throw new IllegalArgumentException("an empty cell is not a cell");
            }
        }
    }
}
