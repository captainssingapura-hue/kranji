package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;

import java.util.List;

/**
 * How many characters this reader can read, and what that is called.
 *
 * <h2>One number</h2>
 *
 * <p>Not readings, not sounds covered, not stories unlocked. Every one of those
 * is true and measurable and every one of them turns the page into a dashboard
 * — the thing a learner scans instead of reads, and the reason most character
 * apps feel like an accounting screen. The question somebody actually has is
 * how many characters they can read, so that is the only number here.</p>
 *
 * <h2>A count is not a place</h2>
 *
 * <p>248 is a fact; "Reading along" is a place to be, and the second is what
 * somebody remembers a week later. So the band name is the headline and the
 * count sits under it. That is the whole design: a number, a name for where
 * that number puts you, and how far to the next name.</p>
 *
 * <h2>Where the boundaries come from</h2>
 *
 * <p>The top two are borrowed rather than invented — 2,500 and 3,500 are the
 * two standard everyday-character lists, so reaching them means something
 * outside this app. Below that each band is roughly double the last, which puts
 * the names close together early and far apart later. That is the right shape:
 * the encouragement is needed most in the first weeks and least once somebody
 * is plainly reading.</p>
 *
 * <p>The corpus is around 8,100 characters and that denominator never appears.
 * 248 of them is 3%, and a page that opens with 3% has told a child their
 * term's work rounds to nothing.</p>
 *
 * <p>What counts as a character is {@link KnownSetModule}'s decision, not one
 * taken again here by picking keys apart. A character claimed at one of its two
 * readings counts: the reader can read it where they meet it, and the
 * per-reading picture is what the Known pane is for.</p>
 *
 * <p>Pure — no DOM, no fetch, no clock — so the bands and the wording both run
 * under GraalVM in ordinary JUnit.</p>
 */
public record KnownProgressModule() implements DomModule<KnownProgressModule> {

    /**
     * Yields {@code bands}, {@code count}, {@code bandOf}, {@code next},
     * {@code headline}, {@code line} and {@code nextLine}.
     */
    public record createKnownProgress() implements Exportable._Constant<KnownProgressModule> {}

    public static final KnownProgressModule INSTANCE = new KnownProgressModule();

    @Override
    public ImportsFor<KnownProgressModule> imports() {
        return ImportsFor.<KnownProgressModule>builder()
                .add(new ModuleImports<>(
                        List.of(new KnownSetModule.createKnownSet()),
                        KnownSetModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<KnownProgressModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownProgress()));
    }
}
