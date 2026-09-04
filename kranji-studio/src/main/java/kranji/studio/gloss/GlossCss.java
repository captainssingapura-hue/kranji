package kranji.studio.gloss;

import hue.captains.singapura.js.homing.core.CssClass;
import hue.captains.singapura.js.homing.core.CssGroup;
import hue.captains.singapura.js.homing.core.CssImportsFor;

import java.util.List;

/**
 * The few classes the studio's internal tools need.
 *
 * <p>Kept small on purpose. These are workbench tools, not a product surface —
 * everything below leans on the framework's own theme variables so a tool
 * inherits whatever the studio is wearing rather than declaring its own look
 * and then drifting from it.</p>
 */
public record GlossCss() implements CssGroup<GlossCss> {

    public static final GlossCss INSTANCE = new GlossCss();

    /** The widget's outermost element. */
    public record gl_root() implements CssClass<GlossCss> {
        @Override public String body() { return """
                height: 100%;
                display: flex;
                flex-direction: column;
                gap: 8px;
                box-sizing: border-box;
                padding: 12px;
                font-family: system-ui, sans-serif;
                color: var(--color-text-primary);
                """;
        }
    }

    /** The relation picker and its count, on one line. */
    public record gl_bar() implements CssClass<GlossCss> {
        @Override public String body() { return """
                display: flex;
                align-items: center;
                gap: 8px;
                flex-wrap: wrap;
                """;
        }
    }

    /** What the grid is showing, and how much of it. */
    public record gl_status() implements CssClass<GlossCss> {
        @Override public String body() { return """
                font-size: 12px;
                color: var(--color-text-muted);
                margin-left: auto;
                font-variant-numeric: tabular-nums;
                """;
        }
    }


    /** A search box over the rows in hand. */
    public record gl_find() implements CssClass<GlossCss> {
        @Override public String body() { return """
                font: inherit;
                font-size: 13px;
                padding: 3px 6px;
                min-width: 14ch;
                background: var(--color-surface);
                color: var(--color-text-primary);
                border: 1px solid var(--color-border);
                border-radius: 3px;
                """;
        }
    }

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

    /** The element a RelationGrid mounts into; it manages its own interior. */
    public record gl_grid_host() implements CssClass<GlossCss> {
        @Override public String body() { return """
                flex: 1 1 auto;
                min-height: 240px;
                overflow: auto;
                border: 1px solid var(--color-border);
                border-radius: 3px;
                """;
        }
    }

    @Override
    public List<CssClass<GlossCss>> cssClasses() {
        return List.of(
                new gl_root(),
                new gl_bar(),
                new gl_status(),
                new gl_find(),
                new gl_select(),
                new gl_button(),
                new gl_picked(),
                new gl_grid_host());
    }

    @Override
    public CssImportsFor<GlossCss> cssImports() {
        return new CssImportsFor<>(this, List.of());
    }
}
