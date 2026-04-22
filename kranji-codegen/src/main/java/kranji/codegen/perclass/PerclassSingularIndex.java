package kranji.codegen.perclass;

import kranji.component.basic.BasicComponents;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.body.BodyParts;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.materials.Materials;
import kranji.singular.nature.NatureElements;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.singular.tools.ToolsAndVessels;
import kranji.zi.SingularBlock;
import kranji.zi.SingularZi;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Reverse-lookup mapping a singular glyph to a typed Java reference
 * suitable for emission in per-class typed record source.
 *
 * <p>Each {@link Ref} captures enough structure to emit both the
 * <em>value</em> expression ({@code Outer.CONSTANT}, e.g.
 * {@code NatureElements.RI} or {@code BasicComponents.DAN_REN_PANG})
 * and the <em>type</em> expression ({@code NestedType}, e.g. {@code Ri},
 * {@code DanRenPang}) with its corresponding import path.</p>
 *
 * <p>For self-contained family classes (e.g. {@link NatureElements}) the
 * value-container and type-container coincide. For re-export hubs like
 * {@link BasicComponents} they diverge — the constant lives on
 * {@code BasicComponents} but the record is declared on
 * {@code PersonFamily} / {@code WaterFamily} / …, so the two import paths
 * must be emitted separately.</p>
 *
 * <p>Scanning priority mirrors the depth-1 generator: {@link BasicComponents}
 * first (so radical-form parts win over syllabic aliases), then each
 * singular family class.</p>
 */
public final class PerclassSingularIndex {

    /**
     * Java reference to a singular block suitable for emission in
     * typed per-class source.
     *
     * @param valueContainerFqn    FQN of the class that exposes the
     *                             {@code static final} field (e.g.
     *                             {@code kranji.component.basic.BasicComponents}
     *                             or {@code kranji.singular.nature.NatureElements}).
     * @param valueContainerSimple simple name of the same class
     *                             (e.g. {@code "BasicComponents"}).
     * @param typeContainerFqn     FQN of the class that <em>declares</em>
     *                             the nested record type (e.g.
     *                             {@code kranji.component.basic.PersonFamily}).
     * @param typeContainerSimple  simple name of the type-declaring class
     *                             (e.g. {@code "PersonFamily"}).
     * @param nestedTypeSimple     simple name of the nested record type
     *                             (e.g. {@code "DanRenPang"}, {@code "Ri"}).
     * @param constantName         the {@code public static final} field
     *                             name (e.g. {@code "DAN_REN_PANG"}).
     */
    public record Ref(String valueContainerFqn,
                      String valueContainerSimple,
                      String typeContainerFqn,
                      String typeContainerSimple,
                      String nestedTypeSimple,
                      String constantName) {

        /** {@code Outer.CONSTANT} — e.g. {@code BasicComponents.DAN_REN_PANG}. */
        public String valueExpression() {
            return valueContainerSimple + "." + constantName;
        }

        /** {@code NestedType} — e.g. {@code DanRenPang}, {@code Ri}. */
        public String typeExpression() {
            return nestedTypeSimple;
        }

        /** Import line for the value: {@code import Outer;}. */
        public String valueImport() {
            return valueContainerFqn;
        }

        /** Import line for the type: {@code import Container.NestedType;}. */
        public String typeImport() {
            return typeContainerFqn + "." + nestedTypeSimple;
        }
    }

    private static final Class<?>[] SOURCE_CLASSES = new Class<?>[] {
            BasicComponents.class,
            PeopleAndRoles.class,
            BodyParts.class,
            Materials.class,
            NatureElements.class,
            Animals.class,
            RadicalComponents.class,
            Structures.class,
            ToolsAndVessels.class,
            NumbersAndMeasure.class,
            ActionsAndStates.class,
            AbstractConcepts.class,
            SpaceAndDirection.class,
            PlantsAndAgriculture.class
    };

    private final Map<String, Ref> byGlyph;

    private PerclassSingularIndex(Map<String, Ref> byGlyph) {
        this.byGlyph = byGlyph;
    }

    public static PerclassSingularIndex build() {
        Map<String, Ref> map = new LinkedHashMap<>();
        for (Class<?> c : SOURCE_CLASSES) {
            scan(c, map);
        }
        return new PerclassSingularIndex(map);
    }

    public Ref find(String glyph) { return byGlyph.get(glyph); }

    public Ref require(String glyph) {
        Ref r = byGlyph.get(glyph);
        if (r == null) {
            throw new IllegalStateException(
                    "No singular reference for glyph \"" + glyph + "\". "
                            + "Add it to BasicComponents or one of the singular "
                            + "family classes, then rebuild the codegen module.");
        }
        return r;
    }

    public int size() { return byGlyph.size(); }

    // ── Internal ─────────────────────────────────────────────────────────

    private static void scan(Class<?> scanClass, Map<String, Ref> map) {
        for (Field f : scanClass.getDeclaredFields()) {
            int mods = f.getModifiers();
            if (!Modifier.isStatic(mods) || !Modifier.isFinal(mods)
                    || !Modifier.isPublic(mods)) continue;
            if (f.isAnnotationPresent(Deprecated.class)) continue;

            Object value;
            try { value = f.get(null); }
            catch (IllegalAccessException e) {
                throw new RuntimeException("Reflection failure on "
                        + scanClass.getSimpleName() + "." + f.getName(), e);
            }
            if (value == null) continue;

            String glyph = glyphOf(value);
            if (glyph == null || glyph.isEmpty()) continue;

            // Type-declaration lookup: prefer the field's declared type
            // if it's a record; fall back to the value's runtime class.
            Class<?> recordType = f.getType();
            if (!recordType.isRecord()) recordType = value.getClass();

            String nestedSimple = recordType.getSimpleName();
            Class<?> typeContainer = recordType.getEnclosingClass();
            if (typeContainer == null) {
                // Top-level record — treat scan class as container
                // (unlikely today but tolerated for future refactors).
                typeContainer = scanClass;
            }

            Ref ref = new Ref(
                    scanClass.getName(),
                    scanClass.getSimpleName(),
                    typeContainer.getName(),
                    typeContainer.getSimpleName(),
                    nestedSimple,
                    f.getName());

            map.putIfAbsent(glyph, ref);
        }
    }

    private static String glyphOf(Object value) {
        if (value instanceof SingularZi sz)    return sz.glyph();
        if (value instanceof SingularBlock sb) return sb.glyph();
        return null;
    }
}
