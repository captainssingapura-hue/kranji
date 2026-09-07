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

    /**
     * The reader's header strip: the Settings button, and the fit line.
     *
     * <p>{@code position: relative} because the settings panel hangs off it.
     * The panel is an overlay rather than a row that opens, and that is not a
     * cosmetic choice: the reader rebuilds the whole article whenever the
     * available width crosses a band boundary, so a panel that pushed the board
     * down would relayout the page every time somebody looked at the
     * settings.</p>
     */
    public record kr_rd_head() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                position: relative;
                display: flex;
                align-items: center;
                gap: var(--space-2, 8px);
                margin-bottom: var(--space-2, 8px);
                """;
        }
    }

    /**
     * The settings panel, open.
     *
     * <p>Floats over the top of the board. Its counterpart is
     * {@link kr_kn_hidden} — one class or the other, never both, because two
     * classes that each set {@code display} are resolved by stylesheet order
     * rather than by the order they were applied.</p>
     */
    public record kr_rd_settings() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-wrap: wrap;
                align-items: center;
                gap: var(--space-2, 8px);
                position: absolute;
                top: calc(100% + 6px);
                left: 0;
                z-index: 30;
                padding: var(--space-3, 12px);
                background: var(--color-surface);
                border: 1px solid var(--color-border);
                border-radius: 8px;
                box-shadow: 0 6px 20px rgba(0, 0, 0, 0.14);
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

    /**
     * The claim, as a two-value picker inside a grid cell.
     *
     * <p>Fills its cell, because the cell's width is fixed and a control that
     * did not fill it would leave the column looking mis-sized rather than
     * deliberately sized.</p>
     */
    public record kr_kn_pick() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font: inherit;
                font-size: 12px;
                width: 100%;
                box-sizing: border-box;
                padding: 2px 4px;
                color: var(--color-text-primary);
                background: var(--color-surface);
                border: 1px solid var(--color-border);
                border-radius: 3px;
                """;
        }
    }

    /**
     * The character beside its readings, or above them.
     *
     * <p>A glyph is square and a reading is a line of prose, so stacking them
     * always wastes one dimension or the other: at any usable width the hero
     * left a band of empty pane either side of it, and the readings underneath
     * got what was left of the height.</p>
     *
     * <p>Which way round is decided by measuring, not by a media query. A
     * widget is a pane inside a workspace and can be any width at any window
     * size, so the viewport does not know.</p>
     */
    public record kr_zd_split() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-direction: row;
                align-items: flex-start;
                gap: var(--space-4, 16px);
                """;
        }
    }

    /** Narrow: the character on top, its readings under it. */
    public record kr_zd_split_narrow() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-direction: column;
                align-items: stretch;
                gap: var(--space-3, 12px);
                """;
        }
    }

    /**
     * The hero's column when the pane is wide.
     *
     * <p>It does not grow. The character is one glyph however much room there
     * is, and every pixel it took past that was one the meanings did not
     * have.</p>
     *
     * <p>The reading toggle sits here too, under the glyph. It is a fixed
     * control of two words, and in the readings column it was a flex child of
     * a stretching row, so it drew itself across the entire pane. It also
     * belongs with the character it re-reads rather than with the cards it
     * swaps out.</p>
     */
    public record kr_zd_aside() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                flex: 0 0 auto;
                display: flex;
                flex-direction: column;
                align-items: center;
                """;
        }
    }

    /**
     * The readings take the rest, and wrap across it.
     *
     * <p>A column of full-width cards wastes a wide pane exactly the way the
     * hero did: a card holds a reading, a short meaning and a few parts, and
     * stretching that across a thousand pixels leaves a line of text alone in
     * a wide box. Wrapping fills the width with cards instead of with
     * padding.</p>
     *
     * <p>{@code align-content: flex-start} so that two rows of cards sit under
     * the toggle rather than spreading down the pane.</p>
     */
    public record kr_zd_main() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                flex: 1 1 auto;
                min-width: 0;
                display: flex;
                flex-direction: row;
                flex-wrap: wrap;
                align-content: flex-start;
                gap: var(--space-3, 12px);
                """;
        }
    }

    /**
     * One reading of a character, and one of its senses.
     *
     * <p>Sized to hold what it holds. The basis is what decides how many sit
     * across a pane — around three at a wide desktop pane, two at half that,
     * one when the pane is narrow enough that the character moves above them.
     * It grows to share the row evenly and stops before a card is wider than
     * its content deserves.</p>
     *
     * <p>A minimum height so that a reading with no gloss and one with three
     * lines of meaning are recognisably the same kind of object; the row
     * stretches them level with each other beyond that.</p>
     */
    public record kr_zd_card() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                flex: 1 1 400px;
                max-width: 460px;
                min-height: 160px;
                box-sizing: border-box;
                display: flex;
                flex-direction: column;
                gap: var(--space-2, 8px);
                padding: var(--space-3, 12px) var(--space-4, 16px);
                border: 1px solid var(--color-border);
                border-radius: 8px;
                background: var(--color-surface);
                """;
        }
    }

    /** The reading, leading its card. */
    public record kr_zd_card_head() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                align-items: baseline;
                gap: var(--space-2, 8px);
                """;
        }
    }

    /** The sound itself, the one thing on the card read first. */
    public record kr_zd_card_reading() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 18px;
                font-weight: 600;
                color: var(--color-text-primary);
                """;
        }
    }

    /** What it means, as a sentence rather than a cell. */
    public record kr_zd_card_meaning() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 14px;
                line-height: 1.45;
                color: var(--color-text-primary);
                """;
        }
    }

    /** The phrases that show the sense in use. */
    public record kr_zd_card_examples() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 14px;
                color: var(--color-text-muted);
                """;
        }
    }

    /**
     * The five parts of the syllable, along the bottom.
     *
     * <p>Wrapping rather than fixed: they were columns to keep two readings
     * comparable, and they still sit side by side, but a narrow card should
     * fold them instead of scrolling sideways.</p>
     */
    public record kr_zd_card_parts() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                margin-top: auto;
                display: flex;
                flex-wrap: wrap;
                gap: var(--space-2, 8px) var(--space-4, 16px);
                padding-top: var(--space-2, 8px);
                border-top: 1px solid var(--color-border);
                font-size: 12px;
                color: var(--color-text-primary);
                font-variant-numeric: tabular-nums;
                """;
        }
    }

    /** One part: its short heading, then its value. */
    public record kr_zd_card_part() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                align-items: baseline;
                gap: var(--space-1, 4px);
                """;
        }
    }

    /** The heading half of a part, kept quiet so the value reads. */
    public record kr_zd_card_part_key() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                color: var(--color-text-muted);
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

    /**
     * What a reading means, under the reading itself.
     *
     * <p>Set below the sound rather than beside it. A reading and its meaning
     * are two different kinds of answer, and running them together on one line
     * makes the pinyin harder to find — which is the thing a reader came for
     * first. Quieter than the reading, and quiet enough that a character with
     * no gloss looks like a character with nothing to add rather than like a
     * pane that failed to load.</p>
     */
    public record kr_reading_meaning() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 14px;
                line-height: 1.4;
                color: var(--color-text-secondary, #555);
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
                line-height: 1.9;
                """;
        }
    }

    /**
     * One character of the title, with room over it for its reading.
     *
     * <p>{@code ruby} rather than a bracketed reading after the word. A title
     * is short and is read as a name, and {@code 从(cóng)这(zhè)里(lǐ)} is not a
     * name any more — the brackets are longer than the thing they gloss. Over
     * the character, the reading takes no width at all and the title still
     * reads as one phrase.</p>
     *
     * <p>The line-height above is what pays for it. Ruby that has to find its
     * own room pushes the paragraph under it down the first time a reading
     * appears, and the title's job is to be the thing that does not move.</p>
     */
    public record kr_read_title_zi() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                ruby-position: over;
                ruby-align: center;
                """;
        }
    }

    /**
     * The reading over one character of the title.
     *
     * <p>Italic and lighter, because a reading is not part of the name. In the
     * article the annotation is told apart by its row; a title has no grid, so
     * the face has to do it.</p>
     *
     * <p>A sans face, never the reader's chosen CJK typeface. Kaiti's Latin is
     * an afterthought and pinyin set in it is harder to read than the character
     * it is helping with.</p>
     *
     * <p>Hidden with {@code visibility}, like the annotation in the article and
     * for the same reason: a reading leaving must not move the title, or the
     * page shifts under a child as a reward for having learnt something.</p>
     */
    public record kr_read_title_rt() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-family: system-ui, sans-serif;
                font-size: 11px;
                font-style: italic;
                font-weight: 400;
                letter-spacing: 0;
                color: var(--color-text-muted);
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

    /** One reading, with its own toggle — the reading left, the claim right. */
    public record kr_kn_line() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                align-items: center;
                justify-content: space-between;
                gap: 12px;
                padding: 6px 0;
                border-bottom: 1px solid var(--color-border);
                """;
        }
    }

    /**
     * Everything said about one reading, stacked, left of its button.
     *
     * <p>{@code min-width: 0} because a flex child will not shrink below its
     * content by default, and a long gloss would otherwise push the button off
     * the edge of a narrow pane rather than wrapping.</p>
     */
    public record kr_kn_claim() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-direction: column;
                min-width: 0;
                """;
        }
    }

    /**
     * A toggle that is currently claimed.
     *
     * <p>Filled rather than outlined, because the question the pane answers at
     * a glance is which readings are already in — and an outline reads as an
     * offer, not a fact.</p>
     */
    public record kr_kn_on() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                color: var(--color-surface);
                background: var(--color-text-primary);
                border-color: var(--color-text-primary);
                """;
        }
    }

    /**
     * A control the page drives rather than the person — the download anchor
     * and the file picker, both clicked from a button that says what it does.
     */
    public record kr_kn_hidden() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: none;
                """;
        }
    }

    /**
     * The exported record, shown so it can be read and selected by hand.
     *
     * <p>Monospaced and tall enough to see that something real came out —
     * a copy that reports success and produced nothing is worse than one that
     * fails loudly.</p>
     */
    public record kr_kn_text() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: block;
                width: 100%;
                box-sizing: border-box;
                height: 120px;
                margin: 8px 0;
                padding: 8px;
                font-family: ui-monospace, monospace;
                font-size: 12px;
                line-height: 1.5;
                color: var(--color-text-primary);
                background: var(--color-surface);
                border: 1px solid var(--color-border);
                border-radius: 3px;
                """;
        }
    }

    /** The count line above the viewer's grid. */
    public record kr_kn_tally() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 12px;
                color: var(--color-text-secondary);
                margin-left: auto;
                align-self: center;
                """;
        }
    }

    // ── Progress: two designs, not one design rotated ──────────────────
    //
    // A wide pane gets a bar; a narrow one gets a named list. They share no
    // markup, because the first attempt shared it - one row of segments with
    // the flex direction swapped - and that is the thing worth not doing. A
    // vertical bar is a horizontal bar turned on its side, which wastes the
    // one thing a narrow column has plenty of, and it made a pane whose shape
    // depended on a class applied in the right order at the right moment.
    //
    // Both are segmented by band rather than filled by proportion, and that is
    // a design decision before it is a styling one: a continuous bar would be
    // drawn against the corpus, and 248 characters of 8,100 is a sliver saying
    // a term's work came to nothing. Bands say something truer - eleven places
    // to stand, and you are at the fourth.

    /** The ladder grid: a row of bands when wide, a column of them when narrow. */
    public record kr_pb_grid() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                margin-bottom: var(--space-4, 16px);
                """;
        }
    }

    // ── A binary choice, drawn as one ──────────────────────────────────
    //
    // Two segments in a track, one lit. A button whose label changed to say
    // what pressing it would do next made a reader work out the current state
    // from the name of the other one; both states are named here and the lit
    // one is the answer.

    /** The track holding the two halves. */
    public record kr_seg() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: inline-flex;
                border: 1px solid var(--color-border);
                border-radius: 999px;
                overflow: hidden;
                margin: var(--space-2, 8px) 0;
                """;
        }
    }

    /** One half, unlit. */
    public record kr_seg_opt() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                appearance: none;
                border: 0;
                background: transparent;
                padding: 4px 14px;
                font: inherit;
                font-size: 12px;
                line-height: 1.6;
                color: var(--color-text-secondary);
                cursor: pointer;
                """;
        }
    }

    /** The half that is the case. */
    public record kr_seg_on() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                appearance: none;
                border: 0;
                padding: 4px 14px;
                font: inherit;
                font-size: 12px;
                line-height: 1.6;
                cursor: default;
                font-weight: 600;
                background: var(--color-accent);
                color: var(--color-surface);
                """;
        }
    }

    /**
     * Wide: a band is a tile — the mark above the name.
     *
     * <p>Square-ish and fixed, so eleven of them read as eleven equal places
     * rather than as columns sized by how long each name happens to be. The
     * mark is what the eye finds first and the name is what it confirms, which
     * is the order they are stacked in.</p>
     */
    public record kr_pb_cell() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                gap: 6px;
                box-sizing: border-box;
                width: 92px;
                height: 92px;
                padding: 6px 4px;
                text-align: center;
                color: var(--color-text-secondary);
                """;
        }
    }

    /**
     * Narrow: a band is a line, because the column has width and not height.
     *
     * <p>The horizontal padding is there for the standing band's background to
     * sit in. Without it the tint would hug the text and read as a highlighter
     * stroke rather than as a place.</p>
     */
    public record kr_pb_line() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: flex;
                flex-direction: row;
                align-items: center;
                gap: var(--space-2, 8px);
                padding: 3px 8px;
                color: var(--color-text-secondary);
                """;
        }
    }

    /** The mark. */
    public record kr_pb_badge() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 22px;
                line-height: 1;
                """;
        }
    }

    /** The name under a tile: small, and allowed to wrap onto two lines. */
    public record kr_pb_name() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-size: 12px;
                line-height: 1.25;
                overflow-wrap: anywhere;
                """;
        }
    }

    /** The name beside a mark, on a line. */
    public record kr_pb_name_line() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                """;
        }
    }

    /** A band already passed. */
    public record kr_pb_past() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                color: var(--color-text-primary);
                """;
        }
    }

    /**
     * The band the reader is standing in.
     *
     * <p>Painted on a span inside the cell rather than on the cell itself, so
     * it cannot fight with the grid's own selection painting. The cursor is a
     * separate thing that moves; this does not.</p>
     *
     * <h2>The background is a hint, not a highlight</h2>
     *
     * <p>A tenth of the accent, which is enough to find at a glance and quiet
     * enough that the cursor's own box still reads as the louder of the two —
     * it has to, because the cursor is the thing a reader is moving and this is
     * the thing they are not.</p>
     *
     * <p>Two background declarations, and the order is the fallback. A browser
     * without {@code color-mix} drops the second and keeps the raised surface,
     * which is duller but still says "this one"; one with it gets the tint. No
     * {@code @supports} needed — an unparsable declaration is simply
     * skipped.</p>
     */
    public record kr_pb_here() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-weight: 700;
                color: var(--color-accent);
                border-radius: 8px;
                background: var(--color-surface-raised, var(--color-surface));
                background: color-mix(in srgb, var(--color-accent) 10%, transparent);
                """;
        }
    }

    /** What the cursor is resting on, said underneath. */
    public record kr_pb_detail() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                color: var(--color-text-secondary);
                min-height: 1.4em;
                """;
        }
    }

    @Override
    public List<CssClass<ReadingCss>> cssClasses() {
        return List.of(
                new kr_seg(),
                new kr_seg_opt(),
                new kr_seg_on(),
                new kr_pb_grid(),
                new kr_pb_cell(),
                new kr_pb_line(),
                new kr_pb_badge(),
                new kr_pb_name(),
                new kr_pb_name_line(),
                new kr_pb_past(),
                new kr_pb_here(),
                new kr_pb_detail(),
                new kr_widget_root(),
                new kr_bar(),
                new kr_btn(),
                new kr_kn_line(),
                new kr_kn_claim(),
                new kr_kn_on(),
                new kr_kn_tally(),
                new kr_kn_hidden(),
                new kr_kn_text(),
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
                new kr_rd_head(),
                new kr_rd_settings(),
                new kr_select(),
                new kr_kn_pick(),
                new kr_zd_split(),
                new kr_zd_split_narrow(),
                new kr_zd_aside(),
                new kr_zd_main(),
                new kr_zd_card(),
                new kr_zd_card_head(),
                new kr_zd_card_reading(),
                new kr_zd_card_meaning(),
                new kr_zd_card_examples(),
                new kr_zd_card_parts(),
                new kr_zd_card_part(),
                new kr_zd_card_part_key(),
                new kr_zi_hero(),
                new kr_zi_hero_glyph(),
                new kr_zi_hero_meta(),
                new kr_reading_row(),
                new kr_reading_name(),
                new kr_reading_meaning(),
                new kr_reading_parts(),
                new kr_read_title(),
                new kr_read_title_zi(),
                new kr_read_title_rt(),
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
                new kr_read_punct_lead(),
                new kr_gr_cell(),
                new kr_gr_ann(),
                new kr_gr_zi(),
                new kr_gr_known(),
                new kr_gr_grid(),
                new kr_gr_host(),
                new kr_gr_ruled(),
                new kr_gr_quiet());
    }

    // ── The grid reader's square ───────────────────────────────────────
    //
    // Separate from the table reader's classes rather than shared. A grid
    // cell is a div the grid owns, not a td we made, and the spike must not
    // be able to perturb the reader that works.

    /**
     * One square: annotation stacked over the character.
     *
     * <p>{@code position: relative} because the mastery tick hangs off its
     * bottom-right. The tick cannot live inside the character box: that box has
     * its textContent rewritten on every repaint, and assigning textContent
     * replaces every child.</p>
     */
    public record kr_gr_cell() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                position: relative;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: flex-end;
                padding: 0;
                cursor: pointer;
                box-sizing: border-box;
                """;
        }
    }

    /** The reading, above the character. Fixed height so hiding cannot reflow. */
    public record kr_gr_ann() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                font-family: system-ui, sans-serif;
                white-space: nowrap;
                color: var(--color-text-muted);
                """;
        }
    }

    /** The character. Its own box, so punctuation can hang off the edge. */
    public record kr_gr_zi() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                position: relative;
                text-align: center;
                color: var(--color-text-primary);
                """;
        }
    }

    /**
     * The board host: shrinks to its content instead of filling the pane.
     *
     * <p>A grid table stretches to whatever it is given, which for a page of
     * squares means the squares stop being square. Minesweeper uses
     * {@code inline-block} for the same reason - the board is a fixed shape,
     * not a layout that adapts.</p>
     *
     * <p>Block rather than inline-block, though: an article is a column of
     * paragraphs, and inline-block lets a second board sit beside the first
     * whenever the pane happens to be wide enough. {@code width: fit-content}
     * keeps the shrink without the flow.</p>
     */
    public record kr_gr_host() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                display: block;
                width: fit-content;
                margin-bottom: var(--space-4, 16px);
                border-radius: 6px;
                overflow: hidden;
                """;
        }
    }

    /**
     * Silences the grid substrate's own cell chrome.
     *
     * <p>RelationGrid draws a hairline on the bottom and right of every
     * {@code td}. That is right for a data table and wrong for a page of
     * squares: the ruling here belongs to the exercise book and is drawn on the
     * character box by {@link kr_gr_grid}, so the grid's own lines sit
     * underneath it as a second set that no control turns off.</p>
     *
     * <p>{@code border-color: transparent} rather than {@code border: none},
     * because those hairlines occupy space — removing them would shift every
     * square, and the reader's rule is that toggling a setting never moves
     * anything.</p>
     *
     * <p><b>{@code !important} is load-bearing here, not laziness.</b> The grid
     * injects its stylesheet <em>unlayered</em> from {@code GridLayoutModule},
     * and an unlayered normal declaration outranks every layer — so this rule,
     * which lands in {@code @layer component}, loses on the cascade no matter
     * how specific it is. Raising importance is the only lever a layer has
     * against unlayered CSS. The grid offers no option for this: the border is
     * hardcoded and so is {@code td.className}.</p>
     */
    public record kr_gr_quiet() implements CssClass<ReadingCss> {
        @Override public String pseudoState() { return " .hgr-td"; }
        @Override public String body() { return """
                border-color: transparent !important;
                """;
        }
    }

    /**
     * The board outline, drawn only when the practice grid is on.
     *
     * <p>Separate from {@link kr_gr_host} because the outline is part of the
     * ruling: with the grid off the squares lose their lines, and a rectangle
     * still around the paragraph is the one line nobody asked for.</p>
     */
    public record kr_gr_ruled() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                border: 1px solid var(--color-border);
                """;
        }
    }

    /**
     * The mastery mark: this reading is already claimed.
     *
     * <p>A green tick. It began as a small accent dot, on the reasoning that a
     * mark competing with the character makes the page harder to read — true in
     * principle, and in practice the dot was so quiet that the one moment worth
     * celebrating did not register at all. A tick is read as <em>done</em>
     * without having to be learnt, and green says it in a way no amount of
     * accent colour does.</p>
     *
     * <p>Its own font, not the reader's. The character box may be set in Kaiti
     * or Fangsong, and a tick rendered from a calligraphic CJK face is a
     * different shape on every typeface the picker offers.</p>
     *
     * <p>The halo is {@code --color-surface}, so the tick stays legible where
     * it overlaps a dense character's strokes and does so in whichever theme is
     * running. The green is a token with a fallback: no theme defines
     * {@code --color-success} yet, and when one does it should win.</p>
     *
     * <p>Absolutely positioned, so appearing and disappearing cannot move a
     * square. That is the same rule the annotation follows: earning a mark must
     * never shift the page under the child who earned it.</p>
     *
     * <p>Bottom-right, where the practice-book rule is quietest and where a
     * character's own strokes are least likely to reach.</p>
     */
    public record kr_gr_known() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                position: absolute;
                right: 3px;
                bottom: 1px;
                font-family: system-ui, sans-serif;
                font-size: 13px;
                font-weight: 700;
                line-height: 1;
                color: var(--color-success, #2f9e44);
                text-shadow: 0 0 2px var(--color-surface),
                             0 0 2px var(--color-surface);
                pointer-events: none;
                """;
        }
    }


    /** The practice-book rule, on the character box rather than the whole cell. */
    public record kr_gr_grid() implements CssClass<ReadingCss> {
        @Override public String body() { return """
                border: 1px solid var(--color-border);
                """;
        }
    }

    @Override
    public CssImportsFor<ReadingCss> cssImports() {
        return new CssImportsFor<>(this, List.of());
    }
}
