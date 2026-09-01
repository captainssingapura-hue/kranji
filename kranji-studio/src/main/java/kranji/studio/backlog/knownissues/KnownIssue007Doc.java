package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-007 — One principal reading per character strains at 得. */
public record KnownIssue007Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000007");
    public static final KnownIssue007Doc INSTANCE = new KnownIssue007Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-007 — One principal reading per character strains at 得"; }
    @Override public String summary() { return "A character with three common readings has no comfortable principal, which tests whether the model is right."; }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
