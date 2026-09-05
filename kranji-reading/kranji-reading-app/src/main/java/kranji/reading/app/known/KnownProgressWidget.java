package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * How many characters this reader can read, and what that is called.
 *
 * <p>A mark, the name of where they have got to, the count, and how far to the
 * next name. Nothing else. {@link KnownProgressModule} holds the bands, their
 * icons and every word of the wording.</p>
 *
 * <h2>What is deliberately not here</h2>
 *
 * <p>An earlier pass showed stories now readable, sounds started and sounds
 * complete alongside this. All three were true, none was the question, and
 * together they made a dashboard — the screen a learner scans rather than
 * reads, which is what most character apps end up being. Sounds belong to the
 * pane beside this one; readable stories belong to the catalogue, where a
 * reader is choosing something to read.</p>
 *
 * <p>No streaks and no memory of the last visit. A tracker that keeps score
 * against a person is one they stop opening, and a tracker nobody opens
 * encourages nobody.</p>
 *
 * <p>It reads the known set and nothing else — no census, no sounds index, no
 * request of any kind. The whole pane is one subtraction.</p>
 *
 * <p>No CJK appears in this file.</p>
 */
public final class KnownProgressWidget
        extends WorkspaceWidget<WorkspaceWidget._None, KnownProgressWidget> {

    public static final KnownProgressWidget INSTANCE = new KnownProgressWidget();

    private KnownProgressWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, KnownProgressWidget> {}

    @Override protected _Construct<_None, KnownProgressWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Progress"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_status(),
                        new ReadingCss.kr_card(),
                        new ReadingCss.kr_title(),
                        new ReadingCss.kr_body()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownSetModule.createKnownSet()),
                        KnownSetModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownProgressModule.createKnownProgress()),
                        KnownProgressModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownStoreModule.createKnownStore()),
                        KnownStoreModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownPersistenceModule.createKnownPersistence()),
                        KnownPersistenceModule.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var progress = createKnownProgress(createKnownSet());",
                "",
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var card = branch.createElement('card', 'div');",
                "    css.setClass(card, kr_card);",
                "    root.appendChild(card);",
                "",
                "    function line(name, klass) {",
                "        var el = branch.createElement(name, 'div');",
                "        css.setClass(el, klass);",
                "        card.appendChild(el);",
                "        return el;",
                "    }",
                "",
                "    // The mark on its own line, big. It is the part somebody sees",
                "    // change before they have read anything.",
                "    var bandIcon = line('icon', kr_title);",
                "",
                "    // Then the band. A count is a fact and a band is a place to be,",
                "    // and the place is what somebody remembers a week later.",
                "    var bandName = line('band', kr_title);",
                "    var countLine = line('count', kr_body);",
                "    var nextLine = line('next', kr_body);",
                "",
                "    // Attached only while it has something to say - a blank line where a",
                "    // sentence was reads as something having gone wrong.",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, kr_status);",
                "",
                "    function say(text) {",
                "        if (text) {",
                "            status.textContent = text;",
                "            if (!status.parentNode) root.appendChild(status);",
                "        } else if (status.parentNode) {",
                "            root.removeChild(status);",
                "        }",
                "    }",
                "",
                "    var __known = [];",
                "",
                "    function render() {",
                "        var n = progress.count(__known);",
                "        bandIcon.textContent = progress.icon(n);",
                "        bandName.textContent = progress.headline(n);",
                "        countLine.textContent = progress.line(n);",
                "        nextLine.textContent = progress.nextLine(n);",
                "    }",
                "",
                "    var __knownParty = (workspaceCtx && workspaceCtx.knownParty)",
                "                     ? workspaceCtx.knownParty : null;",
                "    var __knownActorId = null;",
                "    if (__knownParty) {",
                "        __knownActorId = 'known/prog-' + Math.random().toString(36).slice(2, 8);",
                "        __knownParty.joinActor({",
                "            id: __knownActorId,",
                "            parentSecretary: 'knownSet',",
                "            reactors: {",
                "                KnownChanged: function (msg) {",
                "                    __known = (msg && msg.known) ? msg.known : [];",
                "                    render();",
                "                }",
                "            }",
                "        });",
                "        __knownParty.tellFrom(__knownActorId, { kind: 'WhatIsKnown' });",
                "        // Seeded from the device, never written to it. Seeding matters",
                "        // here: opened on its own, this would otherwise say nobody has",
                "        // read anything on a device that has been read on for months.",
                "        createKnownPersistence({",
                "            store: createKnownStore(),",
                "            tell: function (msg) {",
                "                __knownParty.tellFrom(__knownActorId, msg);",
                "            },",
                "            onProblem: function (broken) {",
                "                if (broken) {",
                "                    say('Could not read what is saved on this device.');",
                "                }",
                "            }",
                "        }).start();",
                "    }",
                "",
                "    render();",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        partyDeregister: function () {",
                "            if (__knownActorId && __knownParty) {",
                "                try { __knownParty.leave(__knownActorId); } catch (e) {}",
                "            }",
                "        }",
                "    };"
        );
    }
}
