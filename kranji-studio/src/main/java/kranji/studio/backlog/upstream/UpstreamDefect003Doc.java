package kranji.studio.backlog.upstream;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** UD-003 — a pane's first click never reaches the widget. */
public record UpstreamDefect003Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0008-0000-4001-8000-000000000003");
    public static final UpstreamDefect003Doc INSTANCE = new UpstreamDefect003Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "UD-003 — a pane's first click never reaches the widget"; }
    @Override public String summary() {
        return "The first pointer event in an unfocused pane lands on the shell's own layer, "
             + "so a row must be clicked twice. A widget cannot fix it: a capturing listener "
             + "on its own element never sees the event.";
    }
    @Override public String category(){ return "UPSTREAM DEFECT"; }
    @Override public List<Reference> references() { return List.of(); }
}
