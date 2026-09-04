package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;
import kranji.reading.app.ui.PinyinSwfModule;

import java.util.List;

/**
 * The rule for getting the set on and off the device.
 *
 * <p>Small, and worth its own module for one reason: the rule is subtle, and it
 * was in two panes.</p>
 *
 * <h2>Nothing is written until the device has answered</h2>
 *
 * <p>The party replies to a new member immediately, with whatever it holds —
 * which at boot is an empty set, while the disk is still being read. Saving
 * that reply erases the record on every visit, silently, in a way only the
 * <em>next</em> visit can reveal. It is the kind of mistake that passes every
 * screenshot, so it is the kind that belongs in one tested place rather than
 * copied into each pane that persists.</p>
 *
 * <p>A change arriving before the load completes is held, not dropped — a
 * reading marked while the disk was still being read is written as soon as it
 * can be.</p>
 *
 * <p>Loading <em>tells the party</em> rather than returning: seeding is a union
 * at the secretary, so no pane has to be elected the loader and none can
 * clobber another. A pane that only reads simply never calls
 * {@code changed()}.</p>
 *
 * <p>The store and the party arrive as arguments and nothing here touches the
 * DOM, so it runs under GraalVM against fakes.</p>
 */
public record KnownPersistenceModule() implements DomModule<KnownPersistenceModule> {

    /** Yields {@code start} and {@code changed}. */
    public record createKnownPersistence()
            implements Exportable._Constant<KnownPersistenceModule> {}

    public static final KnownPersistenceModule INSTANCE = new KnownPersistenceModule();

    @Override
    public ImportsFor<KnownPersistenceModule> imports() {
        return ImportsFor.<KnownPersistenceModule>builder()
                .add(new ModuleImports<>(List.of(
                        new PinyinSwfModule.createPinyinSwf()),
                        PinyinSwfModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<KnownPersistenceModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownPersistence()));
    }
}
