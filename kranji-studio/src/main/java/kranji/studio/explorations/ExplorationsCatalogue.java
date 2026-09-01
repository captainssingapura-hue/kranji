package kranji.studio.explorations;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L1_Catalogue;
import kranji.studio.KranjiCatalogue;

import java.util.List;

/**
 * Section 3 — forward-looking work generalising Kranji's typed-composition
 * pattern to domains beyond Chinese characters.
 *
 * <p>None of this is a planned Kranji feature. Each write-up is intended to
 * seed a separate project; the pattern is inspired by, never coupled to,
 * {@code kranji-core}.</p>
 */
public record ExplorationsCatalogue()
        implements L1_Catalogue<KranjiCatalogue, ExplorationsCatalogue>, DocProvider {

    public static final ExplorationsCatalogue INSTANCE = new ExplorationsCatalogue();

    @Override public KranjiCatalogue parent() { return KranjiCatalogue.INSTANCE; }
    @Override public String name()    { return "Explorations"; }
    @Override public String summary() {
        return "Does the typed-composition pattern generalise? Studies across mathematics, "
             + "slide decks, molecular structure, and music notation — with honest verdicts "
             + "on where each one strains.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "\uD83E\uDDED"; }   // 🧭

    @Override public List<Entry<ExplorationsCatalogue>> leaves() {
        return List.of(
                Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(TypedTreesIndexDoc.INSTANCE.uuid().toString()),
                        TypedTreesIndexDoc.INSTANCE)
        );
    }

    @Override public List<Doc> docs() {
        return List.of(TypedTreesIndexDoc.INSTANCE);
    }
}
