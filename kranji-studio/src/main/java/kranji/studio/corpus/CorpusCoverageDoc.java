package kranji.studio.corpus;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Measured coverage of the Zi catalogue, counted off the built registry. */
public record CorpusCoverageDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0002-0000-4001-8000-000000000001");
    public static final CorpusCoverageDoc INSTANCE = new CorpusCoverageDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Corpus Coverage"; }
    @Override public String summary() {
        return "2,532 distinct Zi across singular and composed populations, counted at runtime "
             + "off the registry rather than inferred from file counts.";
    }
    @Override public String category(){ return "REPORT"; }
    @Override public List<Reference> references() { return List.of(); }
}
