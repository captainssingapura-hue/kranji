package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * The known set's service, wearing a pane because the framework has no other
 * way to be always there.
 *
 * <h2>Why it is a widget at all</h2>
 *
 * <p>What this wants to be is a headless service. The workspace shell has no
 * such thing, but it has {@code pinnedSpawns}: a widget named there is
 * instantiated at boot, hidden from the picker, and cannot be closed. That is
 * every property a service needs — it starts before anything asks it anything,
 * and no reader can accidentally shut down their own record — at the cost of
 * one tab.</p>
 *
 * <p>The tab is not wasted. A record with no visible state is how a failed save
 * went unnoticed: marking looks identical whether or not it persisted. So the
 * pane says how many readings are held and whether the device took them, which
 * gives the warning somewhere to appear without a modal interrupting a child
 * mid-sentence.</p>
 *
 * <h2>What it owns</h2>
 *
 * <p>The record, the IndexedDB copy, and the failure — all three, alone. Panes
 * hold none of them. Before this, seven components each constructed a
 * persistence engine over the same database and exactly two ever wrote, so
 * whether a claim survived the session depended on which panes were open.</p>
 *
 * <p>It joins the known party as the only actor that changes anything. Marks
 * arrive as messages, the answer goes out as news — one key at a time, with a
 * snapshot for whoever has just arrived.</p>
 */
public final class KnownServiceWidget
        extends WorkspaceWidget<WorkspaceWidget._None, KnownServiceWidget> {

    public static final KnownServiceWidget INSTANCE = new KnownServiceWidget();

    private KnownServiceWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, KnownServiceWidget> {}

    @Override protected _Construct<_None, KnownServiceWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Record"; }

    /**
     * SINGLETON rather than PINNED.
     *
     * <p>Pinning is declared by the workspace in {@code pinnedSpawns()}, not by
     * the widget, so the hint's job here is only to make a second instance
     * impossible. Two of these would be two owners of one database, which is
     * the fault this class exists to remove.</p>
     */
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.SINGLETON; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_body()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownServiceModule.createKnownService()),
                        KnownServiceModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownStoreModule.createKnownStore()),
                        KnownStoreModule.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var line = branch.createElement('line', 'div');",
                "    css.setClass(line, kr_body);",
                "    line.textContent = 'Starting the record...';",
                "    root.appendChild(line);",
                "",
                "    var note = branch.createElement('note', 'div');",
                "    css.setClass(note, kr_body);",
                "    note.textContent = 'This pane owns the record: what is claimed, "
                        + "what is on this device, and whether saving it worked. Nothing "
                        + "else writes, so while it is closed a mark has nowhere to go.';",
                "    root.appendChild(note);",
                "",
                "    var commands = (workspaceCtx && workspaceCtx.knownCommandParty)",
                "                 ? workspaceCtx.knownCommandParty : null;",
                "    var events = (workspaceCtx && workspaceCtx.knownEventParty)",
                "               ? workspaceCtx.knownEventParty : null;",
                "    var readerId = null;",
                "    var writerId = null;",
                "    var service = null;",
                "",
                "    if (!commands || !events) {",
                "        // Loud rather than quiet. A workspace missing either channel has",
                "        // no way to save anything, and the old code's answer to that was",
                "        // to carry on marking as though it did.",
                "        line.textContent = 'No record: this workspace is missing a "
                        + "known-set channel, so nothing marked here can be saved.';",
                "    } else {",
                "        readerId = 'known/service-events';",
                "        writerId = 'known/service-commands';",
                "",
                "        service = createKnownService({",
                "            store: createKnownStore(),",
                "            publish: function (msg) { events.tellFrom(readerId, msg); },",
                "            onStatus: function (status) { line.textContent = status.says; }",
                "        });",
                "",
                "        // The only listener on the write channel, ever. A second one",
                "        // would mean the set had two owners again.",
                "        commands.joinActor({",
                "            id: writerId,",
                "            parentSecretary: 'knownCommands',",
                "            reactors: {",
                "                MarkKnown:   function (msg) { service.handle(msg); },",
                "                UnmarkKnown: function (msg) { service.handle(msg); }",
                "            }",
                "        });",
                "",
                "        // On the read channel it is the publisher, and answers the one",
                "        // question that belongs there.",
                "        events.joinActor({",
                "            id: readerId,",
                "            parentSecretary: 'knownEvents',",
                "            reactors: {",
                "                WhatIsKnown: function (msg) { service.handle(msg); }",
                "            }",
                "        });",
                "",
                "        service.start();",
                "    }",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        partyDeregister: function () {",
                "            if (writerId && commands) {",
                "                try { commands.leave(writerId); } catch (e) {}",
                "            }",
                "            if (readerId && events) {",
                "                try { events.leave(readerId); } catch (e) {}",
                "            }",
                "        }",
                "    };"
        );
    }
}
