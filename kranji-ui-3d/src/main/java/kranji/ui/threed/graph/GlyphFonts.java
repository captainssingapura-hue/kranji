package kranji.ui.threed.graph;

import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * CJK-capable font catalogue for the 3D playground.
 *
 * <p>Probes the JavaFX font registry at startup and intersects with a
 * curated list of fonts known to render Chinese glyphs well on the
 * common platforms (Windows, macOS). The first available entry is the
 * recommended default.</p>
 */
public final class GlyphFonts {

    /** Preferred CJK fonts in display order — first available wins as default. */
    private static final String[] CANDIDATES = {
            "Microsoft YaHei",       // Windows · sans
            "Microsoft JhengHei",    // Windows · sans (Traditional)
            "SimHei",                // Windows · bold sans
            "SimSun",                // Windows · serif (宋体)
            "NSimSun",               // Windows · serif fixed-pitch
            "FangSong",              // Windows · 仿宋
            "KaiTi",                 // Windows · 楷体 (calligraphy)
            "DengXian",              // Windows · 等线
            "PingFang SC",           // macOS · sans
            "Hiragino Sans GB",      // macOS · sans
            "STSong",                // macOS · serif
            "STKaiti",               // macOS · 楷体
            "STFangsong",            // macOS · 仿宋
            "Noto Sans CJK SC",      // cross-platform · Google
            "Noto Serif CJK SC",
            "Source Han Sans SC",    // Adobe
            "Source Han Serif SC"
    };

    private GlyphFonts() {}

    /** Subset of {@link #CANDIDATES} actually installed, in declared order. */
    public static List<String> available() {
        Set<String> installed = new LinkedHashSet<>(Font.getFamilies());
        var out = new ArrayList<String>();
        for (String c : CANDIDATES) if (installed.contains(c)) out.add(c);
        // Always offer at least YaHei — JavaFX falls back gracefully if missing.
        if (out.isEmpty()) out.add(GlyphNode3d.DEFAULT_FONT_FAMILY);
        return List.copyOf(out);
    }

    /** First entry of {@link #available()} — best CJK font on this machine. */
    public static String defaultFamily() {
        return available().get(0);
    }
}
