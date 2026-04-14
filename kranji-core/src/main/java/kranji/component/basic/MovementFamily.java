package kranji.component.basic;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;

import java.util.List;

/** 辵部 / 廴部 — Movement-related components. */
public final class MovementFamily {
    private MovementFamily() {}

    /** 辶 — 走之底 (walk/advance). Derives from 辵 chuò. */
    public record ZouZhiDi() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "辶"; }
        @Override public String name()       { return "走之底"; }
        @Override public String standalone() { return "辵"; }
        @Override public String meaning()    { return "walk"; }
        @Override public String pinyinText()     { return "chuò"; }
        @Override public int strokes()       { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 廴 — 建之旁 (long stride). */
    public record JianZhiPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "廴"; }
        @Override public String name()       { return "建之旁"; }
        @Override public String standalone() { return "廴"; }
        @Override public String meaning()    { return "long stride"; }
        @Override public String pinyinText()     { return "yǐn"; }
        @Override public int strokes()       { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final ZouZhiDi ZOU_ZHI_DI = new ZouZhiDi();
    public static final JianZhiPang JIAN_ZHI_PANG = new JianZhiPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(ZOU_ZHI_DI, JIAN_ZHI_PANG);
}
