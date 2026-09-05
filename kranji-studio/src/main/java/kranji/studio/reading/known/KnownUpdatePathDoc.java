package kranji.studio.reading.known;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Known Characters - The Update Path */
public record KnownUpdatePathDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0007-0000-4001-8000-000000000006");
    public static final KnownUpdatePathDoc INSTANCE = new KnownUpdatePathDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "The Update Path"; }
    @Override public String summary() { return "What happens between a tap and the screen agreeing - and why it costs 43ms today."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
