package kranji.simple;

import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.simple.common2000.B;
import kranji.simple.common2000.C;
import kranji.simple.common2000.Ch;
import kranji.simple.common2000.D;
import kranji.simple.common2000.F;
import kranji.simple.common2000.G;
import kranji.simple.common2000.H;
import kranji.simple.common2000.J;
import kranji.simple.common2000.K;
import kranji.simple.common2000.L;
import kranji.simple.common2000.M;
import kranji.simple.common2000.N;
import kranji.simple.common2000.P;
import kranji.simple.common2000.Q;
import kranji.simple.common2000.R;
import kranji.simple.common2000.S;
import kranji.simple.common2000.Sh;
import kranji.simple.common2000.T;
import kranji.simple.common2000.X;
import kranji.simple.common2000.Z;
import kranji.simple.common2000.Zero;
import kranji.simple.common2000.Zh;
import kranji.zi.ZiCharUTF8;
import kranji.zi.ZiCharUTF8Codec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Every {@link SimpleZi} the project knows, keyed by character.
 *
 * <p>Aggregates the per-initial partitions the way {@code AllZiRecords} already
 * aggregates the structural populations. Consumers read from here rather than
 * naming a partition, so adding one is a change in this file alone.</p>
 *
 * <p>Lookup is by {@link ZiCharUTF8}, which is also how the two tiers meet: a
 * caller holding a character can ask this registry for its readings and the
 * structural registry for its composition. Neither tier holds a reference to
 * the other, so structural coverage can grow without touching anything here.</p>
 */
public final class SimpleZiRegistry {

    private SimpleZiRegistry() {}

    /** Every entry, in partition order. */
    public static final List<SimpleZi> ALL;

    private static final Map<ZiCharUTF8, SimpleZi> BY_GLYPH;
    private static final Map<Initial, List<SimpleZi>> BY_INITIAL;

    /** Every syllable declaration, in partition order. */
    public static final List<SyllableDecl> DECLARATIONS;

    static {
        var decls = new ArrayList<SyllableDecl>();
        decls.addAll(B.DECLARATIONS);   decls.addAll(C.DECLARATIONS);
        decls.addAll(Ch.DECLARATIONS);  decls.addAll(D.DECLARATIONS);
        decls.addAll(F.DECLARATIONS);   decls.addAll(G.DECLARATIONS);
        decls.addAll(H.DECLARATIONS);   decls.addAll(J.DECLARATIONS);
        decls.addAll(K.DECLARATIONS);   decls.addAll(L.DECLARATIONS);
        decls.addAll(M.DECLARATIONS);   decls.addAll(N.DECLARATIONS);
        decls.addAll(P.DECLARATIONS);   decls.addAll(Q.DECLARATIONS);
        decls.addAll(R.DECLARATIONS);   decls.addAll(S.DECLARATIONS);
        decls.addAll(Sh.DECLARATIONS);  decls.addAll(T.DECLARATIONS);
        decls.addAll(X.DECLARATIONS);   decls.addAll(Z.DECLARATIONS);
        decls.addAll(Zero.DECLARATIONS); decls.addAll(Zh.DECLARATIONS);
        DECLARATIONS = List.copyOf(decls);

        // Emphasis is spread across declarations, so the invariant it carries
        // - exactly one principal reading per character - is checked before
        // anything derived from it is built.
        PhonicDeclarations.requireValid(DECLARATIONS);

        // SimpleZi is derived, never authored: a character's readings are
        // whichever syllables declare it, principal first.
        var principal = new LinkedHashMap<ZiCharUTF8, PinyinSyllable>();
        var others = new LinkedHashMap<ZiCharUTF8, List<PinyinSyllable>>();
        for (SyllableDecl decl : DECLARATIONS) {
            for (ZiDecl zd : decl.characters()) {
                if (zd.principal()) principal.put(zd.zi(), decl.syllable());
                else others.computeIfAbsent(zd.zi(), k -> new ArrayList<>())
                           .add(decl.syllable());
            }
        }

        var all = new ArrayList<SimpleZi>(principal.size());
        for (var e : principal.entrySet()) {
            all.add(new SimpleZi(e.getKey(), e.getValue(),
                    others.getOrDefault(e.getKey(), List.of())));
        }
        ALL = List.copyOf(all);

        var byGlyph = new LinkedHashMap<ZiCharUTF8, SimpleZi>();
        for (SimpleZi z : ALL) {
            SimpleZi prior = byGlyph.put(z.glyph(), z);
            if (prior != null) {
                throw new IllegalStateException(
                        "duplicate SimpleZi for " + z.glyph() + " - a character has exactly one "
                      + "home, namely the partition of its default phonic");
            }
        }
        BY_GLYPH = Map.copyOf(byGlyph);

        var byInitial = new TreeMap<Initial, List<SimpleZi>>();
        for (SimpleZi z : ALL) {
            byInitial.computeIfAbsent(z.partitionInitial(), k -> new ArrayList<>()).add(z);
        }
        byInitial.replaceAll((k, v) -> List.copyOf(v));
        BY_INITIAL = Map.copyOf(byInitial);
    }

    /** The entry for {@code glyph}, if the simple tier covers it. */
    public static Optional<SimpleZi> find(ZiCharUTF8 glyph) {
        return Optional.ofNullable(BY_GLYPH.get(glyph));
    }

    /**
     * The entry for a one-character string, if the simple tier covers it.
     *
     * <p>Convenience for callers still holding {@link String} — article text,
     * imported lists, the existing corpus. Anything that is not a single Han
     * character yields empty rather than throwing, because at this boundary
     * bad input is a fact about the world rather than a defect.</p>
     */
    public static Optional<SimpleZi> find(String glyph) {
        return ZiCharUTF8Codec.INSTANCE.tryFrom(glyph).flatMap(SimpleZiRegistry::find);
    }

    /** Entries whose default reading carries {@code initial}. */
    public static List<SimpleZi> byInitial(Initial initial) {
        return BY_INITIAL.getOrDefault(initial, List.of());
    }

    /** Initials that currently have at least one entry, in enum order. */
    public static List<Initial> populatedInitials() {
        return List.copyOf(BY_INITIAL.keySet());
    }

    /** Characters with more than one recorded reading. */
    public static List<SimpleZi> polyphonic() {
        return ALL.stream().filter(SimpleZi::isPolyphonic).toList();
    }

    public static int size() {
        return ALL.size();
    }
}
