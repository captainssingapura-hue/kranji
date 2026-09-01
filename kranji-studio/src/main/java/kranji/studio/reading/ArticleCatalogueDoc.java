package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** Article Catalogue and Readability */
public record ArticleCatalogueDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-000000000005");
    public static final ArticleCatalogueDoc INSTANCE = new ArticleCatalogueDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "Article Catalogue and Readability"; }
    @Override public String summary() { return "Organising articles by theme and level, and ranking them by how much of each one the reader can already read."; }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
