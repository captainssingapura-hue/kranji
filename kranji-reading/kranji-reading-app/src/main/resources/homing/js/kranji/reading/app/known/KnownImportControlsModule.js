// =============================================================================
// KnownImportControlsModule — bringing a record in from a file, and taking it
// back out.
//
// Split from Mark Known because reading a file is not making a claim. The pane
// is about one character's readings laid side by side so that "this one, not
// that one" can be chosen honestly; this is about a text file arriving from a
// parent's downloads folder. They shared a widget only because they share a
// bar, and the widget had grown past the point where both fitted.
//
// Owns its three controls and what they do. It does not own the note line - the
// pane writes there too, and two owners of one element is how a message about a
// failed import gets wiped by an unrelated click.
//
// Import is the only WRITE that does not originate in a click on a reading.
// Nothing here knows how the set is stored, or where: it parses a file, asks
// the pane to apply the keys one way or the other, and reports what came back.
//
// TWO IMPORTS, NOT ONE WITH A SETTING. Merge and replace do different things to
// somebody record and the difference is not recoverable, so it is chosen by
// which button is pressed rather than by a mode that has to be checked first.
// =============================================================================

/**
 * opts = {
 *   branch, css, btnClass, hiddenClass,
 *   transfer,      // createKnownTransfer() - parses and describes
 *   apply,         // fn(keys, mode) -> Promise<addedCount>; mode is
 *                  //   "merge"   - union onto what is there
 *                  //   "replace" - the file becomes the record
 *   say            // fn(text) - writes the pane's note line
 * }
 * Returns { controls: [element], showUndo(n) }.
 */
function createKnownImportControls(opts) {
    var branch = opts.branch, css = opts.css;
    var transfer = opts.transfer;

    // Two imports, not one import with a setting.
    //
    // They do different things to somebody's record and the difference is not
    // recoverable, so it is chosen by which button is pressed rather than by a
    // mode a person has to check before pressing. A control whose meaning
    // depends on a toggle elsewhere is how the wrong one gets pressed.
    //
    // Merge leads because it is the safe one and the common one: restoring a
    // backup onto a device that has been read on since, or adding a term's
    // worth to what is already there.
    var mergeIn = branch.createElement('mergeIn', 'button');
    css.setClass(mergeIn, opts.btnClass);
    mergeIn.type = 'button';
    mergeIn.textContent = 'Import and merge';

    var replaceIn = branch.createElement('replaceIn', 'button');
    css.setClass(replaceIn, opts.btnClass);
    replaceIn.type = 'button';
    replaceIn.textContent = 'Import and replace';

    // Hidden, and driven by the buttons: a bare file input cannot be labelled
    // in the pane's own voice, and "Choose file / no file chosen" is not what
    // this is asking for.
    var picker = branch.createElement('picker', 'input');
    css.setClass(picker, opts.hiddenClass);
    picker.type = 'file';
    picker.accept = '.txt,.tsv,text/plain,text/tab-separated-values';

    // Which button opened the picker. Read when the file arrives, because the
    // choice was made before it was chosen.
    var mode = 'merge';

    // Shown only when there is an import to take back. A button that is always
    // there but usually does nothing teaches people to ignore it.
    var undo = branch.createElement('undo', 'button');
    css.setClass(undo, opts.hiddenClass);
    undo.type = 'button';

    mergeIn.addEventListener("click", function () { mode = "merge"; picker.click(); });
    replaceIn.addEventListener("click", function () { mode = "replace"; picker.click(); });

    picker.addEventListener('change', function () {
        var file = picker.files && picker.files[0];
        if (!file) return;
        opts.say('Reading ' + file.name + '…');
        file.text().then(function (text) {
            var read = transfer.fromText(text);
            if (!read.keys.length) {
                opts.say('Nothing to import from ' + file.name
                    + (read.skipped.length ? ' — ' + read.skipped.length
                        + ' lines could not be read.' : '.'));
                return;
            }
            // The count comes back from whoever applied it, because only
            // they know what was actually new - a file listing readings
            // already claimed adds none, and saying otherwise would be a lie
            // about somebody's record.
            return opts.apply(read.keys, mode).then(function (added) {
                opts.say(transfer.describeImport(read, added, file.name, mode));
            });
        }).catch(function (err) {
            opts.say('Could not read ' + file.name + ': '
                + (err && err.message ? err.message : String(err)));
        }).then(function () {
            // Cleared so that choosing the same file twice fires again - a
            // parent who fixed a line and re-picked the file would otherwise
            // see nothing happen.
            picker.value = '';
        });
    });

    return {
        controls: [mergeIn, replaceIn, picker, undo],

        /**
         * How many readings the last import added, or 0 for none.
         *
         * <p>Driven from outside rather than remembered here: the number lives
         * in the set's own state, and a second copy would go stale the first
         * time the set changed for any other reason.</p>
         */
        showUndo: function (n) {
            if (n) {
                css.setClass(undo, opts.btnClass);
                undo.textContent = 'Undo import (' + n
                    + (n === 1 ? ' reading)' : ' readings)');
            } else {
                css.setClass(undo, opts.hiddenClass);
            }
        },

        /** Wired late, so the click can read the count at the moment it fires. */
        onUndo: function (fn) { undo.addEventListener('click', fn); }
    };
}
