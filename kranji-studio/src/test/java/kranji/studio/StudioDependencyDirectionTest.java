package kranji.studio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * The studio may read the product. The product may not read the studio.
 *
 * <p>Not a style preference. This module holds internal tooling — workbench
 * grids, plan trackers, design notes — and the moment a product module can
 * reach a type in here, internal tooling has a route into something a reader
 * runs. The direction is what keeps that impossible rather than merely
 * unlikely.</p>
 *
 * <p>Maven will not catch it. {@code kranji-reading-app} depending on
 * {@code kranji-studio} forms no cycle and builds cleanly, so the rule has no
 * enforcement unless something asserts it. This is that something.</p>
 *
 * <p>Poms are read from disk rather than the classpath because the question is
 * about <i>declarations</i>, not about what happens to be resolved: a
 * dependency that is declared and unused is still a route, and it is exactly
 * the kind that gets used later by someone who sees it already there.</p>
 */
class StudioDependencyDirectionTest {

    private static final String STUDIO = "kranji-studio";

    /**
     * The one module allowed to name the studio: the packaging module.
     *
     * <p>{@code kranji-dist} is not product code and holds none — a launcher
     * that picks an app by its first argument, and nothing else. It composes
     * deployables, so declaring both apps is its entire job, and the route the
     * rule guards against is still closed: no reading-app class can reference a
     * studio type, because no reading module declares the dependency.</p>
     *
     * <p>What this exemption <b>does</b> cost is worth stating. The jar
     * kranji-dist builds carries the studio inside it, so it is a development
     * artifact — fine on a machine that is already running the workbench, and
     * not what you would put on a family's device. A reader-only jar is a
     * second shade execution with the studio filtered out, on the day that
     * matters.</p>
     *
     * <p>Named rather than pattern-matched. A rule with an exception list is
     * only as good as how hard the list is to grow: one literal is a line
     * somebody has to add on purpose.</p>
     */
    private static final String PACKAGING = "kranji-dist";

    /** {@code <dependency>…<artifactId>x</artifactId>…</dependency>}, across newlines. */
    private static final Pattern DEPENDENCY =
            Pattern.compile("<dependency>(.*?)</dependency>", Pattern.DOTALL);
    private static final Pattern ARTIFACT =
            Pattern.compile("<artifactId>\\s*([^<]+?)\\s*</artifactId>");

    /**
     * The reactor root, found by walking up for the pom that lists modules.
     *
     * <p>Fails rather than skips when it cannot be found. A check that quietly
     * passes because it could not run is worse than no check — it reports the
     * rule as held when nothing looked.</p>
     */
    private static Path reactorRoot() {
        Path dir = Path.of("").toAbsolutePath();
        for (int up = 0; up < 6 && dir != null; up++, dir = dir.getParent()) {
            Path pom = dir.resolve("pom.xml");
            if (Files.exists(pom) && read(pom).contains("<module>")) return dir;
        }
        return fail("could not find the reactor root from " + Path.of("").toAbsolutePath()
                  + " - this check did not run, which is not the same as passing");
    }

    private static String read(Path pom) {
        try {
            return Files.readString(pom);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + pom, e);
        }
    }

    /** Build output holds copied poms; they are not declarations. */
    private static final String TARGET =
            java.io.File.separator + "target" + java.io.File.separator;

    /** Every pom in the reactor except the studio's own and the packaging module's. */
    private static List<Path> otherPoms() {
        Path root = reactorRoot();
        try (Stream<Path> walk = Files.walk(root)) {
            return walk.filter(p -> p.getFileName().toString().equals("pom.xml"))
                       .filter(p -> !p.toString().contains(TARGET))
                       .filter(p -> !p.getParent().getFileName().toString().equals(STUDIO))
                       .filter(p -> !p.getParent().getFileName().toString().equals(PACKAGING))
                       .toList();
        } catch (IOException e) {
            throw new UncheckedIOException("could not walk " + root, e);
        }
    }

    @Test
    void noOtherModuleDependsOnTheStudio() {
        var offenders = new ArrayList<String>();

        for (Path pom : otherPoms()) {
            Matcher deps = DEPENDENCY.matcher(read(pom));
            while (deps.find()) {
                Matcher artifact = ARTIFACT.matcher(deps.group(1));
                if (artifact.find() && STUDIO.equals(artifact.group(1))) {
                    offenders.add(pom.toString());
                }
            }
        }

        assertEquals(List.of(), offenders,
                () -> "the studio holds internal tooling, so nothing in the product may depend "
                    + "on it. Declared by:\n  " + String.join("\n  ", offenders));
    }

    @Test
    void theOneExemptionIsLiveAndTheProductIsStillCovered() {
        // An exemption nobody uses is a hole waiting for someone to find. This
        // says the packaging module really does declare the studio - so if that
        // stops being true, the exemption goes rather than lingering.
        Path dist = reactorRoot().resolve(PACKAGING).resolve("pom.xml");
        assertTrue(Files.exists(dist), () -> "no " + PACKAGING + " module at " + dist);
        assertTrue(read(dist).contains("<artifactId>" + STUDIO + "</artifactId>"),
                PACKAGING + " no longer declares the studio - drop the exemption");

        // And the half that matters: the app a child runs is still checked, and
        // still does not name the studio.
        Path app = reactorRoot()
                .resolve("kranji-reading").resolve("kranji-reading-app").resolve("pom.xml");
        assertTrue(otherPoms().contains(app), "the reading app must still be covered");
        assertTrue(!read(app).contains("<artifactId>" + STUDIO + "</artifactId>"),
                "the reading app must not depend on the studio");
    }

    @Test
    void theCheckActuallyReadsTheReactor() {
        // The other test passes trivially if the walk finds nothing. This is
        // what makes its silence meaningful.
        List<Path> poms = otherPoms();
        assertTrue(poms.size() >= 10,
                () -> "expected the reactor's modules, found " + poms.size() + " poms");

        assertTrue(poms.stream().anyMatch(p -> read(p).contains("<artifactId>kranji-core")),
                "the walk should have reached modules that declare kranji dependencies");
    }
}
