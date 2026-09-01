package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The Secretary for the character-selection Party.
 *
 * <p>A second bus, deliberately separate from navigation. The navigator
 * relays tree positions, and every widget joined to it reads an incoming
 * {@code NavigateTo} as "a syllable was selected". A character is not a tree
 * position — terminals hold characters as <em>content</em>, not as children —
 * so putting character selections on that bus would have the syllable panes
 * treat a codepoint as a path and misbehave.</p>
 *
 * <p>Just a redirect, like the navigator: an incoming {@code ZiSelected} is
 * rebroadcast to every member as {@code ShowZi}. The producer never names its
 * consumer.</p>
 *
 * <h2>State shape</h2>
 * <pre>{@code
 * {
 *     lastSelected : { codePoint, glyph } | null,
 *     recentUnknown: [{ kind, from }]     // bounded at 10
 * }
 * }</pre>
 */
public record ZiSelectionSecretaryModule() implements DomModule<ZiSelectionSecretaryModule> {

    /** The single export — a JS object with {@code initial} and {@code behavior}. */
    public record ZiSelectionSecretary()
            implements Exportable._Constant<ZiSelectionSecretaryModule> {}

    public static final ZiSelectionSecretaryModule INSTANCE = new ZiSelectionSecretaryModule();

    @Override
    public ImportsFor<ZiSelectionSecretaryModule> imports() {
        return ImportsFor.<ZiSelectionSecretaryModule>builder().build();
    }

    @Override
    public ExportsOf<ZiSelectionSecretaryModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new ZiSelectionSecretary()));
    }
}
