package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;
import kranji.reading.app.css.ReadingCss;
import kranji.reading.app.ui.PinyinSwfModule;

import java.util.List;

/**
 * One square, as a RelationGrid cell widget — and the Relation behind it.
 *
 * <p>The spike that asks whether the reader's square survives being a cell the
 * grid owns rather than a {@code td} we built. Minesweeper is the precedent:
 * a cell factory returns a stateful widget and the grid hands it the host
 * element to dress.</p>
 *
 * <p>The annotation moves <b>inside</b> the cell, stacked over the character,
 * because a Relation row is one row of cells and there is no row above to put
 * it in. What mattered survives: a column's width comes from the grid, not from
 * the content, so an annotation cannot spread the characters apart the way ruby
 * does — which was the reason for the table in the first place.</p>
 *
 * <p>Separate from {@link ArticleReaderWidget} on purpose. The reader works;
 * a spike should not be able to break it.</p>
 */
public record ArticleBoardModule() implements DomModule<ArticleBoardModule> {

    /** The cell factory for {@code RelationGrid}'s {@code cellFactory}. */
    public record createArticleCell() implements Exportable._Constant<ArticleBoardModule> {}

    /** A Relation over the article: one row per display line. */
    public record createLineRelation() implements Exportable._Constant<ArticleBoardModule> {}

    /** Repaints every square without rebuilding, for a control change. */
    public record repaintGrid() implements Exportable._Constant<ArticleBoardModule> {}

    /** The article as display lines, in reading order. */
    public record articleLines() implements Exportable._Constant<ArticleBoardModule> {}

    /** Builds the article as one board and appends it to the body. */
    public record buildArticleBoard() implements Exportable._Constant<ArticleBoardModule> {}

    public static final ArticleBoardModule INSTANCE = new ArticleBoardModule();

    /**
     * The cell's classes are imported here rather than passed in — which is
     * also what brings the {@code css} binding into scope. A module that
     * dresses elements has to import what it dresses them with.
     */
    @Override
    public ImportsFor<ArticleBoardModule> imports() {
        return ImportsFor.<ArticleBoardModule>builder()
                .add(new ModuleImports<>(List.of(
                        new hue.captains.singapura.js.homing.grid.RelationGridModule.RelationGrid()),
                        hue.captains.singapura.js.homing.grid.RelationGridModule.INSTANCE))
                .add(new ModuleImports<>(List.of(
                        new PinyinSwfModule.createPinyinSwf()),
                        PinyinSwfModule.INSTANCE))
                .add(new ModuleImports<>(List.of(
                        new ReadingCss.kr_gr_cell(),
                        new ReadingCss.kr_gr_ann(),
                        new ReadingCss.kr_gr_zi(),
                        new ReadingCss.kr_gr_grid(),
                        new ReadingCss.kr_read_punct(),
                        new ReadingCss.kr_read_punct_lead(),
                        new ReadingCss.kr_gr_host(),
                        new ReadingCss.kr_gr_ruled(),
                        new ReadingCss.kr_gr_known(),
                        new ReadingCss.kr_read_hidden()),
                        ReadingCss.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<ArticleBoardModule> exports() {
        return new ExportsOf<>(INSTANCE,
                List.of(new createArticleCell(), new createLineRelation(),
                        new repaintGrid(), new articleLines(),
                        new buildArticleBoard()));
    }
}
