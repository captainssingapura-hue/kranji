package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The readings of one character, as cards.
 *
 * <h2>Why not the grid it replaced</h2>
 *
 * <p>A grid earns its keep when rows are compared down columns, when there are
 * enough of them to scroll, and when one of them is aimed at. None of that is
 * true here. A character has one reading, or two, rarely more — and since the
 * claim column came out, nothing in the pane acts on a row at all. What was
 * left was ten fixed-width columns of one-letter values, most of them empty,
 * and the meaning squeezed into whatever remained.</p>
 *
 * <p>The fixed widths existed to stop the claim column moving between
 * characters. With no claim column they only kept the meaning small.</p>
 *
 * <h2>What a card does instead</h2>
 *
 * <p>It uses the width it is given. The reading leads, the meaning reads as a
 * sentence rather than a cell, its examples sit under it, and the five parts of
 * the syllable run along the bottom as small labelled pairs — still side by
 * side, so two readings differing only in the tone still say so at a glance,
 * which is the one thing the columns were genuinely good for.</p>
 *
 * <p>The parts keep their short headings — ini, med, nuc, coda, tone, same —
 * for the reason they had them as columns: spelled out they cost more room than
 * the words are worth, and a reader working on sounds learns the six in a
 * sitting.</p>
 *
 * <h2>A card per sense</h2>
 *
 * <p>A reading that means three things is three things a reader could be
 * looking at. Pooling them into one cell separated by bars made the commonest
 * case — one sense — pay for the rarest, and gave no sense a place to put its
 * own examples. A reading nothing has glossed still gets its card: it is a
 * reading of this character, and the pane is showing the character rather than
 * the dictionary.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the corpus.</p>
 */
public record ZiReadingCardsModule() implements DomModule<ZiReadingCardsModule> {

    /** Yields {@code show}, {@code refresh} and {@code destroy}. */
    public record createZiReadingCards() implements Exportable._Constant<ZiReadingCardsModule> {}

    public static final ZiReadingCardsModule INSTANCE = new ZiReadingCardsModule();

    @Override
    public ImportsFor<ZiReadingCardsModule> imports() {
        return ImportsFor.<ZiReadingCardsModule>builder().build();
    }

    @Override
    public ExportsOf<ZiReadingCardsModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createZiReadingCards()));
    }
}
