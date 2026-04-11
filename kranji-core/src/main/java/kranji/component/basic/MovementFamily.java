package kranji.component.basic;

import kranji.component.BasicComponent;

/** 辵部 / 廴部 — Movement-related components. */
public final class MovementFamily {
    private MovementFamily() {}

    /** 辶 — 走之底 (walk/advance). Derives from 辵 chuò. */
    public record ZouZhiDi() implements BasicComponent {
        @Override public String glyph()      { return "辶"; }
        @Override public String name()       { return "走之底"; }
        @Override public String standalone() { return "辵"; }
        @Override public String meaning()    { return "walk"; }
        @Override public String pinyin()     { return "chuò"; }
        @Override public int strokes()       { return 3; }
    }

    /** 廴 — 建之旁 (long stride). */
    public record JianZhiPang() implements BasicComponent {
        @Override public String glyph()      { return "廴"; }
        @Override public String name()       { return "建之旁"; }
        @Override public String standalone() { return "廴"; }
        @Override public String meaning()    { return "long stride"; }
        @Override public String pinyin()     { return "yǐn"; }
        @Override public int strokes()       { return 2; }
    }

    public static final ZouZhiDi ZOU_ZHI_DI = new ZouZhiDi();
    public static final JianZhiPang JIAN_ZHI_PANG = new JianZhiPang();
}
