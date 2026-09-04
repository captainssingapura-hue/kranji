package kranji.studio.gloss;

import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.simple.gloss.ZiGloss;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The readings themselves, as a relation you can pick from.
 *
 * <h2>Why this sits above demand rather than beside it</h2>
 *
 * <p>The demand relation is keyed on the pair — 462 rows of (character,
 * reading) — which is the right grain for the work but the wrong grain for a
 * question like "what does every fourth-tone <i>d</i> look like". Asking that
 * of a 462-row grid means a text search that matches meanings as readily as
 * readings.</p>
 *
 * <p>So the readings get their own relation, one row each, and demand hangs
 * below it. Selecting sounds narrows the pairs; selecting nothing leaves them
 * whole, because a filter nobody has touched is not a filter that excludes
 * everything.</p>
 *
 * <h2>Derived from demand, not from the glosses</h2>
 *
 * <p>Taken from {@link GlossDemand#rows} rather than from the sounds the
 * collections happen to hold. That is what makes the two relations agree in
 * both directions: no reading can be listed that selects an empty demand, and
 * no demand row can be made unreachable by a reading nobody offered. Deriving
 * it from the glosses would drop every {@code todo} pair on the floor, which
 * is precisely the half the workbench exists for.</p>
 */
public final class GlossSounds {

    /**
     * The zero initial, written.
     *
     * <p>{@link Initial#ZERO} spells itself as the empty string, which is
     * correct pinyin and useless in a filter — an empty option in a dropdown
     * already means "no filter", and the two would be indistinguishable. The
     * conventional mark for it says the same thing out loud.</p>
     */
    public static final String ZERO_INITIAL = "∅";

    /**
     * The empty rime (空韵), written.
     *
     * <p>The same problem one column over. {@code Body.NULL} is the nucleus of
     * zi, ci, si, zhi, chi, shi and ri — acoustic rather than a true vowel, so
     * it spells as nothing, which is correct and unusable as a filter value.
     * {@code -i} is how the codebase's own documentation refers to it, and no
     * real final is spelled that way.</p>
     */
    public static final String EMPTY_RHYME = "-i";

    private GlossSounds() {}

    /**
     * One reading, decomposed.
     *
     * <p>{@code rhyme} is the 韵母 — {@code final} is a Java keyword, and
     * {@link PinyinSyllable} met the same wall and called it {@code fin}. The
     * column a person reads still says "final".</p>
     */
    public record Row(String reading, String initial, String rhyme, int tone,
                      int characters, int todo) {}

    /**
     * Every reading the demand relation uses, in the order it is first met.
     *
     * <p>Library order, like demand itself, so the two grids read the same way
     * round and the first row of one is about the first row of the other.</p>
     */
    public static List<Row> rows(List<ZiGloss> glosses) {
        // reading -> [characters, todo]. Counted here rather than by the grid
        // so a filtered view can still say how much work a reading carries.
        var tally = new LinkedHashMap<String, int[]>();
        for (GlossDemand.Row r : GlossDemand.rows(glosses)) {
            int[] counts = tally.computeIfAbsent(r.reading(), k -> new int[2]);
            counts[0]++;
            if ("todo".equals(r.status())) counts[1]++;
        }

        var out = new ArrayList<Row>();
        tally.forEach((reading, counts) -> {
            PinyinSyllable syllable = PinyinSyllable.parseCanonical(reading);
            String rhyme = syllable.fin().spelling();
            out.add(new Row(reading,
                    syllable.initial() == Initial.ZERO
                            ? ZERO_INITIAL : syllable.initial().pinyin(),
                    rhyme.isEmpty() ? EMPTY_RHYME : rhyme,
                    syllable.tone().number(),
                    counts[0], counts[1]));
        });
        return List.copyOf(out);
    }
}
