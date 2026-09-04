package kranji.studio.gloss;

/** What a phrase means, and which readings that sense pins. */
public final class PhraseSenseEntityWidget extends GlossEntityWidget<PhraseSenseEntityWidget> {

    public static final PhraseSenseEntityWidget INSTANCE = new PhraseSenseEntityWidget();

    private PhraseSenseEntityWidget() {}

    private record construct() implements _Construct<_None, PhraseSenseEntityWidget> {}

    @Override protected _Construct<_None, PhraseSenseEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "phraseSense"; }
    @Override public String title() { return "Phrase Sense"; }
}
