package kranji.studio.reading.known;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Known Characters - Profiles and Storage */
public record ProfileStorageDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0007-0000-4001-8000-000000000004");
    public static final ProfileStorageDoc INSTANCE = new ProfileStorageDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Profiles and Storage"; }
    @Override public String summary() { return "IndexedDB on the device, export to a file the family owns, and several children per browser."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
