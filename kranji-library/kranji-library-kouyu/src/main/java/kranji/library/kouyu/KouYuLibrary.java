package kranji.library.kouyu;

import kranji.reading.library.LibraryTree;

/**
 * 绕口令 and 顺口溜, arranged on two different principles.
 *
 * <p>Sound for the first, subject for the second, and the split is the reason
 * they share a module rather than a branch. Both are said aloud; only one is
 * about the saying.</p>
 *
 * <p>The 绕口令 shelves name contrasts rather than initials, because a drill is
 * about telling two things apart: s from sh, n from l, an from ang. A shelf
 * called "s" would hold half of a distinction and be no use to anybody.</p>
 */
public final class KouYuLibrary {

    /** This group, arranged, for a root to graft. See {@link kranji.reading.library.ArticleLibrary}. */
    public static final LibraryTree TREE = LibraryTree.of("绕口令与顺口溜",
            LibraryTree.shelf(KouYuCollections.SHUO_MING),
            LibraryTree.branch("绕口令",
                    KouYuCollections.S_SH,
                    KouYuCollections.Z_ZH,
                    KouYuCollections.N_L,
                    KouYuCollections.B_P,
                    KouYuCollections.AN_ANG,
                    KouYuCollections.J_Q_X,
                    KouYuCollections.CHANG_JU),
            LibraryTree.branch("顺口溜",
                    KouYuCollections.SHU_ZI,
                    KouYuCollections.JIE_QI,
                    KouYuCollections.SHENG_HUO,
                    KouYuCollections.ZI_RAN,
                    KouYuCollections.XUE_XI));

    private KouYuLibrary() {}
}
