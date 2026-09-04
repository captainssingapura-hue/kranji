package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-003 — A badge counts appearances but says characters. */
public record KnownIssue003Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000003");
    public static final KnownIssue003Doc INSTANCE = new KnownIssue003Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-003 — A badge counts appearances but says characters"; }
    @Override public String summary() { return "The phonic tree labels an appearance count as a character count, so a polyphonic character is counted once per reading."; }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
