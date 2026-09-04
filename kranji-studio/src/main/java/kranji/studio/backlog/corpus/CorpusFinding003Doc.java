package kranji.studio.backlog.corpus;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** CF-003 — No lookup from the hub to composition. */
public record CorpusFinding003Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a000a-0000-4001-8000-000000000003");
    public static final CorpusFinding003Doc INSTANCE = new CorpusFinding003Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "CF-003 — No lookup from the hub to composition"; }
    @Override public String summary() { return "The structural tier has no index by ZiCharUTF8, and its singulars arrive only through a mutable boot step."; }
    @Override public String category(){ return "FINDING"; }
    @Override public List<Reference> references() { return List.of(); }
}
