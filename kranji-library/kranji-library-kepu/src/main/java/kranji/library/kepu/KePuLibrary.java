package kranji.library.kepu;

import kranji.reading.library.LibraryTree;

/**
 * 科普读物, arranged by subject.
 *
 * <p>One level, except under 动物. Nine subjects is a list a child can hold in
 * their head; 动物 is the shelf they go to first and the one that got long
 * enough to divide, so it splits into 昆虫, 鸟 and 兽 - which is also the
 * division a six-year-old already makes without being taught it.</p>
 */
public final class KePuLibrary {

    /** This group, arranged, for a root to graft. See {@link kranji.reading.library.ArticleLibrary}. */
    public static final LibraryTree TREE = LibraryTree.of("科普读物",
            LibraryTree.branch("动物",
                    KePuCollections.KUN_CHONG,
                    KePuCollections.NIAO,
                    KePuCollections.SHOU),
            LibraryTree.shelf(KePuCollections.ZHI_WU),
            LibraryTree.shelf(KePuCollections.TIAN_WEN),
            LibraryTree.shelf(KePuCollections.DI_LI),
            LibraryTree.shelf(KePuCollections.TIAN_QI),
            LibraryTree.shelf(KePuCollections.REN_TI),
            LibraryTree.shelf(KePuCollections.WU_ZHI),
            LibraryTree.shelf(KePuCollections.JI_SHU),
            LibraryTree.shelf(KePuCollections.QI_CHE),
            LibraryTree.shelf(KePuCollections.SHI_WU),
            LibraryTree.shelf(KePuCollections.HUAN_JING),
            LibraryTree.shelf(KePuCollections.SHU_XING));

    private KePuLibrary() {}
}
