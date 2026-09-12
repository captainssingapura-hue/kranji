package kranji.reading.workbench;

import hue.captains.singapura.js.homing.core.AppModule;
import hue.captains.singapura.js.homing.studio.base.DefaultFixtures;
import hue.captains.singapura.js.homing.studio.base.Fixtures;
import hue.captains.singapura.js.homing.studio.base.Umbrella;
import hue.captains.singapura.js.homing.workspace.shell.GenericWorkspace;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpecRegistry;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.ontology.ValueObject;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.app.ReadingFixtures;
import kranji.reading.workbench.coverage.CoverageRelationGetAction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * What the bench serves: the reading widgets' routes and modules, and its own
 * workspace.
 *
 * <p>Nothing here is the bench's own but the workspace registration. The
 * routes are {@link ReadingFixtures#dataActions()} — the same map the reader
 * mounts, taken from the same method, so a route the reader has cannot be one
 * the bench lacks. The servable modules are the reading crate's closure for
 * the same reason. A bench that copied either list would drift from the
 * reader on the first route added for a widget, and drift is the one thing a
 * tool for seeing-what-the-reader-sees cannot afford.</p>
 */
public record LibraryWorkbenchFixtures(Umbrella<LibraryWorkbenchStudio> umbrella)
        implements Fixtures<LibraryWorkbenchStudio>, ValueObject {

    public LibraryWorkbenchFixtures {
        Objects.requireNonNull(umbrella, "umbrella");
        if (WorkspaceSpecRegistry.INSTANCE.get(ArticleBrowserSpec.INSTANCE.kind()).isEmpty()) {
            WorkspaceSpecRegistry.INSTANCE.register(ArticleBrowserSpec.INSTANCE);
        }
        if (WorkspaceSpecRegistry.INSTANCE.get(CoverageWorkbenchSpec.INSTANCE.kind()).isEmpty()) {
            WorkspaceSpecRegistry.INSTANCE.register(CoverageWorkbenchSpec.INSTANCE);
        }
    }

    private DefaultFixtures<LibraryWorkbenchStudio> defaults() {
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
        actions.putAll(ReadingFixtures.dataActions());
        actions.put(CoverageRelationGetAction.PATH, new CoverageRelationGetAction());
        return Map.copyOf(actions);
    }


    @Override
    public NodeChrome chromeFor(Umbrella<LibraryWorkbenchStudio> node) {
        return defaults().chromeFor(node);
    }
    @Override
    public Set<String> servableModuleClasses() {
        return LibraryWorkbenchServableModules.all();
    }
}
