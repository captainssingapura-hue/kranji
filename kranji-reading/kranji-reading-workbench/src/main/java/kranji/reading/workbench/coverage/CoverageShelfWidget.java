package kranji.reading.workbench.coverage;

/**
 * Every shelf of the mounted library, with how much of it the glosses explain.
 *
 * <p>The root of the chain and the place to start: worst shelf first, and the
 * whole-library figure on the status line above the rows. Picking a shelf
 * narrows the article grid beside it.</p>
 */
public final class CoverageShelfWidget extends CoverageWidget<CoverageShelfWidget> {

    public static final CoverageShelfWidget INSTANCE = new CoverageShelfWidget();

    private CoverageShelfWidget() {}

    private record construct() implements _Construct<_None, CoverageShelfWidget> {}

    @Override protected _Construct<_None, CoverageShelfWidget> construct() { return new construct(); }
    @Override protected String entity() { return CoverageRelations.SHELF; }
    @Override public String title() { return "Coverage by shelf"; }
}
