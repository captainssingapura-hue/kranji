package kranji.reading.model;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.zi.ZiCharUTF8;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * One piece a child reads: a title and a sequence of blocks.
 *
 * <p>Content only. What a reader knows lives in a profile, and the two meet
 * at render time — which is what lets one article serve every reader.</p>
 */
public record Article(ArticleId id, String title, List<Block> blocks) implements ValueObject {

    public Article {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(title, "title");
        blocks = List.copyOf(blocks);
        if (title.isBlank()) {
            throw new IllegalArgumentException("an article needs a title: " + id);
        }
        if (blocks.isEmpty()) {
            throw new IllegalArgumentException("an article with no blocks: " + id);
        }
    }

    /** Every character token, in reading order — the same character may recur. */
    public List<Token.Zi> characters() {
        var out = new java.util.ArrayList<Token.Zi>();
        for (Block block : blocks) collect(block, out);
        return List.copyOf(out);
    }

    /** The distinct characters this article asks a reader to know. */
    public Set<ZiCharUTF8> distinctCharacters() {
        Set<ZiCharUTF8> out = new LinkedHashSet<>();
        for (Token.Zi zi : characters()) out.add(zi.zi());
        return Set.copyOf(out);
    }

    /** How long the article is, counted in characters rather than tokens. */
    public int length() { return characters().size(); }

    private static void collect(Block block, List<Token.Zi> out) {
        switch (block) {
            case Block.Paragraph p -> addAll(p.tokens(), out);
            case Block.Verse v -> v.lines().forEach(line -> addAll(line, out));
            case Block.Illustration i -> addAll(i.caption(), out);
        }
    }

    private static void addAll(List<Token> tokens, List<Token.Zi> out) {
        for (Token token : tokens) {
            if (token instanceof Token.Zi zi) out.add(zi);
        }
    }
}
