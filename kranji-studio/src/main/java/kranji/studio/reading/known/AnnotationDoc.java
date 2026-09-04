package kranji.studio.reading.known;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Known Characters - Annotation from the Known Set */
public record AnnotationDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0007-0000-4001-8000-000000000005");
    public static final AnnotationDoc INSTANCE = new AnnotationDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Annotation from the Known Set"; }
    @Override public String summary() { return "Pinyin appears for what is not in the set. That is the whole rule."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
