package kranji.gloss.tsv;

import kranji.pinyin.PinyinSyllable;
import kranji.simple.gloss.EgKey;
import kranji.simple.gloss.EgRef;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.ExampleSense;
import kranji.simple.gloss.Meaning;
import kranji.simple.gloss.Priority;
import kranji.simple.gloss.RankingInfo;
import kranji.simple.gloss.Sense;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The gloss tier as two tab-separated files, and back.
 *
 * <h2>Why tabs</h2>
 *
 * <p>Not a style preference — the data decided it. Meanings and English are
 * free text and already contain commas ("(makes a number an order: first,
 * second)", "Shandong, a province"), so a comma-separated file would need
 * quoting from the very first partition, and quoted fields are where
 * hand-edited files go wrong. The same data contains no tab and no quote
 * anywhere, so a tab-separated file needs no escaping at all. The phonic corpus
 * is TSV for the same reason.</p>
 *
 * <h2>What the compiler used to do</h2>
 *
 * <p>Authoring in a Java DSL meant a mistyped reading was a build error on the
 * offending line. That check does not disappear here, it moves: every problem
 * is collected with its file and line number and reported together, rather than
 * throwing on the first. A test turns that list into a build failure, so the
 * guarantee is the same and the message is better — one run names every bad
 * row instead of the first one.</p>
 *
 * <h2>Order is a column, not a row position</h2>
 *
 * <p>A sense's rank and a phrase-sense's index are authored information. Left
 * implicit in row order they would survive right up until somebody sorted the
 * file in a spreadsheet, which silently re-ranks every meaning and cannot be
 * noticed by reading the diff. So the rank is written out, and the loader
 * checks it rather than trusting where the row sits.</p>
 */
public final class GlossTsv {

    private GlossTsv() {}

    private static final String TAB = "\t";

    /**
     * Phrases in a cell. A list separator, matching the one rule the files
     * follow: a bar lists, a semicolon or comma joins within one item.
     */
    private static final String EG_SEP = "|";

    /**

    /** Pinned readings in a cell, separated by a space. */
    private static final String PIN_SEP = " ";

    public static final String SENSES_HEADER =
            "# codepoint\tglyph\treading\tpriority\tmeaning\texamples\tbecause";
    public static final String PHRASES_HEADER =
            "# phrase\tsense\tenglish\tpins";

    /** Where a file went wrong, and where to look. */
    public record Problem(String file, int line, String message) {
        @Override public String toString() { return file + ":" + line + "  " + message; }
    }

    /** What a file yielded, and everything wrong with it. */
    public record Read<T>(List<T> entries, List<Problem> problems) {
        public boolean ok() { return problems.isEmpty(); }
    }

    // ── Writing ────────────────────────────────────────────────────────
    /**
     * One row per sense. The row <b>is</b> the sense.
     *
     * <p>An earlier shape put a reading's senses in one cell separated by
     * {@code |}, with the example groups in a parallel cell aligned by
     * position. That parses cleanly when it is wrong: {@code busy|to hurry}
     * beside {@code 急忙|很忙} is backwards and nothing can tell. Alignment is a
     * correctness burden the reader carries and the machine cannot check.</p>
     *
     * <p>A row per sense removes the alignment rather than guarding it — each
     * row names its own meaning and its own examples, so the error is no longer
     * expressible. {@code |} is left doing one job, listing the phrases, and
     * {@code ;} is left doing the other, joining near synonyms inside a single
     * meaning.</p>
     *
     * <p>{@code order} is the rank within the reading, written out because rows
     * for one reading now sit side by side again and a sort must not be able to
     * re-rank them.</p>
     */
    public static String writeSenses(List<ZiGloss> glosses) {
        var out = new StringBuilder(SENSES_HEADER).append('\n');
        for (ZiGloss g : glosses) {
            for (SoundGloss s : g.sounds()) {
                for (Meaning m : s.orderedMeanings()) {
                    Sense sense = s.senseOf(m);
                    out.append(g.zi().codePoint()).append(TAB)
                       .append(g.zi().value()).append(TAB)
                       .append(s.reading().numbered()).append(TAB)
                       .append(sense.ranking().priority().code()).append(TAB)
                       .append(m.text()).append(TAB)
                       .append(examplesCell(sense)).append(TAB)
                       .append(sense.ranking().because().orElse(""))
                       .append('\n');
                }
            }
        }
        return out.toString();
    }

