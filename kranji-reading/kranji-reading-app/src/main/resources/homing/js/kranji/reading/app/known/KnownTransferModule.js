// =============================================================================
// KnownTransferModule — the known set as a file a family owns.
//
// Plain text, not an opaque format: a parent should be able to open the file
// and see which readings are claimed. It is their record, and the only copy
// that survives clearing the browser.
//
//   # Kranji reading record
//   # 2 characters, 3 readings
//   床<TAB>chuáng
//   地<TAB>de
//   地<TAB>dì
//
// The glyph, not the codepoint, because the file is for a person. The
// codepoint is recovered on the way back in, so the round trip is exact.
//
// The reading in the form a person reads, for the same reason - this file is
// the one place the set leaves the system, so it crosses the display boundary
// like anything else a person looks at. Coming back in, EITHER form is taken:
// what this wrote, what an older version wrote, and what a parent typed from
// what they saw on screen all mean the same reading.
//
// SORTED, by codepoint then canonical reading. The set itself keeps insertion
// order, but a file does not want that: two exports of nearly the same record
// should differ only where the record differs, so that a diff shows what was
// learnt rather than what order it was clicked in. Sorting before conversion,
// so the order does not depend on where a tone mark sorts.
//
// Pure - no DOM, no file system, no clock. Reading and writing the bytes is
// the widget's business; deciding what the bytes mean is this.
// =============================================================================

/**
 * Returns { toText, fromText }.
 */
function createKnownTransfer() {

    var SEPARATOR = "\t";
    var swf = createPinyinSwf();

    function partsOf(key) {
        var at = key.indexOf(":");
        return { code: Number(key.slice(0, at)), reading: key.slice(at + 1) };
    }

    /**
     * One codepoint, or null.
     *
     * Array.from iterates codepoints rather than UTF-16 units, so a
     * supplementary character counts as the one character it is instead of
     * being rejected as two.
     */
    function singleCodePoint(field) {
        var chars = Array.from(field);
        if (chars.length !== 1) return null;
        return field.codePointAt(0);
    }

    return {

        /** The file body for a set. */
        toText: function (known, characterCount) {
            var rows = [];
            for (var i = 0; i < known.length; i++) {
                var p = partsOf(known[i]);
                rows.push(p);
            }
            rows.sort(function (a, b) {
                if (a.code !== b.code) return a.code - b.code;
                return a.reading < b.reading ? -1 : (a.reading > b.reading ? 1 : 0);
            });

            var chars = typeof characterCount === "number" ? characterCount : null;
            var lines = ["# Kranji reading record"];
            lines.push("# " + (chars === null ? "" : chars
                        + (chars === 1 ? " character, " : " characters, "))
                    + known.length + (known.length === 1 ? " reading" : " readings"));
            for (var j = 0; j < rows.length; j++) {
                lines.push(String.fromCodePoint(rows[j].code) + SEPARATOR
                         + swf.toSWF(rows[j].reading));
            }
            return lines.join("\n") + "\n";
        },

        /**
         * Read a file body back.
         *
         * Returns { keys, skipped } — skipped carries the line number and the
         * text of everything that could not be read. A file is reported on,
         * never silently half-applied: an import that quietly dropped a third
         * of its lines would leave a parent believing a record was restored.
         *
         * Tolerant about whitespace because a parent may well have retyped a
         * line, and a file that rejects two spaces where it wanted a tab is a
         * file that makes them give up.
         */
        fromText: function (text) {
            var keys = [];
            var skipped = [];
            var seen = {};
            var lines = String(text === null || text === undefined ? "" : text).split(/\r?\n/);

            for (var i = 0; i < lines.length; i++) {
                var raw = lines[i];
                var line = raw.replace(/^\s+|\s+$/g, "");
                if (line === "" || line.charAt(0) === "#") continue;

                var at = line.indexOf(SEPARATOR);
                var glyph, reading;
                if (at >= 0) {
                    glyph = line.slice(0, at);
                    reading = line.slice(at + 1).replace(/^\s+|\s+$/g, "");
                } else {
                    var m = line.match(/^(\S+)\s+(\S.*)$/);
                    if (!m) { skipped.push({ line: i + 1, text: raw, why: "no reading" }); continue; }
                    glyph = m[1];
                    reading = m[2].replace(/\s+$/, "");
                }

                var code = singleCodePoint(glyph);
                if (code === null) {
                    skipped.push({ line: i + 1, text: raw, why: "not one character" });
                    continue;
                }
                if (reading === "") {
                    skipped.push({ line: i + 1, text: raw, why: "no reading" });
                    continue;
                }

                // Either form is accepted on the way in. A file exported
                // before the canonical form existed says "chuáng", and so
                // does a line a parent typed from what they saw on screen -
                // neither is a mistake, and both mean chuang2.
                var canonical = swf.canonical(reading);
                if (canonical === null) {
                    skipped.push({ line: i + 1, text: raw, why: "not a reading" });
                    continue;
                }

                var key = code + ":" + canonical;
                if (seen[key]) continue;      // a repeat in the file is not an error
                seen[key] = true;
                keys.push(key);
            }
            return { keys: keys, skipped: skipped };
        },

        /**
         * What to tell the person who chose the file.
         *
         * All three numbers: what the file held, what was new, and what could
         * not be read. A report that gave only its successes would leave a
         * parent believing a record had been restored whole - and the lines it
         * dropped are the ones they would have to retype.
         *
         * Names the first few bad lines rather than counting them, because a
         * count sends somebody hunting through eight hundred lines.
         */
        describeImport: function (read, added, fileName, mode) {
            var held = read.keys.length;
            var says;
            if (mode === "replace") {
                // After a replace, "new readings" is the wrong question and
                // answering it reads like a failure: restoring a backup onto
                // the device it came from is 0 new out of 310, which sounds
                // like nothing happened when in fact the record was rewritten.
                //
                // What the file held is the useful half here. What the record
                // became, and what it cost, belongs to the pane - it knows what
                // was dropped and this does not.
                says = "Read " + held + (held === 1 ? " reading" : " readings")
                     + " from " + fileName + ".";
            } else {
                // Both numbers, because "imported 12" against a file of 800 is
                // the difference between a restore that worked and one that
                // did not.
                says = "Imported " + added
                    + (added === 1 ? " new reading" : " new readings")
                    + " from " + held + " in " + fileName + ".";
            }
            var bad = read.skipped;
            if (!bad.length) return says;

            var named = [];
            for (var i = 0; i < bad.length && i < 3; i++) {
                named.push("line " + bad[i].line + " (" + bad[i].why + ")");
            }
            return says + "  " + bad.length
                + (bad.length === 1 ? " line could not be read: " : " lines could not be read: ")
                + named.join(", ") + (bad.length > 3 ? ", …" : "");
        }
    };
}
