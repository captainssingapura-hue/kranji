package kranji.reading.library;

/**
 * A root library — the whole of what one reader can browse.
 *
 * <p>Only the tree. Collections are reachable through it, so there is no second
 * way to enumerate the library and therefore no way for the two to
 * disagree.</p>
 *
 * <h2>Roots are discovered; everything below one is grafted</h2>
 *
 * <p>Implementations are found with {@link java.util.ServiceLoader}, which is
 * what lets content ship independently of the application binary: a build says
 * which root it wants by putting that jar on the classpath, and swapping the
 * demonstration set for a published one is a deployment change rather than a
 * code change.</p>
 *
 * <p>Discovery stops at the root. The bodies of text underneath — 古诗词, 科普
 * and the rest — are <em>grafted</em> by whoever writes the root, in ordinary
 * Java, because where 古诗词 hangs and what sits beside it is an editorial
 * judgement and not something a classpath should be making. That is also what
 * lets two roots arrange the same content jars differently: a school and a
 * family can each publish a root and share every shelf beneath it.</p>
 *
 * <h2>Roots exclude each other</h2>
 *
 * <p>Glosses compose — two gloss modules make one larger body of glosses,
 * because a character glossed twice is still one character. Content does not.
 * The demonstration library and the published one both hold 蚂蚁 and 熊猫,
 * written for different readers; a tree carrying both offers the same subject
 * twice under two headings. So {@link Libraries} mounts exactly one root, by
 * {@link #precedence()}, and every other discovered root is left alone.</p>
 *
 * <p>An implementation needs a public no-argument constructor, because the
 * loader builds it. An {@code INSTANCE} field beside one is fine and costs
 * nothing, since the tree is a constant either way.</p>
 */
public interface ArticleLibrary {

    /** The whole library, arranged. Leaves are collections. */
    LibraryTree tree();

    /**
     * This root's stable identity — ASCII, never displayed.
     *
     * <p>What {@code -D}{@code kranji.reading.library} names when a build wants
     * a root other than the one precedence would pick, and what an error
     * message calls it.</p>
     */
    String name();

    /**
     * Which root wins when several are on the classpath. Lower wins.
     *
     * <h2>Why this cannot be left to discovery order</h2>
     *
     * <p>{@link java.util.ServiceLoader} does not specify the order it hands
     * providers over — it follows the classpath, which differs between a jar,
     * an IDE and a shaded build. Without a rule, which library a reader gets
     * would change between a developer's machine and the artifact built from
     * the same commit.</p>
     *
     * <p>0 is a root a deployment exists to serve. A sample or a fixture ranks
     * above 0 and yields, so a build that gains real content stops serving the
     * sample without anybody having to remember to take it off.</p>
     */
    default int precedence() { return 0; }
}
