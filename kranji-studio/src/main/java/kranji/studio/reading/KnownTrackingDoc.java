package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Known Characters and Review */
public record KnownTrackingDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000004");
    public static final KnownTrackingDoc INSTANCE = new KnownTrackingDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Known Characters and Review"; }
    @Override public String summary() { return "How a character moves between unseen, learning, and known - and why the learner profile never leaves the device."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
