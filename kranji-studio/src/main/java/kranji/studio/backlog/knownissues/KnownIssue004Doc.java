package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-004 — An unaudited gap in the typed corpus. */
public record KnownIssue004Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000004");
    public static final KnownIssue004Doc INSTANCE = new KnownIssue004Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-004 — An unaudited gap in the typed corpus"; }
    @Override public String summary() { return "The library loads 2,532 distinct glyphs but the modules hold 2,855 distinct glyph literals - roughly 320 are unaccounted for."; }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
