package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * How much of an article this reader can already read.
 *
 * <pre>{@code readability = known Han tokens / total Han tokens}</pre>
 *
 * <p>Simple arithmetic over two things already in hand: the article's census,
 * which the server sent once, and the known set, which never left the device.
 * No request — and none needed when the set changes, which is what makes a
 * catalogue that re-ranks as a child learns affordable.</p>
 *
 * <h2>Two numbers, not one</h2>
 *
 * <p>The ratio counts repeats, because that is the reading experience: a
 * character met forty times is forty moments of support. The count of
 * <em>distinct</em> unknown readings is often the more useful figure — 94% with
 * eight new readings is a different proposition from 94% with one repeated
 * forty times, and the second is much the better lesson.</p>
 *
 * <h2>Best fit is not highest</h2>
 *
 * <p>{@code compare} orders by nearness to the middle of the just-right band,
 * not by readability descending — which would rank the easiest material top and
 * bury everything worth reading. A child wants the story that stretches them
 * slightly.</p>
 *
 * <p>Pure — no DOM, no fetch, no clock — so the arithmetic runs under GraalVM in
 * ordinary JUnit.</p>
 */
public record ReadabilityModule() implements DomModule<ReadabilityModule> {

    /** Yields {@code of}, {@code bandOf}, {@code bands} and {@code compare}. */
    public record createReadability() implements Exportable._Constant<ReadabilityModule> {}

    /** The fit line: owns the census fetch and the sentence. */
    public record createArticleFit() implements Exportable._Constant<ReadabilityModule> {}

    public static final ReadabilityModule INSTANCE = new ReadabilityModule();

    @Override
    public ImportsFor<ReadabilityModule> imports() {
        return ImportsFor.<ReadabilityModule>builder().build();
    }

    @Override
    public ExportsOf<ReadabilityModule> exports() {
        return new ExportsOf<>(INSTANCE,
                List.of(new createReadability(), new createArticleFit()));
    }
}
