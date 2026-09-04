package kranji.gloss.layered;

import kranji.gloss.handcrafted.HandCrafted;
import kranji.gloss.seed.SeededGlosses;
import kranji.simple.gloss.EgKey;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiCollection;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The curated set read over the seeded one, merged a reading at a time.
 *
 * <h2>Why a layer and not a precedence</h2>
 *
 * <p>The composition underneath — {@code Glosses.of} — keeps the first source
 * that has a CHARACTER and discards the rest of it. While the seed held
 * monophones only that was harmless: a shadowed seeded character contributed
 * one reading the winner already covered. It stops being harmless the moment a
 * reading is curated on its own, because a curated 把 at {@code ba3} takes the
 * seed's {@code ba4} down with it — silently, and looking like nothing
 * happened.</p>
 *
 * <p>So the grain here is the <b>(character, reading) pair</b>. A character's
 * readings are the union across layers, and each reading is answered by the
 * highest layer that has it. Curating one reading of a polyphone is then an
 * addition rather than a replacement, which is what the 593 queued splits will
 * need one at a time.</p>
 *
 * <h2>The sense list is not merged</h2>
 *
 * <p>Within a reading the winning layer's senses are taken whole. A gloss is
 * authored as a set — a primary with its secondaries, ordered, each with its
 * examples — and interleaving two sources' senses would produce a ranking
 * neither of them chose. Overriding 猬 means replacing <i>vulgar, wanton,
 * low…</i> with <i>a hedgehog</i>, not appending to it.</p>
 *
 * <h2>What it does not decide</h2>
 *
 * <p>Provenance. {@link #layers()} hands the stack back, so the workbench can
 * still ask which rows a person wrote — a question a single
 * {@link #precedence()} for the whole stack cannot answer, and getting it
 * wrong would empty the review queue by declaring every seeded row
 * hand-authored.</p>
 */
public final class LayeredGlosses implements ZiCollection {

    /**
     * Most authoritative first, and the order is the entire behaviour.
     *
     * <p>Written out rather than discovered. A stack assembled from whatever
     * happened to be on the classpath would reorder itself between a jar and
     * an IDE, which is the failure {@code ZiCollections} sorts to avoid; here
     * the answer is simply stated.</p>
     */
    private static final List<ZiCollection> LAYERS =
            List.of(HandCrafted.INSTANCE, SeededGlosses.INSTANCE);

    public static final LayeredGlosses INSTANCE = new LayeredGlosses();

    /** Public for {@link java.util.ServiceLoader}, which needs a no-arg constructor. */
    public LayeredGlosses() {}

    @Override public String name() { return "Kranji layered"; }

    @Override public String licence() {
        var out = new StringBuilder("a stack, and each layer keeps its own terms: ");
        for (int i = 0; i < LAYERS.size(); i++) {
            if (i > 0) out.append("; ");
            out.append(LAYERS.get(i).name()).append(" - ").append(LAYERS.get(i).licence());
        }
        return out.toString();
    }

    @Override public List<ZiCollection> layers() { return LAYERS; }

    /**
     * 0, because the top of the stack is hand-authored.
     *
     * <p>A number for a stack is a blunt answer and this is the honest one:
     * ask {@link #layers()} when the question is about a row rather than about
     * the collection.</p>
     */
    @Override public int precedence() { return 0; }

    private static final List<ZiGloss> ENTRIES = merge();

    private static List<ZiGloss> merge() {
        // Character -> reading key -> the winning sound gloss. Two nested
        // insertion-ordered maps, so a character keeps the order it was first
        // met in and its readings keep the order the top layer stated them in.
        var byCharacter = new LinkedHashMap<ZiCharUTF8, Map<String, SoundGloss>>();

        for (ZiCollection layer : LAYERS) {
            for (ZiGloss entry : layer.characters().all()) {
                var readings = byCharacter.computeIfAbsent(
                        entry.zi(), z -> new LinkedHashMap<>());
                // putIfAbsent: the first layer to answer for a reading wins it,
                // and a lower layer fills only what is still missing.
                for (SoundGloss sound : entry.sounds()) {
                    readings.putIfAbsent(sound.key(), sound);
                }
            }
        }

        var out = new ArrayList<ZiGloss>(byCharacter.size());
        byCharacter.forEach((zi, readings) ->
                out.add(new ZiGloss(zi, List.copyOf(readings.values()))));
        return List.copyOf(out);
    }

    private static final Map<ZiCharUTF8, ZiGloss> INDEX = index();

    private static Map<ZiCharUTF8, ZiGloss> index() {
        var out = new LinkedHashMap<ZiCharUTF8, ZiGloss>();
        for (ZiGloss entry : ENTRIES) out.put(entry.zi(), entry);
        return out;
    }

    private static final Characters CHARACTERS = new Characters() {
        @Override public Optional<ZiGloss> find(ZiCharUTF8 zi) {
            return Optional.ofNullable(INDEX.get(zi));
        }
        @Override public List<ZiGloss> all() { return ENTRIES; }
        @Override public int size()          { return ENTRIES.size(); }
    };

    private static final List<ExampleEntry> PHRASE_ENTRIES = mergePhrases();

    private static List<ExampleEntry> mergePhrases() {
        // The same rule one level up. Only the curated layer has any today -
        // a seeded row is a definition with nothing to demonstrate it - but
        // stacking them here means that stops being an assumption.
        var out = new LinkedHashMap<EgKey, ExampleEntry>();
        for (ZiCollection layer : LAYERS) {
            for (ExampleEntry phrase : layer.phrases().all()) {
                out.putIfAbsent(phrase.key(), phrase);
            }
        }
        return List.copyOf(out.values());
    }

    private static final Map<EgKey, ExampleEntry> PHRASE_INDEX = phraseIndex();

    private static Map<EgKey, ExampleEntry> phraseIndex() {
        var out = new LinkedHashMap<EgKey, ExampleEntry>();
        for (ExampleEntry phrase : PHRASE_ENTRIES) out.put(phrase.key(), phrase);
        return out;
    }

    private static final Phrases PHRASES = new Phrases() {
        @Override public Optional<ExampleEntry> find(EgKey key) {
            return Optional.ofNullable(PHRASE_INDEX.get(key));
        }
        @Override public List<ExampleEntry> all() { return PHRASE_ENTRIES; }
        @Override public int size()               { return PHRASE_ENTRIES.size(); }
    };

    @Override public Characters characters() { return CHARACTERS; }
    @Override public Phrases phrases()       { return PHRASES; }
}
