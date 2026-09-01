package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Adaptive Pinyin */
public record AdaptivePinyinDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000003");
    public static final AdaptivePinyinDoc INSTANCE = new AdaptivePinyinDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Adaptive Pinyin"; }
    @Override public String summary() { return "Four display modes, the rule that decides whether a character gets an annotation, and the composition hint that replaces a dictionary gloss."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
