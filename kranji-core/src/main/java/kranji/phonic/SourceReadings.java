package kranji.phonic;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * One character as the source data describes it: every reading the standard
 * recognises, which one is principal, and the evidence behind that choice.
 *
 * <p>This is the output of extraction and the input to generation. It is
 * deliberately not {@code SimpleZi} - it carries the things a generator needs
 * to make a judgement and a reviewer needs to check one, including the
 * disagreements. Those disappear once the DSL is emitted.</p>
 *
 * @param zi          the character
 * @param principal   the reading shown when nothing overrides it
 * @param alternates  every other reading it has, in source order
 * @param mandarin    the reading Unihan itself nominates, which is usually
 *                    but not always {@link #principal}
 * @param frequency   corpus evidence, often {@link ReadingFrequency#none()}
 * @param unparseable readings this build cannot model, kept rather than
 *                    dropped so they can be seen and counted
 */
public record SourceReadings(
        ZiCharUTF8 zi,
        PinyinSyllable principal,
        List<PinyinSyllable> alternates,
        String mandarin,
        ReadingFrequency frequency,
        List<String> unparseable) implements ValueObject {

    public SourceReadings {
        Objects.requireNonNull(zi, "zi");
        Objects.requireNonNull(principal, "principal");
        alternates = List.copyOf(alternates);
        unparseable = List.copyOf(unparseable);
        Objects.requireNonNull(frequency, "frequency");
        mandarin = mandarin == null ? "" : mandarin;
        if (alternates.contains(principal)) {
            throw new IllegalArgumentException(
                    "the principal reading must not repeat among the alternates: "
                  + principal.toDiacritic() + " for " + zi.value());
        }
    }

    /** Every reading, principal first. */
    public List<PinyinSyllable> all() {
        var out = new ArrayList<PinyinSyllable>(alternates.size() + 1);
        out.add(principal);
        out.addAll(alternates);
        return List.copyOf(out);
    }

    /** How many readings this character has - its appearances in the tree. */
    public int readingCount() { return alternates.size() + 1; }

    public boolean isPolyphonic() { return !alternates.isEmpty(); }

    /** True when Unihan's own nomination survived as the principal. */
    public boolean mandarinAgrees() {
        return mandarin.equals(principal.toDiacritic());
    }

    /**
     * The most-observed reading, when the corpus points at a <em>different
     * standard reading</em> than the one chosen as principal.
     *
     * <p>The second condition is what makes this worth reading. {@code
     * kHanyuPinlu} counts running text, so it writes the unstressed form
     * wherever a character sits in a neutral-tone position - {@code wu} for
     * wǔ, {@code ye} for yé - and {@code r} for the erhua suffix. Those are
     * the same reading spoken lightly, not another reading, and treating
     * them as dissent buries the real cases: it flags 80 characters that
     * need no decision at all.</p>
     *
     * <p>Requiring the observed form to be one the standard actually lists
     * leaves the genuine question - a character the dictionary reads one way
     * and running text reads another, like 得 as dé against the particle de.
     * Empty means nothing to review.</p>
     */
    public Optional<String> frequencyDissent() {
        List<String> standard = readingTexts();
        return frequency.mostFrequent()
                .filter(most -> !most.equals(principal.toDiacritic()))
                .filter(standard::contains);
    }

    /** Readings as pinyin writes them, for display. */
    public List<String> readingTexts() {
        return all().stream().map(PinyinSyllable::toDiacritic).toList();
    }
}
