package kranji.studio.gloss;

/**
 * The seeded rows nobody has settled, and what has been decided about them.
 *
 * <p>Hangs off demand, so selecting a character anywhere in the chain narrows
 * this to that character's problems — but on the <b>character</b>, not the
 * pair. A problem belongs to a Zi: 欸's queued split is one job whether you
 * reached it through {@code ǎi} or {@code ēi}, and scoping on the pair would
 * hide the row unless you picked the reading it happens to be filed under.
 * {@link GlossRelations#scopeKeysFor} is where that happens.</p>
 *
 * <p>Nothing hangs off it in turn. The chain is a way to walk the data; this
 * is where the walk ends and the fix is an edit to a TSV.</p>
 */
public final class ProblemEntityWidget extends GlossEntityWidget<ProblemEntityWidget> {

    public static final ProblemEntityWidget INSTANCE = new ProblemEntityWidget();

    private ProblemEntityWidget() {}

    private record construct() implements _Construct<_None, ProblemEntityWidget> {}

    @Override protected _Construct<_None, ProblemEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "problem"; }
    @Override public String title() { return "Problems"; }
}
