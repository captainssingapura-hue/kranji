package kranji.common.perclass;

import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.ComposedZi;
import kranji.zi.ComposedZiT;
import kranji.zi.SingularBlock;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;
import kranji.zi.Zi;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Reflects on every generated typed record and asserts that none of its
 * typed-layout slot parameters are generic supertypes (e.g. {@code ComposedZi},
 * {@code BlockStructure}, {@code Zi}).
 *
 * <p>The whole point of the typed-layout hierarchy is <em>precision</em>:
 * each composition slot carries its concrete per-class type so that
 * transposition errors (e.g. {@code LeftRightT<Yue, Ri>} where only
 * {@code LeftRightT<Ri, Yue>} is valid) are caught at compile time. If a
 * generator bug or hand-edit accidentally lets {@code ComposedZi} slip
 * into a slot parameter, the invariant collapses — so we assert it
 * mechanically across every record.</p>
 */
class TypedSlotInvariantTest {

    private static final Set<Class<?>> FORBIDDEN_SLOT_TYPES = Set.of(
            ComposedZi.class,
            ComposedZiT.class,
            ComposedBlock.class,
            SingularBlock.class,
            SingularZi.class,
            SingularPart.class,
            BlockStructure.class,
            Zi.class
    );

    @Test
    void registryIsNonEmpty() {
        assertFalse(AllPerclassRecords.ALL.isEmpty(),
                "AllPerclassRecords.ALL must not be empty");
    }

    @Test
    void everyRecordHasPreciseSlotTypes() {
        List<String> violations = new ArrayList<>();
        for (ComposedZiT record : AllPerclassRecords.ALL) {
            Class<?> c = record.getClass();
            for (Type iface : c.getGenericInterfaces()) {
                if (!(iface instanceof ParameterizedType pt)) continue;
                for (Type arg : pt.getActualTypeArguments()) {
                    Class<?> raw = rawOf(arg);
                    if (raw != null && FORBIDDEN_SLOT_TYPES.contains(raw)) {
                        violations.add(c.getName() + " uses forbidden slot "
                                + "type parameter " + raw.getSimpleName()
                                + " on interface " + rawOf(pt).getSimpleName());
                    }
                }
            }
        }
        assertTrue(violations.isEmpty(),
                "Slot-type precision violated for " + violations.size()
                        + " records:\n  " + String.join("\n  ", violations));
    }

    @Test
    void registryCoversExpectedDepthRange() {
        // 441 depth-1 + 58 depth-2 + 2 depth-3 + 1 depth-5 = 502 minimum;
        // the catalog has grown to 507. Guard against accidental shrinkage.
        assertTrue(AllPerclassRecords.ALL.size() >= 500,
                "Expected at least 500 typed records, got "
                        + AllPerclassRecords.ALL.size());
    }

    /** Raw {@link Class} for a reflected {@link Type}; null for type variables / wildcards. */
    private static Class<?> rawOf(Type t) {
        if (t instanceof Class<?> c) return c;
        if (t instanceof ParameterizedType pt) return (Class<?>) pt.getRawType();
        return null;
    }
}
