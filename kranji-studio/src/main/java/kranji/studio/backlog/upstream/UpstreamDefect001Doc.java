package kranji.studio.backlog.upstream;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** UD-001 — RelationGrid's cell borders cannot be turned off. */
public record UpstreamDefect001Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0008-0000-4001-8000-000000000001");
    public static final UpstreamDefect001Doc INSTANCE = new UpstreamDefect001Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "UD-001 — RelationGrid cell borders cannot be turned off"; }
    @Override public String summary() {
        return "The grid hardcodes a border on every td and injects its stylesheet unlayered, "
             + "so a consumer that is not a data table can only override it with !important.";
    }
    @Override public String category(){ return "UPSTREAM DEFECT"; }
    @Override public List<Reference> references() { return List.of(); }
}
