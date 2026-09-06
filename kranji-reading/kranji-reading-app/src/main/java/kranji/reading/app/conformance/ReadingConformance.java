package kranji.reading.app.conformance;

import hue.captains.singapura.js.homing.conformance.rules.Allowance;
import hue.captains.singapura.js.homing.conformance.rules.Baseline;
import hue.captains.singapura.js.homing.conformance.rules.DefaultJsRulePolicy;
import hue.captains.singapura.js.homing.conformance.rules.FindingGrader;
import hue.captains.singapura.js.homing.conformance.rules.JsRule;
import hue.captains.singapura.js.homing.conformance.rules.JsRulePolicy;
import hue.captains.singapura.js.homing.conformance.rules.JsRuleSet;
import hue.captains.singapura.js.homing.conformance.rules.MaxEffectiveLinesRule;
import hue.captains.singapura.js.homing.conformance.rules.NoCdnImportRule;
import hue.captains.singapura.js.homing.conformance.rules.RuleId;
import hue.captains.singapura.js.homing.conformance.rules.RuleSetId;
import hue.captains.singapura.js.homing.core.Crate;
import hue.captains.singapura.js.homing.core.JsModuleType;
import hue.captains.singapura.js.homing.core.StandardJsModuleType;
import kranji.reading.app.ReadingCrate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * One conformance configuration, shared by the gate test and any later report
 * export.
 *
 * <p>The policy extends the framework default with a rule set for
 * {@link ZiModuleType#ZI_MODEL}. The composite policy does not fold the
 * framework's global rules into an extension type automatically, so the ones
 * we want are listed explicitly alongside our own.</p>
 */
public final class ReadingConformance {

    private ReadingConformance() {}

    /** Crate roots the gate walks. */
    public static final List<Crate> TOP_LEVEL = List.of(ReadingCrate.INSTANCE);

    /** Rules applied to modules declared as {@code zi-model}. */
    public static final JsRuleSet ZI_RULES = new JsRuleSet(
            new RuleSetId("zi-model"),
            "Zi model",
            List.<JsRule>of(
                    NoInlineGlyphRule.INSTANCE,
                    NoCdnImportRule.INSTANCE,
                    MaxEffectiveLinesRule.INSTANCE));

    /** Framework default, extended with the reading domain type. */
    /**
     * The framework default, extended two ways: a new {@code zi-model} type for
     * headless character logic, and the glyph rule folded into the consumer set
     * so it also covers widgets.
     *
     * <p>Covering consumers matters more than the new type does. The reader is
     * where a glyph would most plausibly be hardcoded, and a reader widget is a
     * consumer - a rule that applied only to {@code zi-model} would protect
     * nothing that actually renders text.</p>
     */
    public static final JsRulePolicy POLICY = DefaultJsRulePolicy.INSTANCE.extendedWith(Map.of(
            ZiModuleType.ZI_MODEL, ZI_RULES,
            StandardJsModuleType.CONSUMER, plusGlyphRule(StandardJsModuleType.CONSUMER)));

    /** An existing rule set with {@link NoInlineGlyphRule} appended. */
    private static JsRuleSet plusGlyphRule(JsModuleType type) {
        JsRuleSet base = DefaultJsRulePolicy.INSTANCE.rulesFor(type);
        var rules = new ArrayList<JsRule>(base.rules());
        rules.add(NoInlineGlyphRule.INSTANCE);
        return new JsRuleSet(base.id(), base.title(), List.copyOf(rules));
    }
    /**
     * Documented, intentional exceptions. A legitimate exception is added here
     * with a reason rather than by weakening a rule.
     */
    public static final List<Allowance> ALLOWANCES = List.of(
            new Allowance(
                    "kranji.reading.app.known.KnownTransferWidget",
                    new RuleId("no-raw-href"),
                    "The export writes a blob URL onto an anchor so the record can be saved "
                  + "as a file. The rule routes links through HrefManager so they respect "
                  + "the app's routing, and this is not a link: it is a handle to bytes the "
                  + "page is already holding, alive for one click and revoked afterwards. "
                  + "Reaching HrefManager would mean importing an unrelated app's AppLink "
                  + "purely to obtain the injection - a dependency that would misdescribe "
                  + "the module. The anchor is built through the branch like every other "
                  + "element, so the href assignment is the only exceptional line."));

    /** Grandfathered findings, committed alongside the source. */
    public static Baseline baseline() {
        try (InputStream in = ReadingConformance.class
                .getResourceAsStream("/reading-conformance-baseline.txt")) {
            if (in == null) return Baseline.EMPTY;
            var lines = new ArrayList<String>();
            try (var r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                for (String line; (line = r.readLine()) != null; ) lines.add(line);
            }
            return Baseline.of(lines);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to load the reading conformance baseline", e);
        }
    }

    public static FindingGrader grader(boolean allowPreExisting) {
        return FindingGrader.STRICT
                .withAllowlist(ALLOWANCES)
                .withBaseline(baseline())
                .allowingPreExisting(allowPreExisting);
    }
}
