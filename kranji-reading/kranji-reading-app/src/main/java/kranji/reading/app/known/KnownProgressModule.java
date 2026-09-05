package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;
import kranji.reading.app.read.ReadabilityModule;

import java.util.List;

/**
 * What the record adds up to, and how to say it.
 *
 * <h2>Never lead with the fraction</h2>
 *
 * <p>The corpus is 8,763 (character, reading) pairs. A child who has learnt
 * fifty readings — weeks of work — is at 0.6%, and a tracker that opens with
 * that number has told them their effort rounds to nothing. The same fifty
 * readings can put a dozen stories within reach, and that is the true statement
 * worth making first.</p>
 *
 * <p>So the order is fixed: what you can read, then what is close, then the
 * counts. Counts come last because they are the least motivating true thing
 * available — not because they are unimportant, and they are still shown.</p>
 *
 * <h2>Nothing scolds</h2>
 *
 * <p>No "only", no "still", no red. An empty record is where everyone starts
 * and is phrased as an invitation rather than as a zero. A figure that has not
 * moved since last time is not remarked on: a tracker that notices stalling is
 * a tracker a child stops opening, and the whole value of this one is that it
 * gets opened.</p>
 *
 * <h2>Distances, not percentages</h2>
 *
 * <p>The near lists are ordered by <em>how many new readings</em> each thing
 * needs, not by how close it already is. "Three readings away" is something
 * somebody can go and do; 87% is something they can only feel.</p>
 *
 * <p>Readability comes from {@link ReadabilityModule}, not from arithmetic of
 * its own, so a story this calls readable is the story the catalogue and the
 * reader call readable.</p>
 *
 * <p>Pure — no DOM, no fetch, no clock — so both the counting and the wording
 * run under GraalVM in ordinary JUnit. The wording is worth testing: it is the
 * part a reader actually receives.</p>
 */
public record KnownProgressModule() implements DomModule<KnownProgressModule> {

    /**
     * Yields {@code summarise}, {@code counts}, {@code nearestStories},
     * {@code nearlyDoneSounds}, {@code headline} and {@code encouragement}.
     */
    public record createKnownProgress() implements Exportable._Constant<KnownProgressModule> {}

    public static final KnownProgressModule INSTANCE = new KnownProgressModule();

    @Override
    public ImportsFor<KnownProgressModule> imports() {
        return ImportsFor.<KnownProgressModule>builder()
                .add(new ModuleImports<>(
                        List.of(new ReadabilityModule.createReadability()),
                        ReadabilityModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<KnownProgressModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownProgress()));
    }
}
