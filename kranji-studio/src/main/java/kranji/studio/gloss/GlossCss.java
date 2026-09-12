package kranji.studio.gloss;

import hue.captains.singapura.js.homing.core.CssClass;
import hue.captains.singapura.js.homing.core.CssGroup;
import hue.captains.singapura.js.homing.core.CssImportsFor;

import java.util.List;

/**
 * The three classes only the sound picker needs.
 *
 * <p>The frame every relation grid shares - root, bar, filter, status, grid
 * host - is {@code RelationCss} in the library bench now. What stayed is the
 * picker's own controls. Workbench tools, not a product surface —
 * everything below leans on the framework's own theme variables so a tool
 * inherits whatever the studio is wearing rather than declaring its own look
 * and then drifting from it.</p>
 */
public record GlossCss() implements CssGroup<GlossCss> {

    public static final GlossCss INSTANCE = new GlossCss();


    /** A dropdown narrowing the rows to one initial or one final. */
    public record gl_select() implements CssClass<GlossCss> {
        @Override public String body() { return """
                font: inherit;
                font-size: 13px;
                padding: 3px 4px;
                background: var(--color-surface);
                color: var(--color-text-primary);
                border: 1px solid var(--color-border);
                border-radius: 3px;
                """;
        }
    }

    /** The two bulk moves a picker needs: take everything shown, or drop it. */
    public record gl_button() implements CssClass<GlossCss> {
        @Override public String body() { return """
                font: inherit;
                font-size: 12px;
                padding: 3px 8px;
                cursor: pointer;
                background: var(--color-surface);
                color: var(--color-text-primary);
                border: 1px solid var(--color-border);
                border-radius: 3px;
                """;
        }
    }

    /**
     * What is picked, said in words.
     *
     * <p>The grid paints its own selection, but that painting is lost whenever
     * the view is remapped — filtering by initial resets it. The picked set
     * survives, so it needs somewhere to be visible that a redraw cannot
     * erase.</p>
     */
    public record gl_picked() implements CssClass<GlossCss> {
        @Override public String body() { return """
                font-size: 12px;
                color: var(--color-text-primary);
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                max-width: 40ch;
                """;
        }
    }

    @Override
    public List<CssClass<GlossCss>> cssClasses() {
        return List.of(
                new gl_select(),
                new gl_button(),
                new gl_picked());
    }

    @Override
    public CssImportsFor<GlossCss> cssImports() {
        return new CssImportsFor<>(this, List.of());
    }
}
