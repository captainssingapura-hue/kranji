package kranji.component;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 独体字 — A standalone character that also serves as a component
 * in compound characters (e.g. 口, 日, 月, 土, 心).
 *
 * <p>Sub-interface of {@link BasicComponent} bridging the gap between
 * full characters and radical-form parts. Unlike Parts (偏旁) which only
 * exist inside other characters, a SingularZi IS a character in its own
 * right — its glyph and standalone form are the same.</p>
 *
 * <p>Rich instances (with politeness, hints, metadata) are discovered
 * automatically via {@link ServiceLoader} on first access to {@link #of}.
 * Modules provide them by implementing {@link SingularZiProvider}.</p>
 */
public interface SingularZi extends BasicComponent {

    @Override default String name()       { return glyph(); }
    @Override default String standalone() { return glyph(); }
    @Override default String meaning()    { return ""; }
    @Override default String pinyin()     { return ""; }
    @Override default int strokes()       { return 0; }

    // ── Cache & discovery ─────────────────────────────────────

    /** Internal cache — ensures the same glyph always returns the same instance. */
    Map<String, SingularZi> CACHE = new ConcurrentHashMap<>();

    /** Guards one-time provider discovery. */
    AtomicBoolean PROVIDERS_LOADED = new AtomicBoolean(false);

    /**
     * Load all {@link SingularZiProvider}s via ServiceLoader, then
     * load the in-module {@link HintedZi} class. Called once.
     */
    private static void loadProviders() {
        if (PROVIDERS_LOADED.compareAndSet(false, true)) {
            // 1. In-module hinted instances (kranji-core's HintedZi)
            try { Class.forName(HintedZi.class.getName()); } catch (ClassNotFoundException ignored) {}
            // 2. External providers (e.g. kranji-singulars' SingularRegistry)
            for (SingularZiProvider provider : ServiceLoader.load(SingularZiProvider.class)) {
                provider.registerAll();
            }
        }
    }

    // ── Factory ────────────────────────────────────────────────

    /**
     * Get or create a SingularZi for the given glyph.
     *
     * <p>On first call, discovers and loads all {@link SingularZiProvider}s
     * so that rich instances (with politeness, hints) are available.
     * Returns a plain fallback if no provider registered this glyph.</p>
     */
    static SingularZi of(String glyph) {
        loadProviders();
        return CACHE.computeIfAbsent(glyph, Plain::new);
    }

    /**
     * Register a rich SingularZi so that {@link #of(String)} returns it.
     * Called from providers and static initializers.
     */
    static void register(SingularZi zi) {
        CACHE.put(zi.glyph(), zi);
    }

    // ── Plain implementation ───────────────────────────────────

    /** A SingularZi with no position-specific hints. */
    record Plain(String glyph) implements SingularZi {}
}
