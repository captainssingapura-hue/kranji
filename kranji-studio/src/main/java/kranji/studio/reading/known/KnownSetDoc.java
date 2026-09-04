package kranji.studio.reading.known;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Known Characters - The Known Set */
public record KnownSetDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0007-0000-4001-8000-000000000001");
    public static final KnownSetDoc INSTANCE = new KnownSetDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "The Known Set"; }
    @Override public String summary() { return "Keyed on (character, reading), not the character - one set, no states, no timestamps."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
