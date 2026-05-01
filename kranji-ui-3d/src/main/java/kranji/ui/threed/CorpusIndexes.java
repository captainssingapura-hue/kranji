package kranji.ui.threed;

import kranji.common.perclass.staging.AllZiRecords;
import kranji.library.BasicSet;
import kranji.library.ZiLibrary;
import kranji.singular.SingularFamiliesPerclass;
import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.SingularZi;
import kranji.zi.Zi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pure-function builder for the two glyph-keyed indexes the 3D viewer
 * needs:
 *
 * <ul>
 *   <li>{@code byGlyph} — glyph → preferred {@link Zi} adapter
 *       (used to resolve any structure click target).</li>
 *   <li>{@code immediateUsersByGlyph} — glyph → list of Zi that use it
 *       as an immediate slot value (drives the Component-Graph view).</li>
 * </ul>
 *
 * <p>Both are built once at startup; both keyed by glyph string (not
 * by instance) — same strategy used by the main explorer.</p>
 */
public final class CorpusIndexes {

    public record Indexes(
            Map<String, Zi> byGlyph,
            Map<String, List<Zi>> immediateUsersByGlyph
    ) {}

    private CorpusIndexes() {}

    /** Loads BasicSet + AllZiRecords and returns both indexes. */
    public static Indexes loadAndBuild() {
        SingularFamiliesPerclass.registerInto(BasicSet.INSTANCE);
        ZiLibrary.load(BasicSet.INSTANCE);
        return new Indexes(buildGlyphIndex(), buildImmediateUsersIndex());
    }

    /** Glyph → preferred {@link Zi} adapter. */
    private static Map<String, Zi> buildGlyphIndex() {
        var idx = new HashMap<String, Zi>();
        for (var member : BasicSet.INSTANCE.components()) {
            if (member instanceof SingularZi sz) {
                idx.putIfAbsent(sz.glyph(), sz);
            }
        }
        for (Zi z : AllZiRecords.ALL) {
            String g = z.character();
            if (g != null && !g.isEmpty()) idx.putIfAbsent(g, z);
        }
        return Map.copyOf(idx);
    }

    /** Inverse index: glyph → list of Zi that use it as an immediate slot. */
    private static Map<String, List<Zi>> buildImmediateUsersIndex() {
        var idx = new HashMap<String, List<Zi>>();
        for (Zi z : AllZiRecords.ALL) {
            if (!(z.structure() instanceof ComposedBlock cb)) continue;
            for (BlockStructure slot : cb.composition().components()) {
                String g = slot.glyph();
                if (g == null || g.isEmpty()) continue;
                idx.computeIfAbsent(g, k -> new ArrayList<>()).add(z);
            }
        }
        // Wrap the inner lists for immutability.
        var out = new HashMap<String, List<Zi>>();
        idx.forEach((k, v) -> out.put(k, List.copyOf(v)));
        return Map.copyOf(out);
    }
}
