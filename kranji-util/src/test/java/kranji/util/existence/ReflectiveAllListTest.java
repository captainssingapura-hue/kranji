package kranji.util.existence;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for {@link ReflectiveAllList}: confirms declaration-order,
 * element-type filtering, skipping of non-public / non-static / non-final
 * fields, and null-field diagnostics.
 */
final class ReflectiveAllListTest {

    interface Token { String name(); }

    /** Holder with a mix of relevant and irrelevant fields, to exercise filtering. */
    static final class Holder {
        public static final Token ALPHA = () -> "alpha";
        public static final Token BRAVO = () -> "bravo";
        public static final String UNRELATED_TYPE = "skip-me";   // wrong type
        static final Token PACKAGE_PRIVATE = () -> "skip-me";    // not public
        public static Token nonFinal = () -> "skip-me";          // not final
        public final Token nonStatic = () -> "skip-me";          // not static
        public static final Token CHARLIE = () -> "charlie";

        private Holder() {}
    }

    @Test
    void returnsMatchingFieldsInDeclarationOrder() {
        List<Token> all = ReflectiveAllList.of(Holder.class, Token.class);
        assertEquals(3, all.size(), "Only the three public-static-final Token fields should appear");
        assertEquals("alpha",   all.get(0).name());
        assertEquals("bravo",   all.get(1).name());
        assertEquals("charlie", all.get(2).name());
    }

    @Test
    void returnedListIsUnmodifiable() {
        List<Token> all = ReflectiveAllList.of(Holder.class, Token.class);
        assertThrows(UnsupportedOperationException.class,
                () -> all.add(() -> "mutation-attempt"));
    }

    @Test
    void identityPreserved() {
        List<Token> all = ReflectiveAllList.of(Holder.class, Token.class);
        assertSame(Holder.ALPHA, all.get(0));
        assertSame(Holder.BRAVO, all.get(1));
        assertSame(Holder.CHARLIE, all.get(2));
    }

    /** Holder with a null-valued public-static-final matching field. */
    static final class NullHolder {
        public static final Token OOPS = null;
        private NullHolder() {}
    }

    @Test
    void nullFieldIsRejected() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> ReflectiveAllList.of(NullHolder.class, Token.class));
        assertTrue(e.getMessage().contains("OOPS"),
                "Diagnostic should name the offending field: " + e.getMessage());
    }

    @Test
    void emptyHolderYieldsEmptyList() {
        final class Empty { private Empty() {} }
        assertEquals(List.of(), ReflectiveAllList.of(Empty.class, Token.class));
    }
}
