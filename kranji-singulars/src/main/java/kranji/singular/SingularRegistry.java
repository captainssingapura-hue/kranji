package kranji.singular;

import kranji.component.SingularZi;
import kranji.component.SingularZiProvider;
import kranji.singular.nature.NatureElements;
import kranji.singular.body.BodyParts;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.animals.Animals;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.singular.tools.ToolsAndVessels;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.singular.materials.Materials;
import kranji.singular.concepts.AbstractConcepts;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Registers all SingularZi instances from every category class
 * into {@link SingularZi#CACHE} so that {@code SingularZi.of("日")}
 * returns the rich, hinted instance instead of a plain one.
 *
 * <p>Call {@link #ensureLoaded()} once at startup, or reference
 * {@link #INIT} to trigger via class loading.</p>
 */
public final class SingularRegistry implements SingularZiProvider {

    private static final Class<?>[] FAMILIES = {
        NatureElements.class,
        BodyParts.class,
        PeopleAndRoles.class,
        Animals.class,
        NumbersAndMeasure.class,
        PlantsAndAgriculture.class,
        ToolsAndVessels.class,
        ActionsAndStates.class,
        SpaceAndDirection.class,
        Structures.class,
        Materials.class,
        AbstractConcepts.class,
    };

    private static volatile boolean loaded = false;

    /** No-arg constructor required by {@link java.util.ServiceLoader}. */
    public SingularRegistry() {}

    @Override
    public void registerAll() {
        if (loaded) return;
        loaded = true;
        for (Class<?> family : FAMILIES) {
            registerFamily(family);
        }
    }

    /** Explicitly trigger registration (convenience for direct callers). */
    public static void ensureLoaded() {
        new SingularRegistry().registerAll();
    }

    private static void registerFamily(Class<?> family) {
        for (Field f : family.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())
                    && Modifier.isFinal(f.getModifiers())
                    && SingularZi.class.isAssignableFrom(f.getType())) {
                try {
                    SingularZi zi = (SingularZi) f.get(null);
                    SingularZi.register(zi);
                } catch (IllegalAccessException e) {
                    throw new ExceptionInInitializerError(e);
                }
            }
        }
    }
}
