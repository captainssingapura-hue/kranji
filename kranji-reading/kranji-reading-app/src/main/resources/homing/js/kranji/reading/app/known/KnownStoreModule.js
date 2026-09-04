// =============================================================================
// KnownStoreModule — the known set, on the device.
//
// IndexedDB rather than localStorage: a set of a few thousand readings plus
// whatever comes later outgrows the localStorage budget, and IDB is already the
// framework's substrate for checkpoint and catalogue storage.
//
// Nothing leaves the device. A record of which readings a specific child finds
// difficult is exactly the kind of data that should not accumulate on somebody
// else's computer, so there is no account, no sync, and nothing transmitted.
//
// Schema:
//   database     : "kranji.known"
//   object store : "sets"
//                  primary key: profile
//                  each row: { profile, known: ["34892:xíng", ...] }
//
// ONE ROW PER PROFILE, holding the whole set. The party already broadcasts the
// set whole rather than as a delta, so storing it whole keeps the two in step -
// what is written is exactly what was published. It also makes a save atomic
// and idempotent, which is what lets any pane perform one without coordinating
// with the others.
//
// The profile key is here from the start although there is only ever
// "default" today. Several children per device is a stated requirement, and a
// key added later is a migration, whereas a key with one value is a word.
//
// NO TIMESTAMPS. Not an omission: the design has no scheduling, no decay and no
// streaks, and storing a time nothing reads invites the feature that reads it.
//
// Class form per RFC 0035 P2 - constants are static class fields, no top-level
// script-scope pollution.
// =============================================================================

class KnownStore {

    static DB_NAME         = "kranji.known";
    static STORE_NAME      = "sets";
    static DB_VERSION      = 1;
    static DEFAULT_PROFILE = "default";

    constructor() {
        this._dbPromise = null;
    }

    /**
     * Is this a well-formed key?
     *
     * codePoint:reading, the codepoint in decimal. Guards the boundary in both
     * directions - a malformed key reaching the store would be written and
     * read back forever, and one arriving FROM the store (a hand-edited
     * import, a half-written row) would poison the membership test silently,
     * because a key that matches nothing simply withholds support.
     */
    static isKey(k) {
        if (typeof k !== "string") return false;
        var at = k.indexOf(":");
        if (at <= 0 || at === k.length - 1) return false;
        for (var i = 0; i < at; i++) {
            if (k[i] < "0" || k[i] > "9") return false;
        }
        return true;
    }

    /** Lazy-opened singleton IDB connection. */
    _db() {
        if (this._dbPromise === null) this._dbPromise = this._openDb();
        return this._dbPromise;
    }

    _openDb() {
        return new Promise((resolve, reject) => {
            if (typeof indexedDB === "undefined") {
                reject(new Error("KnownStore: IndexedDB not available"));
                return;
            }
            const req = indexedDB.open(KnownStore.DB_NAME, KnownStore.DB_VERSION);
            req.onupgradeneeded = (e) => {
                const db = e.target.result;
                if (!db.objectStoreNames.contains(KnownStore.STORE_NAME)) {
                    db.createObjectStore(KnownStore.STORE_NAME, { keyPath: "profile" });
                }
            };
            req.onsuccess = (e) => resolve(e.target.result);
            req.onerror   = (e) => reject(e.target.error);
        });
    }

    /** Run `fn(store)` in a transaction of the named mode. */
    _tx(mode, fn) {
        return this._db().then(db => new Promise((resolve, reject) => {
            const tx    = db.transaction(KnownStore.STORE_NAME, mode);
            const store = tx.objectStore(KnownStore.STORE_NAME);
            const req   = fn(store);
            req.onsuccess = () => resolve(req.result);
            req.onerror   = () => reject(req.error);
        }));
    }

    static _clean(list) {
        return Array.isArray(list) ? list.filter(KnownStore.isKey) : [];
    }

    /**
     * { known, lastImport } for a profile; both empty if this device has never
     * held a set.
     *
     * Malformed keys are dropped rather than thrown on. A row that has been
     * hand-edited or half-written should cost the reader the marks it damaged,
     * not the marks it did not.
     */
    load(profile) {
        const p = profile || KnownStore.DEFAULT_PROFILE;
        return this._tx("readonly", store => store.get(p)).then(row => {
            if (!row) return { known: [], lastImport: [] };
            return {
                known:      KnownStore._clean(row.known),
                lastImport: KnownStore._clean(row.lastImport)
            };
        });
    }

    /**
     * Replace the profile's set. Rejects rather than writing a bad key.
     *
     * lastImport rides along because the realisation that an import claimed too
     * much arrives days later, not in the same session - an undo that died with
     * the workspace would be an undo nobody could reach in time.
     */
    save(profile, known, lastImport) {
        const p = profile || KnownStore.DEFAULT_PROFILE;
        if (!Array.isArray(known)) {
            return Promise.reject(new TypeError("KnownStore.save: known must be an array"));
        }
        for (let i = 0; i < known.length; i++) {
            if (!KnownStore.isKey(known[i])) {
                return Promise.reject(
                        new TypeError("KnownStore.save: malformed key " + known[i]));
            }
        }
        return this._tx("readwrite", store => store.put({
            profile:    p,
            known:      known.slice(),
            lastImport: KnownStore._clean(lastImport)
        })).then(() => undefined);
    }

    /** Forget a profile entirely. */
    clear(profile) {
        const p = profile || KnownStore.DEFAULT_PROFILE;
        return this._tx("readwrite", store => store.delete(p)).then(() => undefined);
    }
}

/** Factory — matches the createX() naming convention in adjacent modules. */
function createKnownStore() {
    return new KnownStore();
}
