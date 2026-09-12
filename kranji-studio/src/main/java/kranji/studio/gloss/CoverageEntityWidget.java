package kranji.studio.gloss;

/**
 * Every article, worst-covered first — or the articles of the shelf picked above.
 *
 * <p>A row is one article: how many of its squares have a meaning behind them,
 * and how many distinct pairs it is missing. Picking one shows those pairs in
 * the grid beside it.</p>
 */
public final class CoverageEntityWidget extends GlossEntityWidget<CoverageEntityWidget> {

    public static final CoverageEntityWidget INSTANCE = new CoverageEntityWidget();

    private CoverageEntityWidget() {}

    private record construct() implements _Construct<_None, CoverageEntityWidget> {}

    @Override protected _Construct<_None, CoverageEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "coverage"; }
    @Override public String title() { return "Coverage by article"; }
}
