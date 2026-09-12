package kranji.reading.workbench;

import hue.captains.singapura.js.homing.studio.base.Umbrella;
import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import kranji.reading.app.ReadingFixtures;
import kranji.reading.app.ReadingWorkspaceSpec;
import kranji.reading.app.read.ArticleCatalogueWidget;
import kranji.reading.app.read.ArticleReaderWidget;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The bench shows the reader's browser, not a version of it.
 *
 * <p>Each check here is a way the two could drift: a widget class the bench
 * mounts that the reader does not, a party the reader speaks on that the
 * bench forgot to declare, a route a widget fetches that the bench does not
 * serve. Any of them would make "what the author sees" differ from "what the
 * child sees" without a build noticing, which is the one thing this module
 * exists to prevent.</p>
 */
class ArticleBrowserSpecTest {

    @Test
    void theBrowserIsTheReadersOwnLibraryAndReaderWidgets() {
        List<Class<?>> mounted = ArticleBrowserSpec.INSTANCE.widgetEntries().stream()
                .map(WidgetEntry::widgetClass).collect(Collectors.toList());
        assertEquals(List.of(ArticleCatalogueWidget.class, ArticleReaderWidget.class), mounted,
                "the article browser is the Library and the Reader, in that order, and nothing else");

        // And both really are what the reader mounts - by class, so a fork of
        // either widget under the same label would show up here.
        Set<Class<?>> readers = ReadingWorkspaceSpec.INSTANCE.widgetEntries().stream()
                .map(WidgetEntry::widgetClass).collect(Collectors.toSet());
        for (Class<?> widget : mounted) {
            assertTrue(readers.contains(widget),
                    widget.getSimpleName() + " is not a widget the reader itself mounts");
        }
    }

    @Test
    void everyPartyTheReaderDeclaresThatTheseWidgetsSpeakOnIsDeclaredHere() {
        // The reader widget reaches for articleParty, ziParty and knownParty
        // and is null-safe on all three - which means a missing one changes
        // its behaviour silently rather than loudly. So the bench declares
        // them under the same exposed names, and this says it still does.
        Set<String> benchParties = ArticleBrowserSpec.INSTANCE.parties().stream()
                .map(PartyDecl::exposedAs).collect(Collectors.toSet());
        for (String needed : List.of("articleParty", "ziParty", "knownParty")) {
            assertTrue(benchParties.contains(needed),
                    "the bench does not expose " + needed + ", which the reader widget looks for");
        }
        // Declared under the same exposed names the reader uses, so the same
        // widget code finds the same bus in both hosts.
        Set<String> readerParties = ReadingWorkspaceSpec.INSTANCE.parties().stream()
                .map(PartyDecl::exposedAs).collect(Collectors.toSet());
        assertTrue(readerParties.containsAll(benchParties),
                "the bench exposes a party name the reader does not: " + benchParties);
    }

    @Test
    void theBenchServesEveryRouteTheReaderServes() {
        // Not "the routes these two widgets need" - all of them. The list is
        // the reader's own, taken from the same method, so the contract is
        // that the bench never lags it; a widget added to the bench tomorrow
        // finds its route already there.
        var bench = new LibraryWorkbenchFixtures(new Umbrella.Solo<>(LibraryWorkbenchStudio.INSTANCE));
        Set<String> served = bench.harnessGetActions().keySet();
        for (String path : ReadingFixtures.dataActions().keySet()) {
            assertTrue(served.contains(path), "the bench does not serve " + path);
        }
    }

    @Test
    void theBenchSaysWhichLibraryItMounted() {
        // The failure this guards is a bench that opened on the demo set and
        // looked fine. The landing page names the mounted root and its size,
        // so the wrong library is the first thing on screen rather than the
        // last thing noticed.
        String summary = LibraryWorkbenchCatalogue.INSTANCE.summary();
        assertTrue(summary.contains("Mounted: '"), summary);
        assertTrue(summary.contains(" articles"), summary);
    }
}
