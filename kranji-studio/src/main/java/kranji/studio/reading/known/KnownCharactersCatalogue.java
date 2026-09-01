package kranji.studio.reading.known;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L2_Catalogue;
import kranji.studio.reading.ReadingCatalogue;

import java.util.List;

/**
 * The known set — the one thing the app measures.
 *
 * <p>Small documents rather than two large ones. The earlier pair described a
 * three-state learning model that has been cut: the app does not teach, it
 * lets a child read and records the only fact that changes what they see —
 * which characters they can read without pinyin.</p>
 *
 * <p>Each document here covers one decision, so a change to marking does not
 * require re-reading the storage design to find out whether anything moved.</p>
 */
public record KnownCharactersCatalogue()
        implements L2_Catalogue<ReadingCatalogue, KnownCharactersCatalogue>, DocProvider {

    public static final KnownCharactersCatalogue INSTANCE = new KnownCharactersCatalogue();

    @Override public ReadingCatalogue parent() { return ReadingCatalogue.INSTANCE; }
    @Override public String name()    { return "Known Characters"; }
    @Override public String summary() {
        return "One set, not a learning model - the characters a child reads without pinyin, "
             + "how one gets in, how one gets out, and where the record lives.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "✓"; }   // check mark

    @Override
    public List<Entry<KnownCharactersCatalogue>> leaves() {
        return docs().stream()
                .map(d -> Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(d.uuid().toString()), d))
                .toList();
    }

    @Override
    public List<Doc> docs() {
        return List.of(
                KnownSetDoc.INSTANCE,
                MarkingDoc.INSTANCE,
                RemovalDoc.INSTANCE,
                ProfileStorageDoc.INSTANCE,
                AnnotationDoc.INSTANCE);
    }
}
