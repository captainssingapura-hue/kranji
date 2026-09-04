package kranji.simple.gloss;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.Objects;

/**
 * A reference to one sense of one phrase.
 *
 * <p>A phrase can mean more than one thing, and the senses need not even share
 * a reading: 东西 is <i>east and west</i> read dōngxī and <i>a thing</i> read
 * dōngxi. So a gloss cannot point at a phrase - it has to point at a sense of
 * one, or it is asserting whichever meaning happened to be written first.</p>
 *
 * <p>Sense 0 is the default and, by the same convention as everywhere else in
 * this tier, the commonest. {@code eg("银行")} means sense 0 of 银行, which is
 * the only one it has.</p>
 */
public record EgRef(EgKey phrase, int sense) implements ValueObject {

    public EgRef {
        Objects.requireNonNull(phrase, "phrase");
        if (sense < 0) {
            throw new IllegalArgumentException(
                    phrase + " referenced at sense " + sense + "; senses start at 0");
        }
    }

    /** The commonest sense of a phrase - what a bare reference means. */
    public static EgRef to(String phrase) { return new EgRef(EgKey.of(phrase), 0); }

    /** A particular sense of a phrase. */
    public static EgRef to(String phrase, int sense) {
        return new EgRef(EgKey.of(phrase), sense);
    }

    @Override public String toString() {
        return sense == 0 ? phrase.toString() : phrase + "[" + sense + "]";
    }
}
