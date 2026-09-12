package kranji.studio;

import hue.captains.singapura.js.homing.core.Crate;
import hue.captains.singapura.js.homing.core.CrateEntry;
import hue.captains.singapura.js.homing.grid.RelationGridCrate;
import kranji.studio.gloss.CuratedEntityWidget;
import kranji.studio.gloss.CoverageEntityWidget;
import kranji.studio.gloss.CoverageMissingEntityWidget;
import kranji.studio.gloss.CoverageShelfEntityWidget;
import kranji.studio.gloss.DemandEntityWidget;
import kranji.studio.gloss.ImpactArticleEntityWidget;
import kranji.studio.gloss.ImpactEntityWidget;
import kranji.studio.gloss.IssueEntityWidget;
import kranji.studio.gloss.PartitionEntityWidget;
import kranji.studio.gloss.GlossCss;
import kranji.studio.gloss.GlossSelectionSecretaryModule;
import kranji.studio.gloss.PhraseEntityWidget;
import kranji.studio.gloss.ProblemEntityWidget;
import kranji.studio.gloss.PhraseSenseEntityWidget;
import kranji.studio.gloss.SenseEntityWidget;
import kranji.studio.gloss.SoundEntityWidget;
import kranji.studio.articles.ArticleDraftWidget;
import kranji.studio.articles.ArticleNavigatorWidget;
import kranji.studio.articles.ArticleRootsWidget;
import kranji.studio.articles.ArticleShelfSecretaryModule;
import kranji.studio.articles.ArticleWorkbenchCss;
import kranji.studio.articles.MdPreviewModule;
import kranji.studio.articles.MdSegmentsModule;
import kranji.studio.articles.SectionReaderWidget;
import kranji.studio.articles.KmdBoardModule;
import kranji.studio.articles.MdSquaresModule;

import java.util.List;

/**
 * What {@code kranji-studio} ships to a browser.
 *
 * <p>The studio was a pure doc-and-plan studio and is no longer one: it now
 * carries internal tools that read the gloss tier. The crate exists so that
 * stays declared rather than incidental — {@code OrphanCheck} scans this Maven
 * module for served modules and fails on any not listed here.</p>
 */
public final class KranjiStudioCrate implements Crate {

    public static final KranjiStudioCrate INSTANCE = new KranjiStudioCrate();

    private KranjiStudioCrate() {}

    @Override
    public String name() { return "kranji-studio"; }

    @Override
    public List<CrateEntry> entries() {
        return List.of(
                CrateEntry.of(GlossCss.INSTANCE),
                CrateEntry.of(GlossSelectionSecretaryModule.INSTANCE),
                CrateEntry.of(SoundEntityWidget.INSTANCE),
                CrateEntry.of(DemandEntityWidget.INSTANCE),
                CrateEntry.of(SenseEntityWidget.INSTANCE),
                CrateEntry.of(PhraseEntityWidget.INSTANCE),
                CrateEntry.of(PhraseSenseEntityWidget.INSTANCE),
                CrateEntry.of(ProblemEntityWidget.INSTANCE),
                CrateEntry.of(PartitionEntityWidget.INSTANCE),
                CrateEntry.of(IssueEntityWidget.INSTANCE),
                CrateEntry.of(CuratedEntityWidget.INSTANCE),
                CrateEntry.of(ImpactEntityWidget.INSTANCE),
                CrateEntry.of(ImpactArticleEntityWidget.INSTANCE),
                CrateEntry.of(CoverageShelfEntityWidget.INSTANCE),
                CrateEntry.of(CoverageEntityWidget.INSTANCE),
                CrateEntry.of(CoverageMissingEntityWidget.INSTANCE),

                CrateEntry.of(ArticleWorkbenchCss.INSTANCE),
                CrateEntry.of(MdPreviewModule.INSTANCE),
                CrateEntry.of(MdSquaresModule.INSTANCE),
                CrateEntry.of(KmdBoardModule.INSTANCE),
                CrateEntry.of(MdSegmentsModule.INSTANCE),
                CrateEntry.of(ArticleShelfSecretaryModule.INSTANCE),
                CrateEntry.of(ArticleRootsWidget.INSTANCE),
                CrateEntry.of(ArticleNavigatorWidget.INSTANCE),
                CrateEntry.of(ArticleDraftWidget.INSTANCE),
                CrateEntry.of(SectionReaderWidget.INSTANCE));
    }

    @Override
    public List<Crate> requires() {
        return List.of(RelationGridCrate.INSTANCE);
    }
}
