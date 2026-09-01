package kranji.studio.architecture;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Two-stage layout (blocks then SVG) and the JSON-first codegen pipeline. */
public record LayoutPipelineDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0001-0000-4001-8000-000000000002");
    public static final LayoutPipelineDoc INSTANCE = new LayoutPipelineDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Layout & Codegen Pipeline"; }
    @Override public String summary() {
        return "Politeness-driven block layout, SVG rendering, and the JSON catalogue that "
             + "generates every typed record mechanically.";
    }
    @Override public String category(){ return "DOC"; }
    @Override public List<Reference> references() { return List.of(); }
}
