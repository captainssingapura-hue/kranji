package kranji.studio.gloss;

/** The phrase registry. The other root: phrases have identity of their own, which is why they are referenced rather than nested. */
public final class PhraseEntityWidget extends GlossEntityWidget<PhraseEntityWidget> {

    public static final PhraseEntityWidget INSTANCE = new PhraseEntityWidget();

    private PhraseEntityWidget() {}

    private record construct() implements _Construct<_None, PhraseEntityWidget> {}

    @Override protected _Construct<_None, PhraseEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "phrase"; }
    @Override public String title() { return "Phrase"; }
}
