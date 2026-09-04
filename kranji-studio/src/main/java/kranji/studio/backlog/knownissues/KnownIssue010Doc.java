package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** KI-010 — ArticleReadingsModule decides what pinyin a child sees, untested. */
public record KnownIssue010Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0006-0000-4001-8000-000000000010");
    public static final KnownIssue010Doc INSTANCE = new KnownIssue010Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "KI-010 — ArticleReadingsModule has no tests"; }
    @Override public String summary() {
        return "The module that decides which reading a child sees was built to be testable "
             + "under GraalVM and then shipped without any - its only cover is one manual "
             + "browser check.";
    }
    @Override public String category(){ return "KNOWN ISSUE"; }
    @Override public List<Reference> references() { return List.of(); }
}
