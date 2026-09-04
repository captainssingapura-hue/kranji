package kranji.studio.backlog.corpus;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** CF-001 — What the corpus holds. */
public record CorpusFinding001Doc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a000a-0000-4001-8000-000000000001");
    public static final CorpusFinding001Doc INSTANCE = new CorpusFinding001Doc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "CF-001 — What the corpus holds"; }
    @Override public String summary() { return "Four tiers, their sizes, and how much of a bundled article each can actually speak to."; }
    @Override public String category(){ return "FINDING"; }
    @Override public List<Reference> references() { return List.of(); }
}
