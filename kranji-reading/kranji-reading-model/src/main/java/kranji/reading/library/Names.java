package kranji.reading.library;

/**
 * The lexical rule for a dotted id.
 *
 * <p>Lowercase segments separated by dots; each begins with a letter and
 * continues with letters, digits or hyphens.</p>
 *
 * <p><b>Lowercase is enforced, not conventional.</b> {@code Kranji.Demo} and
 * {@code kranji.demo} resolving as two different collections would be a
 * collision that reads as a typo, and it would be found by someone opening the
 * wrong article rather than by the build.</p>
 */
final class Names {

    private Names() {}

    /** @throws IllegalArgumentException naming what was wrong with it */
    static String require(String raw, String what) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("a " + what + " cannot be blank");
        }
        String value = raw.trim();
        if (!value.equals(value.toLowerCase(java.util.Locale.ROOT))) {
            throw new IllegalArgumentException(
                    "a " + what + " must be lowercase: '" + value + "'");
        }
        if (value.startsWith(".") || value.endsWith(".") || value.contains("..")) {
            throw new IllegalArgumentException(
                    "a " + what + " has an empty segment: '" + value + "'");
        }
        for (String segment : value.split("\\.")) {
            requireSegment(segment, value, what);
        }
        return value;
    }

    private static void requireSegment(String segment, String whole, String what) {
        if (segment.isEmpty()) {
            throw new IllegalArgumentException("a " + what + " has an empty segment: '" + whole + "'");
        }
        char first = segment.charAt(0);
        if (first < 'a' || first > 'z') {
            throw new IllegalArgumentException("a " + what + " segment must start with a letter: '"
                    + segment + "' in '" + whole + "'");
        }
        for (int i = 1; i < segment.length(); i++) {
            char c = segment.charAt(i);
            boolean ok = (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '-';
            if (!ok) {
                throw new IllegalArgumentException("a " + what
                        + " segment allows letters, digits and hyphens: '" + segment
                        + "' in '" + whole + "'");
            }
        }
    }
}
