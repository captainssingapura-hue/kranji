package kranji.studio.gloss;

/**
 * What the seeder could not settle in the selected partition.
 *
 * <p>The same rows {@link ProblemEntityWidget} shows, asked for differently.
 * That one hangs off a character reached by walking sounds and pairs and
 * answers "what is wrong with THIS one"; this hangs off a partition and
 * answers "what is left in THIS file". A relation cannot have two parents,
 * and neither question is the other.</p>
 */
public final class IssueEntityWidget extends GlossEntityWidget<IssueEntityWidget> {

    public static final IssueEntityWidget INSTANCE = new IssueEntityWidget();

    private IssueEntityWidget() {}

    private record construct() implements _Construct<_None, IssueEntityWidget> {}

    @Override protected _Construct<_None, IssueEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "issue"; }
    @Override public String title() { return "Issues"; }
}
