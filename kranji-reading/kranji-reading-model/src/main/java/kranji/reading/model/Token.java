package kranji.reading.model;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;

import java.util.Objects;

/**
 * One piece of an article's text.
 *
 * <p>Two cases, and the split is the annotatable/not-annotatable split.
 * {@link ZiCharUTF8} refuses a non-Han codepoint, so 。 and {@code 3} cannot
 * be constructed as a {@link Zi} — the invariant lives in the type rather than
 * in the parser's care.</p>
 */
public sealed interface Token extends ValueObject permits Token.Zi, Token.Plain {

    /** What this token contributes to the running text. */
    String text();

    /**
     * One character with the reading it has <b>here</b>.
     *
     * <p>Strictly one syllable per character. An annotation never spans two
     * characters, so word-grouped pinyin is not expressible — that follows
     * from having no word segmentation, and is a decision rather than an
     * omission.</p>
     *
     * @param authored true when the reading came from a {@code {…}} override
     *                 rather than from the corpus, which is what a reviewer
     *                 needs to see in a diff
     */
    record Zi(ZiCharUTF8 zi, PinyinSyllable reading, boolean authored) implements Token {
        public Zi {
            Objects.requireNonNull(zi, "zi");
            Objects.requireNonNull(reading, "reading");
        }

        @Override public String text() { return zi.value(); }
    }

    /** Punctuation, spaces, Latin, digits — carried through, never annotated. */
    record Plain(String text) implements Token {
        public Plain {
            Objects.requireNonNull(text, "text");
            if (text.isEmpty()) {
                throw new IllegalArgumentException("an empty token is not a token");
            }
        }
    }
}
