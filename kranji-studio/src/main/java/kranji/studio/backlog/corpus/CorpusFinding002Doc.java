package kranji.studio.backlog.corpus;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** CF-002 — Meaning sits at the wrong cardinality. */
public record CorpusFinding002Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a000a-0000-4001-8000-000000000002");
    public static final CorpusFinding002Doc INSTANCE = new CorpusFinding002Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "CF-002 — Meaning sits at the wrong cardinality"; }
    @Override public String summary() { return "CD-001 is applied to the phonic tier and not the structural one - 801 empty glosses are the evidence, not the problem."; }
    @Override public String category(){ return "FINDING"; }
    @Override public List<Reference> references() { return List.of(); }
}
