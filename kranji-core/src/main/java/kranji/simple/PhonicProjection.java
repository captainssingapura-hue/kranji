package kranji.simple;

import hue.captains.singapura.tao.ontology.StatelessFunctionalObject;
import kranji.pinyin.Final;
import kranji.pinyin.Initial;
import kranji.phonic.SyllableIndex;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tone;
import kranji.zi.ZiCharUTF8;
import kranji.zi.tree.ZiBranch;
import kranji.zi.tree.ZiProjectionTree;
import kranji.zi.tree.ZiTerminal;
import kranji.zi.tree.ZiTreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The characters grouped by sound: initial, then final, then tone.
 *
 * <p>Built from {@link SyllableIndex}, which is already grouped by syllable —
 * so this is an inversion of the source data rather than a regrouping of it.
 * There is no logic deciding where a character belongs; it belongs wherever it
 * is read.</p>
 *
 * <p>That is what decoupling sound from shape buys: <b>a character appears
 * under every reading it has</b>. 好 is found under hǎo and under hào, because
 * both are true. Emphasis decides which reading is principal, not which
 * appearances exist.</p>
 *
 * <p>Three levels, terminating at a complete syllable whose characters are its
 * content. The shape keeps the projection small: a tree whose leaves are
 * characters grows with the corpus, while this one is bounded by Mandarin
 * phonology — roughly the same node count at twenty thousand characters as at
 * two thousand.</p>
 *
 * <p>Per CD-001 this is an index and owns no data.</p>
 */
public record PhonicProjection() implements StatelessFunctionalObject {

    public static final PhonicProjection INSTANCE = new PhonicProjection();

    /** Stable name of this projection, for addressing and for node identity. */
    public static final String NAME = "phonic";

    /** The named projection — the form an adapter takes, since identity must carry the name. */
    public ZiProjectionTree projection() {
        return new ZiProjectionTree(NAME, tree());
    }

    /**
     * Root, a branch per populated initial, a branch per populated final
     * beneath it, and a terminal per populated tone.
     *
     * <p>Order is deliberate at every level — initials and tones in enum order,
     * finals by written spelling. A projection is a navigation route, so its
     * ordering carries meaning and should not be left to hash iteration.</p>
     */
    public ZiBranch tree() {
        var onsets = new ArrayList<ZiTreeNode>();
        for (String onset : ONSETS) {
            ZiBranch branch = branchFor(onset);
            if (branch.characterCount() > 0) onsets.add(branch);
        }
        return new ZiBranch(NAME, "By sound", List.copyOf(onsets));
    }

    /**
     * One consonant initial's subtree.
     *
     * <p>{@link Initial#ZERO} yields the <em>literal</em> zero branch: the
     * syllables written with no onset letter at all. The ones a reader would
     * look for under {@code y} or {@code w} are in those branches instead —
     * reach them with {@link #branchFor(String)}.</p>
     */
    public ZiBranch branchFor(Initial initial) {
        return branchFor(segmentFor(initial));
    }

    /** One onset's subtree: its finals, each with its tones. */
    public ZiBranch branchFor(String onset) {
        // The index is already keyed by syllable, so this is a regrouping of
        // (final, tone) rather than an inspection of any character. The syllable
        // rides along because only it knows how it is written.
        Map<Final, Map<Tone, Cell>> byFinal = new TreeMap<>(
                Comparator.comparing(PhonicProjection::writtenFinal));

        SyllableIndex index = SyllableIndex.instance();
        for (PinyinSyllable syllable : index.syllables()) {
            if (!onsetSegment(syllable).equals(onset)) continue;
            byFinal.computeIfAbsent(syllable.fin(), k -> new LinkedHashMap<>())
                   .put(syllable.tone(), new Cell(syllable, index.charactersOf(syllable)));
        }

        var finals = new ArrayList<ZiTreeNode>();
        byFinal.forEach((fin, byTone) -> {
            var tones = new ArrayList<ZiTreeNode>();
            PinyinSyllable any = null;
            for (Tone tone : Tone.values()) {
                Cell cell = byTone.get(tone);
                if (cell == null || cell.characters().isEmpty()) continue;
                any = cell.syllable();
                tones.add(new ZiTerminal(
                        String.valueOf(tone.number()),
                        cell.syllable().toDiacritic(),
                        List.copyOf(cell.characters())));
            }
            if (!tones.isEmpty()) {
                finals.add(new ZiBranch(segmentFor(fin),
                        spellingOf(any.initial(), fin), List.copyOf(tones)));
            }
        });

        return new ZiBranch(onset, onsetLabel(onset), List.copyOf(finals));
    }

