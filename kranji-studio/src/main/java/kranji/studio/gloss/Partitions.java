package kranji.studio.gloss;

import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiCollection;
import kranji.simple.gloss.ZiCollections;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiPartition;

import java.util.ArrayList;
import java.util.List;

/**
 * The 101 partitions, each with what is written, what was guessed, and what is
 * still open.
 *
 * <h2>Computed, not filed</h2>
 *
 * <p>Nothing on disk is organised by partition summary — the number of curated
 * rows in p041 is a property of p041's file, and counting it is cheaper than
 * maintaining a second file that says so. A partition's identity is arithmetic
 * anyway: {@link ZiPartition} is codepoint modulo 101, so membership is
 * derived everywhere rather than stored anywhere.</p>
 *
 * <h2>All 101, always</h2>
 *
 * <p>Including the ones with nothing in them. A selector that showed only
 * non-empty partitions would hide exactly the partitions where work has not
 * started, which is the opposite of what a worklist is for — p013 has no
 * curated rows at all and is the first place somebody should look.</p>
 */
public final class Partitions {

    private Partitions() {}

    /**
     * One partition, as a row.
     *
     * @param curated  sense rows a person has written
     * @param seeded   sense rows the machine guessed
     * @param issues   entries in the seeder's log
     * @param open     those nobody has settled
     */
    public record Row(int partition, int curated, int seeded, int issues, int open,
                      String source) {

        /** {@code p041}, so files, keys and labels all read the same. */
        public String label() { return "p%03d".formatted(partition); }
    }

    /**
     * The rows, counting the curated side from what was just read off disk.
     *
     * <p>Takes the partitions rather than fetching them, so the count in the
     * selector and the rows in the grid come from ONE read. Counting them
     * separately would let the two disagree for the length of an edit, which
     * is precisely the window this bench exists to work in.</p>
     */
    public static List<Row> rows(List<CuratedSource.Partition> curatedNow) {
        int[] seeded = countSenses(1);
        int[] issues = new int[ZiPartition.COUNT];
        int[] open = new int[ZiPartition.COUNT];

        for (SeedProblems.Row row : SeedProblems.rows()) {
            issues[row.partition()]++;
            if (row.state() == SeedProblems.State.OPEN) open[row.partition()]++;
        }

        var out = new ArrayList<Row>(ZiPartition.COUNT);
        for (CuratedSource.Partition p : curatedNow) {
            int senses = 0;
            for (ZiGloss entry : p.glosses()) {
                for (SoundGloss sound : entry.sounds()) senses += sound.senses().size();
            }
            // A partition that will not parse says so where the count goes -
            // a zero there would read as "nothing written yet", which is the
            // one thing it does not mean.
            String origin = p.ok() ? p.origin()
                    : p.origin() + "  [" + p.problems().size() + " problems]";
            out.add(new Row(p.partition(), senses, seeded[p.partition()],
                    issues[p.partition()], open[p.partition()], origin));
        }
        return List.copyOf(out);
    }

    /**
     * Sense rows per partition, from the layers at a given precedence.
     *
     * <p>Through {@code layers()}, because what the classpath offers is one
     * layered collection and its precedence is that of its top layer — asking
     * the collection directly would count the seed as curated.</p>
     */
    private static int[] countSenses(int precedence) {
        int[] out = new int[ZiPartition.COUNT];
        for (ZiCollection c : ZiCollections.discovered()) {
            for (ZiCollection layer : c.layers()) {
                if (layer.precedence() != precedence) continue;
                for (ZiGloss entry : layer.characters().all()) {
                    int p = ZiPartition.of(entry.zi().codePoint());
                    for (SoundGloss sound : entry.sounds()) out[p] += sound.senses().size();
                }
            }
        }
        return out;
    }

}
