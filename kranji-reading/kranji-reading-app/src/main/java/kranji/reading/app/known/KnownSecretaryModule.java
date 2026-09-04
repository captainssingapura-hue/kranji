package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The Secretary for the known-set Party.
 *
 * <p>A fourth bus, alongside navigation, character selection and article
 * selection — and different in kind from all three. Those relay a selection
 * and forget it; this one <em>is</em> the state. The control marks a reading,
 * the viewer lists it, and the reader will stop annotating it, none of the
 * three naming the others.</p>
 *
 * <p>Every broadcast carries the whole set rather than a delta. A set of
 * readings a child can manage is small, and carrying it whole means a pane
 * that mounted late, or one that was not listening when a reading was marked,
 * is correct as soon as the next change arrives — no replay, no resync.</p>
 *
 * <h2>Messages</h2>
 * <pre>{@code
 * in : { kind: "MarkKnown",   key: "34892:xíng" }
 *      { kind: "UnmarkKnown", key: "34892:xíng" }
 *      { kind: "SeedKnown",   known: [...], lastImport: [...] }  // the device
 *      { kind: "ImportKnown", known: [...] }   // a bulk list from a file
 *      { kind: "UndoImport" }                  // take that list back, whole
 *      { kind: "WhatIsKnown" }                 // a member that has just joined
 * out: { kind: "KnownChanged", known: [...], lastImport: [...], changed: key | null }
 * }</pre>
 *
 * <p>{@code ImportKnown} is a union that remembers <em>what it added</em> — not
 * what it contained — so {@code UndoImport} can take back an optimistic import
 * whole without destroying marks that were already there. Marking or unmarking
 * a reading by hand removes it from that batch, so a reading claimed
 * deliberately since the import survives the undo.</p>
 *
 * <p>{@code SeedKnown} is a <em>union</em>, never a replace, which is what
 * lets every pane seed from storage without electing one of them to do it: a
 * second seed of the same rows changes nothing and broadcasts nothing. It also
 * means a slow load cannot take back a reading claimed while it was in
 * flight.</p>
 *
 * <h2>State shape</h2>
 * <pre>{@code
 * {
 *     known        : ["34892:xíng", ...],   // insertion order
 *     lastImport   : ["34892:xíng", ...],   // what the last import added
 *     recentUnknown: [{ kind, from }]       // bounded at 10
 * }
 * }</pre>
 *
 * <p>Keys come from {@link KnownSetModule}, which is where the meaning of a
 * key lives; this only holds them. Nothing here persists — a Secretary's state
 * dies with the workspace, and storage is a separate concern.</p>
 */
public record KnownSecretaryModule() implements DomModule<KnownSecretaryModule> {

    /** The single export — a JS object with {@code initial} and {@code behavior}. */
    public record KnownSecretary() implements Exportable._Constant<KnownSecretaryModule> {}

    public static final KnownSecretaryModule INSTANCE = new KnownSecretaryModule();

    @Override
    public ImportsFor<KnownSecretaryModule> imports() {
        return ImportsFor.<KnownSecretaryModule>builder().build();
    }

    @Override
    public ExportsOf<KnownSecretaryModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new KnownSecretary()));
    }
}
