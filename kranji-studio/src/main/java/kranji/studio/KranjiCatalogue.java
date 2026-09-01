package kranji.studio;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L0_Catalogue;
import hue.captains.singapura.js.homing.studio.base.app.L1_Catalogue;
import kranji.studio.architecture.ArchitectureCatalogue;
import kranji.studio.corpus.CorpusCatalogue;
import kranji.studio.design.CoreDesignCatalogue;
import kranji.studio.explorations.ExplorationsCatalogue;
import kranji.studio.backlog.BacklogCatalogue;
import kranji.studio.plans.PlansCatalogue;
import kranji.studio.reading.ReadingCatalogue;

import java.util.List;

/**
 * L0 root of Kranji Studio. Three sections, each a focused L1:
 *
 * <ol>
 *   <li><b>Architecture</b> — the type system and the layout/codegen pipeline.</li>
 *   <li><b>Corpus</b> — what the catalogue actually contains, and how it grows.</li>
 *   <li><b>Explorations</b> — forward-looking work generalising Kranji's
 *       typed-composition pattern to other domains.</li>
 * </ol>
 *
 * <p>In-flight work is tracked separately as {@code Plan}s, registered on
 * {@link KranjiStudio#plans()} and served by the framework's shared plan host.</p>
 */
public record KranjiCatalogue()
        implements L0_Catalogue<KranjiCatalogue>, DocProvider {

    public static final KranjiCatalogue INSTANCE = new KranjiCatalogue();

    @Override public String name()    { return "Kranji · Studio"; }
    @Override public String summary() {
        return "Documentation and planning for Kranji — a Java 21 library that models Chinese "
             + "characters as typed composition trees. Architecture notes, corpus reports, and "
             + "explorations generalising the pattern to other domains.";
    }
    @Override public String badge()   { return "STUDIO"; }
    @Override public String icon()    { return "\u6797"; }   // 林

    @Override public List<Entry<KranjiCatalogue>> leaves() {
        return List.of(
                Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(KranjiIntroDoc.INSTANCE.uuid().toString()),
                        KranjiIntroDoc.INSTANCE)
        );
    }

    @Override public List<? extends L1_Catalogue<KranjiCatalogue, ?>> subCatalogues() {
        return List.of(
                CoreDesignCatalogue.INSTANCE,
                ArchitectureCatalogue.INSTANCE,
                CorpusCatalogue.INSTANCE,
                ExplorationsCatalogue.INSTANCE,
                ReadingCatalogue.INSTANCE,
                PlansCatalogue.INSTANCE,
                BacklogCatalogue.INSTANCE
        );
    }

    @Override public List<Doc> docs() {
        return List.of(KranjiIntroDoc.INSTANCE);
    }
}
