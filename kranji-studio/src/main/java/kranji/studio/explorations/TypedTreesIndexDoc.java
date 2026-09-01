package kranji.studio.explorations;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Index of the typed-composition-tree exploration thread. */
public record TypedTreesIndexDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0003-0000-4001-8000-000000000001");
    public static final TypedTreesIndexDoc INSTANCE = new TypedTreesIndexDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Typed Composition Trees — Index"; }
    @Override public String summary() {
        return "The catalogue of domain studies, the shared template every write-up follows, "
             + "and the hygiene rules that keep the explorations out of Kranji proper.";
    }
    @Override public String category(){ return "DOC"; }
    @Override public List<Reference> references() { return List.of(); }
}
