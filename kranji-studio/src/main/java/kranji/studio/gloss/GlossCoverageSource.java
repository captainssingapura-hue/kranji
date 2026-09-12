package kranji.studio.gloss;

import kranji.reading.content.GlossCoverage;
import kranji.reading.library.Libraries;

/**
 * The mounted library measured against the composed glosses, held.
 *
 * <h2>Why a third walk beside demand and impact</h2>
 *
 * <p>{@link GlossDemand} answers "which pairs does the library ask for" and
 * {@link GlossImpact} answers "which open problem costs a reader most". Neither
 * answers the question a person asks first when they open this workbench:
 * <em>how much of it is done</em> — and done per shelf and per article, so the
 * answer can be turned into a place to start. That is a different shape of
 * result, and {@link GlossCoverage} already computes it for the build; this
 * holds the same report for the tool, so the number a build fails on and the
 * number a workbench shows cannot disagree.</p>
 *
 * <h2>Held, like demand</h2>
 *
 * <p>The library arrives as jars and cannot change while the process runs, and
 * the glosses the studio measures against are the ones it composed at start.
 * Recomputing on every grid request would re-parse six hundred articles for a
 * refresh that could not show anything new.</p>
 */
public final class GlossCoverageSource {

    private GlossCoverageSource() {}

    private static final class Held {
        static final GlossCoverage.Report REPORT = GlossCoverage.of(
                Libraries.mounted().name(),
                Libraries.mounted().tree(),
                GlossWorkbench.registry());
    }

    /** The measurement, taken once. */
    public static GlossCoverage.Report report() { return Held.REPORT; }
}
