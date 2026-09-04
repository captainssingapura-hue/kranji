package kranji.studio;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L0_Catalogue;
import hue.captains.singapura.js.homing.studio.base.app.L1_Catalogue;
import hue.captains.singapura.js.homing.studio.base.app.Navigable;
import hue.captains.singapura.js.homing.workspace.shell.GenericWorkspace;
import kranji.studio.architecture.ArchitectureCatalogue;
import kranji.studio.corpus.CorpusCatalogue;
import kranji.studio.design.CoreDesignCatalogue;
import kranji.studio.explorations.ExplorationsCatalogue;
import kranji.studio.backlog.BacklogCatalogue;
import kranji.studio.plans.PlansCatalogue;
import kranji.studio.reading.ReadingCatalogue;

import java.util.List;

/**
 * L0 root of Kranji Studio. Seven sections, each a focused L1:
 *
 * <ol>
 *   <li><b>Core Design</b> — numbered decisions about the shape of the system.</li>
 *   <li><b>Architecture</b> — the type system, the layout/codegen pipeline, and
 *       the gloss tier.</li>
 *   <li><b>Corpus</b> — what the catalogue actually contains, and how it grows.</li>
 *   <li><b>Explorations</b> — forward-looking work generalising Kranji's
 *       typed-composition pattern to other domains.</li>
 *   <li><b>Reading</b> — the reading app's domain and its decisions.</li>
 *   <li><b>Plans</b> — in-flight work, tracked with the plan kit.</li>
 *   <li><b>Backlog</b> — findings, known issues, and enhancements not yet scheduled.</li>
 * </ol>
 *
 * <p>Alongside them sits the <b>Gloss Workbench</b>, a leaf rather than a
 * section: the first tool here that reads the model instead of describing it.
 * Sections hold writing; the workbench holds instruments.</p>
 */
public record KranjiCatalogue()
        implements L0_Catalogue<KranjiCatalogue>, DocProvider {

    public static final KranjiCatalogue INSTANCE = new KranjiCatalogue();

    @Override public String name()    { return "Kranji · Studio"; }
    @Override public String summary() {
        return "Internal tooling for building Kranji — a Java 21 library that models Chinese "
             + "characters as typed composition trees. Design notes, corpus reports, plans, and "
             + "workbench tools that read the model so it can be checked.";
    }
    @Override public String badge()   { return "STUDIO"; }
    @Override public String icon()    { return "\u6797"; }   // 林

    @Override public List<Entry<KranjiCatalogue>> leaves() {
        Navigable<GenericWorkspace.Params, GenericWorkspace> workbench =
                new Navigable<>(GenericWorkspace.INSTANCE,
                        new GenericWorkspace.Params(GlossWorkspaceSpec.KIND),
                        "Gloss Workbench",
                        "The gloss tier as relations - one grid per relation, several at once.");
        return List.of(
                Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(KranjiIntroDoc.INSTANCE.uuid().toString()),
                        KranjiIntroDoc.INSTANCE),
                Entry.of(this, workbench)
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
