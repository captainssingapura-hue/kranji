package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** The simple Zi layer - broad phonic and meaning coverage inside the core. */
public record SimpleZiLayerDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000009");
    public static final SimpleZiLayerDoc INSTANCE = new SimpleZiLayerDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "The Simple Zi Layer"; }
    @Override public String summary() {
        return "A simple record carrying a default phonic, additional phonics, and a meaning - "
             + "living in the core beside the structure-driven Zi family and composing with it.";
    }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
