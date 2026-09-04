package kranji.reading.content;

import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.LibraryTree;

/**
 * The library the app ships with when it ships with nothing else.
 *
 * <p>A root at a precedence that yields: put a real content jar on the
 * classpath and this stops being served, without anybody having to remember to
 * take it off. That is also why it is a root rather than a branch of one — it
 * holds 蚂蚁, 熊猫 and 水 written for a first reader, and so does the published
 * 科普 shelf. Mounted together they would offer the same subject twice under
 * two headings.</p>
 *
 * <p>The tree is written here and nowhere else. It is ordinary Java, which is
 * the point: a school, a family or a publisher can depend on the same
 * collection jars and arrange them differently without any of them rebuilding
 * the reader.</p>
 *
 * <p>Note that the arrangement is a separate act from the collections
 * themselves. Adding a sixth poem to 唐诗启蒙 touches no tree at all.</p>
 */
public final class DemoLibrary implements ArticleLibrary {

    public static final DemoLibrary INSTANCE = new DemoLibrary();

    private static final LibraryTree TREE = LibraryTree.of("Articles",
            LibraryTree.branch("诗歌",
                    DemoCollections.TANG_SHI,
                    DemoCollections.ER_GE),
            LibraryTree.branch("故事",
                    DemoCollections.YU_YAN,
                    DemoCollections.CHENG_YU),
            LibraryTree.branch("生活与自然",
                    DemoCollections.SHENG_HUO,
                    DemoCollections.KE_PU),
            LibraryTree.branch("文言",
                    DemoCollections.WEN_YAN));

    /** Public because the loader builds it; {@link #INSTANCE} is for direct use. */
    public DemoLibrary() {}

    @Override
    public LibraryTree tree() { return TREE; }

    @Override public String name() { return "demo"; }

    /** Above 0, so anything published displaces it. */
    @Override public int precedence() { return 10; }
}
