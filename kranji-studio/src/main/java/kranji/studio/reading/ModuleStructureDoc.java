package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Module Structure */
public record ModuleStructureDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000008");
    public static final ModuleStructureDoc INSTANCE = new ModuleStructureDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Module Structure"; }
    @Override public String summary() { return "The nested multi-module tree, what each module may depend on, and the crate boundary."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