    public static String writePhrases(List<ExampleEntry> entries) {
        var out = new StringBuilder(PHRASES_HEADER).append('\n');
        for (ExampleEntry e : entries) {
            List<ExampleSense> senses = e.senses();
            for (int i = 0; i < senses.size(); i++) {
                ExampleSense s = senses.get(i);
                out.append(e.key().phrase()).append(TAB)
                   .append(i).append(TAB)
                   .append(s.english().text()).append(TAB)
                   .append(pinsCell(e, s))
                   .append('\n');
            }
        }
        return out.toString();
    }

    private static String examplesCell(Sense sense) {
        var parts = new ArrayList<String>();
        for (EgRef ref : sense.orderedExamples()) {
            parts.add(ref.sense() == 0 ? ref.phrase().phrase()
                                       : ref.phrase().phrase() + "#" + ref.sense());
        }
        return String.join(EG_SEP, parts);
    }

    /**
     * Pins written by <b>position</b>, never by glyph.
     *
     * <p>The workbench shows {@code 得=de0} because a person reads that more
     * easily, but a file is a source and 慢慢地走 repeats 慢 — two glyph-keyed
     * pins on that phrase could not be told apart. Position is the key the
     * model actually uses.</p>
     */
    private static String pinsCell(ExampleEntry entry, ExampleSense sense) {
        var parts = new ArrayList<String>();
        for (int at = 0; at < entry.key().length(); at++) {
            int position = at;
            sense.soundAt(at).ifPresent(syllable ->
                    parts.add(position + "=" + syllable.numbered()));
        }
        return String.join(PIN_SEP, parts);
    }

    // ── Reading ────────────────────────────────────────────────────────

    private record SenseRow(int line, int codePoint, String glyph, String reading,
                            Priority priority, String meaning, String examples,
                            String because) {}

    public static Read<ZiGloss> readSenses(String file, String text) {
        var problems = new ArrayList<Problem>();
        var rows = new ArrayList<SenseRow>();

        int lineNo = 0;
        for (String raw : text.split("\n", -1)) {
            lineNo++;
            String line = strip(raw);
            if (line.isEmpty() || line.startsWith("#")) continue;

            String[] f = raw.split(TAB, -1);
            if (f.length < 6) {
                problems.add(new Problem(file, lineNo,
                        "expected 6 or 7 tab-separated fields, found " + f.length));
                continue;
            }
            Integer cp = codePoint(file, lineNo, f[0], f[1], problems);
            Priority priority = priority(file, lineNo, f[3], problems);
            if (cp == null || priority == null) continue;
            if (!canonical(f[2])) {
                problems.add(new Problem(file, lineNo,
                        "'" + f[2] + "' is not a canonical reading like di4 or de0"));
                continue;
            }
            if (strip(f[4]).isEmpty()) {
                problems.add(new Problem(file, lineNo, "a sense with no meaning says nothing"));
                continue;
            }
            rows.add(new SenseRow(lineNo, cp, f[1], f[2], priority,
                    strip(f[4]), strip(f[5]), f.length > 6 ? strip(f[6]) : ""));
        }
        return new Read<>(buildGlosses(file, rows, problems), List.copyOf(problems));
    }

