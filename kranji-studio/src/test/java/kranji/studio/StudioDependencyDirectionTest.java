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

    /** Every pom in the reactor except the studio's own. */
    private static List<Path> otherPoms() {
        Path root = reactorRoot();
        try (Stream<Path> walk = Files.walk(root)) {
            return walk.filter(p -> p.getFileName().toString().equals("pom.xml"))
                       .filter(p -> !p.toString().contains(TARGET))
                       .filter(p -> !p.getParent().getFileName().toString().equals(STUDIO))
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
