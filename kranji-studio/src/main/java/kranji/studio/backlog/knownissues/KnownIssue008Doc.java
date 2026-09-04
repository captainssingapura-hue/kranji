package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-008 — NodeName.slug collapses any CJK label to n. */
public record KnownIssue008Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000008");
    public static final KnownIssue008Doc INSTANCE = new KnownIssue008Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-008 — NodeName.slug collapses any CJK label to n"; }
    @Override public String summary() { return "The framework's slug helper strips non-ASCII, so every Chinese label produces the same name and they collide."; }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
