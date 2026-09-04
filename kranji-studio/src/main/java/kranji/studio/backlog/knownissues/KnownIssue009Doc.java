package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-009 — Widget JavaScript is a Java string, and nothing checks it until it loads. */
public record KnownIssue009Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000009");
    public static final KnownIssue009Doc INSTANCE = new KnownIssue009Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-009 — Widget JavaScript is unchecked until it runs"; }
    @Override public String summary() {
        return "A widget's behaviour is a List<String> in Java, so neither the compiler nor "
             + "conformance sees inside it - a broken edit reaches the browser intact.";
    }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
