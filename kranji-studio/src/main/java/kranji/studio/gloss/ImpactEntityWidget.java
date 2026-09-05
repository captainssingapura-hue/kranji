package kranji.studio.gloss;

/**
 * The worklist, ordered by what it costs a reader.
 *
 * <p>The same problems {@link IssueEntityWidget} lists, asked a different
 * question. That one says what is left in a file; this says what is left that
 * anybody actually reads — and the two answers are very far apart, because
 * three quarters of the open queue is never read by the mounted library at
 * all.</p>
 *
 * <p>Unscoped it shows every problem ranked by weight, which is how you choose
 * a partition. Pick one and it narrows to that file, which is how you work
 * through it. Same widget, two states, told apart by the bus rather than by a
 * mode.</p>
 *
 * <p>Sort by {@code blind}, not {@code read}. See {@link GlossImpact} for why
 * the obvious measure ranks a finished character first.</p>
 */
public final class ImpactEntityWidget extends GlossEntityWidget<ImpactEntityWidget> {

    public static final ImpactEntityWidget INSTANCE = new ImpactEntityWidget();

    private ImpactEntityWidget() {}

    private record construct() implements _Construct<_None, ImpactEntityWidget> {}

    @Override protected _Construct<_None, ImpactEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "impact"; }
    @Override public String title() { return "Impact"; }
}
