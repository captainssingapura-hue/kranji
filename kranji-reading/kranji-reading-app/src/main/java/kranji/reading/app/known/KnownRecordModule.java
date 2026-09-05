package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The known set, held as something you can ask rather than something you scan.
 *
 * <h2>Why this exists</h2>
 *
 * <p>The set was a plain array and {@code has} was {@code indexOf}. That is the
 * fault underneath most of the update path: with no membership structure, every
 * consumer that needed one built its own — readability rebuilt a claimed map
 * once per article, the reader's toggle scanned to decide direction, and the
 * Secretary scanned again to apply the result. Ranking the library at 2,000
 * readings known cost 23ms, almost all of it re-indexing the same set 475
 * times.</p>
 *
 * <p>One record, asked rather than scanned, removes all of them together.</p>
 *
 * <h2>Two structures</h2>
 *
 * <p>A map answers {@code has} in constant time; an array remembers insertion
 * order, which is what makes an export file diffable and a list of claims read
 * chronologically. Removal splices the array and so is linear — and stays
 * linear, because a reader takes a claim back far less often than the app asks
 * whether one exists.</p>
 *
 * <h2>Seeding is a union</h2>
 *
 * <p>Never a replace, for the reason the Secretary's seed was: a record that
 * arrives late — a slow disk, a service that started after marking began —
 * must not take back a claim made in the meantime.</p>
 *
 * <p>Pure — no DOM, no storage, no clock, no party — so it runs under GraalVM
 * in ordinary JUnit.</p>
 */
public record KnownRecordModule() implements DomModule<KnownRecordModule> {

    /** Yields {@code has}, {@code add}, {@code remove}, {@code seed} and the counts. */
    public record createKnownRecord() implements Exportable._Constant<KnownRecordModule> {}

    public static final KnownRecordModule INSTANCE = new KnownRecordModule();

    @Override
    public ImportsFor<KnownRecordModule> imports() {
        return ImportsFor.<KnownRecordModule>builder().build();
    }

    @Override
    public ExportsOf<KnownRecordModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownRecord()));
    }
}
