package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-002 — The served tree payload grows with the corpus. */
public record KnownIssue002Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000002");
    public static final KnownIssue002Doc INSTANCE = new KnownIssue002Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-002 — The served tree payload grows with the corpus"; }
    @Override public String summary() { return "21 KB at 77 terminals extrapolates to roughly 350 KB at full coverage, because every terminal lists every glyph it holds."; }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
