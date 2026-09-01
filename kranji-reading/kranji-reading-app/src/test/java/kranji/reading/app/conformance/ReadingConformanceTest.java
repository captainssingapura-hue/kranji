package kranji.reading.app.conformance;

import hue.captains.singapura.js.homing.conformance.engine.ConformanceEngine;
import hue.captains.singapura.js.homing.conformance.engine.ServedModuleRenderer;
import hue.captains.singapura.js.homing.conformance.rules.CrateClosure;
import hue.captains.singapura.js.homing.conformance.rules.CrateConformance;
import hue.captains.singapura.js.homing.conformance.rules.CrateCoverage;
import hue.captains.singapura.js.homing.conformance.rules.Finding;
import hue.captains.singapura.js.homing.conformance.rules.GradedFinding;
import hue.captains.singapura.js.homing.conformance.rules.Severity;
import kranji.reading.app.ReadingCrate;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The conformance gate for {@code kranji-reading-app}.
 *
 * <p>Added while the module holds a single widget rather than at the end of the
 * project. The OrphanCheck is cheapest to satisfy when there is little to
 * declare, and a baseline established now records a genuinely clean start
 * instead of grandfathering months of drift.</p>
 */
class ReadingConformanceTest {

    private static final boolean ALLOW_PRE_EXISTING =
            Boolean.parseBoolean(System.getProperty("conformance.allowPreExisting", "true"));

    @Test
    void crateIsStructurallyComplete() {
        CrateConformance.Result result =
                CrateConformance.evaluate(CrateClosure.of(ReadingConformance.TOP_LEVEL));
        CrateConformance.CrateResult crate = result.crates().get(ReadingCrate.INSTANCE.name());

        assertNotNull(crate, "the kranji-reading-app crate must be present in the evaluation");
        assertEquals(List.of(), crate.orphans(),
                "every served module in this Maven module must be declared in ReadingCrate");
        assertEquals(List.of(), crate.illegalImports(),
                "every cross-crate import must be declared in ReadingCrate.requires()");
    }

    @Test
    void everyServedModuleIsCrated() {
        List<String> gaps = CrateCoverage.check(
                CrateClosure.of(ReadingConformance.TOP_LEVEL), List.of(ReadingCrate.class));
        assertEquals(List.of(), gaps,
                () -> "served modules missing from every crate:\n" + String.join("\n", gaps));
    }

    @Test
    void servedModulesAreConformant() {
        List<Finding> raw = new ConformanceEngine(ReadingConformance.POLICY, new ServedModuleRenderer())
                .checkCrates(ReadingConformance.TOP_LEVEL);
        List<GradedFinding> graded = ReadingConformance.grader(ALLOW_PRE_EXISTING).grade(raw);

        graded.stream().filter(g -> g.severity() == Severity.WARNING)
                .forEach(g -> System.out.println("[reading-conformance] WARN " + describe(g)));

        List<GradedFinding> errors = graded.stream().filter(GradedFinding::isError).toList();
        assertEquals(List.of(), errors,
                () -> "reading conformance ERRORS (" + errors.size() + "):\n"
                        + errors.stream().map(ReadingConformanceTest::describe)
                                .collect(Collectors.joining("\n")));
    }

    private static String describe(GradedFinding g) {
        Finding f = g.finding();
        return f.moduleClass() + " [" + f.rule().value() + "] " + f.message()
                + (g.note().isBlank() ? "" : "  (" + g.note() + ")");
    }
}
