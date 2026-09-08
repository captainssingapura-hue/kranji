package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * A document as the tree of segments it should become.
 *
 * <h2>Not a table of contents</h2>
 *
 * <p>A TOC shows a reader where to go, and one will be built from this. What
 * this shows is what the <em>cut</em> produced, which is an author's question:
 * which sections are too long, which parts the budget had to invent, and which
 * segments still have no address a bookmark could survive.</p>
 *
 * <p>The third view on a single fetch. The document says what the file says,
 * the squares say what a reader will do with a page of it, and this says how
 * many pages there are.</p>
 */
public record MdSegmentsModule() implements DomModule<MdSegmentsModule> {

    /** Yields {@code draw}. */
    public record createMdSegments() implements Exportable._Constant<MdSegmentsModule> {}

    public static final MdSegmentsModule INSTANCE = new MdSegmentsModule();

    @Override
    public ImportsFor<MdSegmentsModule> imports() {
        return ImportsFor.<MdSegmentsModule>builder().build();
    }

    @Override
    public ExportsOf<MdSegmentsModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createMdSegments()));
    }
}
