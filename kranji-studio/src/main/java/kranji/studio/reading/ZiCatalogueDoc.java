package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.ClasspathMarkdownDoc;
import hue.captains.singapura.js.homing.studio.base.Reference;

import java.util.List;
import java.util.UUID;

/** The Zi catalogue - the partition as a navigable tree, and other projections. */
public record ZiCatalogueDoc() implements ClasspathMarkdownDoc {
    private static final UUID ID = UUID.fromString("4b1a0004-0000-4001-8000-00000000000a");
    public static final ZiCatalogueDoc INSTANCE = new ZiCatalogueDoc();

    @Override public UUID   uuid()    { return ID; }
    @Override public String title()   { return "The Zi Catalogue"; }
    @Override public String summary() {
        return "The phonic partition is already a tree - so browse it as one. Projections over "
             + "the same character set, and the boundary that keeps the model free of Homing.";
    }
    @Override public String category(){ return "DESIGN"; }
    @Override public List<Reference> references() { return List.of(); }
}
