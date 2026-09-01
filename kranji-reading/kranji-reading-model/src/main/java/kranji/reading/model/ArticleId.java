package kranji.reading.model;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.Objects;

/**
 * An article's identity — a slug, safe to put in an address.
 *
 * <p>Same charset as a tree segment, for the same reason: it travels in URLs
 * and in module names, and anything that needs escaping there will eventually
 * be escaped inconsistently.</p>
 */
public record ArticleId(String value) implements ValueObject {

    public ArticleId {
        Objects.requireNonNull(value, "value");
        if (!value.matches("[a-z0-9][a-z0-9-]{0,63}")) {
            throw new IllegalArgumentException(
                    "an article id is lower-case letters, digits and hyphens: '" + value + "'");
        }
    }

    @Override public String toString() { return value; }
}
