package kranji.library.all;

import kranji.library.gushi.GuShiLibrary;
import kranji.library.kepu.KePuLibrary;
import kranji.library.kouyu.KouYuLibrary;
import kranji.library.shici.ShiCiLibrary;
import kranji.library.xiaohua.XiaoHuaLibrary;
import kranji.library.zhinan.ZhiNanLibrary;
import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.LibraryTree;

/**
 * The root: six groups, grafted under one arrangement.
 *
 * <h2>What this class decides, and what it does not</h2>
 *
 * <p>Nothing about any group's internals. How 唐诗 divides by era, author and
 * form is a judgement about 唐诗 and lives beside it, in
 * {@link ShiCiLibrary}. This file decides only what sits beside what — which
 * is the judgement a reader meets first and the one most likely to be made
 * differently by somebody else. A school that wants these same five jars under
 * a different arrangement writes its own root and depends on the same five
 * artifacts; nothing here is in its way.</p>
 *
 * <h2>Two categories, and one group that does without</h2>
 *
 * <p>文学 covers the two groups a reader browses for the writing: verse and
 * story. 语言游戏 covers the two that are played rather than read — a joke
 * lands or does not, a 绕口令 is a drill — and putting them together says
 * something true about how they are used.</p>
 *
 * <h2>The guide comes first</h2>
 *
 * <p>使用指南 is a shelf at the top of the root rather than a group among the
 * others, and it sits above 文学 because a reader who does not yet know how
 * the pinyin behaves gets less out of everything under it. It is the one
 * thing here that is about the reader rather than about the reading.</p>
 *
 * <p>科普读物 hangs off the root directly. A 科学 heading above it would hold
 * exactly one thing, and a level with nothing to distinguish is a level a
 * reader has to click through for no reason. When a second factual group
 * arrives, that is when the heading is worth adding.</p>
 */
public final class KranjiLibrary implements ArticleLibrary {

    public static final KranjiLibrary INSTANCE = new KranjiLibrary();

    private static final LibraryTree TREE = LibraryTree.of("读物",
            ZhiNanLibrary.TREE,
            LibraryTree.of("文学",
                    ShiCiLibrary.TREE,
                    GuShiLibrary.TREE),
            KePuLibrary.TREE,
            LibraryTree.of("语言游戏",
                    XiaoHuaLibrary.TREE,
                    KouYuLibrary.TREE));

    /** Public because the loader builds it; {@link #INSTANCE} is for direct use. */
    public KranjiLibrary() {}

    @Override public LibraryTree tree() { return TREE; }

    @Override public String name() { return "kranji"; }

    /**
     * 0 — this is the library a deployment carrying it exists to serve, so it
     * displaces the demonstration set without anybody having to remove that
     * jar.
     */
    @Override public int precedence() { return 0; }
}
