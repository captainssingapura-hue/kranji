package kranji.studio.gloss;

/**
 * The 101 partitions, with what is written, guessed and still open in each.
 *
 * <p>The root of the curated chain, and the only widget in the workbench that
 * is not about a character. Picking p041 here is picking a pair of files -
 * kranji/gloss/p041.tsv and kranji/seed/p041.tsv - which is the unit the seed
 * was cut into and the unit a review sitting is meant to be.</p>
 */
public final class PartitionEntityWidget extends GlossEntityWidget<PartitionEntityWidget> {

    public static final PartitionEntityWidget INSTANCE = new PartitionEntityWidget();

    private PartitionEntityWidget() {}

    private record construct() implements _Construct<_None, PartitionEntityWidget> {}

    @Override protected _Construct<_None, PartitionEntityWidget> construct() { return new construct(); }
    @Override protected String entity() { return "partition"; }
    @Override public String title() { return "Partitions"; }
}
