package kranji.studio.gloss;

/**
 * The pairs an article is missing — what a reader of it taps and gets nothing for.
 *
 * <p>Each row says how often this article reads the pair and, beside it, how
 * often the whole library does, so a gap that matters on this page alone
 * reads differently from one that matters everywhere. The last column says
 * whether the character is absent from the library or present under another
 * reading, which is the difference between an entry to write and a row to add
 * to one.</p>
 */
public final class CoverageMissingEntityWidget extends GlossEntityWidget<CoverageMissingEntityWidget> {

    public static final CoverageMissingEntityWidget INSTANCE = new CoverageMissingEntityWidget();

    private CoverageMissingEntityWidget() {}

    private record construct() implements _Construct<_None, CoverageMissingEntityWidget> {}

    @Override protected _Construct<_None, CoverageMissingEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "coverageMissing"; }
    @Override public String title() { return "Missing here"; }
}
