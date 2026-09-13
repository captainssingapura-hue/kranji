package kranji.reading.testkit;

import kranji.reading.content.GlossCoverage;
import kranji.reading.library.LibraryTree;
import kranji.simple.gloss.Glosses;
import kranji.simple.gloss.ZiCollections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * How much of the named trees the meanings on the classpath can explain —
 * written down every build, and held above a floor.
 *
 * <h2>The failure this exists to stop</h2>
 *
 * <p>Coverage moves in two directions and both are quiet. An article full of
 * characters nobody has glossed lowers it; a gloss collection dropped from a
 * deployment lowers it; a generator that stops emitting polyphones lowers it.
 * None of those fail a build on their own. A reader finds out one square at a
 * time, and nobody adds it up.</p>
 *
 * <p>So this adds it up. {@link #coverageIsWrittenDown} leaves the full report
 * in {@code target/} — every shelf, every article worst-first, and the missing
 * pairs most-read first, which is the worklist. {@link #coverageHoldsTheFloor}
 * fails the build if the read-coverage falls under {@link #floor()}.</p>
 *
 * <h2>The floor is a ratchet, not a target</h2>
 *
 * <p>It defaults to 0, which reports and never fails: the machinery should run
 * in a module before anyone has decided what that module owes. A subclass that
 * has looked at its report sets the floor just under what it found, and raises
 * it as the meanings catch up. Lowering it is allowed and is a decision, made
 * in a diff somebody reviews, rather than a drift nobody sees.</p>
 *
 * <h2>A missing library is a failure, not a zero</h2>
 *
 * <p>When the gloss collections are absent from the classpath every pair is
 * uncovered, and 0% would be reported truthfully and mean nothing. So unless a
 * subclass says {@link #expectsGlosses()} is false, an empty registry fails
 * outright — the same reason the reader's launch script announces the library
 * jars it found rather than starting quietly without them.</p>
 *
 * <h2>Extending it</h2>
 *
 * <pre>
 * class GlossCoverageTest extends GlossCoverageTestBase {
 *     protected List&lt;LibraryTree&gt; trees() { return List.of(KranjiLibrary.INSTANCE.tree()); }
 *     protected String library()          { return "kranji"; }
 *     protected double floor()            { return 0.62; }
 * }
 * </pre>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class GlossCoverageTestBase {

    private GlossCoverage.Report report;

    /** The trees this test speaks for. */
    protected abstract List<LibraryTree> trees();

    /** What to call the library in the report's heading. */
    protected abstract String library();

    /** The read-coverage the build must not fall under. {@code 0} reports without failing. */
    protected double floor() { return 0.0; }

    /** Whether an empty gloss registry is a broken classpath. It usually is. */
    protected boolean expectsGlosses() { return true; }

    /** Where the report is left. Relative to the module. */
    protected Path reportPath() { return Path.of("target", "gloss-coverage.txt"); }

    /**
     * The registry to measure against: whatever is discovered, or nothing.
     *
     * <p>Nothing rather than a throw, so a module that deliberately measures a
     * library against no glosses can — and {@link #expectsGlosses()} is what
     * says whether that was deliberate.</p>
     */
    protected Glosses glosses() {
        try {
            return ZiCollections.glosses();
        } catch (RuntimeException noCollections) {
            return Glosses.none();
        }
    }

    /** The measurement, taken once per class. */
    protected final GlossCoverage.Report report() {
        if (report != null) return report;
        List<LibraryTree> trees = trees();
        LibraryTree tree = trees.size() == 1 ? trees.get(0)
                : LibraryTree.of(library(), trees.toArray(LibraryTree[]::new));
        report = GlossCoverage.of(library(), tree, glosses());
        return report;
    }

    @Test
    protected void theGlossesWereFound() {
        if (!expectsGlosses()) return;
        assertFalse(glosses().all().isEmpty(),
                "no gloss collection on the test classpath - coverage would read 0% and mean "
              + "nothing. Add the collections this deployment ships, or override "
              + "expectsGlosses() if measuring against none is the point.");
    }

    @Test
    protected void coverageIsWrittenDown() throws IOException {
        Path path = reportPath();
        Files.createDirectories(path.toAbsolutePath().getParent());
        Files.writeString(path, report().render(), StandardCharsets.UTF_8);
        assertTrue(Files.exists(path), "no coverage report at " + path.toAbsolutePath());
    }

    @Test
    protected void coverageHoldsTheFloor() {
        GlossCoverage.Report r = report();
        // The figure is given in reads, not rounded to a percentage: at a floor
        // of 1.0 every failure would otherwise read "100.0%, under 100.0%".
        assertTrue(r.readRatio() >= floor(), () -> String.format(Locale.ROOT,
                "read-coverage is %d of %d reads (%.2f%%), under the floor of %.2f%%: "
              + "%d reads across %d (character, reading) pairs have no meaning behind them.%n  %s%n"
              + "  The report at %s names them, most-read first. Raise the "
              + "meanings, or lower the floor in a change somebody reviews.",
                r.readsCovered(), r.reads(), 100 * r.readRatio(), 100 * floor(),
                r.reads() - r.readsCovered(), r.missing().size(), r.summary(),
                reportPath().toAbsolutePath()));
    }
}
