package kranji.studio;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Landing page for Kranji Studio — what the project is and how to navigate. */
public record KranjiIntroDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0000-0000-4001-8000-000000000001");
    public static final KranjiIntroDoc INSTANCE = new KranjiIntroDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Kranji — Read Me First"; }
    @Override public String summary() {
        return "What Kranji is, the one idea it is built on, and how this studio is organised.";
    }
    @Override public String category(){ return "DOC"; }
    @Override public List<Reference> references() { return List.of(); }
}
