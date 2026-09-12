package kranji.studio.gloss;

/**
 * Every shelf of the mounted library, with how much of it the glosses explain.
 *
 * <p>The root of the coverage chain and the place to start: worst shelf first,
 * and the whole-library figure on the status line above the rows. Picking a
 * shelf narrows the article grid beside it.</p>
 */
public final class CoverageShelfEntityWidget extends GlossEntityWidget<CoverageShelfEntityWidget> {

    public static final CoverageShelfEntityWidget INSTANCE = new CoverageShelfEntityWidget();

    private CoverageShelfEntityWidget() {}

    private record construct() implements _Construct<_None, CoverageShelfEntityWidget> {}

    @Override protected _Construct<_None, CoverageShelfEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "coverageShelf"; }
    @Override public String title() { return "Coverage by shelf"; }
}
