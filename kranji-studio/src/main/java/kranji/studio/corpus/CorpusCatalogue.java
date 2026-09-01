package kranji.studio.corpus;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L1_Catalogue;
import kranji.studio.KranjiCatalogue;

import java.util.List;

/** Section 2 — what the catalogue contains today and how it grows. */
public record CorpusCatalogue()
        implements L1_Catalogue<KranjiCatalogue, CorpusCatalogue>, DocProvider {

    public static final CorpusCatalogue INSTANCE = new CorpusCatalogue();

    @Override public KranjiCatalogue parent() { return KranjiCatalogue.INSTANCE; }
    @Override public String name()    { return "Corpus"; }
    @Override public String summary() {
        return "Coverage as measured off the built registry, the three-stage lifecycle every "
             + "record passes through, and the workflow for adding new characters.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "\uD83D\uDCDA"; }   // 📚

    @Override public List<Entry<CorpusCatalogue>> leaves() {
        return List.of(
                Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(CorpusCoverageDoc.INSTANCE.uuid().toString()),
                        CorpusCoverageDoc.INSTANCE)
        );
    }

    @Override public List<Doc> docs() {
        return List.of(CorpusCoverageDoc.INSTANCE);
    }
}
