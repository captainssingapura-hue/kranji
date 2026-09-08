package kranji.studio.architecture;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** The markdown subset an article may use, and the four extensions on top of it. */
public record MarkdownSubsetDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0001-0000-4001-8000-000000000005");
    public static final MarkdownSubsetDoc INSTANCE = new MarkdownSubsetDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "The Markdown Kranji Reads"; }
    @Override public String summary() {
        return "Every construct an article may use, with a verdict on each, and the four "
             + "extensions the reader needs that markdown cannot say: a phonic override, a "
             + "pinned segment id, a plain run, and a verse fence.";
    }
    @Override public String category(){ return "DOC"; }
    @Override public List<Reference> references() { return List.of(); }
}
