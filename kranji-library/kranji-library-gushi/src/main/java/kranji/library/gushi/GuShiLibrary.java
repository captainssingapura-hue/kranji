package kranji.library.gushi;

import kranji.reading.library.LibraryTree;

/**
 * 寓言故事, 成语故事 and 神话, arranged.
 *
 * <p>Three branches divided on three principles, because the three kinds are
 * looked for differently: a 寓言 by tradition, a 成语 by what it is about, a
 * 神话 by nothing at all - it is one shelf because it is one body of stories.
 * A single scheme covering all three would have to be either "source", which
 * nobody uses to find 掩耳盗铃, or "theme", which puts 庄子 beside 伊索 and
 * loses the only thing that distinguishes them.</p>
 */
public final class GuShiLibrary {

    /** This group, arranged, for a root to graft. See {@link kranji.reading.library.ArticleLibrary}. */
    public static final LibraryTree TREE = LibraryTree.of("故事",
            LibraryTree.shelf(GuShiCollections.SHUO_MING),
            LibraryTree.of("寓言故事",
                    LibraryTree.branch("中国寓言",
                            GuShiCollections.HAN_FEI,
                            GuShiCollections.ZHUANG_ZI,
                            GuShiCollections.ZHAN_GUO),
                    LibraryTree.branch("外国寓言",
                            GuShiCollections.YI_SUO)),
            LibraryTree.branch("成语故事",
                    GuShiCollections.QIN_XUE,
                    GuShiCollections.ZHI_HUI,
                    GuShiCollections.YU_CHUN,
                    GuShiCollections.PIN_GE),
            LibraryTree.branch("神话传说",
                    GuShiCollections.SHEN_HUA),
            LibraryTree.branch("民间与童话",
                    GuShiCollections.MIN_JIAN,
                    GuShiCollections.WAI_GUO));

    private GuShiLibrary() {}
}
