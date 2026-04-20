package kranji.codegen.depth1;

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
 * Reverse-lookup table mapping a singular glyph (e.g. {@code "人"},
 * {@code "刂"}) to the Java reference {@link Ref} that should appear in
 * generated code (e.g. {@code PeopleAndRoles.REN},
 * {@code BasicComponents.LI_DAO_PANG}).
 *
 * <p>Built once by reflecting on the singular family classes plus
 * {@link BasicComponents}. {@link BasicComponents} is scanned first so
 * its flat constants take precedence over the family-class constants
 * for radical-form parts (the family classes still cover the
 * {@link SingularZi} entries that BasicComponents does not re-export).</p>
 *
 * <p>If two distinct fields would map to the same glyph, the first one
 * wins — including {@link Deprecated} aliases, which are skipped if
 * a non-deprecated field already covers that glyph.</p>
 */
public final class SingularZiIndex {

    /** A Java reference suitable for emission in generated code. */
    public record Ref(String importFqn, String simpleClass, String constant) {
        /** {@code SimpleClass.CONSTANT} — the source-level expression. */
        public String expression() {
            return simpleClass + "." + constant;
        }
    }

    /** Classes scanned, in priority order: BasicComponents wins ties. */
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

    private SingularZiIndex(Map<String, Ref> byGlyph) {
        this.byGlyph = byGlyph;
    }

    /** Build the index by scanning every {@link #SOURCE_CLASSES source class}. */
    public static SingularZiIndex build() {
        Map<String, Ref> map = new LinkedHashMap<>();
        for (Class<?> c : SOURCE_CLASSES) {
            scan(c, map);
        }
        return new SingularZiIndex(map);
    }

    /** Look up the {@link Ref} for a glyph; returns {@code null} if absent. */
    public Ref find(String glyph) {
        return byGlyph.get(glyph);
    }

    /** Same as {@link #find} but throws when the glyph is missing. */
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

    /** Total number of distinct glyphs in the index. */
    public int size() {
        return byGlyph.size();
    }

    // ── Internal ──────────────────────────────────────────────────────

    private static void scan(Class<?> c, Map<String, Ref> map) {
        for (Field f : c.getDeclaredFields()) {
            int mods = f.getModifiers();
            if (!Modifier.isStatic(mods) || !Modifier.isFinal(mods)
                    || !Modifier.isPublic(mods)) continue;
            // Skip deprecated aliases — they share identity with a
            // canonical field that scan order already (or will) record.
            if (f.isAnnotationPresent(Deprecated.class)) continue;

            Object value;
            try { value = f.get(null); }
            catch (IllegalAccessException e) {
                throw new RuntimeException("Reflection failure on "
                        + c.getSimpleName() + "." + f.getName(), e);
            }
            if (value == null) continue;
            String glyph = glyphOf(value);
            if (glyph == null || glyph.isEmpty()) continue;

            // First-seen wins; subsequent matches are silently ignored
            // so the priority order in SOURCE_CLASSES is meaningful.
            map.putIfAbsent(glyph, new Ref(
                    c.getName(),
                    c.getSimpleName(),
                    f.getName()));
        }
    }

    private static String glyphOf(Object value) {
        if (value instanceof SingularZi sz)    return sz.glyph();
        if (value instanceof SingularBlock sb) return sb.glyph();
        return null;
    }
}
