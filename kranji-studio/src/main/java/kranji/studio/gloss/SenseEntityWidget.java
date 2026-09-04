package kranji.studio.gloss;

/** The meanings of the selected readings, in the order they were authored. */
public final class SenseEntityWidget extends GlossEntityWidget<SenseEntityWidget> {

    public static final SenseEntityWidget INSTANCE = new SenseEntityWidget();

    private SenseEntityWidget() {}

    private record construct() implements _Construct<_None, SenseEntityWidget> {}

    @Override protected _Construct<_None, SenseEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "sense"; }
    @Override public String title() { return "Sense"; }
}
