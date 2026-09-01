package kranji.studio.reading.known;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Known Characters - Marking a Character Known */
public record MarkingDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0007-0000-4001-8000-000000000002");
    public static final MarkingDoc INSTANCE = new MarkingDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Marking a Character Known"; }
    @Override public String summary() { return "Article to Zi Details to Add - deliberate because it costs a look at the character first."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
