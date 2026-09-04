package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The known set, on the device.
 *
 * <p>IndexedDB rather than {@code localStorage}: a set of a few thousand
 * readings plus whatever comes later outgrows the {@code localStorage} budget,
 * and IDB is already the framework's substrate for checkpoint and catalogue
 * storage.</p>
 *
 * <h2>Nothing leaves the device</h2>
 *
 * <p>A record of which readings a specific child finds difficult is exactly the
 * kind of data that should not accumulate on somebody else's computer. There is
 * no account, no sync and nothing transmitted — so there is nothing to breach,
 * subpoena, or quietly analyse. The cost is that clearing browser data loses
 * the record, and the answer to that is export, not an account.</p>
 *
 * <h2>One row per profile, holding the whole set</h2>
 *
 * <pre>{@code
 * database     : "kranji.known"
 * object store : "sets"        key: profile
 *                row: { profile, known: ["34892:xíng", ...] }
 * }</pre>
 *
 * <p>The party already broadcasts the set whole rather than as a delta, so
 * storing it whole keeps the two in step: what is written is exactly what was
 * published. It also makes a save atomic and idempotent, which is what lets any
 * pane perform one without coordinating with the others.</p>
 *
 * <p>The {@code profile} key is here from the start although there is only ever
 * {@code "default"} today. Several children per device is a stated requirement,
 * and a key added later is a migration, whereas a key with one value is a
 * word.</p>
 *
 * <p><strong>No timestamps.</strong> Not an omission: the design has no
 * scheduling, no decay and no streaks, and storing a time nothing reads invites
 * the feature that reads it.</p>
 */
public record KnownStoreModule() implements DomModule<KnownStoreModule> {

    /** The store itself — {@code load}, {@code save}, {@code clear}, {@code isKey}. */
    public record KnownStore() implements Exportable._Class<KnownStoreModule> {}

    /** Factory, matching the {@code createX()} convention in adjacent modules. */
    public record createKnownStore() implements Exportable._Constant<KnownStoreModule> {}

    public static final KnownStoreModule INSTANCE = new KnownStoreModule();

    @Override
    public ImportsFor<KnownStoreModule> imports() {
        return ImportsFor.<KnownStoreModule>builder().build();
    }

    @Override
    public ExportsOf<KnownStoreModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new KnownStore(), new createKnownStore()));
    }
}
