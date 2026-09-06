package kranji.reading.app;

import hue.captains.singapura.js.homing.core.Crate;
import hue.captains.singapura.js.homing.core.CrateEntry;
import hue.captains.singapura.js.homing.core.js.CoreJsCrate;
import hue.captains.singapura.js.homing.grid.RelationGridCrate;
import kranji.reading.app.css.ReadingCss;
import kranji.reading.app.phonic.PhonicSourceWidget;
import kranji.reading.app.read.ArticleCatalogueWidget;
import kranji.reading.app.read.ArticleReaderWidget;
import kranji.reading.app.read.ArticleReadingsModule;
import kranji.reading.app.read.ReaderCellStyleModule;
import kranji.reading.app.read.ArticleSelectionSecretaryModule;
import kranji.reading.app.known.KnownSecretaryModule;
import kranji.reading.app.known.KnownProgressModule;
import kranji.reading.app.known.KnownProgressWidget;
import kranji.reading.app.known.KnownSoundsModule;
import kranji.reading.app.known.KnownSoundsWidget;
import kranji.reading.app.known.KnownZiWidget;
import kranji.reading.app.known.KnownSetModule;
import kranji.reading.app.known.KnownWatchModule;
import kranji.reading.app.known.KnownStoreModule;
import kranji.reading.app.known.KnownPersistenceModule;
import kranji.reading.app.known.KnownImportControlsModule;
import kranji.reading.app.known.KnownTransferModule;
import kranji.reading.app.known.KnownTransferWidget;
import kranji.reading.app.read.ArticleScannerModule;
import kranji.reading.app.read.ArticleCensusModule;
import kranji.reading.app.read.ReadabilityModule;
import kranji.reading.app.read.ArticleBoardModule;
import kranji.reading.app.ui.GlyphMetricsModule;
import kranji.reading.app.ui.ReaderControlsModule;
import kranji.reading.app.ui.TextBandsModule;
import kranji.reading.app.ui.PinyinSwfModule;
import kranji.reading.app.ui.TypefacePickerModule;
import kranji.reading.app.zi.SyllableDetailWidget;
import kranji.reading.app.zi.SyllableTreeWidget;
import kranji.reading.app.zi.ZiCharactersWidget;
import kranji.reading.app.zi.ZiDetailWidget;
import kranji.reading.app.zi.ZiReadingsGridModule;
import kranji.reading.app.zi.ZiSelectionSecretaryModule;

import java.util.List;

/**
 * The crate for {@code kranji-reading-app} — every served module this Maven
 * module ships.
 *
 * <p>One crate per Maven module is load-bearing: {@code OrphanCheck} scans the
 * whole module output for concrete served modules and fails on any this crate
 * omits. Re-run {@code CrateSeed.suggest(...)} whenever a widget is added.</p>
 */
public final class ReadingCrate implements Crate {

    public static final ReadingCrate INSTANCE = new ReadingCrate();

    private ReadingCrate() {}

    @Override
    public String name() { return "kranji-reading-app"; }

    @Override
    public List<CrateEntry> entries() {
        return List.of(
                CrateEntry.of(ReadingHomeWidget.INSTANCE),
                CrateEntry.of(ReadingCss.INSTANCE),
                CrateEntry.of(SyllableTreeWidget.INSTANCE),
                CrateEntry.of(SyllableDetailWidget.INSTANCE),
                CrateEntry.of(ZiCharactersWidget.INSTANCE),
                CrateEntry.of(ZiReadingsGridModule.INSTANCE),
                CrateEntry.of(ZiDetailWidget.INSTANCE),
                CrateEntry.of(ZiSelectionSecretaryModule.INSTANCE),
                CrateEntry.of(PhonicSourceWidget.INSTANCE),
                CrateEntry.of(ArticleReaderWidget.INSTANCE),
                CrateEntry.of(ArticleScannerModule.INSTANCE),
                CrateEntry.of(ArticleCensusModule.INSTANCE),
                CrateEntry.of(ReadabilityModule.INSTANCE),
                CrateEntry.of(KnownSetModule.INSTANCE),
                CrateEntry.of(KnownStoreModule.INSTANCE),
                CrateEntry.of(KnownTransferModule.INSTANCE),
                CrateEntry.of(KnownImportControlsModule.INSTANCE),
                CrateEntry.of(KnownPersistenceModule.INSTANCE),
                CrateEntry.of(KnownWatchModule.INSTANCE),
                CrateEntry.of(KnownSecretaryModule.INSTANCE),
                CrateEntry.of(KnownZiWidget.INSTANCE),
                CrateEntry.of(KnownSoundsModule.INSTANCE),
                CrateEntry.of(KnownProgressModule.INSTANCE),
                CrateEntry.of(KnownProgressWidget.INSTANCE),
                CrateEntry.of(KnownSoundsWidget.INSTANCE),
                CrateEntry.of(KnownTransferWidget.INSTANCE),
                CrateEntry.of(ArticleBoardModule.INSTANCE),
                CrateEntry.of(ArticleReadingsModule.INSTANCE),
                CrateEntry.of(ReaderCellStyleModule.INSTANCE),
                CrateEntry.of(ArticleCatalogueWidget.INSTANCE),
                CrateEntry.of(ArticleSelectionSecretaryModule.INSTANCE),
                CrateEntry.of(TypefacePickerModule.INSTANCE),
                CrateEntry.of(PinyinSwfModule.INSTANCE),
                CrateEntry.of(GlyphMetricsModule.INSTANCE),
                CrateEntry.of(TextBandsModule.INSTANCE),
                CrateEntry.of(ReaderControlsModule.INSTANCE)
        );
    }

    @Override
    public List<Crate> requires() {
        // SyllableTreeWidget imports TreeRendererModule (CoreJsCrate); the phonic
        // source widget imports RelationGrid and TextCell (RelationGridCrate).
        return List.of(CoreJsCrate.INSTANCE, RelationGridCrate.INSTANCE);
    }
}
