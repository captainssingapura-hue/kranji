package kranji.phonic;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * How often each reading of a character was actually observed.
 *
 * <p>Unihan's {@code kHanyuPinlu} writes it as
 * {@code "hǎo(6060) hāo(142) hào(115)"} - counts from a real corpus rather
 * than a dictionary's judgement. It covers only 2,829 of the 8,105 standard
 * characters, so most of the time this is {@link #none()}.</p>
 *
 * <p>It supplies no readings, only evidence about which one is principal.
 * Where it disagrees with the dictionary that disagreement is worth seeing
 * rather than resolving silently - for the commonest polyphonic characters
 * the most frequent form in running text is the neutral-tone particle, while
 * a dictionary gives the citation reading.</p>
 */
public record ReadingFrequency(Map<String, Long> counts) implements ValueObject {

    private static final ReadingFrequency NONE = new ReadingFrequency(Map.of());

    public ReadingFrequency {
        counts = Map.copyOf(counts);
    }

    /** No frequency evidence for this character. */
    public static ReadingFrequency none() { return NONE; }

    public boolean isEmpty() { return counts.isEmpty(); }

    /** Total observations across every reading. */
    public long total() {
        return counts.values().stream().mapToLong(Long::longValue).sum();
    }

    /**
     * The reading observed most often, if there is any evidence at all.
     *
     * <p>Ties are broken on the reading text so the answer is the same on
     * every run. That matters more than it looks: {@code counts} is an
     * immutable map, whose iteration order Java varies per JVM instance, so
     * taking the maximum without a tiebreak makes a character like 哦
     * (ò 98, ó 98) land differently from one run to the next - and a corpus
     * generated from it would differ too.</p>
     *
     * <p>Alphabetical is arbitrary, which is the point: where the evidence
     * ties it settles nothing, and {@link #isTiedAtTop()} says so.</p>
     */
    public Optional<String> mostFrequent() {
        return byDescendingCount().stream().findFirst();
    }

    /**
     * True when the top two readings are observed equally often - the
     * evidence exists but does not decide.
     */
    public boolean isTiedAtTop() {
        List<String> ranked = byDescendingCount();
        return ranked.size() > 1
                && countOf(ranked.get(0)) == countOf(ranked.get(1));
    }

    /** Observations of one reading, zero when unattested. */
    public long countOf(String reading) {
        return counts.getOrDefault(reading, 0L);
    }

    /**
     * Readings in descending order of observation, ties broken on the
     * reading text so the order is total and stable across runs.
     */
    public List<String> byDescendingCount() {
        return counts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Parses a {@code kHanyuPinlu} value. Anything that does not look like
     * {@code reading(count)} is skipped rather than thrown over: this is
     * evidence, and partial evidence still beats none.
     */
    public static ReadingFrequency parse(String raw) {
        if (raw == null || raw.isBlank()) return NONE;
        Map<String, Long> out = new LinkedHashMap<>();
        for (String token : raw.trim().split("\\s+")) {
            int open = token.indexOf('(');
            int close = token.lastIndexOf(')');
            if (open <= 0 || close <= open) continue;
            String reading = token.substring(0, open);
            String digits = token.substring(open + 1, close).replaceAll("[^0-9]", "");
            if (digits.isEmpty()) continue;
            out.merge(reading, Long.parseLong(digits), Long::sum);
        }
        return out.isEmpty() ? NONE : new ReadingFrequency(out);
    }

    /** The raw form, rebuilt - what the partition file holds. */
    public String toSourceText() {
        return String.join(" ", counts.entrySet().stream()
                .map(e -> e.getKey() + "(" + e.getValue() + ")").toList());
    }
}
