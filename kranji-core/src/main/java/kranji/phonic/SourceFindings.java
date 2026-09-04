package kranji.phonic;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.ArrayList;
import java.util.List;

/**
 * What is worth a human's attention in the extracted source data.
 *
 * <p>Extraction is mechanical, but it runs into three things a machine should
 * not settle on its own. Each becomes a finding rather than a silent choice,
 * and together they are the review queue: 41 characters across the whole
 * standard set, which is small enough to work through properly.</p>
 */
public final class SourceFindings {

    private SourceFindings() {}

    /** What kind of attention a row needs. */
    public enum Kind {
        /**
         * A reading this build cannot model - syllabic nasals and the bare
         * {@code ê}, all interjections. The character keeps its other
         * readings; only the unmodelled one is dropped.
         */
        UNPARSEABLE_READING,

        /**
         * Unihan nominates a principal that the standard does not list at
         * all. Not an ordering question - the two authorities disagree about
         * what the character reads.
         */
        MANDARIN_NOT_IN_STANDARD,

        /**
         * The corpus evidence points at a different reading than the one
         * chosen. Often the neutral-tone particle against the citation form,
         * which for a reading app is a real question rather than noise.
         */
        FREQUENCY_DISAGREES
    }

    /** One row needing review, with enough context to decide without the source. */
    public record Finding(Kind kind, SourceReadings row, String detail)
            implements ValueObject {

        public String glyph() { return row.zi().value(); }

        @Override public String toString() {
            return kind + " " + glyph() + " (" + row.zi().codePointLabel() + ") - " + detail;
        }
    }

    /** Everything worth reviewing in one partition or the whole set. */
    public static List<Finding> check(List<SourceReadings> rows) {
        var out = new ArrayList<Finding>();
        for (SourceReadings row : rows) {
            if (!row.unparseable().isEmpty()) {
                out.add(new Finding(Kind.UNPARSEABLE_READING, row,
                        "cannot model " + String.join(", ", row.unparseable())
                      + "; keeping " + String.join(", ", row.readingTexts())));
            }
            if (!row.mandarin().isEmpty() && !row.mandarinAgrees()) {
                out.add(new Finding(Kind.MANDARIN_NOT_IN_STANDARD, row,
                        "Unihan says " + row.mandarin() + ", the standard lists "
                      + String.join(", ", row.readingTexts())
                      + " - using " + row.principal().toDiacritic()));
            }
            row.frequencyDissent().ifPresent(most ->
                    out.add(new Finding(Kind.FREQUENCY_DISAGREES, row,
                            "most observed is " + most + " ("
                          + row.frequency().countOf(most) + ") but the principal is "
                          + row.principal().toDiacritic() + " ("
                          + row.frequency().countOf(row.principal().toDiacritic()) + ")"
                          + (row.frequency().isTiedAtTop()
                                ? " - and the top two tie, so the evidence decides nothing"
                                : ""))));
        }
        return List.copyOf(out);
    }

    /** Findings of one kind, for a caller that wants to review a queue at a time. */
    public static List<Finding> of(List<Finding> findings, Kind kind) {
        return findings.stream().filter(f -> f.kind() == kind).toList();
    }
}
