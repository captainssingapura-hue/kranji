package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.CssClass;
import hue.captains.singapura.js.homing.core.CssGroup;
import hue.captains.singapura.js.homing.core.CssImportsFor;

import java.util.List;

/**
 * Typed CSS for the article workbench.
 *
 * <p>Colours come from theme tokens, like everything else in the studio: this
 * is a tool a person works in for an hour at a time, and it has no business
 * ignoring the theme they chose.</p>
 *
 * <h2>Every class is set on an element this code built</h2>
 *
 * <p>An earlier draft had three of these as descendant selectors — a
 * {@code pseudoState} of {@code " .kw-run"} matching a class name buried in a
 * string of HTML. That is what styling injected markup forces, and it is worth
 * naming as the tell: a selector reaching for a class nothing in Java sets
 * means something is building DOM the framework does not own. The pane builds
 * its own elements now, so each of these is set on one of them by name.</p>
 *
 * <p>The preview is styled as a <em>document</em>, not as the reader's grid.
 * Stage one asks whether the file says what its author meant; what a child will
 * see is stage two's question, and stage two's Chinese-optimised table.</p>
 */
public record ArticleWorkbenchCss() implements CssGroup<ArticleWorkbenchCss> {

    public static final ArticleWorkbenchCss INSTANCE = new ArticleWorkbenchCss();

    // ── The bench ──────────────────────────────────────────────────────

