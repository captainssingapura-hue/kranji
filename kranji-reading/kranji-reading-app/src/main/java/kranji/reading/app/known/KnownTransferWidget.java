package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * The record as a file — out of the browser, and back in.
 *
 * <h2>Whose pane is whose</h2>
 *
 * <p>The three known-set panes are split by <b>who uses them and on what</b>,
 * which is a sharper line than the one they were split on before.</p>
 *
 * <ul>
 *   <li><b>Mark Known</b> acts on a single character. It is the daily
 *       operation, and it is the child's.</li>
 *   <li><b>Known</b> reviews the whole list, a row per reading.</li>
 *   <li><b>This</b> moves the whole list at once. Import and export are both
 *       list operations and both maintenance, and maintenance is a parent's
 *       job.</li>
 * </ul>
 *
 * <p>The earlier split put export with the review because export is a read and
 * import with marking because import is a write. That reasoning was sound and
 * still produced the wrong panes: it separated the two halves of one task, so
 * backing up and restoring a record — the same job, done a month apart — lived
 * in different places, and a pane a child uses every day carried a file picker
 * they have no use for.</p>
 *
 * <h2>Why the two belong together</h2>
 *
 * <p>They are each other's inverse and share a format. The file this writes is
 * the file it reads, and {@code KnownTransferModule} owns both directions for
 * exactly that reason — a change to one that forgot the other would break the
 * round trip, and the round trip is the whole point of having a file.</p>
 *
 * <p>Undo lives here too, beside the import that would need undoing. It is the
 * one control that is neither a read nor a write of a file, and it makes no
 * sense anywhere else.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the corpus.</p>
 */
