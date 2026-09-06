// =============================================================================
// ArticleTextModule — an article's text, in the shapes the reader draws from.
//
// Three questions that all have the same subject and none of which are about
// the pane: which lines an article is made of, what squares a line becomes,
// and how a paragraph's first line is set in. Four callers wanted them - the
// board, the title, the readability line and the loader - and while they were
// closures inside the widget they were four callers reaching into one
// function's scope.
//
// Nothing here touches the DOM, so it can be asked these questions under
// GraalVM with no browser: the scan rule is ArticleScannerModule's and the
// readings are ArticleReadingsModule's, and both arrive injected.
// =============================================================================

/**
 * opts = {
 *   scanner,     // createArticleScanner()
 *   readings     // createArticleReadings(...)
 * }
 * Returns { cellsOf(line), indented(cells), textsOf(mod) }.
 */
function createArticleText(opts) {

    return {

        /**
         * One authored line -> the squares it is written in.
         *
         * <p>The scan rule is the scanner's; the readings are filled in after,
         * and an authored one always wins because the corpus cannot know
         * it.</p>
         */
        cellsOf: function (line) {
            return opts.readings.fill(opts.scanner.scan(line));
        },

        // 首行缩进两格. On squared paper those two squares are left empty
        // rather than the text being nudged, so the indent is two blank cells -
        // it keeps the column grid, and with the grid on it looks like what a
        // child writes.
        indented: function (cells) {
            return [{ t: '' }, { t: '' }].concat(cells);
        },

        /**
         * Every line of the article, in reading order.
         *
         * <p>The body and only the body. The title is on the screen too, but it
         * is not part of what the article asks of a reader, and this is what
         * the readability count is taken over.</p>
         */
        textsOf: function (mod) {
            var out = [];
            var blocks = (mod && mod.blocks) || [];
            for (var b = 0; b < blocks.length; b++) {
                var block = blocks[b];
                if (block.kind === 'verse') out = out.concat(block.lines);
                else if (block.kind === 'p') out.push(block.text);
            }
            return out;
        }
    };
}
