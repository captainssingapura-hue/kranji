package kranji.reading.workbench.coverage;

import kranji.reading.content.GlossCoverage;
import kranji.reading.library.Libraries;
import kranji.simple.gloss.Glosses;
import kranji.simple.gloss.ZiCollections;

/**
 * The mounted library measured against the discovered glosses, held.
 *
 * <p>The same report the build fails on — {@link GlossCoverage} computes it
 * for both — held once for the tool, so the number a build reports and the
 * number a bench shows cannot disagree. Held because the library arrives as
 * jars and cannot change while the process runs; recomputing per grid request
 * would re-parse six hundred articles for a refresh that could not show
 * anything new.</p>
 *
 * <p>An empty registry when no gloss collection is on the classpath, rather
 * than a throw: a bench without meanings is still a bench, and what it shows
 * for coverage — 0%, every pair missing — is the truth about that classpath.
 * The reader's Character pane says the same thing in words.</p>
 */
public final class CoverageSource {

    private CoverageSource() {}

    private static final class Held {
        static final GlossCoverage.Report REPORT = GlossCoverage.of(
                Libraries.mounted().name(),
                Libraries.mounted().tree(),
                glosses());
    }

    private static Glosses glosses() {
        try {
            return ZiCollections.glosses();
        } catch (RuntimeException noCollections) {
            return Glosses.none();
        }
    }

    /** The measurement, taken once. */
    public static GlossCoverage.Report report() { return Held.REPORT; }
}
