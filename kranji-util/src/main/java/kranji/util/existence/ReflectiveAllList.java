package kranji.util.existence;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builds the {@code ALL} list of a holder class by reflecting its
 * {@code public static final} fields of a given element type, in source
 * declaration order.
 *
 * <p>Motivation: every partition or aggregator class in
 * {@code kranji-common} and {@code kranji-singulars} used to maintain a
 * hand-rolled {@code List.of(…)} that duplicated the field names. Adding
 * a record meant editing the declaration and the list, and forgetting
 * either half produced a silent drop-out from the registry. This helper
 * replaces the list with a one-liner:</p>
 *
 * <pre>
 * public static final List&lt;ComposedZi&gt; ALL =
 *         ReflectiveAllList.of(D1S07P3.class, ComposedZi.class);
 * </pre>
 *
 * <p>The returned list is unmodifiable. Order is
 * {@link Class#getDeclaredFields()} order, which the JLS guarantees is
 * source order for a compiled class file produced by {@code javac} — the
 * property {@link kranji.util.existence.StrongTypedRegistry} relies on
 * for reproducible duplicate diagnostics.</p>
 */
public final class ReflectiveAllList {

    private ReflectiveAllList() {}

    /**
     * Collect every {@code public static final} field on {@code holder}
     * whose declared type is assignable to {@code elementType}, returning
     * their values in source declaration order.
     *
     * <p>Non-matching fields (different type, non-static, non-final,
     * non-public) are silently skipped — a holder class may mix
     * constants with helper fields.</p>
     *
     * @throws IllegalStateException if a matching field cannot be read
     *         (indicates a module-access misconfiguration, not authoring
     *         error) or if a matching field is {@code null}.
     */
    public static <T> List<T> of(Class<?> holder, Class<T> elementType) {
        List<T> out = new ArrayList<>();
        for (Field f : holder.getDeclaredFields()) {
            int m = f.getModifiers();
            if (!Modifier.isPublic(m))  continue;
            if (!Modifier.isStatic(m))  continue;
            if (!Modifier.isFinal(m))   continue;
            if (!elementType.isAssignableFrom(f.getType())) continue;

            Object value;
            try {
                value = f.get(null);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Cannot read " + holder.getName() + "." + f.getName() +
                        " — is the module exporting/opens its package?", e);
            }
            if (value == null) {
                throw new IllegalStateException(
                        holder.getName() + "." + f.getName() +
                        " is null; every ReflectiveAllList field must be" +
                        " initialised with a non-null instance.");
            }
            out.add(elementType.cast(value));
        }
        return Collections.unmodifiableList(out);
    }
}
