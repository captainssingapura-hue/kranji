package kranji.stroke.glyph;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Registry mapping glyph strings to their stroke definitions.
 *
 * <p>When a glyph has no registered definition, the renderer falls back
 * to text-based rendering.</p>
 */
public final class GlyphRegistry {

    private static final GlyphRegistry INSTANCE = new GlyphRegistry();

    private final Map<String, GlyphDef> glyphs = new HashMap<>();

    private GlyphRegistry() {
        BuiltinGlyphs.registerAll(this);
    }

    public static GlyphRegistry instance() {
        return INSTANCE;
    }

    public void register(GlyphDef def) {
        glyphs.put(def.glyph(), def);
    }

    public Optional<GlyphDef> lookup(String glyph) {
        return Optional.ofNullable(glyphs.get(glyph));
    }

    public boolean has(String glyph) {
        return glyphs.containsKey(glyph);
    }
}
