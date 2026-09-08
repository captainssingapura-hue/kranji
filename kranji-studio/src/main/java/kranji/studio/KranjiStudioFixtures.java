package kranji.studio;

import hue.captains.singapura.js.homing.core.AppModule;
import hue.captains.singapura.js.homing.studio.base.DefaultFixtures;
import hue.captains.singapura.js.homing.studio.base.Fixtures;
import hue.captains.singapura.js.homing.studio.base.Studio;
import hue.captains.singapura.js.homing.studio.base.Umbrella;
import hue.captains.singapura.js.homing.workspace.shell.GenericWorkspace;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpecRegistry;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.ontology.ValueObject;
import io.vertx.ext.web.RoutingContext;
import kranji.studio.articles.ArticleDraftGetAction;
import kranji.studio.gloss.GlossRelationGetAction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Composition root.
 *
 * <p>Kranji Studio was a pure doc-and-plan studio — no harness apps, no
 * workspace, no data endpoints. It now carries internal tools over the gloss
 * tier, so it registers a workspace spec, mounts the generic workspace shell,
 * and serves one data route.</p>
 *
 * <p>The tools read the gloss modules and never write to them. That is a
 * property of the composition, not a promise: nothing here is handed anything
 * that could mutate the data.</p>
 */
public record KranjiStudioFixtures<S extends Studio<?>>(Umbrella<S> umbrella)
        implements Fixtures<S>, ValueObject {

    public KranjiStudioFixtures {
        Objects.requireNonNull(umbrella);
        if (WorkspaceSpecRegistry.INSTANCE.get(GlossWorkspaceSpec.INSTANCE.kind()).isEmpty()) {
            WorkspaceSpecRegistry.INSTANCE.register(GlossWorkspaceSpec.INSTANCE);
        }
        if (WorkspaceSpecRegistry.INSTANCE.get(CuratedWorkspaceSpec.INSTANCE.kind()).isEmpty()) {
            WorkspaceSpecRegistry.INSTANCE.register(CuratedWorkspaceSpec.INSTANCE);
        }
        if (WorkspaceSpecRegistry.INSTANCE.get(ArticlesWorkspaceSpec.INSTANCE.kind()).isEmpty()) {
            WorkspaceSpecRegistry.INSTANCE.register(ArticlesWorkspaceSpec.INSTANCE);
        }
    }

    private DefaultFixtures<S> defaults() {
        return new DefaultFixtures<>(umbrella);
    }

    @Override public List<AppModule<?, ?>> harnessApps() {
        var apps = new ArrayList<>(defaults().harnessApps());
        apps.add(GenericWorkspace.INSTANCE);
        return List.copyOf(apps);
    }

    @Override
    public Map<String, GetAction<RoutingContext, ?, ?, ?>> harnessGetActions() {
        var actions = new LinkedHashMap<>(defaults().harnessGetActions());
        actions.put(GlossRelationGetAction.PATH, new GlossRelationGetAction());
        actions.put(ArticleDraftGetAction.PATH, new ArticleDraftGetAction());
        return Map.copyOf(actions);
    }

    @Override public NodeChrome chromeFor(Umbrella<S> node) {
        return defaults().chromeFor(node);
    }

    @Override public Set<String> servableModuleClasses() {
        return KranjiStudioServableModules.all();
    }
}
