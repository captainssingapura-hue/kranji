package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;
import kranji.reading.app.read.ReadabilityModule;

import java.util.List;

/**
 * Where this reader has got to, said in a way worth reading.
 *
 * <h2>Three cards, in the order that matters</h2>
 *
 * <p>What you can read, what is close, then the counts. {@link
 * KnownProgressModule} says why that order is fixed and why the corpus fraction
 * never leads.</p>
 *
 * <p>The near lists are the working half. A count tells somebody where they
 * are; "3 readings away" tells them what to do next, and it is short enough to
 * be worth doing today. Both come from data already on the device — the census
 * the reader fetched and the sounds index the pane beside this one uses — so
 * the whole tracker costs one cached module each and no request when a reading
 * is marked.</p>
 *
 * <h2>Read-only, and quiet</h2>
 *
 * <p>Nothing here claims or withdraws anything. It also does not remember what
 * it said last time: no streaks, no "you have not marked anything since
 * Tuesday". A tracker that keeps score against a person is one they stop
 * opening, and a tracker nobody opens encourages nobody.</p>
 *
 * <p>Text only in this first pass. What it says is the part worth getting
 * right, and a bar chart drawn over the wrong sentence is still the wrong
 * sentence.</p>
 *
 * <p>No CJK appears in this file. Titles arrive from the library.</p>
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
                        new ReadingCss.kr_body(),
                        new ReadingCss.kr_badge(),
                        new ReadingCss.kr_kn_line(),
                        new ReadingCss.kr_kn_text(),
                        new ReadingCss.kr_kn_tally()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new ReadabilityModule.createReadability()),
                        ReadabilityModule.INSTANCE),
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
                "    var progress = createKnownProgress(createReadability());",
                "",
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    function card(name) {",
                "        var c = branch.createElement(name, 'div');",
                "        css.setClass(c, kr_card);",
                "        root.appendChild(c);",
                "        return c;",
                "    }",
                "",
                "    function line(parent, name, klass, text) {",
                "        var el = branch.createElement(name, 'div');",
                "        css.setClass(el, klass);",
                "        el.textContent = text || '';",
                "        parent.appendChild(el);",
                "        return el;",
                "    }",
                "",
                "    var top = card('top');",
                "    var headline = line(top, 'headline', kr_title, '');",
                "    var cheer = line(top, 'cheer', kr_body, '');",
                "",
                "    var closeCard = card('close');",
                "    line(closeCard, 'closeTitle', kr_title, 'Almost there');",
                "    var closeBody = branch.createElement('closeBody', 'div');",
                "    css.setClass(closeBody, kr_body);",
                "    closeCard.appendChild(closeBody);",
                "",
                "    var countCard = card('counts');",
                "    line(countCard, 'countTitle', kr_title, 'What is on the record');",
                "    var countBody = branch.createElement('countBody', 'div');",
                "    css.setClass(countBody, kr_body);",
                "    countCard.appendChild(countBody);",
                "",
                "    // Attached only while it has something to say. Blanking it would",
                "    // leave an empty line the layout still reserves, and a gap where a",
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
                "    var owner = Object.freeze({ toString: function () { return 'knownProgress'; } });",
                "    var __census = null;",
                "    var __syllables = null;",
                "    var __known = [];",
                "    var __seq = 0;",
                "",
                "    // The lists are remade on every change, so their elements are",
                "    // remade too - a branch registers names, and reusing one collides.",
                "    function freshList(host, name) {",
                "        if (branch.getBranch(name)) branch.dissolveBranch(name);",
                "        var b = branch.createBranch(name);",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    function listInto(host, name, items, textOf) {",
                "        var b = freshList(host, name);",
                "        for (var i = 0; i < items.length; i++) {",
                "            var row = b.createElement('row' + i, 'div');",
                "            css.setClass(row, kr_kn_line);",
                "            row.textContent = textOf(items[i]);",
                "            host.appendChild(row);",
                "        }",
                "    }",
                "",
                "    function plural(n, one, many) {",
                "        return n + ' ' + (n === 1 ? one : many);",
                "    }",
                "",
                "    function render() {",
                "        if (!__census || !__syllables) {",
                "            say('Working out where you have got to...');",
                "            return;",
                "        }",
                "        say('');",
                "",
                "        var p = progress.summarise(__census, __syllables, __known);",
                "        headline.textContent = progress.headline(p);",
                "        cheer.textContent = progress.encouragement(p);",
                "",
                "        // Stories first, sounds second: a story is the thing somebody",
                "        // wanted in the first place, and a sound is how they get there.",
                "        var stories = progress.nearestStories(p, 5);",
                "        var sounds = progress.nearlyDoneSounds(__syllables, __known, 5);",
                "        var items = [];",
                "        stories.forEach(function (s) { items.push({ kind: 'story', it: s }); });",
                "        sounds.forEach(function (s) { items.push({ kind: 'sound', it: s }); });",
                "",
                "        if (items.length === 0) {",
                "            listInto(closeBody, 'closeList', [{}], function () {",
                "                return __known.length === 0",
                "                    ? 'This fills in as soon as there is something to be near.'",
                "                    : 'Nothing part-finished - a good place to start something new.';",
                "            });",
                "        } else {",
                "            listInto(closeBody, 'closeList', items, function (row) {",
                "                if (row.kind === 'story') {",
                "                    return row.it.title + ' - '",
                "                         + plural(row.it.unknown, 'new reading', 'new readings')",
                "                         + ' away';",
                "                }",
                "                return row.it.label + ' - ' + row.it.left + ' to go, '",
                "                     + row.it.known + ' of ' + row.it.characters + ' claimed';",
                "            });",
                "        }",
                "",
                "        var counts = [",
                "            plural(p.characters, 'character', 'characters') + ' claimed',",
                "            plural(p.readings, 'reading', 'readings') + ' in all',",
                "            p.soundsStarted + ' of ' + p.sounds + ' sounds started'",
                "                + (p.soundsComplete > 0",
                "                   ? ', ' + p.soundsComplete + ' complete' : ''),",
                "            p.ready + ' of ' + p.stories + ' stories readable on your own'",
                "        ];",
                "        listInto(countBody, 'countList', counts, function (t) { return t; });",
                "    }",
                "",
                "    // Both halves are cached by URL and profile-free, so opening this",
                "    // pane a second time pays nothing and marking a reading pays nothing.",
                "    var mine = ++__seq;",
                "    import('/article-census')",
                "        .then(function (mod) {",
                "            if (mine !== __seq) return;",
                "            __census = mod.articles || {};",
                "            render();",
                "        })",
                "        .catch(function () {",
                "            say('Could not read the library just now.');",
                "        });",
                "    import('/syllable-index')",
                "        .then(function (mod) {",
                "            if (mine !== __seq) return;",
                "            __syllables = mod.syllables || [];",
                "            render();",
                "        })",
                "        .catch(function () {",
                "            say('Could not read the sounds index just now.');",
                "        });",
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
                "        // Seeded from the device, never written to it.",
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
