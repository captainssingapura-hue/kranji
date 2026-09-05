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
// Import is the only WRITE that does not originate in a click on a reading, so
// it goes through the same party as every other write rather than touching the
// set. Nothing here knows how the set is stored.
// =============================================================================

/**
 * opts = {
 *   branch, css, btnClass, hiddenClass,
 *   transfer,      // createKnownTransfer() - parses and describes
 *   apply,         // fn(keys) -> Promise<addedCount> - puts them on the device
 *   say            // fn(text) - writes the pane's note line
 * }
 * Returns { controls: [element], showUndo(n) }.
 */
function createKnownImportControls(opts) {
    var branch = opts.branch, css = opts.css;
    var transfer = opts.transfer;

    var bring = branch.createElement('bring', 'button');
    css.setClass(bring, opts.btnClass);
    bring.type = 'button';
    bring.textContent = 'Import';

    // Hidden, and driven by the button: a bare file input cannot be labelled
    // in the pane's own voice, and "Choose file / no file chosen" is not what
    // this is asking for.
    var picker = branch.createElement('picker', 'input');
    css.setClass(picker, opts.hiddenClass);
    picker.type = 'file';
    picker.accept = '.txt,text/plain';

    // Shown only when there is an import to take back. A button that is always
    // there but usually does nothing teaches people to ignore it.
    var undo = branch.createElement('undo', 'button');
    css.setClass(undo, opts.hiddenClass);
    undo.type = 'button';

    bring.addEventListener('click', function () { picker.click(); });

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
            return opts.apply(read.keys).then(function (added) {
                opts.say(transfer.describeImport(read, added, file.name));
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
        controls: [bring, picker, undo],

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
