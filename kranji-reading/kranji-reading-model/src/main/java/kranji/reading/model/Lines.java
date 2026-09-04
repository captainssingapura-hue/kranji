package kranji.reading.model;

import java.util.List;

/**
 * Writes a line of tokens back to the form it was authored in.
 *
 * <p>The counterpart to the parser, and the reason the wire and the source are
 * the same format. An article is authored as text, stored as text, and sent as
 * text; {@link Cells} turns that text into squares at the point it is drawn,
 * once, in the browser.</p>
 *
 * <h2>Why one format rather than two</h2>
 *
 * <p>A separate wire format would have to restate what the corpus already
 * knows — every character's reading, on every character, in every article — and
 * would then hold those readings frozen. Correcting a reading in the corpus
 * would leave every article that used the character serving the old one. Here
 * an article carries only what nothing else can supply: the text, and the
 * readings its author chose against the principal.</p>
 *
 * <p>Because the format is just text, it can be produced from anywhere — a
 * {@code .txt} file, a Java builder, a JSON document — and all of them agree by
 * construction rather than by convention.</p>
 */
public final class Lines {

    private Lines() {}

    /** The source line for these tokens. */
    public static String of(List<Token> tokens) {
        var sb = new StringBuilder();
        for (Token token : tokens) {
            if (token instanceof Token.Zi zi) {
                sb.append(zi.zi().value());
                // Only an authored reading is written. An unmarked character
                // takes the corpus principal, which is what leaves the corpus
                // free to be corrected.
                if (zi.authored()) {
                    sb.append('{').append(zi.reading().numbered()).append('}');
                }
            } else {
                sb.append(token.text());
            }
        }
        return sb.toString();
    }

    /** The source lines for a block, in order. */
    public static List<String> of(Block block) {
        return switch (block) {
            case Block.Paragraph p -> List.of(of(p.tokens()));
            case Block.Verse v -> v.lines().stream().map(Lines::of).toList();
            case Block.Illustration i -> List.of(of(i.caption()));
        };
    }
}
