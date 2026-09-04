package kranji.studio.gloss;

/** Every (character, reading) the bundled library uses - written or not. */
public final class DemandEntityWidget extends GlossEntityWidget<DemandEntityWidget> {

    public static final DemandEntityWidget INSTANCE = new DemandEntityWidget();

    private DemandEntityWidget() {}

    private record construct() implements _Construct<_None, DemandEntityWidget> {}

    @Override protected _Construct<_None, DemandEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "demand"; }
    @Override public String title() { return "Demand"; }
}
