package kranji.studio.gloss;

/**
 * Which articles the selected problem is read in, and how often in each.
 *
 * <p>The half of impact a number cannot carry. "35 articles" says the work is
 * worth doing; it does not say that 无 is a poetry problem and 数 is spread
 * across four groups, which is what decides whether settling it improves one
 * shelf or all of them.</p>
 *
 * <p>A row per (article, reading) rather than per article, because the subject
 * here is usually a polyphone: 数 shǔ in one article and 数 shù in another are
 * two gaps, and the status column says which of them a reader can already
 * answer.</p>
 */
public final class ImpactArticleEntityWidget extends GlossEntityWidget<ImpactArticleEntityWidget> {

    public static final ImpactArticleEntityWidget INSTANCE = new ImpactArticleEntityWidget();

    private ImpactArticleEntityWidget() {}

    private record construct() implements _Construct<_None, ImpactArticleEntityWidget> {}

    @Override protected _Construct<_None, ImpactArticleEntityWidget> construct() {
        return new construct();
    }

    @Override protected String entity() { return "impactArticle"; }
    @Override public String title() { return "Read in"; }
}
