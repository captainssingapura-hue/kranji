package kranji.library.xiaohua;

import kranji.reading.library.LibraryTree;

/** 一般笑话, by setting. Flat: four places is not a hierarchy. */
public final class XiaoHuaLibrary {

    /** This group, arranged, for a root to graft. See {@link kranji.reading.library.ArticleLibrary}. */
    public static final LibraryTree TREE = LibraryTree.branch("笑话",
            XiaoHuaCollections.SHUO_MING,
            XiaoHuaCollections.XUE_XIAO,
            XiaoHuaCollections.JIA_LI,
            XiaoHuaCollections.DONG_WU,
            XiaoHuaCollections.XIE_YIN,
            XiaoHuaCollections.LENG,
            XiaoHuaCollections.ZHI_YE);

    private XiaoHuaLibrary() {}
}
