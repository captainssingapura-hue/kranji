package kranji.studio.architecture;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Accepting markdown articles: segmentation, the subset, and what it costs. */
public record MarkdownArticlesDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0001-0000-4001-8000-000000000004");
    public static final MarkdownArticlesDoc INSTANCE = new MarkdownArticlesDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Markdown Articles"; }
    @Override public String summary() {
        return "A long document becomes a tree of segments rather than one article: where to "
             + "cut it, which markdown the reader accepts, and the two rules the design "
             + "collides with. Measured against one real 8,470-character document.";
    }
    @Override public String category(){ return "DOC"; }
    @Override public List<Reference> references() { return List.of(); }
}
