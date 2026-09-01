package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Reading - Domain Model */
public record ReadingDomainModelDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000002");
    public static final ReadingDomainModelDoc INSTANCE = new ReadingDomainModelDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Reading - Domain Model"; }
    @Override public String summary() { return "Blocks and tokens as two small sealed sums, the learner profile beside them, and why per-occurrence readings matter more than they look."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
