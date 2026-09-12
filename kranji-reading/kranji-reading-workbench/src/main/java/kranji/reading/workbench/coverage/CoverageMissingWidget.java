package kranji.reading.workbench.coverage;

/**
 * The pairs an article is missing — what a reader of it taps and gets nothing for.
 *
 * <p>Each row says how often this article reads the pair and, beside it, how
 * often the whole library does, so a gap that matters on this page alone reads
 * differently from one that matters everywhere. The last column says whether
 * the character is absent from the library or present under another reading,
 * which is the difference between an entry to write and a row to add to
 * one.</p>
 */
public final class CoverageMissingWidget extends CoverageWidget<CoverageMissingWidget> {

    public static final CoverageMissingWidget INSTANCE = new CoverageMissingWidget();

    private CoverageMissingWidget() {}

    private record construct() implements _Construct<_None, CoverageMissingWidget> {}

    @Override protected _Construct<_None, CoverageMissingWidget> construct() { return new construct(); }
    @Override protected String entity() { return CoverageRelations.MISSING; }
    @Override public String title() { return "Missing here"; }
}