public final class KnownTransferWidget
        extends WorkspaceWidget<WorkspaceWidget._None, KnownTransferWidget> {

    public static final KnownTransferWidget INSTANCE = new KnownTransferWidget();

    private KnownTransferWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, KnownTransferWidget> {}

    @Override protected _Construct<_None, KnownTransferWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Import / Export"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_status(),
                        new ReadingCss.kr_bar(),
                        new ReadingCss.kr_btn(),
                        new ReadingCss.kr_kn_tally(),
                        new ReadingCss.kr_kn_hidden(),
                        new ReadingCss.kr_kn_text(),
                        new ReadingCss.kr_body()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownSetModule.createKnownSet()),
                        KnownSetModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownStoreModule.createKnownStore()),
                        KnownStoreModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownPersistenceModule.createKnownPersistence()),
                        KnownPersistenceModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownTransferModule.createKnownTransfer()),
                        KnownTransferModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownImportControlsModule.createKnownImportControls()),
                        KnownImportControlsModule.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var knownSet = createKnownSet();",
                "    var transfer = createKnownTransfer();",
                "",
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var bar = branch.createElement('bar', 'div');",
                "    css.setClass(bar, kr_bar);",
                "    root.appendChild(bar);",
                "",
                "    var __known = [];",
                "    var __lastImport = [];",
                "",
                "    // Import, its file picker, and Undo. Their own module - see the",
                "    // note there for why reading a file is not this pane's business",
                "    // either, only deciding that it should happen.",
                "    var imports = createKnownImportControls({",
                "        branch: branch, css: css,",
                "        btnClass: kr_btn, hiddenClass: kr_kn_hidden,",
                "        transfer: transfer,",
                "        tell: function (msg) { tellKnown(msg); },",
                "        knownCount: function () { return __known.length; },",
                "        say: function (text) { note.textContent = text; }",
                "    });",
                "    for (var ci = 0; ci < imports.controls.length; ci++) {",
                "        bar.appendChild(imports.controls[ci]);",
                "    }",
                "",
                "    var save = branch.createElement('save', 'button');",
                "    css.setClass(save, kr_btn);",
                "    save.type = 'button';",
                "    save.textContent = 'Export';",
                "    bar.appendChild(save);",
                "",
                "    var tally = branch.createElement('tally', 'div');",
                "    css.setClass(tally, kr_kn_tally);",
                "    bar.appendChild(tally);",
                "",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, kr_status);",
                "    root.appendChild(status);",
                "",
                "    // The import's own report, kept apart from the status line so that",
                "    // an export does not wipe news of lines that could not be read.",
                "    var note = branch.createElement('note', 'div');",
                "    css.setClass(note, kr_status);",
                "    root.appendChild(note);",
                "",
                "    // The record itself, shown as well as copied. A copy that reports",
                "    // success and produced nothing is worse than one that fails loudly,",
                "    // and a parent whose clipboard is blocked can still select it by",
                "    // hand. Hidden until asked for.",
                "    var sheet = branch.createElement('sheet', 'textarea');",
                "    // One class or the other, never both: two classes that each set",
                "    // display are decided by stylesheet order, not by the order they",
                "    // are passed here, and the hidden one loses.",
                "    css.setClass(sheet, kr_kn_hidden);",
                "    sheet.readOnly = true;",
                "    sheet.spellcheck = false;",
                "    root.appendChild(sheet);",
                "",
                "    var __knownParty = (workspaceCtx && workspaceCtx.knownParty)",
                "                     ? workspaceCtx.knownParty : null;",
                "    var __knownActorId = null;",
                "",
                "    function tellKnown(msg) {",
                "        if (__knownParty && __knownActorId) {",
                "            __knownParty.tellFrom(__knownActorId, msg);",
                "        }",
                "    }",
                "",
                "    function paint() {",
                "        var readings = __known.length;",
                "        var chars = knownSet.characters(__known).length;",
                "        tally.textContent = readings === 0 ? 'Nothing marked yet'",
                "            : chars + (chars === 1 ? ' character' : ' characters')",
                "              + ', ' + readings + (readings === 1 ? ' reading' : ' readings');",
                "        imports.showUndo(__lastImport.length);",
                "    }",
                "",
                "    // Copied and shown, rather than downloaded.",
                "    //",
                "    // A download needs an anchor's href, and the framework's href manager",
                "    // - the only sanctioned way to write one - is injected solely into",
                "    // modules that import an AppLink. There is no AppLink that means",
                "    // \"save a file the page just made\", and importing an unrelated app's",
                "    // link to obtain the manager would be a dependency that lies. Raised",
                "    // upstream as UD-002; until it is answered, the clipboard reaches the",
                "    // same text file in one more step and needs no URL at all.",
                "    save.addEventListener('click', function () {",
                "        if (!__known.length) {",
                "            status.textContent = 'Nothing to export yet.';",
                "            return;",
                "        }",
                "        var text = transfer.toText(__known, knownSet.characters(__known).length);",
                "        sheet.value = text;",
                "        css.setClass(sheet, kr_kn_text);",
                "        sheet.select();",
                "        var says = __known.length",
                "            + (__known.length === 1 ? ' reading' : ' readings');",
                "        function shown(how) {",
                "            status.textContent = says + ' ' + how",
                "                + ' Paste it into a text file and keep it somewhere safe -'",
                "                + ' this is the only copy that survives clearing the browser.';",
                "        }",
                "        if (navigator.clipboard && navigator.clipboard.writeText) {",
                "            navigator.clipboard.writeText(text)",
                "                .then(function () { shown('copied.'); },",
                "                      function () { shown('ready, and selected below.'); });",
                "        } else {",
                "            shown('ready, and selected below.');",
                "        }",
                "    });",
                "",
                "    imports.onUndo(function () {",
                "        if (!__lastImport.length) return;",
                "        var n = __lastImport.length;",
                "        tellKnown({ kind: 'UndoImport' });",
                "        note.textContent = 'Took back ' + n",
                "            + (n === 1 ? ' reading' : ' readings') + ' that import had added.';",
                "    });",
                "",
                "    if (__knownParty) {",
                "        __knownActorId = 'known/transfer-' + Math.random().toString(36).slice(2, 8);",
                "        __knownParty.joinActor({",
                "            id: __knownActorId,",
                "            parentSecretary: 'knownSet',",
                "            reactors: {",
                "                KnownChanged: function (msg) {",
                "                    __known = (msg && msg.known) ? msg.known : [];",
                "                    __lastImport = (msg && msg.lastImport) ? msg.lastImport : [];",
                "                    store.changed(__known, __lastImport);",
                "                    paint();",
                "                }",
                "            }",
                "        });",
                "    }",
                "",
                "    // This pane writes - an import is a change to the record - so it",
                "    // persists as well as reads. The order rule, never write before the",
                "    // device has answered, lives in the persistence module.",
                "    var store = createKnownPersistence({",
                "        store: createKnownStore(),",
                "        tell: function (msg) { tellKnown(msg); },",
                "        onProblem: function (broken) {",
                "            if (broken) {",
                "                status.textContent =",
                "                    'Could not read what is saved on this device.';",
                "            }",
                "        }",
                "    });",
                "    if (__knownParty) {",
                "        tellKnown({ kind: 'WhatIsKnown' });",
                "        // Seeding matters here in particular: opened on its own to take a",
                "        // backup, this would otherwise export an empty record from a",
                "        // device that has been read on for months.",
                "        store.start();",
                "    }",
                "",
                "    paint();",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        partyDeregister: function () {",
                "            if (__knownActorId && __knownParty) {",
                "                try { __knownParty.leave(__knownActorId); } catch (e) {}",
                "            }",
                "        }",
                "    };");
    }
}
