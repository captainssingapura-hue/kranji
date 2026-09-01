package kranji.reading.content;

import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.LibraryTree;

/**
 * The library the app ships with.
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

    private DemoLibrary() {}

    @Override
    public LibraryTree tree() { return TREE; }
}
