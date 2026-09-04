package kranji.zi.tree;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.List;
import java.util.Objects;

/**
 * An interior node — a grouping key of the projection.
 *
 * <p>In index terms this is a key and its posting list: the branch for
 * {@code h} holds every character whose default reading begins with h-.</p>
 */
public record ZiBranch(String segment, String label, List<ZiTreeNode> children)
        implements ZiTreeNode, ValueObject {

    public ZiBranch {
        Objects.requireNonNull(segment, "segment");
        Objects.requireNonNull(label, "label");
        children = List.copyOf(Objects.requireNonNull(children, "children"));
        if (segment.isBlank()) {
            throw new IllegalArgumentException("segment must not be blank for '" + label + "'");
        }
        long distinct = children.stream().map(ZiTreeNode::segment).distinct().count();
        if (distinct != children.size()) {
            throw new IllegalArgumentException(
                    "sibling segments must be unique under '" + segment + "'");
        }
    }

    @Override
    public int characterCount() {
        return children.stream().mapToInt(ZiTreeNode::characterCount).sum();
    }
}