    /** One (final, tone) position: the syllable, and what is read as it. */
    private record Cell(PinyinSyllable syllable, List<ZiCharUTF8> characters) {}

    // ── Onsets ─────────────────────────────────────────────────────────

    /**
     * The tree's top level, in the order a pinyin table prints it.
     *
     * <p>{@code y} and {@code w} sit here as initials in their own right,
     * after the consonants, which is where a learner has been taught to look
     * for them. Phonologically they are not initials at all — the model keeps
     * that straight, and {@link Initial#ZERO} remains the truth. This is the
     * navigation convention layered on top.</p>
     *
     * <p>{@code zero} is therefore literal: what is left once the glides are
     * taken out is the set of syllables written with no onset letter — a, e,
     * o and their kin.</p>
     */
    private static final List<String> ONSETS = List.of(
            "zero",
            "b", "p", "m", "f",
            "d", "t", "n", "l",
            "g", "k", "h",
            "j", "q", "x",
            "zh", "ch", "sh", "r",
            "z", "c", "s",
            "y", "w");

    /**
     * Which top-level branch a syllable is filed under.
     *
     * <p>For a real initial this is just its letter. For a zero-initial
     * syllable it is the glide the syllable is <em>written</em> with, which is
     * read off the spelling rather than derived from the medial.</p>
     *
     * <p>That distinction is not pedantic. {@code yi}, {@code yin} and
     * {@code ying} have an <b>open</b> medial — their {@code i} is the nucleus,
     * not a glide — so grouping by {@link kranji.pinyin.Head} files them under
     * no onset at all, away from the other fourteen y- syllables. The written
     * form knows better, and it is already computed.</p>
     */
    public static String onsetSegment(PinyinSyllable syllable) {
        if (syllable.initial() != Initial.ZERO) return segmentFor(syllable.initial());
        char first = spellingOf(Initial.ZERO, syllable.fin()).charAt(0);
        return first == 'y' || first == 'w' ? String.valueOf(first) : "zero";
    }

    /** Display name for a top-level branch. */
    public static String onsetLabel(String onset) {
        return onset.equals("zero") ? "no initial" : onset + "-";
    }

    // ── Segments ───────────────────────────────────────────────────────

    /**
     * The address segment for an initial. The zero initial has no letters, so
     * it takes the name the corpus already uses for its directory.
     */
    public static String segmentFor(Initial initial) {
        return initial == Initial.ZERO ? "zero" : initial.pinyin();
    }

    /**
     * The address segment for a final, with {@code ü} folded to {@code v}.
     *
     * <p>The fold is safe here in a way it would not be for a whole reading: a
     * final's spelling is structural rather than corrigible data, and no other
     * final spells {@code v}, so the mapping is injective. It is also the
     * convention Chinese input methods already use.</p>
     */
    public static String segmentFor(Final fin) {
        return writtenFinal(fin).replace("ü", "v");
    }

    /**
     * A final as pinyin writes it.
     *
     * <p>Differs from {@link Final#spelling()} in exactly one case: the final of
     * a syllabic consonant — {@code zhi chi shi ri zi ci si} — is phonemically
     * empty, and pinyin writes a placeholder {@code i}. An empty segment is not
     * addressable and an empty label says nothing, so the written form is used
     * for both.</p>
     */
    public static String writtenFinal(Final fin) {
        String spelt = fin.spelling();
        return spelt.isEmpty() ? "i" : spelt;
    }

    /**
     * A final's branch label: the whole syllable it forms with its parent
     * initial, untoned — {@code f} over {@code a} reads {@code fa}.
     *
     * <p>A bare final is hard to place. Under {@code f-} a row saying
     * {@code a} asks the reader to do the joining themselves, and the answer
     * is not always the obvious concatenation: pinyin orthography is
     * initial-dependent, so {@code j} + {@code ü} is written {@code ju},
     * {@code l} + {@code iou} is {@code liu}, and the zero initial grows a
     * {@code y-} or {@code w-} that exists in no final.</p>
     *
     * <p>Rather than reimplement those rules, this asks for the syllable in
     * the neutral tone — which spells everything the same way but adds no tone
     * mark. The segment is left alone, so addressing is unaffected.</p>
     */
    public static String spellingOf(Initial initial, Final fin) {
        return new PinyinSyllable(initial, fin, Tone.NEUTRAL).toDiacritic();
    }

    /** Display name for an initial's branch, e.g. {@code h-}. */
    public static String labelFor(Initial initial) {
        return onsetLabel(segmentFor(initial));
    }
}
