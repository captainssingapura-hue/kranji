package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-005 — The syllable data action rescans the whole registry. */
public record KnownIssue005Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000005");
    public static final KnownIssue005Doc INSTANCE = new KnownIssue005Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-005 — The syllable data action rescans the whole registry"; }
    @Override public String summary() { return "Every request walks the entire corpus to find one syllable's characters, which is free at 79 characters and not at 8,100."; }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
