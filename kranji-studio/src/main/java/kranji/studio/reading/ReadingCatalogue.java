package kranji.studio.reading;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L1_Catalogue;
import kranji.studio.KranjiCatalogue;

import java.util.List;

/**
 * Section 4 — design for Kranji Reading, an adaptive Chinese reading app
 * for children.
 *
 * <p>Design and planning only. No implementation exists yet; these documents
 * are the specification being worked towards, and the accompanying plan
 * tracks execution.</p>
 */
public record ReadingCatalogue()
        implements L1_Catalogue<KranjiCatalogue, ReadingCatalogue>, DocProvider {

    public static final ReadingCatalogue INSTANCE = new ReadingCatalogue();

    @Override public KranjiCatalogue parent() { return KranjiCatalogue.INSTANCE; }
    @Override public String name()    { return "Reading"; }
    @Override public String summary() {
        return "An adaptive Chinese reading app for children. Pinyin appears only where the "
             + "reader needs it, and an unfamiliar character can explain its own structure. "
             + "Design documents and open questions.";
    }
    @Override public String badge()   { return "DESIGN"; }
    @Override public String icon()    { return "\uD83D\uDCD6"; }   // 📖

    @Override public List<Entry<ReadingCatalogue>> leaves() {
        return List.of(
                entry(ReadingOverviewDoc.INSTANCE),
                entry(ReadingDomainModelDoc.INSTANCE),
                entry(AdaptivePinyinDoc.INSTANCE),
                entry(KnownTrackingDoc.INSTANCE),
                entry(KnownZiManagementDoc.INSTANCE),
                entry(ArticleCatalogueDoc.INSTANCE),
                entry(ArticlePreparationDoc.INSTANCE),
                entry(ArticleFormatDoc.INSTANCE),
                entry(SimpleZiLayerDoc.INSTANCE),
                entry(ZiCatalogueDoc.INSTANCE),
                entry(ModuleStructureDoc.INSTANCE)
        );
    }

    private Entry<ReadingCatalogue> entry(Doc doc) {
        return Entry.of(this, DocReader.INSTANCE,
                new DocReader.Params(doc.uuid().toString()), doc);
    }

    @Override public List<Doc> docs() {
        return List.of(ReadingOverviewDoc.INSTANCE, ReadingDomainModelDoc.INSTANCE,
                AdaptivePinyinDoc.INSTANCE, KnownTrackingDoc.INSTANCE,
                KnownZiManagementDoc.INSTANCE, ArticleCatalogueDoc.INSTANCE,
                ArticlePreparationDoc.INSTANCE, ArticleFormatDoc.INSTANCE,
                SimpleZiLayerDoc.INSTANCE,
                ZiCatalogueDoc.INSTANCE,
                ModuleStructureDoc.INSTANCE);
    }
}