    private static List<ZiGloss> buildGlosses(String file, List<SenseRow> rows,
                                              List<Problem> problems) {
        // Grouped by character, then by reading, each keeping first-seen order
        // so the file's sequence survives into the model.
        var byZi = new LinkedHashMap<Integer, LinkedHashMap<String, List<SenseRow>>>();
        for (SenseRow r : rows) {
            byZi.computeIfAbsent(r.codePoint(), k -> new LinkedHashMap<>())
                .computeIfAbsent(r.reading(), k -> new ArrayList<>())
                .add(r);
        }

        var out = new ArrayList<ZiGloss>();
        byZi.forEach((cp, byReading) -> {
            var sounds = new ArrayList<SoundGloss>();
            byReading.forEach((reading, group) -> {
                var senses = new LinkedHashMap<Meaning, Sense>();
                // The stamp is the row's position in its reading's group, not
                // anything the file states. That is the whole point of dropping
                // the number: an author says which band a sense is in, and the
                // sequence they wrote breaks ties within it.
                int stamp = 0;
                for (SenseRow r : group) {
                    try {
                        RankingInfo ranking = r.because().isEmpty()
                                ? RankingInfo.of(r.priority(), stamp)
                                : RankingInfo.of(r.priority(), stamp).explained(r.because());
                        var sense = new Sense(ranking, examplesOf(file, r, problems));
                        // Only a row that made it into the map advances the
                        // stamp. A row that threw would otherwise leave a hole,
                        // and the hole would be reported as a numbering fault
                        // on top of the fault that actually caused it.
                        if (senses.put(Meaning.of(r.meaning()), sense) == null) {
                            stamp++;
                        } else {
                            // The number used to catch this by accident: two
                            // rows claiming one meaning left a gap. Nothing
                            // catches it by accident now, so it is named.
                            problems.add(new Problem(file, r.line(),
                                    r.glyph() + " " + r.reading() + " says '" + r.meaning()
                                  + "' twice - one meaning is one sense"));
                        }
                    } catch (RuntimeException e) {
                        problems.add(new Problem(file, r.line(), e.getMessage()));
                    }
                }
                if (senses.isEmpty()) return;
                try {
                    // SoundGloss requires the orders to be 0..n-1 with no gap or
                    // repeat, so a mis-numbered row fails here rather than being
                    // absorbed into a plausible-looking ranking.
                    sounds.add(new SoundGloss(new ZiCharUTF8(cp),
                            PinyinSyllable.parseCanonical(reading), senses));
                } catch (RuntimeException e) {
                    problems.add(new Problem(file, group.get(0).line(), e.getMessage()));
                }
            });
            if (sounds.isEmpty()) return;
            try {
                out.add(new ZiGloss(new ZiCharUTF8(cp), sounds));
            } catch (RuntimeException e) {
                problems.add(new Problem(file, byReading.values().iterator().next()
                        .get(0).line(), e.getMessage()));
            }
        });
        return List.copyOf(out);
    }

    private static Map<EgRef, RankingInfo> examplesOf(String file, SenseRow row,
                                                      List<Problem> problems) {
        var out = new LinkedHashMap<EgRef, RankingInfo>();
        if (row.examples().isEmpty()) return out;

        String[] parts = row.examples().split(java.util.regex.Pattern.quote(EG_SEP), -1);
        for (int i = 0; i < parts.length; i++) {
            String part = strip(parts[i]);
            if (part.isEmpty()) {
                problems.add(new Problem(file, row.line(),
                        "an empty example between separators"));
                continue;
            }
            int hash = part.indexOf('#');
            String phrase = hash < 0 ? part : part.substring(0, hash);
            int sense = 0;
            if (hash >= 0) {
                Integer parsed = number(file, row.line(), "example index",
                        part.substring(hash + 1), problems);
                if (parsed == null) continue;
                sense = parsed;
            }
            try {
                out.put(new EgRef(EgKey.of(phrase), sense), RankingInfo.at(i));
            } catch (RuntimeException e) {
                problems.add(new Problem(file, row.line(), e.getMessage()));
            }
        }
        return out;
    }


    private record PhraseRow(int line, String phrase, int sense, String english, String pins) {}

