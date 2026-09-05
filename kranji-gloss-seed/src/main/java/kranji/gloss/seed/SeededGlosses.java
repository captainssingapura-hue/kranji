package kranji.gloss.seed;

import kranji.gloss.tsv.GlossTsv;
import kranji.simple.gloss.EgKey;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.ZiCollection;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiCharUTF8;
import kranji.zi.ZiPartition;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The corpus, seeded from Unihan and not yet checked by anybody.
 *
 * <h2>It yields to the hand-crafted set</h2>
 *
 * <p>{@link #precedence()} is 1, so a character somebody actually read and
 * wrote about wins over a machine's first guess at the same character. That is
 * decision gc5, and it holds by construction rather than by classpath luck.</p>
 *
 * <h2>What it does not have</h2>
 *
 * <p>No examples and no phrases. A seeded row is a <em>definition</em>; a
 * hand-crafted one is a definition with a phrase that shows it, which is the
 * whole argument for the extra structure. Seeding cannot produce the second, so
 * it does not pretend to — {@link #phrases()} is empty and the reader simply
 * shows a meaning with nothing to demonstrate it.</p>
 *
 * <p>No polyphones either. A per-character field cannot say which sense belongs
 * to which reading, so the seeder queues them instead of guessing.</p>
 *
 * <h2>Missing partitions are normal</h2>
 *
 * <p>Partitions are seeded one at a time and reviewed one at a time, so at any
 * moment some exist and some do not. An absent file is skipped rather than
 * failing: the alternative would make the collection unloadable until all 101
 * were generated, which is exactly the big-bang this work is arranged to
 * avoid.</p>
 */
public final class SeededGlosses implements ZiCollection {

    /** Where a partition's seeded rows live, by index. */
    private static final String PATTERN = "/kranji/seed/p%03d.tsv";

    public static final SeededGlosses INSTANCE = new SeededGlosses();

    /** Public for {@link java.util.ServiceLoader}, which needs a no-arg constructor. */
    public SeededGlosses() {}

    @Override public String name()    { return "Unihan seed"; }

    @Override public String licence() {
        return "seeded from the Unicode Han Database (Unihan), (c) Unicode, Inc. "
             + "- Unicode Terms of Use, which permit redistribution";
    }

    /**
     * Below the hand-crafted set.
     *
     * <p>The number is not arbitrary and not a scale: it says only "after 0".
     * If a third collection ever arrives, what matters is where it sits
     * relative to a person's judgement, and everything machine-made sits after
     * it.</p>
     */
    @Override public int precedence() { return 1; }

    private static final List<ZiGloss> ENTRIES = load();

    private static List<ZiGloss> load() {
        var out = new ArrayList<ZiGloss>();
        for (int p = 0; p < ZiPartition.COUNT; p++) {
            String resource = PATTERN.formatted(p);
            String text = read(resource);
            if (text == null) continue;          // not seeded yet; normal
            GlossTsv.Read<ZiGloss> parsed = GlossTsv.readSenses(resource, text);
            if (!parsed.ok()) {
                // A malformed seed is a generator bug, and shipping it as a
                // partially loaded collection would hide which partition broke.
                throw new IllegalStateException(
                        resource + " did not parse:\n  " + parsed.problems());
            }
            out.addAll(parsed.entries());
        }
        return List.copyOf(out);
    }

    private static final Map<ZiCharUTF8, ZiGloss> BY_CHARACTER = index();

    private static Map<ZiCharUTF8, ZiGloss> index() {
        // LinkedHashMap, not Map.copyOf: the iteration order of a frozen map is
        // unspecified, and this one is read out again by all().
        var out = new LinkedHashMap<ZiCharUTF8, ZiGloss>();
        for (ZiGloss entry : ENTRIES) out.put(entry.zi(), entry);
        return out;
    }

    private static final Characters CHARACTERS = new Characters() {
        @Override public Optional<ZiGloss> find(ZiCharUTF8 zi) {
            return Optional.ofNullable(BY_CHARACTER.get(zi));
        }
        @Override public List<ZiGloss> all() { return ENTRIES; }
        @Override public int size()          { return ENTRIES.size(); }
    };

    /** Empty, and deliberately so - see the note on this class. */
    private static final Phrases NO_PHRASES = new Phrases() {
        @Override public Optional<ExampleEntry> find(EgKey key) { return Optional.empty(); }
        @Override public List<ExampleEntry> all()               { return List.of(); }
        @Override public int size()                             { return 0; }
    };

    @Override public Characters characters() { return CHARACTERS; }
    @Override public Phrases phrases()       { return NO_PHRASES; }

    /** How many partitions have been seeded so far. */
    public static int partitionsPresent() {
        int found = 0;
        for (int p = 0; p < ZiPartition.COUNT; p++) {
            if (read(PATTERN.formatted(p)) != null) found++;
        }
        return found;
    }

    /** The file's text, or null when that partition has not been seeded. */
    private static String read(String resource) {
        try (InputStream in = SeededGlosses.class.getResourceAsStream(resource)) {
            if (in == null) return null;
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + resource, e);
        }
    }
}
