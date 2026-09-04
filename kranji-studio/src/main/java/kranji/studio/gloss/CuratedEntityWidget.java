package kranji.studio.gloss;

/**
 * What is written in the selected partition's curated file.
 *
 * <p>READ ONLY, and that is the first stage rather than the destination. The
 * rows come out of the model, which round-trips through GlossTsv and so is the
 * file - showing them is worth having before editing them, because the first
 * thing a reviewer needs is to see the guess and the writing together.</p>
 */
public final class CuratedEntityWidget extends GlossEntityWidget<CuratedEntityWidget> {

    public static final CuratedEntityWidget INSTANCE = new CuratedEntityWidget();

    private CuratedEntityWidget() {}

    private record construct() implements _Construct<_None, CuratedEntityWidget> {}

    @Override protected _Construct<_None, CuratedEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "curated"; }
    @Override public String title() { return "Curated"; }
}
