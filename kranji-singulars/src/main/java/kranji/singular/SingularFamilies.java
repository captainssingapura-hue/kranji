package kranji.singular;

import kranji.library.BasicSet;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.body.BodyParts;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.materials.Materials;
import kranji.singular.nature.NatureElements;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.tools.ToolsAndVessels;
import kranji.singular.unsure.UnsureSingulars;

/**
 * Registers all singular (独体字) families into {@link BasicSet}.
 *
 * <p>Call {@link #registerInto} once at application startup,
 * after {@code BasicSet.INSTANCE.register()} has been called.</p>
 */
public final class SingularFamilies {

    private static volatile boolean loaded = false;

    private SingularFamilies() {}

    /**
     * Add all singular family components to the given BasicSet.
     */
    public static void registerInto(BasicSet basicSet) {
        if (loaded) return;
        loaded = true;

        basicSet.addAll(NatureElements.ALL);
        basicSet.addAll(BodyParts.ALL);
        basicSet.addAll(PeopleAndRoles.ALL);
        basicSet.addAll(Animals.ALL);
        basicSet.addAll(NumbersAndMeasure.ALL);
        basicSet.addAll(PlantsAndAgriculture.ALL);
        basicSet.addAll(ToolsAndVessels.ALL);
        basicSet.addAll(ActionsAndStates.ALL);
        basicSet.addAll(SpaceAndDirection.ALL);
        basicSet.addAll(Structures.ALL);
        basicSet.addAll(Materials.ALL);
        basicSet.addAll(AbstractConcepts.ALL);
        basicSet.addAll(RadicalComponents.ALL);
        basicSet.addAll(UnsureSingulars.ALL);
    }
}
