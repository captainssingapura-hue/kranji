package kranji.studio.design;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** CD-002 — meaning belongs to a reading, and is seeded from a source that does not know that. */
public record CoreDesign002Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0005-0000-4001-8000-000000000002");
    public static final CoreDesign002Doc INSTANCE = new CoreDesign002Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "CD-002 — Meaning Belongs to a Reading"; }
    @Override public String summary() {
        return "Meaning is keyed on (character, reading), seeded from Unihan's per-character "
             + "kDefinition and corrected by hand where that cardinality mismatch shows.";
    }
    @Override public String category(){ return "CORE DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
