package kranji.simple.gloss;

import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;
import kranji.zi.ZiCharUTF8Codec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The authoring surface for glosses.
 *
 * <p>A chain of <b>typestate builders</b>: every step returns a different type,
 * so what may follow what is decided by the compiler rather than by a
 * {@code validate()} call over a bag of mutable fields. There is no single
 * builder accumulating flags — each step is an immutable record, and an illegal
 * order does not compile.</p>
 *
 * <pre>{@code
 * import static kranji.simple.gloss.GlossDsl.zi;
 *
 * zi("行")
 *     .read("xíng")
 *         .means("to go; to walk").eg("行走", "to walk").done()
 *         .means("all right; OK").eg("不行", "won't do").done()
 *     .done()
 *     .read("háng")
 *         .means("a row; a line").eg("一行字", "a line of writing").done()
 *     .done()
 * .build()
 * }</pre>
 *
 * <h2>done() goes up one level; build() finishes</h2>
 *
 * <p>{@code done()} closes exactly one level and returns its parent — never
 * two, so inserting a line above one cannot change what that line does. An
 * earlier draft let {@code .read()} on a meaning silently close the meaning and
 * the reading before opening the next; concise, and it meant a reader had to
 * know that one call unwound two levels.</p>
 *
 * <p>{@code build()} closes everything remaining and hands back the
 * {@link ZiGloss}. It is available from any level, because the tail of an entry
 * is the one place where {@code done()} carries no information — nothing
 * follows it to disambiguate. So the 70% case pays one call rather than
 * three:</p>
 *
 * <pre>{@code
 * zi("大").read("dà").means("big").eg("大人", "an adult").build()
 * }</pre>
 *
 * <p>The two words cannot be mistaken for each other, which {@code done}/
 * {@code end} could: both are English for finishing, and neither said whether
 * it meant this level or the whole entry.</p>
 *
 * <h2>Order is recorded, not declared</h2>
 *
 * <p>The builder stamps the position it observes. Senses are written
 * most-important-first — which an author would do anyway — and the first is the
 * primary <b>by construction</b>. Nothing is typed to say so, and the invariant
 * cannot be broken because it is not an invariant.</p>
 *
 * <h2>Readings are written as readings</h2>
 *
 * <p>{@code .read("xíng")} rather than {@code .read(Initial.X, ING, SECOND)}.
 * Both fail the build on a typo — constants at compile time, the string at
 * class-initialisation — but only one reads like the thing it denotes, and
 * neither can tell whether a character is actually <em>read</em> that way. That
 * check needs the phonic corpus and lives in a test.</p>
 */
public final class GlossDsl {

    private GlossDsl() {}

    /** Opens an entry for one character. */
    public static ZiBuilder zi(String glyph) {
        return new ZiBuilder(ZiCharUTF8Codec.INSTANCE.from(glyph), List.of());
    }

    private static <T> List<T> plus(List<T> list, T item) {
        var out = new ArrayList<T>(list.size() + 1);
        out.addAll(list);
        out.add(item);
        return List.copyOf(out);
    }

    // ── The three states ───────────────────────────────────────────────

    /** A character, with the readings closed so far. */
    public record ZiBuilder(ZiCharUTF8 zi, List<SoundGloss> sounds) {

        /** Opens a reading of this character. */
        public ReadBuilder read(String reading) {
            return new ReadBuilder(this, PinyinSyllable.parseCanonical(reading), List.of());
        }

        /** Finishes the entry. */
        public ZiGloss build() {
            return new ZiGloss(zi, sounds);
        }
    }

    /** A reading, with the meanings closed so far. */
    public record ReadBuilder(ZiBuilder parent, PinyinSyllable reading,
                              List<Map.Entry<Meaning, Sense>> senses) {

        /**
         * Opens a sense of this reading.
         *
         * <p>Its position is stamped from how many senses this reading already
         * has, so the first written is the primary and nothing says so.</p>
         */
        public MeaningBuilder means(String text) {
            return new MeaningBuilder(this, RankingInfo.at(senses.size()), text, List.of());
        }

        /** Closes the reading, returning the character. */
        public ZiBuilder done() {
            var map = new LinkedHashMap<Meaning, Sense>();
            for (var e : senses) {
                // Refused rather than absorbed, unlike a repeated example. An
                // example written twice says nothing new; a SENSE written twice
                // means the author meant two different ones and wrote one of
                // them twice - and silently collapsing it would leave the
                // reading a sense short with nothing to show for it.
                if (map.containsKey(e.getKey())) {
                    throw new IllegalArgumentException(
                            parent.zi() + " " + reading.toDiacritic()
                          + " gives the meaning \"" + e.getKey() + "\" twice");
                }
                map.put(e.getKey(), e.getValue());
            }
            return new ZiBuilder(parent.zi(),
                    plus(parent.sounds(), new SoundGloss(parent.zi(), reading, map)));
        }

        /** Finishes the entry from here. */
        public ZiGloss build() {
            return done().build();
        }
    }

    /** A sense, with the examples given so far. */
    public record MeaningBuilder(ReadBuilder parent, RankingInfo ranking, String text,
                                 List<EgRef> examples) {

        /**
         * A phrase showing this sense in use, by reference.
         *
         * <p>Defined once in the example registry; this only says it belongs
         * here. A reference to a phrase nobody defined is caught by a test
         * rather than here - requiring the registry to exist first would mean
         * authoring every phrase before any gloss could use one.</p>
         */
        public MeaningBuilder eg(String phrase) {
            return eg(phrase, 0);
        }

        /**
         * A particular sense of a phrase, where the phrase means more than one
         * thing. 东西 is east-and-west at sense 1 and a thing at sense 0.
         */
        public MeaningBuilder eg(String phrase, int sense) {
            return new MeaningBuilder(parent, ranking, text,
                    plus(examples, EgRef.to(phrase, sense)));
        }

        /**
         * Why this sense sits where it does.
         *
         * <p>For a placement that would otherwise look like an oversight. A
         * method rather than an overload of {@code means}, because two String
         * parameters would have been ambiguous at the call site in exactly the
         * way that produces a silently swapped gloss and reason.</p>
         */
        public MeaningBuilder because(String reason) {
            return new MeaningBuilder(parent, ranking.explained(reason), text, examples);
        }

        /**
         * Closes the sense, returning the reading.
         *
         * <p>Where the examples' order is <b>stamped</b>: the builder recorded
         * it by appending as they were written, and it becomes data here. After
         * this the list position is no longer load-bearing, so nothing
         * downstream has to preserve it.</p>
         */
        public ReadBuilder done() {
            var stamped = new LinkedHashMap<EgRef, RankingInfo>();
            for (int i = 0; i < examples.size(); i++) {
                stamped.put(examples.get(i), RankingInfo.at(i));
            }
            return new ReadBuilder(parent.parent(), parent.reading(),
                    plus(parent.senses(),
                         Map.entry(Meaning.of(text), new Sense(ranking, stamped))));
        }

        /** Finishes the entry from here. */
        public ZiGloss build() {
            return done().build();
        }
    }
}
