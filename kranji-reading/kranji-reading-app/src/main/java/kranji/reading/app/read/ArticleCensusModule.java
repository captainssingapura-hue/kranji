package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;
import kranji.reading.app.known.KnownSetModule;

import java.util.List;

/**
 * What an article asks of a reader, counted — in the browser.
 *
 * <p>Everything needed to work out how much of an article somebody can read,
 * and <em>nothing</em> about any particular reader. That split is the point: a
 * census is a property of the article alone, so the same article always gives
 * the same census, and the answer for a given child is that census met with a
 * known set somewhere else.</p>
 *
 * <p>Which is why it is not part of {@link ReadabilityModule}. Readability is a
 * comparison between two things; this is one of the two.</p>
 *
 * <h2>Counted by reading, not by character</h2>
 *
 * <p>Because that is how the known set is keyed, and because it is the truthful
 * count. An article using 行 as háng asks nothing of a reader who has learnt
 * xíng — treating the two as one would report a readability the child cannot
 * actually achieve.</p>
 *
 * <h2>The same rule as the server, on data already here</h2>
 *
 * <p>{@code ArticleCensus.of} computes this server-side from the same rule, and
 * {@code /article-census} used to send all 475 of them — 274KB — so that one
 * pane could describe one article. That was the right shape while the library
 * ranked every article and the wrong shape the moment it stopped: the reader
 * has already scanned the article it is showing, and every cell already carries
 * the reading it resolved to. The census was in the browser all along, waiting
 * to be counted.</p>
 *
 * <p>The caller supplies the scan rather than this importing one, because the
 * pane that draws the article already has it. Counting through a second scanner
 * would be a second chance to disagree.</p>
 *
 * <p>The key comes from {@link KnownSetModule} rather than being spelled out
 * again — readability is a set intersection against the known set, so a key
 * differing by one character would make every article read 0% and nothing would
 * throw.</p>
 *
 * <p>Pure — no DOM, no fetch, no clock — so it runs under GraalVM in ordinary
 * JUnit.</p>
 */
public record ArticleCensusModule() implements DomModule<ArticleCensusModule> {

    /** Yields {@code ofCells} and {@code ofLines}. */
    public record createArticleCensus() implements Exportable._Constant<ArticleCensusModule> {}

    public static final ArticleCensusModule INSTANCE = new ArticleCensusModule();

    @Override
    public ImportsFor<ArticleCensusModule> imports() {
        return ImportsFor.<ArticleCensusModule>builder()
                .add(new ModuleImports<>(
                        List.of(new KnownSetModule.createKnownSet()),
                        KnownSetModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<ArticleCensusModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createArticleCensus()));
    }
}
