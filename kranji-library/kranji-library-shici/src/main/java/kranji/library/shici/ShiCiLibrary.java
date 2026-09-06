package kranji.library.shici;

import kranji.reading.library.LibraryTree;

/**
 * 古诗词, arranged.
 *
 * <p>Three levels where three are earned and two where they are not. 唐诗 and
 * 宋词 are the eras a child is taught to place a poem in; under each, the poet;
 * under 李白, 杜甫 and 王维 alone, a third level - form for the first two,
 * subject for 王维, because what separates his poems is not line length but
 * whether anybody else is in them.</p>
 *
 * <p>边塞诗 is the one shelf named for a kind rather than a person. It holds
 * one poem each from six poets who are otherwise met once, and grouping them
 * by what they are about is more use than six shelves of one.</p>
 *
 * <p>汉魏 sits above 唐诗 as a shelf and not as a branch. It is an era like the
 * other two and belongs in their sequence, but it holds three poems by one man,
 * and a heading above one shelf is a level a reader clicks through for nothing.
 * It stays a shelf until there is a second poet on it — 曹植, 陶渊明 and the
 * 汉乐府 all belong there, and the day one of them arrives is the day this
 * becomes a branch.</p>
 *
 * <p>The arrangement is a separate act from the collections, which is why it
 * lives in its own class. Adding a ninth 杜牧 poem touches no tree; deciding
 * that 杜牧 should divide by form touches only this file.</p>
 */
public final class ShiCiLibrary {

    /** This group, arranged, for a root to graft. See {@link kranji.reading.library.ArticleLibrary}. */
    public static final LibraryTree TREE = LibraryTree.of("古诗词",
            LibraryTree.shelf(ShiCiCollections.HAN_WEI),
            LibraryTree.of("唐诗",
                    LibraryTree.branch("李白",
                            ShiCiCollections.LI_BAI_WUYAN,
                            ShiCiCollections.LI_BAI_QIYAN),
                    LibraryTree.branch("杜甫",
                            ShiCiCollections.DU_FU_WUYAN,
                            ShiCiCollections.DU_FU_QIYAN),
                    LibraryTree.branch("王维",
                            ShiCiCollections.WANG_WEI_SHAN_SHUI,
                            ShiCiCollections.WANG_WEI_SONG_BIE),
                    LibraryTree.shelf(ShiCiCollections.BAI_JU_YI),
                    LibraryTree.shelf(ShiCiCollections.MENG_HAO_RAN),
                    LibraryTree.shelf(ShiCiCollections.DU_MU),
                    LibraryTree.shelf(ShiCiCollections.LI_SHANG_YIN),
                    LibraryTree.shelf(ShiCiCollections.LIU_YU_XI),
                    LibraryTree.shelf(ShiCiCollections.WANG_CHANG_LING),
                    LibraryTree.shelf(ShiCiCollections.BIAN_SAI),
                    LibraryTree.shelf(ShiCiCollections.TANG_QI_TA)),
            LibraryTree.of("宋词与宋诗",
                    LibraryTree.shelf(ShiCiCollections.SU_SHI),
                    LibraryTree.shelf(ShiCiCollections.LI_QING_ZHAO),
                    LibraryTree.shelf(ShiCiCollections.XIN_QI_JI),
                    LibraryTree.shelf(ShiCiCollections.LU_YOU),
                    LibraryTree.shelf(ShiCiCollections.YANG_WAN_LI),
                    LibraryTree.shelf(ShiCiCollections.WANG_AN_SHI),
                    LibraryTree.shelf(ShiCiCollections.LI_YU),
                    LibraryTree.shelf(ShiCiCollections.SONG_QI_TA)));

    private ShiCiLibrary() {}
}
