package kranji.reading.content;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.List;

/**
 * Something wrong with an article's source.
 *
 * <p>Reported rather than thrown, so one bad override does not hide the other
 * nine problems in the same file. Same shape as the corpus findings.</p>
 */
public record ParseFinding(ParseFinding.Severity severity, int line, String message)
        implements ValueObject {

    public enum Severity { ERROR, WARNING }

    public static ParseFinding error(int line, String message) {
        return new ParseFinding(Severity.ERROR, line, message);
    }

    public static ParseFinding warning(int line, String message) {
        return new ParseFinding(Severity.WARNING, line, message);
    }

    /** True when nothing here prevents the article from being served. */
    public static boolean servable(List<ParseFinding> findings) {
        return findings.stream().noneMatch(f -> f.severity() == Severity.ERROR);
    }

    @Override public String toString() {
        return severity + " line " + line + ": " + message;
    }
}
