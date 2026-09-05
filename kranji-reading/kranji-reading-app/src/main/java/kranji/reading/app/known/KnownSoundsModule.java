package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * Where the record meets the corpus: the known set, bucketed by sound.
 *
 * <p>The whole of the arithmetic behind {@link KnownSoundsWidget}, kept out of
 * it because a pane that both computes and draws cannot be checked without a
 * browser — and what is worth checking here is the counting, not the markup.
 * Pure: no DOM, no fetch, no clock, so it runs under GraalVM in ordinary
 * JUnit.</p>
 *
 * <h2>The join is free</h2>
 *
 * <p>A known-set entry is {@code codePoint:reading} and a syllable is keyed on
 * that same canonical reading, so bucketing the set by sound is reading the
 * back half of each key. No lookup, no request, nothing the server has to have
 * seen. That is the return on keying both on the pair rather than on the
 * character.</p>
 *
 * <h2>Left, not done</h2>
 *
 * <p>Rows are ordered by how much of a sound is still unclaimed. A sound with
 * forty characters and two claimed is a larger piece of work than one with
 * three and none, and somebody deciding where to spend an afternoon wants the
 * first at the top — ordering by what is finished puts the smallest jobs
 * there.</p>
 *
 * <p>A count is clamped to the size of its sound. A reader can hold a claim for
 * a (character, reading) the corpus does not file under that syllable — an
 * import from another corpus version, a reading since corrected — and an
 * unclamped count would print 41 of 40 and make every total slightly wrong.</p>
 */
public record KnownSoundsModule() implements DomModule<KnownSoundsModule> {

    /** Yields {@code byReading}, {@code rowsOf} and {@code summarise}. */
    public record createKnownSounds() implements Exportable._Constant<KnownSoundsModule> {}

    public static final KnownSoundsModule INSTANCE = new KnownSoundsModule();

    @Override
    public ImportsFor<KnownSoundsModule> imports() {
        return ImportsFor.<KnownSoundsModule>builder().build();
    }

    @Override
    public ExportsOf<KnownSoundsModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownSounds()));
    }
}
