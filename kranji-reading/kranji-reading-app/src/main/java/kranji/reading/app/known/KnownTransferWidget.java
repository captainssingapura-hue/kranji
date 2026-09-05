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
                        List.of(new KnownRecordModule.createKnownRecord()),
                        KnownRecordModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownStoreModule.createKnownStore()),
                        KnownStoreModule.INSTANCE),
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
                "    var transfer = createKnownTransfer();",
                "",
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var bar = branch.createElement('bar', 'div');",
                "    css.setClass(bar, kr_bar);",
                "    root.appendChild(bar);",
                "",
                "",
                "    // Import, its file picker, and Undo. Their own module - see the",
                "    // note there for why reading a file is not this pane's business",
                "    // either, only deciding that it should happen.",
                "    var imports = createKnownImportControls({",
                "        branch: branch, css: css,",
                "        btnClass: kr_btn, hiddenClass: kr_kn_hidden,",
                "        transfer: transfer,",
                "        // Import is not connected either, and refuses rather than",
                "        // posting a message nothing carries and nothing handles. A file",
                "        // read successfully and then dropped in silence is the worst",
                "        // outcome available here: the parent believes the record moved.",
                "        tell: function (msg) {",
                "            if (msg && msg.kind === 'ImportKnown') {",
                "                note.textContent = 'Import is not available yet: the file "
                        + "was read, but nothing on this device would accept it. Nothing "
                        + "has changed.';",
                "                return;",
                "            }",
                "            tellKnown(msg);",
                "        },",
                "        knownCount: function () { return 0; },",
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
                "    var __knownParty = (workspaceCtx && workspaceCtx.knownEventParty)",
                "                     ? workspaceCtx.knownEventParty : null;",
                "    var __knownActorId = null;",
                "",
                "    function tellKnown(msg) {",
                "        if (__knownParty && __knownActorId) {",
                "            __knownParty.tellFrom(__knownActorId, msg);",
                "        }",
                "    }",
                "",
                "    // The device, asked directly, at the moment somebody asks.",
                "    //",
                "    // This pane used to keep a mirror of the set all session, fed by",
                "    // every change, so that it could answer a question asked twice a",
                "    // year. Export is a read: it wants the record once, when the button",
                "    // is pressed. Reading the durable copy is also the more truthful",
                "    // answer for a backup - what a family gets in a file is what is",
                "    // actually saved, not what is in memory on the way there.",
                "    var store = createKnownStore();",
                "",
                "    function readRecord() {",
                "        return store.load(null).then(function (row) {",
                "            var keys = (row && row.known) || [];",
                "            return { keys: keys, record: createKnownRecord(keys),",
                "                     lastImport: (row && row.lastImport) || [] };",
                "        });",
                "    }",
                "",
                "    function paint() {",
                "        readRecord().then(function (held) {",
                "            var readings = held.keys.length;",
                "            var chars = held.record.characters().length;",
                "            tally.textContent = readings === 0 ? 'Nothing marked yet'",
                "                : chars + (chars === 1 ? ' character' : ' characters')",
                "                  + ', ' + readings + (readings === 1 ? ' reading' : ' readings');",
                "            imports.showUndo(held.lastImport.length);",
                "        }, function () {",
                "            tally.textContent = 'Could not read the record on this device.';",
                "        });",
                "    }",
                "",
                "    // The file the record leaves as. Tab-separated, because the format",
                "    // already is - glyph, tab, reading - and naming it .tsv is the",
                "    // difference between a file a spreadsheet opens and one it asks",
                "    // questions about. Dated, so a parent keeping several can tell which",
                "    // is the recent one without opening any of them.",
                "    function fileName() {",
                "        var d = new Date();",
                "        function two(n) { return (n < 10 ? '0' : '') + n; }",
                "        return 'kranji-reading-record-' + d.getFullYear()",
                "             + '-' + two(d.getMonth() + 1) + '-' + two(d.getDate()) + '.tsv';",
                "    }",
                "",
                "    function offerFile(text) {",
                "        if (typeof Blob !== 'function' || !URL || !URL.createObjectURL) return false;",
                "        try {",
                "            var blob = new Blob([text], "
                        + "{ type: 'text/tab-separated-values;charset=utf-8' });",
                "            var url = URL.createObjectURL(blob);",
                "            var a = branch.createElement('download', 'a');",
                "            a.href = url;",
                "            a.download = fileName();",
                "            a.click();",
                "            // Held briefly rather than revoked on the next line: a revoke",
                "            // that lands before the browser has read the blob cancels the",
                "            // download, and whether it has read it yet is not observable.",
                "            setTimeout(function () {",
                "                try { URL.revokeObjectURL(url); } catch (e) {}",
                "            }, 60000);",
                "            return true;",
                "        } catch (e) {",
                "            return false;",
                "        }",
                "    }",
                "",
                "    // Downloaded, and also shown.",
                "    //",
                "    // This was clipboard-only, because a download needs an anchor's href",
                "    // and the href manager is injected solely into modules importing an",
                "    // AppLink - there being no AppLink that means 'save a file the page",
                "    // just made'. That reasoning was about the wrong thing. A blob URL is",
                "    // not a link to anywhere in this app; it is a handle to bytes the page",
                "    // is already holding, alive for one click. So it goes through the rule",
                "    // as a named allowance carrying that reason, rather than by importing",
                "    // an unrelated app's link to reach a manager it has no use for.",
                "    //",
                "    // The anchor is built through the branch like every other element, so",
                "    // the href is the only exceptional line.",
                "    //",
                "    // Both paths run. A download can be refused - by policy, or by a",
                "    // browser that wants a gesture it decides it did not get - and it is",
                "    // refused silently. The text stays on screen and on the clipboard, so",
                "    // a parent who got no file still has their record.",
                "    save.addEventListener('click', function () {",
                "      readRecord().then(function (held) {",
                "        if (!held.keys.length) {",
                "            status.textContent = 'Nothing is saved on this device yet.';",
                "            return;",
                "        }",
                "        var text = transfer.toText(held.keys, held.record.characters().length);",
                "        sheet.value = text;",
                "        css.setClass(sheet, kr_kn_text);",
                "        sheet.select();",
                "        var downloaded = offerFile(text);",
                "        var says = held.keys.length",
                "            + (held.keys.length === 1 ? ' reading' : ' readings');",
                "        // Offered, not saved - and the wording says so.",
                "        //",
                "        // Whether the file arrived is not observable from here: a browser",
                "        // that silently refuses a download throws nothing, so the only",
                "        // honest claim is that it was asked for. Telling a parent their",
                "        // record was saved when it may not have been is how this pane",
                "        // already went wrong once, and the text below is the copy that",
                "        // does not depend on the answer.",
                "        function shown(how) {",
                "            status.textContent = says + ' ' + how",
                "                + (downloaded",
                "                    ? ' Offered as ' + fileName()",
                "                      + ' - if no file arrived, the text below is the copy.'",
                "                    : ' No file could be offered here, so the text below is"
                        + " the copy.')",
                "                + ' Keep it somewhere safe -'",
                "                + ' this is the only copy that survives clearing the browser.';",
                "        }",
                "        if (navigator.clipboard && navigator.clipboard.writeText) {",
                "            navigator.clipboard.writeText(text)",
                "                .then(function () { shown('copied.'); },",
                "                      function () { shown('ready, and selected below.'); });",
                "        } else {",
                "            shown('ready, and selected below.');",
                "        }",
                "      }, function () {",
                "        // Loud. An export that quietly produces nothing is worse than",
                "        // one that fails, because a family keeps the empty file.",
                "        status.textContent = 'Could not read the record on this device, "
                        + "so there is nothing to export. Do not treat this as an empty "
                        + "record.';",
                "      });",
                "    });",
                "",
                "    // Undo is not connected, and says so rather than appearing to work.",
                "    //",
                "    // UndoImport has no carrier and no handler: the write channel relays",
                "    // MarkKnown and UnmarkKnown, and the service answers those and the",
                "    // snapshot request. The batch it would take back is gone too - the",
                "    // service saves no lastImport. Both come back together or not at all.",
                "    imports.onUndo(function () {",
                "        note.textContent = 'Undo is not available: the record no longer "
                        + "remembers which readings an import added.';",
                "    });",
                "",
                "    // Listening only to know when the tally is stale. It carries no set,",
                "    // holds no mirror, and re-reads the device rather than accumulating",
                "    // its own idea of what is on it.",
                "    if (__knownParty) {",
                "        __knownActorId = 'known/transfer-' + Math.random().toString(36).slice(2, 8);",
                "        __knownParty.joinActor({",
                "            id: __knownActorId,",
                "            parentSecretary: 'knownEvents',",
                "            reactors: {",
                "                KnownSnapshot: function () { paint(); },",
                "                KnownAdded:    function () { paint(); },",
                "                KnownRemoved:  function () { paint(); }",
                "            }",
                "        });",
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
