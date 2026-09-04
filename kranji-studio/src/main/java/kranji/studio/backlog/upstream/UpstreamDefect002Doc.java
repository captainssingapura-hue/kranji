package kranji.studio.backlog.upstream;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** UD-002 — a widget cannot offer a generated file for download. */
public record UpstreamDefect002Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0008-0000-4001-8000-000000000002");
    public static final UpstreamDefect002Doc INSTANCE = new UpstreamDefect002Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "UD-002 — no sanctioned way to offer a generated file"; }
    @Override public String summary() {
        return "no-raw-href forbids writing an anchor's href, and the href manager that would "
             + "be the sanctioned route is injected only into modules importing an AppLink.";
    }
    @Override public String category(){ return "UPSTREAM DEFECT"; }
    @Override public List<Reference> references() { return List.of(); }
}
