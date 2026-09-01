package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * Fills in the readings an article did not state.
 *
 * <p>An article carries its text and only the readings its author chose against
 * the corpus principal. Everything else comes from {@code /syllable-map}, which
 * is why correcting a reading in the corpus corrects every article rather than
 * none of them.</p>
 *
 * <p>The map arrives in hash partitions and only the ones a text needs are
 * pulled. <b>How</b> they are pulled is injected rather than hardcoded — the
 * reader passes a dynamic {@code import()}, a test passes a stub. That is what
 * lets this run under GraalVM in ordinary JUnit with no browser and no
 * network, which for a module that decides what pinyin a child sees is worth
 * more than the indirection costs.</p>
 *
 * <p>Split out of the reader when it crossed the 250 effective-line limit.
 * The limit picked a good seam: the reader draws, this resolves.</p>
 */
public record ArticleReadingsModule() implements DomModule<ArticleReadingsModule> {

    /** Yields {@code ensure}, {@code fill}, {@code readingsOf}, {@code cpLabel}. */
    public record createArticleReadings() implements Exportable._Constant<ArticleReadingsModule> {}

    public static final ArticleReadingsModule INSTANCE = new ArticleReadingsModule();

    @Override
    public ImportsFor<ArticleReadingsModule> imports() {
        return ImportsFor.<ArticleReadingsModule>builder().build();
    }

    @Override
    public ExportsOf<ArticleReadingsModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createArticleReadings()));
    }
}
