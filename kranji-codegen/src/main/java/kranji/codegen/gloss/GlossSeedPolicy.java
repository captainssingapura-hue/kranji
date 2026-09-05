package kranji.codegen.gloss;

import kranji.simple.gloss.Meaning;

import java.util.ArrayList;
import java.util.HashSet;
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
 * <p>Two things it drops, and they are not dropped the same way. Material that
 * is <b>never</b> a meaning — a cross-reference, a radical number — goes
 * whatever else the field says. A <b>proper noun</b> goes only while a real
 * meaning is standing: 邴 is "pleased" and loses the city named after it, but
 * 侴 <em>is</em> a surname, and striking that deletes the character's one
 * gloss rather than tidying it.</p>
 */
public final class GlossSeedPolicy {

    /**
     * How many senses a seeded reading may carry.
     *
     * <p>Five. This was three, taken from the hand-crafted set's own ceiling,
     * and three turned out to be a rule about the wrong thing: the bands are
     * not one each. A reading has one primary and one secondary, and then as
     * many auxiliary meanings as it has — 避 is avoid, turn aside, escape AND
     * hide, and cutting the fourth was the cap talking, not the language.</p>
     *
     * <p>Five rather than none, because the original worry is still real: a
     * dictionary field will happily offer eight and a reader's cell should not
     * become a paragraph. Five is where the data sits — of the 141 fields that
     * offered more than three, 138 offer four or five. What is left flagged is
     * three characters whose field really is a dictionary entry, which is
     * exactly what the flag should mean.</p>
     */
    public static final int MAX_SENSES = 5;

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
        /**
         * Everything was discarded as not-a-meaning.
         *
         * <p>Only raised alongside {@link #EMPTY}. A strike that leaves a real
         * meaning behind is the filter doing its job — a surname, a radical
         * number, a place name are all derived from the meaning that survived,
         * and a reviewer would strike every one of them too.</p>
         */
        FILTERED,
        /**
         * Every sense was too long for the model to hold.
         *
         * <p>Also only raised alongside {@link #EMPTY}, and for the same reason
         * as {@link #FILTERED}: a drop that leaves a meaning standing is the
         * policy working. 岷's field is the case — "Minshan mountain range in
         * northern Sichuan and southern Gansu, Min River", 72 characters where
         * {@link Meaning} takes 60, and nothing else on offer.</p>
         *
         * <p>When nothing survives, it matters again: the character needs a
         * gloss somebody writes, and this says the source had something to say
         * and said it at length rather than saying nothing at all.</p>
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
            "obsolete form",
            "non-classical",
            "radical number",
            "kangxi radical",
            // Only visible once a leading bracket is stripped, which is how
            // they went unnoticed: "(used erroneously for 汩)" was a meaning.
            "used erroneously",
            "used with"
    );

    /**
     * Proper nouns: struck when a real meaning survives, kept when nothing
     * else does.
     *
     * <h2>The condition is the whole rule</h2>
     *
     * <p>邴 is <i>pleased</i>, and that a city in the state of Song was called
     * 邴 follows from the character rather than explaining it — so the city
     * goes. But 侴 is <b>only</b> a surname and 岽 is <b>only</b> a place in
     * Guangxi. Striking those unconditionally does not remove apparatus, it
     * deletes the character's one meaning and leaves it looking like a
     * character nobody has glossed. For 114 characters that is exactly what
     * happened before this list was separated out.</p>
     *
     * <h2>Every pattern needs a specifier</h2>
     *
     * <p>Bare "river" is what 河 means, bare "city" is what 邑 means, bare
     * "state" is what 偁 means. It is "a river <i>in Shandong</i>" that names
     * a place instead of describing one. Patterns stopping at the noun would
     * strike the meanings they exist to protect.</p>
     */
    private static final List<String> DERIVED_MEANING = List.of(
            "used in place names",
            "a surname",
            "surname",
            "name of",
            "the name of",
            "place name",
            "a place in",
            "place in",
            "a county in",
            "a district in",
            "a city in",
            "a prefecture in",
            "a state in",
            "a river in",
            "a mountain in",
            "an ancient city",
            "an ancient country",
            "a kingdom in"
    );

