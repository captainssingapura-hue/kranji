package kranji.studio.backlog.enhancements;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** EN-001 — row width declared by the content, not only by the pane. */
public record Enhancement001Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0009-0000-4001-8000-000000000001");
    public static final Enhancement001Doc INSTANCE = new Enhancement001Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "EN-001 — Row width per collection, overridable per article"; }
    @Override public String summary() {
        return "How many squares a line holds is currently only a function of pane width. "
             + "The form should get a say, and a particular article should be able to "
             + "overrule the form.";
    }
    @Override public String category(){ return "ENHANCEMENT"; }
    @Override public List<Reference> references() { return List.of(); }
}
