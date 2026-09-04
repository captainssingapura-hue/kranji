package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Reading - Article Library */
public record ArticleLibraryDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000012");
    public static final ArticleLibraryDoc INSTANCE = new ArticleLibraryDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Reading - Article Library"; }
    @Override public String summary() {
        return "Collections are atomic and globally identified, articles are local to one, "
             + "trees mount collections, and an SPI hands the reader a single tree - so "
             + "content ships independently of the binary.";
    }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
