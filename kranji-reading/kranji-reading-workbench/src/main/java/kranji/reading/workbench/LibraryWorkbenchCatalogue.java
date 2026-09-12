package kranji.reading.workbench;

import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L0_Catalogue;
import hue.captains.singapura.js.homing.studio.base.app.Navigable;
import hue.captains.singapura.js.homing.workspace.shell.GenericWorkspace;
import kranji.reading.library.Libraries;

import java.util.List;

/**
 * The front page of the bench: what it holds, and which library it is holding.
 *
 * <p>The summary names the mounted library and its size. A bench run in the
 * wrong directory, or with a collections jar missing from its classpath,
 * opens on the 23-article demonstration set and looks exactly like one that
 * is working - the same failure the reader's launch script exists to make
 * visible, made visible here on the page a person lands on.</p>
 */
public record LibraryWorkbenchCatalogue() implements L0_Catalogue<LibraryWorkbenchCatalogue> {

    public static final LibraryWorkbenchCatalogue INSTANCE = new LibraryWorkbenchCatalogue();

    @Override public String name()  { return "Kranji · Library Workbench"; }
    @Override public String badge() { return "BENCH"; }
    @Override public String icon()  { return "🔧"; }   // 🔧

    @Override public String summary() {
        var library = Libraries.mounted();
        int articles = library.tree().collections().stream()
                .mapToInt(c -> c.articles().size()).sum();
        return "Tools for whoever writes the library, run against the library itself. "
             + "Mounted: '" + library.name() + "', " + articles + " articles.";
    }

    @Override
    public List<Entry<LibraryWorkbenchCatalogue>> leaves() {
        Navigable<GenericWorkspace.Params, GenericWorkspace> browser =
                new Navigable<>(GenericWorkspace.INSTANCE,
                        new GenericWorkspace.Params(ArticleBrowserSpec.KIND),
                        "Article Browser",
                        "The library and the reader, exactly as a child gets them.");
        Navigable<GenericWorkspace.Params, GenericWorkspace> coverage =
                new Navigable<>(GenericWorkspace.INSTANCE,
                        new GenericWorkspace.Params(CoverageWorkbenchSpec.KIND),
                        "Coverage",
                        "How much of the library the meanings explain - by shelf, by "
                      + "article, down to the pair a page is missing.");
        return List.of(Entry.of(this, browser), Entry.of(this, coverage));
    }
}
