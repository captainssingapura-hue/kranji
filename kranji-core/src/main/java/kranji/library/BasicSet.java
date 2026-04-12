package kranji.library;

import kranji.component.HintedZi;
import kranji.component.basic.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The foundational library containing all basic components: Parts (偏旁)
 * and Singulars (独体字).
 *
 * <p>This is the base layer that all other libraries depend on.
 * Component families in both {@code kranji-core} and {@code kranji-singulars}
 * implement {@code Zi<BasicSet>} to declare membership.</p>
 *
 * <p>Core families (in kranji-core) are collected directly by
 * {@link #components()}. Singular families (in kranji-singulars) are
 * contributed via {@link #addAll} during {@link #register()}.</p>
 */
public final class BasicSet implements ZiLibrary {

    public static final BasicSet INSTANCE = new BasicSet();

    private final List<LibraryMember<BasicSet>> components = new ArrayList<>();
    private volatile boolean registered = false;

    private BasicSet() {}

    @Override public String name() { return "Basic"; }
    @Override public List<ZiLibrary> dependencies() { return List.of(); }

    @Override
    public List<? extends LibraryMember<BasicSet>> components() {
        return Collections.unmodifiableList(components);
    }

    /**
     * Called by external modules (e.g. kranji-singulars) to contribute
     * additional component families to this library.
     */
    public void addAll(List<? extends LibraryMember<BasicSet>> family) {
        components.addAll(family);
    }

    @Override
    public void register() {
        if (registered) return;
        registered = true;

        // ── kranji-core families (directly accessible) ──────────
        components.addAll(HintedZi.ALL);
        components.addAll(PersonFamily.ALL);
        components.addAll(WaterFamily.ALL);
        components.addAll(HeartFamily.ALL);
        components.addAll(HandFamily.ALL);
        components.addAll(AnimalFamily.ALL);
        components.addAll(SpeechFamily.ALL);
        components.addAll(MetalFamily.ALL);
        components.addAll(FoodFamily.ALL);
        components.addAll(MovementFamily.ALL);
        components.addAll(SilkFamily.ALL);
        components.addAll(ClothingFamily.ALL);
        components.addAll(KnifeFamily.ALL);
        components.addAll(StrikeFamily.ALL);
        components.addAll(EarFamily.ALL);
        components.addAll(PlantFamily.ALL);
        components.addAll(ShelterFamily.ALL);
        components.addAll(FireFamily.ALL);
        components.addAll(WoodFamily.ALL);
        components.addAll(EnclosureFamily.ALL);

        // ── kranji-singulars families are added via addAll() ────
        // The app or a higher-level library calls
        // SingularFamilies.registerInto(BasicSet.INSTANCE) before use.
    }
}
