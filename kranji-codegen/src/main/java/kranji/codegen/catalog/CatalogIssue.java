package kranji.codegen.catalog;

/**
 * One catalog row that the iterator could not process for a reason
 * indicating the row itself is broken (not just blocked on a missing
 * component, which is a normal transient state).
 *
 * <p>Used by {@link CatalogIssueWriter} to render a markdown report at
 * {@code docs/catalog/issues.md} for human review.</p>
 *
 * @param row    the source row
 * @param kind   issue category (used to bucket the report)
 * @param detail short human-readable explanation
 */
public record CatalogIssue(CatalogRow row, Kind kind, String detail) {

    public enum Kind {
        /** Layout code expected N components, row provided M ≠ N. */
        ARITY_MISMATCH("Arity mismatches"),
        /** Layout code has no registered generator. */
        UNSUPPORTED_LAYOUT("Unsupported layouts"),
        /** Pinyin (initial+finalTone) couldn't be decoded into Head/Body/Tail. */
        PINYIN_PARSE("Pinyin parse failures"),
        /** Component token wasn't a single glyph and wasn't a recognised group form. */
        BAD_COMPONENT_TOKEN("Bad component tokens");

        public final String sectionTitle;
        Kind(String t) { this.sectionTitle = t; }
    }
}
