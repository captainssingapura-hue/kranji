package kranji.codegen.gloss;

import kranji.simple.gloss.Meaning;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * One {@code kDefinition} field, turned into the few senses this model wants.
 *
 * <h2>The separator does not mean what ours means</h2>
 *
 * <p>Unihan writes {@code throne; position, post; rank, status; seat}: the
 * semicolon divides senses and the comma offers near-synonyms within one. Our
 * own format is the other way round — a semicolon <em>joins</em>, and
 * {@code "a hill; a mountain"} is deliberately one sense, because dividing is
 * what a row boundary does.</p>
 *
 * <p>So a semicolon group becomes a row and its commas are left alone. Getting
 * that backwards would produce four senses where the corpus meant one, or one
 * sense reading like a dictionary entry.</p>
 *
 * <h2>Conservative on purpose</h2>
 *
 * <p>This is a <b>seed for a person to check</b>, not an answer. Where it is
 * unsure it says so and keeps the material, because a reviewer can delete a bad
 * sense in a second and cannot recover one that was silently dropped.</p>
 *
 * <p>The one thing it does drop is material that is not a meaning at all —
 * cross-references, radical numbers, encyclopaedia. Those are noise a reviewer
 * would delete every time, and leaving them in would make the flags useless by
 * raising one on nearly every row.</p>
 */
public final class GlossSeedPolicy {

    /**
     * How many senses a seeded reading may carry.
     *
     * <p>Three. The hand-crafted set averages 1.3 senses per pair and its
     * longest reading has three, so three is the observed ceiling of what
     * somebody writing carefully actually produces. A dictionary field will
     * happily offer eight; taking all of them would turn a reader's cell into
     * a paragraph.</p>
     */
    public static final int MAX_SENSES = 3;

    /**
     * Longer than this is not a gloss this model can hold.
     *
     * <p>Taken from {@link Meaning#MAX_LENGTH} rather than chosen, because the
     * model already refuses a longer one — "a child mid-story will not read a
     * paragraph". A second copy of the number would drift, and the symptom
     * would be a generator that builds a whole partition and throws on its
     * last row.</p>
     *
     * <p>Which is exactly what happened: the first run died on 岷,
     * "Minshan mountain range in northern Sichuan and southern Gansu, Min
     * River", at 72 characters.</p>
     */
    static final int LONG_SENSE = Meaning.MAX_LENGTH;

    private GlossSeedPolicy() {}

    /** Why a seeded row might not be trustworthy. Empty means nothing noticed. */
    public enum Doubt {
        /** More senses on offer than were taken; the choice may be wrong. */
        TRUNCATED,
        /** Something was discarded as not-a-meaning. */
        FILTERED,
        /**
         * A sense too long for the model to hold, so it was dropped.
         *
         * <p>Not a style note. {@link Meaning} refuses anything over its limit,
         * so an over-long candidate cannot be seeded at all — the character
         * needs a gloss somebody writes, and the flag is how it gets asked
         * for.</p>
         */
        VERBOSE,
        /** Nothing survived. There is no seed here, only a queue entry. */
        EMPTY
    }

    /** What the policy made of one field. */
    public record Seed(List<String> meanings, List<Doubt> doubts) {

        public Seed {
            meanings = List.copyOf(meanings);
            doubts = List.copyOf(doubts);
        }

        /** Whether this can be written at all. */
        public boolean usable() { return !meanings.isEmpty(); }

        /** Whether a person should look before this is trusted. */
        public boolean wantsReview() { return !doubts.isEmpty(); }
    }

    /**
     * Not meanings. Each is a thing Unihan says <em>about</em> a character
     * rather than a thing the character means, and a reviewer would strike
     * every one of them.
     */
    private static final List<String> NOT_A_MEANING = List.of(
            "same as",          // a cross-reference to another character
            "variant of",
            "old variant",
            "simplified form",
            "traditional form",
            "ancient form",
            "another form",
            "corrupted form",
            "non-classical",
            "radical number",
            "kangxi radical",
            "used in place names",
            "a surname",
            "surname"
    );

    /**
     * Register markers Unihan puts in front of a sense. The sense after them is
     * real; the marker is apparatus a child does not read.
     */
    private static final List<String> LEADING_MARKERS = List.of(
            "(coll.)", "(colloq.)", "(lit.)", "(fig.)", "(arch.)",
            "(dialect)", "(interj.)", "(onom.)"
    );

    /** Turn one raw {@code kDefinition} into at most {@link #MAX_SENSES} senses. */
    public static Seed of(String kDefinition) {
        var doubts = new ArrayList<Doubt>();
        if (kDefinition == null || kDefinition.isBlank()) {
            doubts.add(Doubt.EMPTY);
            return new Seed(List.of(), doubts);
        }

        var kept = new ArrayList<String>();
        boolean filtered = false;

        // A semicolon group is a candidate sense. Commas inside one are left
        // exactly as they are - they are near-synonyms, and rewriting them
        // would be this tool having an opinion about wording.
        boolean verbose = false;
        for (String raw : kDefinition.split(";")) {
            String sense = clean(raw);
            if (sense.isEmpty()) continue;
            if (isNotAMeaning(sense)) { filtered = true; continue; }
            // Dropped rather than carried: the model refuses it, so passing it
            // on would only move the failure into the writer.
            if (sense.length() > LONG_SENSE) { verbose = true; continue; }
            kept.add(sense);
        }

        if (filtered) doubts.add(Doubt.FILTERED);
        if (verbose) doubts.add(Doubt.VERBOSE);
        if (kept.isEmpty()) {
            doubts.add(Doubt.EMPTY);
            return new Seed(List.of(), doubts);
        }
        if (kept.size() > MAX_SENSES) doubts.add(Doubt.TRUNCATED);

        return new Seed(kept.subList(0, Math.min(MAX_SENSES, kept.size())), doubts);
    }

    /** Trim, drop a leading register marker, and squeeze runs of whitespace. */
    private static String clean(String raw) {
        String s = raw.trim();
        for (String marker : LEADING_MARKERS) {
            if (s.toLowerCase(Locale.ROOT).startsWith(marker)) {
                s = s.substring(marker.length()).trim();
                break;
            }
        }
        // A tab would shift every field of the row it lands in, and the source
        // is a tab-separated file being written into another one.
        return s.replaceAll("\\s+", " ").trim();
    }

    private static boolean isNotAMeaning(String sense) {
        String s = sense.toLowerCase(Locale.ROOT);
        for (String pattern : NOT_A_MEANING) {
            // Anchored at the start: "same as X" is a cross-reference, but a
            // meaning that merely CONTAINS the words - "to treat the same as" -
            // is a meaning, and dropping it would lose a real sense.
            if (s.startsWith(pattern)) return true;
        }
        return false;
    }
}
