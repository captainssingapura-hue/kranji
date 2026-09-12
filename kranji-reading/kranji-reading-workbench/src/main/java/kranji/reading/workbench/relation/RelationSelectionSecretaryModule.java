package kranji.reading.workbench.relation;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The selection bus a family of relation grids cascades over.
 *
 * <p>One bus per family, not one per relation. A message names the relation
 * it concerns and each widget reacts only to the one above it, so adding a
 * relation costs a widget rather than a party. The whole state rides on every
 * broadcast, so a grid that mounted late is right on the next change rather
 * than needing a resync.</p>
 *
 * <p>Generic over the family: the gloss workbench and the coverage workbench
 * each declare a party with this module behind it, under their own party
 * name, and the same JavaScript serves both.</p>
 */
public record RelationSelectionSecretaryModule()
        implements DomModule<RelationSelectionSecretaryModule> {

    public record RelationSelectionSecretary()
            implements Exportable._Constant<RelationSelectionSecretaryModule> {}

    public static final RelationSelectionSecretaryModule INSTANCE =
            new RelationSelectionSecretaryModule();

    @Override
    public ImportsFor<RelationSelectionSecretaryModule> imports() {
        return ImportsFor.<RelationSelectionSecretaryModule>builder().build();
    }

    @Override
    public ExportsOf<RelationSelectionSecretaryModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new RelationSelectionSecretary()));
    }
}
