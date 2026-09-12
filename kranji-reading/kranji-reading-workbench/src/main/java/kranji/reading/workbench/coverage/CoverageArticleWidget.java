package kranji.reading.workbench.coverage;

/**
 * Every article, worst-covered first — or the articles of the shelf picked above.
 *
 * <p>A row is one article: how many of its squares have a meaning behind them,
 * and how many distinct pairs it is missing. Picking one shows those pairs in
 * the grid beside it.</p>
 */
public final class CoverageArticleWidget extends CoverageWidget<CoverageArticleWidget> {

    public static final CoverageArticleWidget INSTANCE = new CoverageArticleWidget();

    private CoverageArticleWidget() {}

    private record construct() implements _Construct<_None, CoverageArticleWidget> {}

    @Override protected _Construct<_None, CoverageArticleWidget> construct() { return new construct(); }
    @Override protected String entity() { return CoverageRelations.ARTICLE; }
    @Override public String title() { return "Coverage by article"; }
}
