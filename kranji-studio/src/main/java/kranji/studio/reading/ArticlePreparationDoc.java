package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Article Preparation */
public record ArticlePreparationDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000007");
    public static final ArticlePreparationDoc INSTANCE = new ArticlePreparationDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Article Preparation"; }
    @Override public String summary() { return "The polyphony problem, four candidate paths to correct in-context readings, and the validator that refuses to decide by silence."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
