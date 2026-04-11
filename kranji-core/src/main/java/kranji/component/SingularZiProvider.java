package kranji.component;

/**
 * Service-provider interface for modules that register rich
 * {@link SingularZi} instances (with politeness, hints, metadata).
 *
 * <p>Discovered automatically via {@link java.util.ServiceLoader}
 * the first time {@link SingularZi#of(String)} is called.
 * Each provider's {@link #registerAll()} is invoked exactly once.</p>
 *
 * <p>To add a provider, create an implementing class and list it in
 * {@code META-INF/services/kranji.component.SingularZiProvider}.</p>
 */
public interface SingularZiProvider {

    /** Register all SingularZi instances into {@link SingularZi#CACHE}. */
    void registerAll();
}
