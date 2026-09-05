package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;

import java.util.List;

/**
 * The one owner of the known set.
 *
 * <p>It holds the record, it holds the device, and it holds the failure.
 * Nothing else may hold any of the three.</p>
 *
 * <h2>What it replaces</h2>
 *
 * <p>The set lived in a message bus; seven components each started their own
 * persistence engine; exactly two of them ever saved, and neither was the pane
 * that claims. Whether a mark survived the session depended on which panes the
 * reader happened to have open — a property no amount of care in any one pane
 * could fix, because it was a property of there being no owner.</p>
 *
 * <h2>A write is a call; a read is a return</h2>
 *
 * <p>The party stays, because panes still need telling, but it now carries news
 * rather than state: {@code KnownChanged} names the one key that moved, and
 * {@code KnownSnapshot} answers a pane that has just arrived. Publishing the
 * whole set on every change is how a two-character edit came to cost two
 * thousand strings, six times over.</p>
 *
 * <h2>The rule that survives unchanged</h2>
 *
 * <p><strong>Nothing is written until the device has answered.</strong> Writing
 * before the load returns erases the record on every visit, silently, in a way
 * only the next visit can reveal. A claim made while the disk is still being
 * read is held, applied to the record so the reader sees it, and written the
 * moment writing is safe.</p>
 *
 * <p>A failed save is announced without being asked for, and there is no
 * argument that turns it off — the watch used to start persistence with an
 * empty handler, which is exactly how the warning went missing in the two panes
 * most likely to be open.</p>
 *
 * <p>No DOM and no globals: the store, the party and the status line arrive as
 * arguments, so this runs under GraalVM against fakes.</p>
 */
public record KnownServiceModule() implements DomModule<KnownServiceModule> {

    /** Yields {@code start}, {@code handle}, {@code has}, {@code size}, {@code status}. */
    public record createKnownService() implements Exportable._Constant<KnownServiceModule> {}

    public static final KnownServiceModule INSTANCE = new KnownServiceModule();

    @Override
    public ImportsFor<KnownServiceModule> imports() {
        return ImportsFor.<KnownServiceModule>builder()
                .add(new ModuleImports<>(
                        List.of(new KnownRecordModule.createKnownRecord()),
                        KnownRecordModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<KnownServiceModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownService()));
    }
}
