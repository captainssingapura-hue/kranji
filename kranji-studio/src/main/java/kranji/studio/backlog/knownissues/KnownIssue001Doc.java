package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-001 — Unihan provenance and build reproducibility. */
public record KnownIssue001Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000001");
    public static final KnownIssue001Doc INSTANCE = new KnownIssue001Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-001 — Unihan provenance and build reproducibility"; }
    @Override public String summary() { return "The source file is untracked while data derived from it is committed - so the build cannot be reproduced and a redistribution has happened unexamined."; }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
