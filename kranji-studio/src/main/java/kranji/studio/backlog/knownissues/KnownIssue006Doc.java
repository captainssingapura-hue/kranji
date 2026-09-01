package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-006 — Tone sandhi has no home. */
public record KnownIssue006Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000006");
    public static final KnownIssue006Doc INSTANCE = new KnownIssue006Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-006 — Tone sandhi has no home"; }
    @Override public String summary() { return "One reading whose tone shifts by context is deliberately outside the catalogue, and nothing downstream implements it yet."; }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
