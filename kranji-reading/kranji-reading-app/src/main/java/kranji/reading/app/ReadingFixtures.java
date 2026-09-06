package kranji.reading.app;

import hue.captains.singapura.js.homing.core.AppModule;
import hue.captains.singapura.js.homing.studio.base.DefaultFixtures;
import hue.captains.singapura.js.homing.studio.base.Fixtures;
import hue.captains.singapura.js.homing.studio.base.Umbrella;
import hue.captains.singapura.js.homing.workspace.shell.GenericWorkspace;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpecRegistry;
import hue.captains.singapura.tao.http.action.GetAction;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.app.phonic.PhonicSourceGetAction;
import kranji.reading.app.read.ArticleGetAction;
import kranji.reading.app.gloss.ZiGlossGetAction;
import kranji.reading.app.read.ArticleTreeGetAction;
import kranji.reading.app.zi.ZiDataGetAction;
import kranji.reading.app.zi.SyllableIndexGetAction;
import kranji.reading.app.zi.SyllableMapGetAction;
import kranji.reading.app.zi.ZiDetailGetAction;
import kranji.reading.app.zi.ZiTreeGetAction;
import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Composition root: registers the reading workspace spec, adds the generic
 * workspace shell, and gates serving to this crate's closure.
 *
 * <p>The {@code servableModuleClasses} override is the runtime half of
 * RFC 0044 — {@code /module?class=...} refuses anything outside the allow-list,
 * so a module cannot reach the browser while escaping conformance.</p>
 */
public record ReadingFixtures(Umbrella<ReadingStudio> umbrella)
        implements Fixtures<ReadingStudio>, ValueObject {

    public ReadingFixtures {
        Objects.requireNonNull(umbrella, "umbrella");
        if (WorkspaceSpecRegistry.INSTANCE.get(ReadingWorkspaceSpec.INSTANCE.kind()).isEmpty()) {
            WorkspaceSpecRegistry.INSTANCE.register(ReadingWorkspaceSpec.INSTANCE);
        }
        if (WorkspaceSpecRegistry.INSTANCE.get(KnownWorkspaceSpec.INSTANCE.kind()).isEmpty()) {
            WorkspaceSpecRegistry.INSTANCE.register(KnownWorkspaceSpec.INSTANCE);
        }
    }

    private DefaultFixtures<ReadingStudio> defaults() {
        return new DefaultFixtures<>(umbrella);
    }

    @Override
    public List<AppModule<?, ?>> harnessApps() {
        var apps = new ArrayList<>(defaults().harnessApps());
        apps.add(GenericWorkspace.INSTANCE);
        return List.copyOf(apps);
    }

    @Override
    public Map<String, GetAction<RoutingContext, ?, ?, ?>> harnessGetActions() {
        var actions = new LinkedHashMap<>(defaults().harnessGetActions());
        actions.put(ZiTreeGetAction.PATH, new ZiTreeGetAction());
        actions.put(ZiDataGetAction.PATH, new ZiDataGetAction());
        actions.put(PhonicSourceGetAction.PATH, new PhonicSourceGetAction());
        actions.put(ZiDetailGetAction.PATH, new ZiDetailGetAction());
        actions.put(ArticleGetAction.PATH, new ArticleGetAction());
        actions.put(ArticleTreeGetAction.PATH, new ArticleTreeGetAction());
        actions.put(SyllableMapGetAction.PATH, new SyllableMapGetAction());
        actions.put(SyllableIndexGetAction.PATH, new SyllableIndexGetAction());
        actions.put(ZiGlossGetAction.PATH, new ZiGlossGetAction());
        return Map.copyOf(actions);
    }

    @Override
    public NodeChrome chromeFor(Umbrella<ReadingStudio> node) {
        return defaults().chromeFor(node);
    }

    @Override
    public Set<String> servableModuleClasses() {
        return ReadingServableModules.all();
    }
}