    /**
     * Drop a leading parenthetical, so what follows can be judged on its own.
     *
     * <h2>Why generically rather than by a list of markers</h2>
     *
     * <p>This was a list — {@code (coll.)}, {@code (lit.)}, {@code (fig.)} and
     * six more — and a list only strips what somebody thought to write down.
     * 109 seeded meanings open with a bracket and the list caught a handful:
     * {@code (Cant.)} 60 times, but also {@code (餄餎) buckwheat noodles},
     * {@code (archaic)}, {@code (used with 乒)}.</p>
     *
     * <p>The one that mattered is 㤘, seeded as
     * <b>{@code (simplified form of 㥮) obstinate}</b>. The cross-reference
     * filter anchors at the start, so a bracket in front of it hid a pointer
     * inside a meaning — and the meaning shipped with the pointer attached.</p>
     *
     * <h2>What is left when the bracket was everything</h2>
     *
     * <p>Then the bracket's contents ARE the sense, and are classified as one:
     * 砒's whole field is {@code (obsolete form of 砒)}, which unwrapped is a
     * cross-reference and struck. Keeping the brackets would have made it a
     * meaning purely by being punctuated.</p>
     */
    private static String unbracket(String sense) {
        // A loop, because a field may open with two of them: 㺄 is
        // "(same as 狳) (a variant of 貐) a kind of beast", and stripping one
        // leaves a string that still starts with a bracket and so still hides
        // its pointer from a filter that anchors at the start.
        //
        // Terminates because the string strictly shortens on every pass.
        String s = sense;
        while (s.startsWith("(")) {
            int close = s.indexOf(')');
            if (close < 0) return s;                 // unbalanced; leave it alone

            String inside = s.substring(1, close).trim();
            String after = s.substring(close + 1).trim();
            if (after.isEmpty()) return inside;      // the bracket was the sense
            s = after;
        }
        return s;
    }

    /** Turn one raw {@code kDefinition} into at most {@link #MAX_SENSES} senses. */
    public static Seed of(String kDefinition) {
        var doubts = new ArrayList<Doubt>();
        if (kDefinition == null || kDefinition.isBlank()) {
            doubts.add(Doubt.EMPTY);
            return new Seed(List.of(), doubts);
        }

        var kept = new ArrayList<String>();
        // Proper nouns, held back rather than dropped. Whether they are
        // apparatus or the answer depends on what else survives, and that is
        // not known until every group has been read.
        var derived = new ArrayList<String>();
        // A sense is identified by its text, so a field that offers the same
        // one twice - "to join; to connect; to join" - is one sense, not two.
        // Left in, the second copy overwrites the first in the sense map and
        // the numbering comes out 1,2 instead of 0,1, which the model rejects.
        var seen = new HashSet<String>();
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
            // Case-insensitively, so "Seat" after "seat" does not become a
            // second sense saying the same thing. The first spelling is kept.
            if (!seen.add(sense.toLowerCase(Locale.ROOT))) continue;
            if (isDerived(sense)) { derived.add(sense); continue; }
            kept.add(sense);
        }

        // The condition in the rule: a proper noun is apparatus only while
        // something else is standing. 侴 is a surname and nothing else, and
        // deleting that leaves a character with no meaning rather than a
        // character with one fewer.
        if (kept.isEmpty() && !derived.isEmpty()) kept.addAll(derived);

        if (kept.isEmpty()) {
            // Nothing survived, so what was struck becomes the whole story:
            // "had no gloss at all" and "had one that was all apparatus" are
            // different jobs for whoever writes the replacement.
            if (filtered) doubts.add(Doubt.FILTERED);
            if (verbose) doubts.add(Doubt.VERBOSE);
            doubts.add(Doubt.EMPTY);
            return new Seed(List.of(), doubts);
        }
        // A strike that leaves a meaning standing is the policy working, not a
        // doubt. 豹 kept "leopard, panther" and lost "surname"; 邴 kept
        // "pleased" and lost a sentence about a city in the state of Song.
        // Neither wants a person's time, and flagging them meant 245 of the
        // 386 flags were the filter announcing its own success.
        if (kept.size() > MAX_SENSES) doubts.add(Doubt.TRUNCATED);

        return new Seed(kept.subList(0, Math.min(MAX_SENSES, kept.size())), doubts);
    }

    /** Trim, drop a leading register marker, and squeeze runs of whitespace. */
    private static String clean(String raw) {
        String s = unbracket(raw.trim());
        // A tab would shift every field of the row it lands in, and the source
        // is a tab-separated file being written into another one.
        return s.replaceAll("\\s+", " ").trim();
    }

    /** Never a meaning, whatever else the field says. */
    private static boolean isNotAMeaning(String sense) {
        return startsWithAny(sense, NOT_A_MEANING);
    }

    /** A meaning only when it is the last one left. */
    private static boolean isDerived(String sense) {
        return startsWithAny(sense, DERIVED_MEANING);
    }

    private static boolean startsWithAny(String sense, List<String> patterns) {
        String s = sense.toLowerCase(Locale.ROOT);
        for (String pattern : patterns) {
            // Anchored at the start: "same as X" is a cross-reference, but a
            // meaning that merely CONTAINS the words - "to treat the same as" -
            // is a meaning, and dropping it would lose a real sense.
            if (s.startsWith(pattern)) return true;
        }
        return false;
    }
}
