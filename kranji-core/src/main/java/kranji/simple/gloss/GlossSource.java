package kranji.simple.gloss;

import java.util.List;

/**
 * A body of glosses with one provenance.
 *
 * <p>The unit of licensing, not of storage. Hand-crafted glosses and glosses
 * ported from a dictionary are different facts about the world with different
 * terms attached, and the thing that keeps that statable is that they never
 * share a source — or a module.</p>
 *
 * <p>{@link #name()} and {@link #licence()} are not decoration. A build that
 * includes a share-alike source is a different distribution from one that does
 * not, and the difference has to be visible from the data rather than
 * remembered.</p>
 *
 * <p>Sources are composed <b>explicitly</b> by whoever builds a registry —
 * there is no discovery mechanism and no mutable global to register into. See
 * CF-003 for what the alternative costs: a consumer that forgets a boot step
 * gets partial data silently, and partial coverage looks exactly like the
 * coverage gap already expected.</p>
 */
public interface GlossSource {

    /** Where these glosses came from, in a few words. */
    String name();

    /**
     * The licence these glosses are under.
     *
     * <p>Project-authored data says so; ported data names its licence and the
     * corpus it came from.</p>
     */
    String licence();

    /** The characters this source glosses, each with all its readings. */
    List<ZiGloss> entries();
}
