package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Known Zi Management */
public record KnownZiManagementDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000006");
    public static final KnownZiManagementDoc INSTANCE = new KnownZiManagementDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Known Zi Management"; }
    @Override public String summary() { return "The known set as a place rather than a setting - channels, provenance, bulk operations, and why every mark must be reversible."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
