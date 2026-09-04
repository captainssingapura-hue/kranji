package kranji.studio.design;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** CD-001 — the character as hub, and every dimension as a projection. */
public record CoreDesign001Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0005-0000-4001-8000-000000000001");
    public static final CoreDesign001Doc INSTANCE = new CoreDesign001Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "CD-001 — The Character as Hub"; }
    @Override public String summary() {
        return "ZiCharUTF8 is the identity everything joins on; every other dimension is a "
             + "projection over it, and a projection is a secondary index.";
    }
    @Override public String category(){ return "CORE DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
