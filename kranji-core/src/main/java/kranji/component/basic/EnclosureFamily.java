package kranji.component.basic;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;

import java.util.List;

/** 包围部件 — Enclosure-related components. */
public final class EnclosureFamily {
    private EnclosureFamily() {}

    /** 囗 — 国字框 (enclosure). */
    public record GuoZiKuang() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "囗"; }
        @Override public String name()       { return "国字框"; }
        @Override public String standalone() { return "囗"; }
        @Override public String meaning()    { return "enclosure"; }
        @Override public String pinyin()     { return "wéi"; }
        @Override public int strokes()       { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 疒 — 病字旁 (sickness). */
    public record BingZiPang() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "疒"; }
        @Override public String name()       { return "病字旁"; }
        @Override public String standalone() { return "疒"; }
        @Override public String meaning()    { return "sickness"; }
        @Override public String pinyin()     { return "nè"; }
        @Override public int strokes()       { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 勹 — 包字头 (wrap). */
    public record BaoZiTou() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "勹"; }
        @Override public String name()       { return "包字头"; }
        @Override public String standalone() { return "勹"; }
        @Override public String meaning()    { return "wrap"; }
        @Override public String pinyin()     { return "bāo"; }
        @Override public int strokes()       { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 匚 — 三框 (box/container). */
    public record SanKuang() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "匚"; }
        @Override public String name()       { return "三框"; }
        @Override public String standalone() { return "匚"; }
        @Override public String meaning()    { return "box"; }
        @Override public String pinyin()     { return "fāng"; }
        @Override public int strokes()       { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 凵 — 凶字框 (receptacle). */
    public record XiongZiKuang() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "凵"; }
        @Override public String name()       { return "凶字框"; }
        @Override public String standalone() { return "凵"; }
        @Override public String meaning()    { return "receptacle"; }
        @Override public String pinyin()     { return "kǎn"; }
        @Override public int strokes()       { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 冂 — 同字框 (border/boundary). */
    public record TongZiKuang() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "冂"; }
        @Override public String name()       { return "同字框"; }
        @Override public String standalone() { return "冂"; }
        @Override public String meaning()    { return "border"; }
        @Override public String pinyin()     { return "jiōng"; }
        @Override public int strokes()       { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final GuoZiKuang GUO_ZI_KUANG = new GuoZiKuang();
    public static final BingZiPang BING_ZI_PANG = new BingZiPang();
    public static final BaoZiTou BAO_ZI_TOU = new BaoZiTou();
    public static final SanKuang SAN_KUANG = new SanKuang();
    public static final XiongZiKuang XIONG_ZI_KUANG = new XiongZiKuang();
    public static final TongZiKuang TONG_ZI_KUANG = new TongZiKuang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(GUO_ZI_KUANG, BING_ZI_PANG, BAO_ZI_TOU, SAN_KUANG, XIONG_ZI_KUANG, TONG_ZI_KUANG);
}