    public static Read<ExampleEntry> readPhrases(String file, String text) {
        var problems = new ArrayList<Problem>();
        var rows = new ArrayList<PhraseRow>();

        int lineNo = 0;
        for (String raw : text.split("\n", -1)) {
            lineNo++;
            String line = strip(raw);
            if (line.isEmpty() || line.startsWith("#")) continue;

            String[] f = raw.split(TAB, -1);
            if (f.length < 3) {
                problems.add(new Problem(file, lineNo,
                        "expected 3 or 4 tab-separated fields, found " + f.length));
                continue;
            }
            Integer sense = number(file, lineNo, "sense", f[1], problems);
            if (sense == null) continue;
            if (strip(f[2]).isEmpty()) {
                problems.add(new Problem(file, lineNo, "a phrase sense with no English"));
                continue;
            }
            rows.add(new PhraseRow(lineNo, strip(f[0]), sense, strip(f[2]),
                    f.length > 3 ? strip(f[3]) : ""));
        }

        var byPhrase = new LinkedHashMap<String, List<PhraseRow>>();
        for (PhraseRow r : rows) byPhrase.computeIfAbsent(r.phrase(), k -> new ArrayList<>()).add(r);

        var out = new ArrayList<ExampleEntry>();
        byPhrase.forEach((phrase, group) -> {
            try {
                EgKey key = EgKey.of(phrase);
                var senses = new ArrayList<ExampleSense>();
                for (PhraseRow r : group) {
                    senses.add(new ExampleSense(RankingInfo.at(r.sense()),
                            Meaning.of(r.english()), pins(file, key, r, problems)));
                }
                out.add(new ExampleEntry(key, senses));
            } catch (RuntimeException e) {
                problems.add(new Problem(file, group.get(0).line(), e.getMessage()));
            }
        });
        return new Read<>(List.copyOf(out), List.copyOf(problems));
    }

    private static Map<Integer, PinyinSyllable> pins(String file, EgKey key, PhraseRow row,
                                                     List<Problem> problems) {
        var out = new LinkedHashMap<Integer, PinyinSyllable>();
        if (row.pins().isEmpty()) return out;

        for (String part : row.pins().split("\\s+")) {
            int eq = part.indexOf('=');
            if (eq < 0) {
                problems.add(new Problem(file, row.line(),
                        "'" + part + "' is not position=reading, like 2=di4"));
                continue;
            }
            Integer at = number(file, row.line(), "pin position", part.substring(0, eq), problems);
            String reading = part.substring(eq + 1);
            if (at == null) continue;
            if (!canonical(reading)) {
                problems.add(new Problem(file, row.line(),
                        "'" + reading + "' is not a canonical reading like di4"));
                continue;
            }
            out.put(at, PinyinSyllable.parseCanonical(reading));
        }
        return out;
    }

    // ── Field helpers ──────────────────────────────────────────────────

    /**
     * The codepoint, checked against the glyph beside it.
     *
     * <p>The glyph is redundant — the codepoint is the key. It is written
     * anyway so a person can read the file, and checked so the redundancy
     * cannot rot: a row whose two halves disagree is a mis-paste, and it is
     * far easier to catch here than to explain later.</p>
     */
    private static Integer codePoint(String file, int line, String cpField, String glyph,
                                     List<Problem> problems) {
        Integer cp = number(file, line, "codepoint", cpField, problems);
        if (cp == null) return null;
        String expected = new String(Character.toChars(cp));
        if (!expected.equals(strip(glyph))) {
            problems.add(new Problem(file, line,
                    "codepoint " + cp + " is " + expected + ", not " + strip(glyph)));
            return null;
        }
        return cp;
    }

    /**
     * The band a row claims, or null with a problem recorded.
     *
     * <p>Blank is refused rather than defaulted. A missing band is not a
     * statement that a sense is auxiliary, and defaulting it to primary would
     * make every unfilled row outrank the ones somebody thought about.</p>
     */
    private static Priority priority(String file, int line, String field,
                                     List<Problem> problems) {
        try {
            return Priority.ofCode(field);
        } catch (IllegalArgumentException e) {
            problems.add(new Problem(file, line, e.getMessage()));
            return null;
        }
    }

    private static Integer number(String file, int line, String what, String field,
                                  List<Problem> problems) {
        try {
            return Integer.valueOf(strip(field));
        } catch (NumberFormatException e) {
            problems.add(new Problem(file, line, what + " '" + strip(field) + "' is not a number"));
            return null;
        }
    }

    private static boolean canonical(String reading) {
        return strip(reading).matches("[a-zü]+[0-4]");
    }

    /** Trailing \r survives a file edited on Windows and would corrupt a key. */
    private static String strip(String s) {
        return s == null ? "" : s.replace("\r", "").strip();
    }
}
