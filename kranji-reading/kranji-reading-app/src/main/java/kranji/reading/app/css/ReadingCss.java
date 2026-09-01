package kranji.reading.app.css;

import hue.captains.singapura.js.homing.core.CssClass;
import hue.captains.singapura.js.homing.core.CssGroup;
import hue.captains.singapura.js.homing.core.CssImportsFor;

import java.util.List;

/**
 * Typed CSS classes for the reading widgets.
 *
 * <p>Styling goes through this group rather than through {@code element.style},
 * and colours come from theme tokens rather than literals - both are
 * conformance rules, and both matter for a reading app in particular: the text
 * has to stay legible in whichever theme a child's device is using.</p>
 */
public record ReadingCss() implements CssGroup<ReadingCss> {

    public static final ReadingCss INSTANCE = new ReadingCss();

    /** Scroll container filling the widget pane. */
    public record kr_widget_root() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                height: 100%;
                overflow: auto;
                box-sizing: border-box;
                padding: var(--space-5, 20px);
                font-family: system-ui, sans-serif;
                color: var(--color-text-primary);
                """;
        }
    }

    /** A bordered panel holding one block of content. */
    public record kr_card() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                border: 1px solid var(--color-border);
                border-radius: 8px;
                padding: var(--space-5, 20px);
                max-width: 640px;
                background: var(--color-surface);
                """;
        }
    }

    /** Small-caps label above a heading. */
    public record kr_badge() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 11px;
                text-transform: uppercase;
                letter-spacing: 0.06em;
                color: var(--color-text-muted);
                margin-bottom: var(--space-2, 8px);
                """;
        }
    }

    /** Panel heading. */
    public record kr_title() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 20px;
                font-weight: 600;
                margin-bottom: var(--space-3, 12px);
                color: var(--color-text-primary);
                """;
        }
    }

    /** Body prose. */
    public record kr_body() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 14px;
                line-height: 1.6;
                color: var(--color-text-secondary);
                """;
        }
    }

    /** Muted status line — loading, empty state. */
    public record kr_status() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                padding: var(--space-2, 8px);
                font-size: 12px;
                color: var(--color-text-muted);
                """;
        }
    }

    /** A syllable in its written form, shown large. */
    public record kr_syllable() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 32px;
                font-weight: 600;
                letter-spacing: 0.02em;
                margin-bottom: var(--space-2, 8px);
                color: var(--color-text-primary);
                """;
        }
    }

    /** Row of characters sharing a reading. */
    public record kr_glyph_row() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-wrap: wrap;
                gap: var(--space-2, 8px);
                margin-top: var(--space-4, 16px);
                """;
        }
    }

    /** Small caption chip for a syllable part. */
    public record kr_part() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                padding: 2px 8px;
                border: 1px solid var(--color-border);
                border-radius: 999px;
                font-size: 12px;
                color: var(--color-text-secondary);
                """;
        }
    }

    /** Row of syllable-part chips. */
    public record kr_part_row() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-wrap: wrap;
                gap: var(--space-2, 8px);
                margin-bottom: var(--space-3, 12px);
                """;
        }
    }

    /**
     * One character card.
     *
     * <p>Fixed box, uniform content. Every card carries a glyph and one
     * reading — the syllable being viewed, which is the same for every card on
     * the page — so the rows stay square. Anything a particular character has
     * <em>extra</em> goes in the corner mark, which is positioned out of flow
     * and so cannot push a card taller than its neighbours.</p>
     */
    public record kr_zi_card() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                position: relative;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                gap: 4px;
                padding: var(--space-3, 12px);
                min-width: 92px;
                min-height: 92px;
                box-sizing: border-box;
                border: 1px solid var(--color-border);
                border-radius: 8px;
                background: var(--color-surface);
                cursor: default;
                transition: background 120ms ease, border-color 120ms ease,
                            transform 120ms ease;
                """;
        }
    }

    /**
     * The corner mark: this character is read more ways than one.
     *
     * <p>Out of flow, so it never changes a card's height. It says how many
     * other readings exist and nothing about what they are — the per-character
     * view is where that belongs.</p>
     */
    public record kr_zi_mark() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                position: absolute;
                top: 4px;
                right: 6px;
                font-size: 10px;
                line-height: 1;
                font-variant-numeric: tabular-nums;
                color: var(--color-text-muted);
                """;
        }
    }

    /**
     * The mark on a card whose principal reading is somewhere else.
     *
     * <p>Same position and size as {@link kr_zi_mark}, so it cannot disturb
     * the grid — only the colour differs, because "this is not how the
     * character is usually read" is worth seeing without being loud about
     * it.</p>
     */
    public record kr_zi_mark_alt() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                position: absolute;
                top: 4px;
                right: 6px;
                font-size: 10px;
                line-height: 1;
                font-variant-numeric: tabular-nums;
                font-weight: 600;
                color: var(--color-accent, var(--color-text-secondary));
                """;
        }
    }

    /**
     * A card under the pointer.
     *
     * <p>A separate class carrying {@code :hover} rather than a variant of the
     * base, because {@code variants()} re-uses the base body — it says "the
     * same rule also applies in this state", which is not what a hover
     * <em>change</em> needs. The card wears both classes.</p>
     *
     * <p>Deliberately quiet: a nudge and a warmer surface. These cards are
     * read, not clicked, so the hover should help a child keep their place
     * rather than promise that something will happen.</p>
     */
    public record kr_zi_card_hover() implements CssClass<ReadingCss> {
        @Override public String pseudoState() { return ":hover"; }
        @Override public String body() { return """
                border-color: var(--color-accent, var(--color-text-secondary));
                background: var(--color-surface-raised, var(--color-surface));
                transform: translateY(-2px);
                """;
        }
    }

    /** The character itself, shown large. */
    public record kr_zi_glyph() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 40px;
                line-height: 1.1;
                color: var(--color-text-primary);
                """;
        }
    }

    /** Every reading of a character. */
    public record kr_zi_readings() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 12px;
                color: var(--color-text-secondary);
                text-align: center;
                """;
        }
    }

    // ── Glyph typefaces ────────────────────────────────────────────────
    //
    // A browser cannot be asked what fonts are installed, so these are
    // stacks rather than families: each names the Windows, macOS and
    // cross-platform members of one style and lets the device pick. A
    // missing family costs nothing — the next one answers.
    //
    // Applied as a second class on the glyph, never as an inline style,
    // because inline styles are a conformance violation and because the
    // choice belongs to the page rather than to each character.

    /** Whatever the device already uses — no opinion. */
    public record kr_font_system() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-family: inherit;
                """;
        }
    }

    /** Heiti — the modern sans, and what most screens show by default. */
    public record kr_font_hei() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-family: "Microsoft YaHei", "PingFang SC", "Hiragino Sans GB",
                             "Noto Sans CJK SC", "Source Han Sans SC", sans-serif;
                """;
        }
    }

    /** Songti — the printing serif, with its thin horizontals. */
    public record kr_font_song() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-family: "SimSun", "Songti SC", "STSong",
                             "Noto Serif CJK SC", "Source Han Serif SC", serif;
                """;
        }
    }

    /**
     * Kaiti — the regular script.
     *
     * <p>The one that matters most here. Kaiti keeps the entry and exit of
     * each stroke visible, which is how the character is actually written,
     * and it is the script Chinese schoolbooks use for exactly that reason.
     * A child copying from Heiti is copying a shape that no hand makes.</p>
     */
    public record kr_font_kai() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-family: "KaiTi", "STKaiti", "Kaiti SC", "TW-Kai",
                             "Noto Serif CJK SC", cursive;
                """;
        }
    }

    /** Fangsong — between the two, common in official print. */
    public record kr_font_fangsong() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-family: "FangSong", "STFangsong", "Fangsong SC", serif;
                """;
        }
    }

    /** A labelled control sitting above a grid. */
    public record kr_control() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                align-items: center;
                gap: var(--space-2, 8px);
                margin-bottom: var(--space-2, 8px);
                font-size: 12px;
                color: var(--color-text-muted);
                """;
        }
    }

    /** A dropdown in a control row. */
    public record kr_select() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font: inherit;
                font-size: 12px;
                padding: 2px 6px;
                color: var(--color-text-primary);
                background: var(--color-surface);
                border: 1px solid var(--color-border);
                border-radius: 3px;
                """;
        }
    }

    /** The character, shown at reading size in the detail pane. */
    public record kr_zi_hero() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: var(--space-2, 8px);
                padding: var(--space-5, 20px);
                margin-bottom: var(--space-4, 16px);
                border: 1px solid var(--color-border);
                border-radius: 8px;
                background: var(--color-surface);
                """;
        }
    }

    /** The glyph itself, large enough to read stroke shapes. */
    public record kr_zi_hero_glyph() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 96px;
                line-height: 1.05;
                color: var(--color-text-primary);
                """;
        }
    }

    /** Codepoint and principal reading, beneath the glyph. */
    public record kr_zi_hero_meta() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 12px;
                color: var(--color-text-muted);
                font-variant-numeric: tabular-nums;
                """;
        }
    }

    /** One reading of a character, with its parts. */
    public record kr_reading_row() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                padding: var(--space-3, 12px) 0;
                border-top: 1px solid var(--color-border);
                """;
        }
    }

    /** The reading, written. */
    public record kr_reading_name() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 18px;
                font-weight: 600;
                color: var(--color-text-primary);
                margin-bottom: var(--space-2, 8px);
                """;
        }
    }

    /** The row of part chips under a reading. */
    public record kr_reading_parts() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-wrap: wrap;
                gap: 4px;
                margin-bottom: var(--space-2, 8px);
                """;
        }
    }

    // ── Reader ─────────────────────────────────────────────────────────

    /** The article title. */
    public record kr_read_title() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 22px;
                font-weight: 600;
                margin-bottom: var(--space-4, 16px);
                color: var(--color-text-primary);
                """;
        }
    }

    /** A verse, centred as a block the way a poem is set. */
    public record kr_read_verse() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-direction: column;
                align-items: center;
                margin: var(--space-4, 16px) 0;
                """;
        }
    }

    /**
     * One line of text as a two-row table: annotations above, characters below.
     *
     * <p>Fixed layout so every column is one character wide - the even rhythm
     * of 方块字, and the same grid a child writes in.</p>
     */
    public record kr_read_line() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                border-collapse: collapse;
                table-layout: fixed;
                margin: 0;
                """;
        }
    }

    // A reader cell is assembled from parts rather than enumerated: a base,
    // a size, and optionally a rule and its punctuation. Three sizes times
    // grid-on/off would otherwise be six classes that must stay in step.

    /** An annotation cell - reserved whether or not it carries anything. */
    public record kr_read_ann() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                padding: 0;
                text-align: center;
                vertical-align: bottom;
                font-family: system-ui, sans-serif;
                white-space: nowrap;
                color: var(--color-text-muted);
                """;
        }
    }

    /**
     * Hidden but still occupying its space.
     *
     * <p>Never {@code display: none} — that collapses the row and every line
     * below it moves. Switching modes must not reflow the page.</p>
     */
    public record kr_read_hidden() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                visibility: hidden;
                """;
        }
    }

    /**
     * A character square.
     *
     * <p>The box is bigger than the glyph, the way a 田字格 leaves room around
     * what a child writes. That margin is also what gives punctuation somewhere
     * to sit without widening the column.</p>
     */
    public record kr_read_zi() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                position: relative;
                padding: 0;
                text-align: center;
                vertical-align: middle;
                cursor: pointer;
                color: var(--color-text-primary);
                """;
        }
    }

    /** The practice-book rule, drawn on whatever size the cell is. */
    public record kr_read_grid() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                border: 1px solid var(--color-border);
                """;
        }
    }

    // Sizes. The annotation stays near 0.3 of the character throughout —
    // measured as the largest ratio at which the longest pinyin (shuāng,
    // chuáng) still fits one character's width without colliding.

    public record kr_read_size_s() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                width: 46px;
                height: 46px;
                font-size: 32px;
                line-height: 46px;
                """;
        }
    }

    public record kr_read_size_m() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                width: 62px;
                height: 62px;
                font-size: 42px;
                line-height: 62px;
                """;
        }
    }

    public record kr_read_size_l() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                width: 80px;
                height: 80px;
                font-size: 54px;
                line-height: 80px;
                """;
        }
    }

    public record kr_read_ann_s() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                height: 15px;
                font-size: 10px;
                line-height: 15px;
                """;
        }
    }

    public record kr_read_ann_m() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                height: 19px;
                font-size: 13px;
                line-height: 19px;
                """;
        }
    }

    public record kr_read_ann_l() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                height: 23px;
                font-size: 16px;
                line-height: 23px;
                """;
        }
    }

    /**
     * Punctuation, in the corner of the square its character owns.
     *
     * <p>Out of flow, so it cannot widen the column - which is the whole
     * reason it lives here rather than in a cell of its own. Bottom-left is
     * where 。and ，sit inside their em box when set properly.</p>
     */
    public record kr_read_punct() implements CssClass<ReadingCss> {
        @Override public String pseudoState() { return "::after"; }
        @Override public String body() { return """
                content: attr(data-punct);
                position: absolute;
                right: 0;
                bottom: 0.55em;
                width: 1em;
                font-size: 0.5em;
                line-height: 1;
                text-align: left;
                color: var(--color-text-secondary);
                pointer-events: none;
                """;
        }
    }

    // How far the mark is pushed depends on how much of its own box the
    // preceding character fills. Measured per glyph and quantised into four
    // buckets, because a computed offset would have to be an inline style.
    //
    // A character whose ink reaches its box edge (光, 床) needs the mark pushed
    // further; one that stops short (月, 乡) already has air and needs less.

    /** The character leaves plenty of room - push least. */
    public record kr_read_punct_x0() implements CssClass<ReadingCss> {
        @Override public String pseudoState() { return "::after"; }
        @Override public String body() { return """
                transform: translateX(40%);
                """;
        }
    }

    public record kr_read_punct_x1() implements CssClass<ReadingCss> {
        @Override public String pseudoState() { return "::after"; }
        @Override public String body() { return """
                transform: translateX(47%);
                """;
        }
    }

    public record kr_read_punct_x2() implements CssClass<ReadingCss> {
        @Override public String pseudoState() { return "::after"; }
        @Override public String body() { return """
                transform: translateX(54%);
                """;
        }
    }

    /** The character fills its box - push most, or the mark touches it. */
    public record kr_read_punct_x3() implements CssClass<ReadingCss> {
        @Override public String pseudoState() { return "::after"; }
        @Override public String body() { return """
                transform: translateX(61%);
                """;
        }
    }

    /**
     * An opening mark, in the leading corner of its character's square.
     *
     * <p>Drawn by {@code ::before} from {@code data-lead}, for the same reason
     * as {@link kr_read_punct}: the mark belongs to the cell, not to an
     * element inside it.</p>
     */
    public record kr_read_punct_lead() implements CssClass<ReadingCss> {
        @Override public String pseudoState() { return "::before"; }
        @Override public String body() { return """
                content: attr(data-lead);
                position: absolute;
                left: 0;
                top: 0.1em;
                width: 1em;
                font-size: 0.5em;
                line-height: 1;
                text-align: left;
                transform: translateX(-54%);
                color: var(--color-text-secondary);
                pointer-events: none;
                """;
        }
    }

    /** A row of controls above a grid — the partition picker. */
    public record kr_bar() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-wrap: wrap;
                gap: 4px;
                margin-bottom: 8px;
                """;
        }
    }

    /** One partition button. */
    public record kr_btn() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font: inherit;
                font-size: 12px;
                padding: 2px 8px;
                cursor: pointer;
                color: var(--color-text-primary);
                background: var(--color-surface);
                border: 1px solid var(--color-border);
                border-radius: 3px;
                """;
        }
    }

    /** The element a RelationGrid mounts into; it manages its own interior. */
    public record kr_grid_host() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                min-height: 320px;
                overflow: auto;
                border: 1px solid var(--color-border);
                border-radius: 3px;
                """;
        }
    }

    @Override
    public List<CssClass<ReadingCss>> cssClasses() {
        return List.of(
                new kr_widget_root(),
                new kr_bar(),
                new kr_btn(),
                new kr_grid_host(),
                new kr_card(),
                new kr_badge(),
                new kr_title(),
                new kr_body(),
                new kr_status(),
                new kr_syllable(),
                new kr_glyph_row(),
                new kr_part(),
                new kr_part_row(),
                new kr_zi_card(),
                new kr_zi_card_hover(),
                new kr_zi_glyph(),
                new kr_zi_readings(),
                new kr_zi_mark(),
                new kr_zi_mark_alt(),
                new kr_font_system(),
                new kr_font_hei(),
                new kr_font_song(),
                new kr_font_kai(),
                new kr_font_fangsong(),
                new kr_control(),
                new kr_select(),
                new kr_zi_hero(),
                new kr_zi_hero_glyph(),
                new kr_zi_hero_meta(),
                new kr_reading_row(),
                new kr_reading_name(),
                new kr_reading_parts(),
                new kr_read_title(),
                new kr_read_verse(),
                new kr_read_line(),
                new kr_read_ann(),
                new kr_read_hidden(),
                new kr_read_zi(),
                new kr_read_grid(),
                new kr_read_size_s(),
                new kr_read_size_m(),
                new kr_read_size_l(),
                new kr_read_ann_s(),
                new kr_read_ann_m(),
                new kr_read_ann_l(),
                new kr_read_punct(),
                new kr_read_punct_x0(),
                new kr_read_punct_x1(),
                new kr_read_punct_x2(),
                new kr_read_punct_x3(),
                new kr_read_punct_lead());
    }

    @Override
    public CssImportsFor<ReadingCss> cssImports() {
        return new CssImportsFor<>(this, List.of());
    }
}
