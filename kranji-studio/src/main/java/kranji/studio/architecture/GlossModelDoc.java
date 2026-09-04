package kranji.studio.architecture;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** The gloss tier's shape: Zi, Sound, Sense — and the pair key they hang on. */
public record GlossModelDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0001-0000-4001-8000-000000000003");
    public static final GlossModelDoc INSTANCE = new GlossModelDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Gloss Data Model"; }
    @Override public String summary() {
        return "Three levels on natural composite keys - a character, a reading, a meaning. "
             + "The (codepoint, reading) pair is the grain, and the same key joins the article "
             + "census and a reader's known set.";
    }
    @Override public String category(){ return "DOC"; }
    @Override public List<Reference> references() { return List.of(); }
}
