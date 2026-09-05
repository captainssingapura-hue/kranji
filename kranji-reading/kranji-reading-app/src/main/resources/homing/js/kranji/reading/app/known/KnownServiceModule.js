// =============================================================================
// KnownServiceModule — the one owner of the known set.
//
// It holds the record, it holds the device, and it holds the failure. Nothing
// else may hold any of the three. Before this existed the set lived in a
// message bus, seven panes each started their own persistence engine, two of
// them happened to save, and whether a claim survived the session depended on
// which panes the reader had open.
//
//   A WRITE IS A CALL. A READ IS A RETURN.
//
// TWO CHANNELS, ONE DIRECTION EACH. Requests arrive on the write channel;
// facts go out on the read channel. Nothing a pane sends comes back to it, so
// "did I ask?" and "did it work?" can never be confused - and a pane that
// never joins the write channel cannot change the set, by wiring rather than
// by agreement.
//
//   in  (write channel) : { kind: "MarkKnown",   key: "34892:xing2" }
//                         { kind: "UnmarkKnown", key: "34892:xing2" }
//   in  (read channel)  : { kind: "WhatIsKnown" }    // a pane that just joined
//
//   out (read channel)  : { kind: "KnownSnapshot", keys: [...] }
//                         { kind: "KnownAdded",   key }
//                         { kind: "KnownRemoved", key }
//
// News names one key. Shipping the whole set on every change was how a
// two-character edit came to cost two thousand strings, six times over; the
// snapshot exists for the one case that genuinely needs the lot, which is a
// pane that has only just arrived.
//
// NOTHING IS WRITTEN UNTIL THE DEVICE HAS ANSWERED. The rule survives from
// KnownPersistence, because the mistake it prevents is unchanged: writing
// before the load returns erases the record on every visit, silently, in a way
// only the next visit can reveal. A claim made while the disk is still being
// read is held, applied to the record so the reader sees it, and written the
// moment writing is safe.
//
// No DOM and no globals - the store, the party and the status line arrive as
// arguments, so this runs under GraalVM against fakes.
// =============================================================================

/**
 * deps: {
 *     store   : { load(profile) -> Promise<{known, lastImport}>,
 *                 save(profile, known, lastImport) -> Promise },
 *     publish : function (message)          // onto the READ channel
 *     onStatus: function (status)           // { size, loaded, broken, says }
 * }
 *
 * Returns { start, handle, has, size, keys, status }.
 */
function createKnownService(deps) {

    var record  = createKnownRecord();
    var loaded  = false;
    var broken  = false;
    var dirty   = false;      // a change is waiting for the device to answer
    var says    = 'Reading the record...';

    function status() {
        return { size: record.size(), loaded: loaded, broken: broken, says: says };
    }

    function announce(text) {
        says = text;
        if (deps.onStatus) deps.onStatus(status());
    }

    /**
     * A failed save is announced unprompted, always.
     *
     * Marking looks identical whether or not it persisted, so a reader given
     * no warning discovers the loss a week later, with no way to tell which
     * marks survived. The watch used to start persistence with an empty
     * handler; that is why this one is not optional and takes no callback to
     * suppress it.
     */
    function wrote(ok) {
        broken = !ok;
        announce(ok ? said(record.size())
                    : 'The record could not be saved to this device. '
                    + 'Marks made now may not survive closing the page.');
    }

    function said(n) {
        return n === 1 ? '1 reading in the record' : n + ' readings in the record';
    }

    /** Write the record, if there is anything to write and it is safe to. */
    function flush() {
        if (!loaded || !dirty) return Promise.resolve();
        dirty = false;
        return deps.store.save(null, record.keys(), [])
                .then(function () { wrote(true); },
                      function () { wrote(false); });
    }

    return {

        /**
         * Read the device, seed the record, and tell the party what is in it.
         *
         * Seeding is a union, so a claim made in the moments before the disk
         * answered is kept rather than overwritten - and the snapshot that
         * goes out afterwards carries both.
         */
        start: function () {
            return deps.store.load(null).then(function (stored) {
                record.seed((stored && stored.known) || []);
                loaded = true;
                wrote(true);
                deps.publish({ kind: 'KnownSnapshot', keys: record.keys() });
                return flush();
            }, function () {
                // Reading failed, so there is nothing to lose by writing - and
                // if writing fails too, the reader is told.
                loaded = true;
                broken = true;
                announce('This device would not give up the record. '
                       + 'Marks made now may not survive closing the page.');
                deps.publish({ kind: 'KnownSnapshot', keys: record.keys() });
                return flush();
            });
        },

        /**
         * The only way the set changes.
         *
         * Idempotent both ways: claiming twice is not an error and says
         * nothing, and neither is giving back what was never held. Marking is
         * meant to be cheap to do and cheap to undo.
         */
        handle: function (msg) {
            if (!msg) return Promise.resolve();

            if (msg.kind === 'WhatIsKnown') {
                deps.publish({ kind: 'KnownSnapshot', keys: record.keys() });
                return Promise.resolve();
            }

            var claiming = msg.kind === 'MarkKnown';
            if (!claiming && msg.kind !== 'UnmarkKnown') return Promise.resolve();
            if (!msg.key) return Promise.resolve();

            // A request that changes nothing produces no fact. Claiming twice
            // is not an error and is not news; neither is giving back what was
            // never held. This is where idempotence belongs - at the owner,
            // where the truth is - so a pane with a stale mirror costs one
            // wasted message and never a wrong set.
            var moved = claiming ? record.add(msg.key) : record.remove(msg.key);
            if (!moved) return Promise.resolve();

            deps.publish({ kind: claiming ? 'KnownAdded' : 'KnownRemoved',
                           key: msg.key });
            dirty = true;
            return flush();
        },

        has:    function (key) { return record.has(key); },
        size:   function ()    { return record.size(); },
        keys:   function ()    { return record.keys(); },
        status: status
    };
}
