package kranji.studio.reading.known;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Known Characters - Removing a Character */
public record RemovalDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0007-0000-4001-8000-000000000003");
    public static final RemovalDoc INSTANCE = new RemovalDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Removing a Character"; }
    @Override public String summary() { return "Give one reading back, never a character - and take a whole optimistic import back at once."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
