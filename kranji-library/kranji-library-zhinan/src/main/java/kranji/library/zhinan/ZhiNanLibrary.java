package kranji.library.zhinan;

import kranji.reading.library.LibraryTree;

/**
 * 使用指南 — a shelf, not a branch.
 *
 * <p>The other four groups open onto categories a reader chooses between. This
 * one holds the pieces about the reader itself, and there is nothing to choose
 * between: a heading above a single shelf is a level to click through for no
 * reason. Grafted as a shelf, it costs the root one row and the reader none.</p>
 *
 * <p>It stays a shelf however many pieces it grows to. A guide divides by what
 * the reader wants to do next, which is a running order rather than a
 * classification, and a running order is a list.</p>
 */
public final class ZhiNanLibrary {

    /** This group, arranged, for a root to graft. See {@link kranji.reading.library.ArticleLibrary}. */
    public static final LibraryTree TREE = LibraryTree.shelf(ZhiNanCollections.SHI_YONG);

    private ZhiNanLibrary() {}
}
