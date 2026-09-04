package kranji.studio;

import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.WidgetGroup;
import hue.captains.singapura.js.homing.workspace.WidgetIcon;
import hue.captains.singapura.js.homing.workspace.WidgetLabel;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpec;
import kranji.studio.gloss.DemandEntityWidget;
import kranji.studio.gloss.GlossSelectionSecretaryModule;
import kranji.studio.gloss.PhraseEntityWidget;
import kranji.studio.gloss.PhraseSenseEntityWidget;
import kranji.studio.gloss.SenseEntityWidget;
import kranji.studio.gloss.SoundEntityWidget;

import java.util.List;

/**
 * The workbench: gloss relations, side by side.
 *
 * <p>One widget, offered once — but the widget is {@code MULTI}, so the useful
 * arrangement is several of it. Senses beside citations answers "which meaning
 * has nothing to show for it"; phrases beside phrase-senses answers "which
 * phrase pins a reading". Neither question has a home in a tree.</p>
 */
public final class GlossWorkspaceSpec implements WorkspaceSpec {

    public static final String KIND = "gloss";

    public static final GlossWorkspaceSpec INSTANCE = new GlossWorkspaceSpec();

    private GlossWorkspaceSpec() {}

    @Override public String kind()  { return KIND; }
    @Override public String title() { return "Gloss Workbench"; }

    @Override
    public List<WidgetEntry> widgetEntries() {
        // Two groups because there are two chains, and they meet at the
        // citation. Grouping them apart is what makes that legible from the
        // picker rather than something you work out by clicking.
        WidgetGroup character = WidgetGroup.of("Character chain");
        WidgetGroup phrase = WidgetGroup.of("Phrase chain");
        return List.of(
                WidgetEntry.of(SoundEntityWidget.class, WidgetLabel.of("Sound"))
                        .withIcon(new WidgetIcon.Emoji("🔊"))
                        .withGroup(character),
                WidgetEntry.of(DemandEntityWidget.class, WidgetLabel.of("Demand"))
                        .withIcon(new WidgetIcon.Emoji("📋"))
                        .withGroup(character),
                WidgetEntry.of(SenseEntityWidget.class, WidgetLabel.of("Sense"))
                        .withIcon(new WidgetIcon.Emoji("💬"))    // 💬
                        .withGroup(character),
                WidgetEntry.of(PhraseEntityWidget.class, WidgetLabel.of("Phrase"))
                        .withIcon(new WidgetIcon.Emoji("📑"))    // 📑
                        .withGroup(phrase),
                WidgetEntry.of(PhraseSenseEntityWidget.class, WidgetLabel.of("Phrase Sense"))
                        .withIcon(new WidgetIcon.Emoji("📄"))    // 📄
                        .withGroup(phrase));
    }

    /**
     * The selection bus.
     *
     * <p>One party for every entity type, not one each. A message names the
     * entity it concerns and a widget reacts only to the one above it, so
     * adding a relation costs a widget rather than a party.</p>
     */
    @Override
    public List<PartyDecl> parties() {
        return List.of(
                PartyDecl.of("glossSelection", GlossSelectionSecretaryModule.INSTANCE,
                             "GlossSelectionSecretary")
                         .exposedAs("glossParty")
                         .build());
    }
}
