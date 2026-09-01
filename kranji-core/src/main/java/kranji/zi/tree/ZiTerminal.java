package kranji.zi.tree;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.zi.ZiCharUTF8;

import java.util.List;
import java.util.Objects;

/**
 * A terminal node — the finest grouping the projection distinguishes, holding
 * its characters as <b>content</b> rather than as further nodes.
 *
 * <p>This is the decision that keeps a projection small. If characters were
 * nodes, the tree would grow with the corpus; because they are content, the
 * tree's size is set by how many distinct keys the projection has. The phonic
 * projection terminates at a syllable, so its size is bounded by Mandarin
 * phonology — around 1,300 nodes whether the corpus holds two thousand
 * characters or twenty.</p>
 *
 * <p>It also means characters hold no positions. A character can be content of
 * a phonic terminal and of a radical terminal at once without occupying two
 * positions, so the one-position-per-navigable rule constrains only the
 * grouping nodes. A character's own detail view is a destination reached from
 * here, not a place in any tree.</p>
 */
public record ZiTerminal(String segment, String label, List<ZiCharUTF8> characters)
        implements ZiTreeNode, ValueObject {

    public ZiTerminal {
        Objects.requireNonNull(segment, "segment");
        Objects.requireNonNull(label, "label");
        characters = List.copyOf(Objects.requireNonNull(characters, "characters"));
        if (segment.isBlank()) {
            throw new IllegalArgumentException("segment must not be blank for '" + label + "'");
        }
        if (characters.isEmpty()) {
            throw new IllegalArgumentException(
                    "terminal '" + label + "' has no characters - an empty grouping is noise, "
                  + "so a projection should not emit it");
        }
        if (characters.size() != characters.stream().distinct().count()) {
            throw new IllegalArgumentException("terminal '" + label + "' repeats a character");
        }
    }

    @Override public int characterCount() { return characters.size(); }
}
