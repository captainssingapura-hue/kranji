package kranji.studio.architecture;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L1_Catalogue;
import kranji.studio.KranjiCatalogue;

import java.util.List;

/** Section 1 - the type system, the pipeline that turns it into artifacts, and the gloss tier. */
public record ArchitectureCatalogue()
        implements L1_Catalogue<KranjiCatalogue, ArchitectureCatalogue>, DocProvider {

    public static final ArchitectureCatalogue INSTANCE = new ArchitectureCatalogue();

    @Override public KranjiCatalogue parent() { return KranjiCatalogue.INSTANCE; }
    @Override public String name()    { return "Architecture"; }
    @Override public String summary() {
        return "The sealed type hierarchy that models character composition, the two-stage "
             + "layout pipeline plus JSON-first codegen that turn it into rendered glyphs and "
             + "generated source, and the gloss tier that keys meaning on a reading.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "\uD83E\uDDEC"; }   // 🧬

    @Override public List<Entry<ArchitectureCatalogue>> leaves() {
        return List.of(
                Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(TypeHierarchyDoc.INSTANCE.uuid().toString()),
                        TypeHierarchyDoc.INSTANCE),
                Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(LayoutPipelineDoc.INSTANCE.uuid().toString()),
                        LayoutPipelineDoc.INSTANCE),
                Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(GlossModelDoc.INSTANCE.uuid().toString()),
                        GlossModelDoc.INSTANCE)
        );
    }

    @Override public List<Doc> docs() {
        return List.of(TypeHierarchyDoc.INSTANCE, LayoutPipelineDoc.INSTANCE,
                       GlossModelDoc.INSTANCE);
    }
}
