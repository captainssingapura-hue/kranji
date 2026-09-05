// =============================================================================
// KnownRecordModule — the known set, held as something you can ask.
//
// The set used to be a plain array, and `has` was indexOf. That is the fault
// underneath most of the update path: with no membership structure, every
// consumer that needed one built its own - readability per article, the
// toggle before it sent, the Secretary again to apply it. One record, asked
// rather than scanned, removes all of them at once.
//
// Two structures, deliberately. The map answers `has` in constant time; the
// array remembers insertion order, which is what makes an export file
// diffable and a list of claims read chronologically. Removal splices the
// array, which is linear - and stays linear, because a reader removes a claim
// far less often than the app asks whether one exists.
//
// The key is (character, reading). See KnownSetModule for why, and for keyOf,
// which builds it. This module never parses a key except to split off the
// codepoint for a character count.
//
// Pure - no DOM, no storage, no clock, no party.
// =============================================================================

/**
 * A record of claimed (character, reading) keys.
 *
 * `seed` is optional and is a union, never a replace, for the same reason the
 * Secretary's seed was: a record that arrives late - a slow disk, a service
 * that started after marking began - must not take back a claim made in the
 * meantime.
 *
 * Returns { has, add, remove, seed, keys, size, characters }.
 */
function createKnownRecord(seed) {

    var claimed = Object.create(null);
    var order   = [];

    /** True if this key was not already held. The answer callers act on. */
    function add(key) {
        if (!key || claimed[key]) return false;
        claimed[key] = true;
        order.push(key);
        return true;
    }

    /** True if this key was held. False means the caller asked for a no-op. */
    function remove(key) {
        if (!key || !claimed[key]) return false;
        delete claimed[key];
        var at = order.indexOf(key);
        if (at >= 0) order.splice(at, 1);
        return true;
    }

    function union(keys) {
        var added = 0;
        var list = keys || [];
        for (var i = 0; i < list.length; i++) if (add(list[i])) added++;
        return added;
    }

    union(seed);

    return {

        /** Constant time. This is the whole reason the module exists. */
        has: function (key) { return !!key && claimed[key] === true; },

        add:    add,
        remove: remove,

        /** A union, never a replace. Returns how many keys were new. */
        seed: union,

        /**
         * A copy, in insertion order. A copy because a caller that could
         * mutate this would be a second owner of the set, which is the thing
         * being removed.
         */
        keys: function () { return order.slice(); },

        size: function () { return order.length; },

        /**
         * Distinct characters, as codepoints.
         *
         * A character counts once however many of its readings are claimed,
         * which is why this is not size() and why "characters known" and
         * "readings known" must never be shown as the same number.
         */
        characters: function () {
            var seen = Object.create(null);
            var out  = [];
            for (var i = 0; i < order.length; i++) {
                var cp = order[i].slice(0, order[i].indexOf(':'));
                if (!seen[cp]) { seen[cp] = true; out.push(Number(cp)); }
            }
            return out;
        }
    };
}
