package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Reading - Article Format */
public record ArticleFormatDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000011");
    public static final ArticleFormatDoc INSTANCE = new ArticleFormatDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Reading - Article Format"; }
    @Override public String summary() {
        return "Polymorphic input, one model, one wire format - and the inline layer every "
             + "input format shares.";
    }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
