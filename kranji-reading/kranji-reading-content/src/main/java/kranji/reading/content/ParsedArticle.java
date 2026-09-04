package kranji.reading.content;

import kranji.reading.model.Article;

import java.util.List;
import java.util.Optional;

/**
 * The result of reading an article's source: what was understood, and
 * everything wrong with it.
 *
 * <p>The article is absent only when a finding made it unbuildable. Findings
 * can be present either way — a warning does not stop the article.</p>
 */
public record ParsedArticle(Optional<Article> article, List<ParseFinding> findings) {

    public ParsedArticle {
        findings = List.copyOf(findings);
    }

    public boolean ok() { return article.isPresent(); }

    public Article orThrow() {
        return article.orElseThrow(() -> new IllegalStateException(
                "article did not parse:\n  " + String.join("\n  ",
                        findings.stream().map(ParseFinding::toString).toList())));
    }
}
