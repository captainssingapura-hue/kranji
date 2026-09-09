package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;

import java.util.List;

/**
 * The cell rule, applied where the text arrives.
 *
 * <p>A closing mark joins the character before it, an opening mark joins the
 * character after it, and anything left over is its own plain cell. This is
 * {@link kranji.reading.model.Cells} ported to JavaScript, because the wire now
 * carries an article as its source lines rather than as pre-assembled cells.</p>
 *
 * <p>What that buys is not only bytes. A reading the corpus already knows is no
 * longer restated once per character per article, so correcting the corpus
 * cannot leave a thousand articles quietly serving the old reading. An article
 * carries only what only it knows — the authored override.</p>
 *
 * <p>Pure: no DOM, no fetch, no captures. That is what lets it be read by
 * GraalVM under ordinary JUnit, and the port is held to the original by a
 * parity test that runs both over the same articles.</p>
 */
public record ArticleScannerModule() implements DomModule<ArticleScannerModule> {

    /** Yields {@code scan(line)}, plus the {@code closing}/{@code opening} tables. */
    public record createArticleScanner() implements Exportable._Constant<ArticleScannerModule> {}

    public static final ArticleScannerModule INSTANCE = new ArticleScannerModule();

    @Override
    public ImportsFor<ArticleScannerModule> imports() {
        // The canonical form of a reading. A file is written by hand and may
        // say dì; everything below the display layer carries di4, and this is
        // where the file crosses in — see PinyinSwfModule, which owns the rule.
        return ImportsFor.<ArticleScannerModule>builder()
                .add(new ModuleImports<>(
                        List.of(new kranji.reading.app.ui.PinyinSwfModule.createPinyinSwf()),
                        kranji.reading.app.ui.PinyinSwfModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<ArticleScannerModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createArticleScanner()));
    }
}
