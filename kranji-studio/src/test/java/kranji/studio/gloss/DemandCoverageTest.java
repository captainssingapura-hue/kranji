package kranji.studio.gloss;

import kranji.reading.content.Articles;
import kranji.reading.content.DemoLibrary;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.model.ArticleCensus;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * How much of what the reader asks for can actually be explained.
 *
 * <h2>Demand, not corpus</h2>
 *
 * <p>The corpus has 8,105 characters and the number that matters is much
 * smaller: the (character, reading) pairs the bundled articles actually use.
 * Glossing those is what makes the library self-contained; glossing beyond them
 * is coverage nobody asked for yet.</p>
 *
 * <p>Demand is computed here rather than written down, from the same
 * {@link ArticleCensus} the reader uses — so adding an article moves the target
 * automatically instead of leaving a constant quietly wrong.</p>
 *
 * <h2>What this fails on</h2>
 *
 * <p>Not on the gap. The set is being written and a test that failed until it
 * was finished would be red for weeks and ignored by the second day. It fails
 * when coverage <b>goes backwards</b>, and it writes the outstanding pairs to
 * {@code target/outstanding.tsv} in the shape of the file they belong in — so
 * the worklist is a by-product of measuring rather than a document to maintain.</p>
 */
class DemandCoverageTest {

    /**
     * Where coverage stood when this was last looked at.
     *
     * <p>A ratchet, not a target. Raise it as pairs are authored; it exists to
     * catch a regression, and a regression here is a reader meeting a character
     * the library can no longer explain.</p>
     */
    private static final int COVERED_AT_LEAST = 447;

    /** Every (codepoint, reading) the bundled articles use, in reading order. */
    private static Set<String> demand() {
        var out = new LinkedHashSet<String>();
        for (ArticleCollection c : DemoLibrary.INSTANCE.tree().collections()) {
            for (ArticleRef ref : c.articles()) {
                Articles.read(c.address(ref.id()), ref).ifPresent(parsed ->
                        parsed.article().ifPresent(a ->
                                out.addAll(ArticleCensus.of(a).pairs().keySet())));
            }
        }
        return out;
    }

    /** Every pair some collection on the classpath can explain. */
    private static Map<String, String> glossed() {
        var out = new LinkedHashMap<String, String>();
        for (ZiGloss g : GlossWorkbench.glosses().all()) {
            for (SoundGloss s : g.sounds()) out.put(s.key(), s.primary().text());
        }
        return out;
    }

    @Test
    void coverageOfTheBundledLibraryHasNotGoneBackwards() {
        Set<String> demand = demand();
        Map<String, String> glossed = glossed();

        var outstanding = new ArrayList<String>();
        for (String pair : demand) if (!glossed.containsKey(pair)) outstanding.add(pair);

        int covered = demand.size() - outstanding.size();
        writeWorklist(outstanding, demand.size(), covered);

        assertAtLeast(covered, COVERED_AT_LEAST,
                covered + " of " + demand.size() + " demanded pairs are glossed; "
              + outstanding.size() + " outstanding, listed in target/outstanding.tsv");
    }

    @Test
    void everyGlossBeyondDemandCompletesAPolyphone() {
        // A gloss the library never reaches is not automatically waste: a
        // polyphone glossed at some readings and not others implies the rest
        // are meaningless, so completing one is required rather than optional.
        //
        // This used to cap the count at a number picked when there was one such
        // gloss. That was the wrong shape - finishing the set produced fifteen,
        // every one of them a completion, and a ceiling would have failed on
        // work that was correct. What matters is not how many there are but
        // that each one has a reason, so the reason is what gets asserted:
        // the CHARACTER must be demanded, even where this reading is not.
        Set<String> demand = demand();
        var demandedCharacters = demand.stream()
                .map(p -> p.substring(0, p.indexOf(':'))).collect(Collectors.toSet());

        var stray = new ArrayList<String>();
        for (String pair : glossed().keySet()) {
            if (demand.contains(pair)) continue;
            String character = pair.substring(0, pair.indexOf(':'));
            if (!demandedCharacters.contains(character)) stray.add(pair);
        }

        assertEquals(List.of(), stray,
                "glossed at a character the library never reads at all: " + stray
              + " - completing a polyphone is deliberate; a stray character is drift");
    }

    /**
     * The outstanding pairs, in the shape of the file they belong in.
     *
     * <p>Written to {@code target}, never to source: a generated worklist
     * committed beside the data is a second thing to keep in step, and the
     * first time the articles change it becomes a confident lie.</p>
     */
    private static void writeWorklist(List<String> outstanding, int demand, int covered) {
        var out = new StringBuilder();
        out.append("# Outstanding (character, reading) pairs the bundled library uses.\n");
        out.append("# ").append(covered).append(" of ").append(demand)
           .append(" glossed; ").append(outstanding.size()).append(" to write.\n");
        out.append("#\n# Generated by DemandCoverageTest. Fill a meaning and move the row\n");
        out.append("# into kranji-gloss senses.tsv - the columns already line up.\n");
        out.append("# codepoint\tglyph\treading\torder\tmeaning\texamples\tbecause\n");

        for (String pair : outstanding) {
            int colon = pair.indexOf(':');
            int cp = Integer.parseInt(pair.substring(0, colon));
            out.append(cp).append('\t')
               .append(new String(Character.toChars(cp))).append('\t')
               .append(pair.substring(colon + 1)).append('\t')
               .append(0).append("\t\t\t\n");
        }
        try {
            Path path = Path.of("target", "outstanding.tsv");
            Files.createDirectories(path.getParent());
            Files.writeString(path, out.toString());
        } catch (IOException e) {
            throw new UncheckedIOException("could not write the worklist", e);
        }
    }

    private static void assertAtLeast(int actual, int floor, String message) {
        if (actual < floor) {
            throw new AssertionError("coverage fell from " + floor + " to " + actual
                                   + "\n" + message);
        }
    }
}
