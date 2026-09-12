package kranji.reading.workbench.relation;

import hue.captains.singapura.js.homing.core.CssClass;
import hue.captains.singapura.js.homing.core.CssGroup;
import hue.captains.singapura.js.homing.core.CssImportsFor;

import java.util.List;

/**
 * The five classes a relation grid needs, and no more.
 *
 * <p>Workbench tools, not a product surface — everything below leans on the
 * framework's theme variables so a tool inherits whatever its host is wearing
 * rather than declaring its own look and then drifting from it. Moved here
 * from the studio's gloss CSS so the bench and the studio draw a relation the
 * same way; the studio keeps the classes only its own picker uses.</p>
 */
public record RelationCss() implements CssGroup<RelationCss> {

    public static final RelationCss INSTANCE = new RelationCss();

    /** The widget's outermost element. */
    public record rel_root() implements CssClass<RelationCss> {
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

    /** The filter, the refresh and the count, on one line. */
    public record rel_bar() implements CssClass<RelationCss> {
        @Override public String body() { return """
                display: flex;
                align-items: center;
                gap: 8px;
                flex-wrap: wrap;
                """;
        }
    }

    /** What the grid is showing, and how much of it. */
    public record rel_status() implements CssClass<RelationCss> {
        @Override public String body() { return """
                font-size: 12px;
                color: var(--color-text-muted);
                margin-left: auto;
                font-variant-numeric: tabular-nums;
                """;
        }
    }

    /** A search box over the rows in hand. */
    public record rel_find() implements CssClass<RelationCss> {
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

    /** The element a RelationGrid mounts into; it manages its own interior. */
    public record rel_grid_host() implements CssClass<RelationCss> {
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
    public List<CssClass<RelationCss>> cssClasses() {
        return List.of(new rel_root(), new rel_bar(), new rel_status(), new rel_find(),
                       new rel_grid_host());
    }

    @Override
    public CssImportsFor<RelationCss> cssImports() {
        return new CssImportsFor<>(this, List.of());
    }
}
