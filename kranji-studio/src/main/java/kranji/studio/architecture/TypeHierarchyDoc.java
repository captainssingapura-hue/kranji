package kranji.studio.architecture;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** The sealed-interface type system: SingularZi, ComposedZi, SingularPart. */
public record TypeHierarchyDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0001-0000-4001-8000-000000000001");
    public static final TypeHierarchyDoc INSTANCE = new TypeHierarchyDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Type Hierarchy"; }
    @Override public String summary() {
        return "Every character is a typed tree. Sealed interfaces plus records give exhaustive "
             + "handling of all composition variants and etymology categories at compile time.";
    }
    @Override public String category(){ return "DOC"; }
    @Override public List<Reference> references() { return List.of(); }
}
