package kranji.reading.model;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.List;
import java.util.Objects;

/**
 * A piece of an article's structure.
 *
 * <p>Deliberately small. Adding a case is a decision, not a convenience, and a
 * sealed set is what lets the renderer handle every kind exhaustively.</p>
 */
public sealed interface Block extends ValueObject
        permits Block.Paragraph, Block.Verse, Block.Illustration {

    /** Prose. */
    record Paragraph(List<Token> tokens) implements Block {
        public Paragraph {
            tokens = List.copyOf(tokens);
            if (tokens.isEmpty()) {
                throw new IllegalArgumentException("an empty paragraph is noise");
            }
        }
    }

    /**
     * A poem or rhyme, kept as one block with its lines intact.
     *
     * <p>儿歌 and 古诗 are staples of what children read, and a poem is one
     * thing with four lines rather than four paragraphs. Flattening it loses
     * the grouping and the layout that goes with it.</p>
     */
    record Verse(List<List<Token>> lines) implements Block {
        public Verse {
            lines = lines.stream().map(List::copyOf).toList();
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("a verse with no lines is noise");
            }
            if (lines.stream().anyMatch(List::isEmpty)) {
                throw new IllegalArgumentException("a verse line must carry something");
            }
        }

        public int lineCount() { return lines.size(); }
    }

    /**
     * A picture between blocks, never inside one.
     *
     * @param alt     required — for a reader who cannot see the picture, which
     *                is a different job from a caption
     * @param caption optional, and annotated: a caption a child reads is
     *                Chinese, so it is tokens rather than a string
     */
    record Illustration(String file, String alt, List<Token> caption) implements Block {
        public Illustration {
            Objects.requireNonNull(file, "file");
            Objects.requireNonNull(alt, "alt");
            caption = List.copyOf(caption);
            if (file.isBlank()) {
                throw new IllegalArgumentException("an illustration needs a file");
            }
            if (alt.isBlank()) {
                throw new IllegalArgumentException(
                        "an illustration needs alt text: " + file);
            }
        }

        /** An illustration with no visible caption. */
        public static Illustration of(String file, String alt) {
            return new Illustration(file, alt, List.of());
        }
    }
}