    /** The whole bench: drafts on the left, what one says on the right. */
    public record aw_root() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                height: 100%;
                display: flex;
                flex-direction: column;
                box-sizing: border-box;
                font-family: system-ui, sans-serif;
                color: var(--color-text-primary);
                """;
        }
    }

    /** Where the drafts are being read from, and the button that re-reads them. */
    public record aw_head() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                display: flex;
                align-items: center;
                gap: var(--space-3, 12px);
                padding: var(--space-2, 8px) var(--space-3, 12px);
                border-bottom: 1px solid var(--color-border);
                font-size: 12px;
                color: var(--color-text-muted);
                """;
        }
    }

    public record aw_btn() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                appearance: none;
                border: 1px solid var(--color-border);
                border-radius: 6px;
                background: var(--color-surface);
                color: var(--color-text-primary);
                padding: 3px 10px;
                font: inherit;
                cursor: pointer;
                """;
        }
    }

    public record aw_split() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                flex: 1 1 auto;
                display: flex;
                min-height: 0;
                """;
        }
    }

    /** The draft list. Fixed, because a file name is not what you are reading. */
    public record aw_list() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                flex: 0 0 260px;
                overflow: auto;
                border-right: 1px solid var(--color-border);
                padding: var(--space-2, 8px);
                """;
        }
    }

    public record aw_item() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                padding: 6px 8px;
                border-radius: 6px;
                cursor: pointer;
                font-size: 13px;
                line-height: 1.35;
                """;
        }
    }

    public record aw_item_on() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                padding: 6px 8px;
                border-radius: 6px;
                cursor: pointer;
                font-size: 13px;
                line-height: 1.35;
                background: var(--color-surface-raised, var(--color-surface));
                border-left: 3px solid var(--color-accent, var(--color-text-secondary));
                """;
        }
    }

    /** How big a draft is, in characters. Bytes would be 2.6x on Chinese. */
    public record aw_size() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                display: block;
                font-size: 11px;
                color: var(--color-text-muted);
                font-variant-numeric: tabular-nums;
                """;
        }
    }

    /**
     * The document above, the findings below.
     *
     * <p>It scrolls nothing itself. Both halves do their own scrolling, which
     * is what keeps the findings on screen: they are the point of the bench,
     * and a long document that pushed them below the fold would hide the one
     * thing an author came here to read.</p>
     */
    public record aw_main() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                flex: 1 1 auto;
                min-width: 0;
                min-height: 0;
                overflow: hidden;
                display: flex;
                flex-direction: column;
                """;
        }
    }

    /** What the document scrolls in. */
    public record aw_preview() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                flex: 1 1 auto;
                min-height: 0;
                overflow: auto;
                """;
        }
    }

    /**
     * The document itself.
     *
     * <p>A reading measure of text, not the full pane width. Stage one is
     * about whether the document says what its author meant, and a line of
     * Chinese a thousand pixels wide is hard to check.</p>
     */
    public record aw_page() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                padding: var(--space-5, 20px);
                max-width: 46em;
                line-height: 1.9;
                font-size: 16px;
                """;
        }
    }

    // ── The document ───────────────────────────────────────────────────

    public record aw_title() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                font-size: 24px;
                font-weight: 600;
                line-height: 1.5;
                margin: 0 0 var(--space-4, 16px) 0;
                """;
        }
    }

    public record aw_h2() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                font-size: 19px;
                font-weight: 600;
                line-height: 1.5;
                margin: var(--space-5, 20px) 0 var(--space-2, 8px) 0;
                padding-bottom: 4px;
                border-bottom: 1px solid var(--color-border);
                """;
        }
    }

    public record aw_h3() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                font-size: 16px;
                font-weight: 600;
                line-height: 1.5;
                margin: var(--space-4, 16px) 0 var(--space-1, 4px) 0;
                """;
        }
    }

    /**
     * A heading's pinned id.
     *
     * <p>Shown rather than hidden, because it is the address a reader keeps
     * and an author has to be able to see which one a heading has.</p>
     */
    public record aw_pin() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                margin-left: var(--space-2, 8px);
                font-size: 11px;
                font-weight: 400;
                font-family: ui-monospace, monospace;
                color: var(--color-text-muted);
                """;
        }
    }

    /** A heading with no pinned id yet — it has no address a reader can keep. */
    public record aw_unpinned() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                margin-left: var(--space-2, 8px);
                font-size: 11px;
                font-weight: 400;
                color: var(--color-text-muted);
                opacity: 0.7;
                """;
        }
    }

    /** 首行缩进两格 — the indent a Chinese paragraph is written with. */
    public record aw_p() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                margin: 0 0 var(--space-3, 12px) 0;
                text-indent: 2em;
                """;
        }
    }

    /** One list item. The marker is an element, so the text can wrap under it. */
    public record aw_li() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                display: flex;
                gap: var(--space-2, 8px);
                margin: 0 0 4px 0;
                padding-left: var(--space-3, 12px);
                """;
        }
    }

    public record aw_marker() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                flex: 0 0 auto;
                color: var(--color-text-muted);
                font-variant-numeric: tabular-nums;
                """;
        }
    }

    public record aw_quote() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                margin: var(--space-3, 12px) 0;
                padding: 2px 0 2px var(--space-4, 16px);
                border-left: 3px solid var(--color-border);
                color: var(--color-text-secondary, var(--color-text-muted));
                """;
        }
    }

    /** Verse: the lines the author wrote, kept as they were written. */
    public record aw_verse() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                margin: var(--space-3, 12px) 0;
                padding-left: var(--space-4, 16px);
                border-left: 2px solid var(--color-border);
                """;
        }
    }

    public record aw_vline() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                margin: 0;
                """;
        }
    }

    /** A ‹…› run, marked so an author can see the reader will not practise it. */
    public record aw_run() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                background: var(--color-surface-raised, var(--color-surface));
                border-radius: 3px;
                padding: 0 3px;
                """;
        }
    }

    /** An override: the character, with the reading its author pinned to it. */
    public record aw_ruby() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                ruby-align: center;
                """;
        }
    }

    public record aw_rt() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                font-size: 0.5em;
                font-family: system-ui, sans-serif;
                color: var(--color-accent, var(--color-text-muted));
                """;
        }
    }

    /** Not the document: what the pane has to say instead of one. */
    public record aw_msg() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                color: var(--color-text-muted);
                text-indent: 0;
                """;
        }
    }

    // ── What the subset had to say ─────────────────────────────────────

    /** What the subset had to say. Empty when it had nothing. */
    public record aw_findings() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                flex: 0 1 auto;
                max-height: 40%;
                overflow: auto;
                border-top: 1px solid var(--color-border);
                padding: var(--space-3, 12px);
                font-size: 12px;
                """;
        }
    }

    public record aw_finding() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                display: flex;
                gap: var(--space-2, 8px);
                padding: 2px 0;
                font-variant-numeric: tabular-nums;
                """;
        }
    }

    /** An error: the document does not render at all. */
    public record aw_error() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                flex: 0 0 5.5em;
                font-weight: 600;
                color: var(--color-danger, var(--color-text-primary));
                """;
        }
    }

    /** A warning: it rendered, with something dropped. */
    public record aw_warn() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                flex: 0 0 5.5em;
                color: var(--color-text-muted);
                """;
        }
    }

    public record aw_status() implements CssClass<ArticleWorkbenchCss> {
        @Override public String body() { return """
                padding: var(--space-3, 12px);
                font-size: 12px;
                color: var(--color-text-muted);
                """;
        }
    }

    @Override
    public List<CssClass<ArticleWorkbenchCss>> cssClasses() {
        return List.of(
                new aw_root(), new aw_head(), new aw_btn(), new aw_split(),
                new aw_list(), new aw_item(), new aw_item_on(), new aw_size(),
                new aw_main(), new aw_preview(), new aw_page(),
                new aw_title(), new aw_h2(), new aw_h3(), new aw_pin(),
                new aw_unpinned(), new aw_p(), new aw_li(), new aw_marker(),
                new aw_quote(), new aw_verse(), new aw_vline(), new aw_run(),
                new aw_ruby(), new aw_rt(), new aw_msg(),
                new aw_findings(), new aw_finding(),
                new aw_error(), new aw_warn(), new aw_status());
    }

    @Override
    public CssImportsFor<ArticleWorkbenchCss> cssImports() {
        return new CssImportsFor<>(this, List.of());
    }
}
