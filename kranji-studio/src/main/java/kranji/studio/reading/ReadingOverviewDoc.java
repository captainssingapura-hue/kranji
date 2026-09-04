package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Reading - Overview */
public record ReadingOverviewDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000001");
    public static final ReadingOverviewDoc INSTANCE = new ReadingOverviewDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Reading - Overview"; }
    @Override public String summary() { return "What the app is, who it is for, and the one capability that needs a structural corpus to work."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
