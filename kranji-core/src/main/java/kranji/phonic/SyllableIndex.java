package kranji.phonic;

import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Sound to characters, built from the source partitions at first use.
 *
 * <p>{@link PinyinSyllable} is a record of {@code (Initial, Final, Tone)} and
 * {@code Final} is itself {@code (Head, Body, Tail)} — enums all the way
 * down — so it is a sound map key with structural equality. That is what
 * makes this an inversion rather than a search: the readings are already
 * parsed, and grouping them by syllable is the whole of the work.</p>
 *
 * <p>Measured at 83 ms for the full standard set: 8,100 characters, 8,759
 * appearances, 1,284 distinct syllables. Paid once, on first access.</p>
 *
 * <h2>Why a character appears more than once</h2>
 *
 * <p>A polyphonic character is listed under every reading it has, with no
 * special handling anywhere — 好 under hǎo and hào, 行 under xíng and háng
 * even though those fall in different partitions. Sound and shape are
 * independent, so the inversion simply visits every reading.</p>
 *
 * <p>Per CD-001 this owns no data. {@link ZiCharUTF8} is the identity; this
 * is a secondary index over it.</p>
 */
public final class SyllableIndex {

    private final Map<PinyinSyllable, List<ZiCharUTF8>> bySyllable;
    private final Map<ZiCharUTF8, SourceReadings> byCharacter;

    private SyllableIndex(Map<PinyinSyllable, List<ZiCharUTF8>> bySyllable,
                          Map<ZiCharUTF8, SourceReadings> byCharacter) {
        this.bySyllable = bySyllable;
        this.byCharacter = byCharacter;
    }

    /** Holder idiom: the 83 ms is paid on first access, once, without locking. */
    private static final class Holder {
        private static final SyllableIndex INSTANCE = build(PhonicPartitions.loadAll());
    }

    public static SyllableIndex instance() { return Holder.INSTANCE; }

    /**
     * Builds an index over any set of readings.
     *
     * <p>Takes its input rather than loading it, so a test can index a handful
     * of characters without the full set and a future override layer can index
     * a corrected one.</p>
     */
    public static SyllableIndex build(List<SourceReadings> rows) {
        Map<PinyinSyllable, List<ZiCharUTF8>> bySyllable = new LinkedHashMap<>();
        Map<ZiCharUTF8, SourceReadings> byCharacter = new LinkedHashMap<>();

        for (SourceReadings row : rows) {
            byCharacter.putIfAbsent(row.zi(), row);
            for (PinyinSyllable syllable : row.all()) {
                List<ZiCharUTF8> chars =
                        bySyllable.computeIfAbsent(syllable, k -> new ArrayList<>());
                if (!chars.contains(row.zi())) chars.add(row.zi());
            }
        }
        bySyllable.replaceAll((k, v) -> List.copyOf(v));
        return new SyllableIndex(Map.copyOf(bySyllable), Map.copyOf(byCharacter));
    }

    // ── Lookups ────────────────────────────────────────────────────────

    /** The characters read as this syllable, empty when nothing is. */
    public List<ZiCharUTF8> charactersOf(PinyinSyllable syllable) {
        return bySyllable.getOrDefault(syllable, List.of());
    }

    /** Everything the source says about one character. */
    public Optional<SourceReadings> readingsOf(ZiCharUTF8 zi) {
        return Optional.ofNullable(byCharacter.get(zi));
    }

    /** True when this character is principally read as this syllable. */
    public boolean isPrincipalAt(ZiCharUTF8 zi, PinyinSyllable syllable) {
        return readingsOf(zi).map(r -> r.principal().equals(syllable)).orElse(false);
    }

    /** Every syllable some character is read as. */
    public Set<PinyinSyllable> syllables() { return bySyllable.keySet(); }

    /** The syllables under one initial, in no particular order. */
    public List<PinyinSyllable> syllablesOf(Initial initial) {
        return bySyllable.keySet().stream()
                .filter(s -> s.initial() == initial)
                .toList();
    }

    /** Distinct characters indexed. */
    public int characterCount() { return byCharacter.size(); }

    /** Appearances — always at least {@link #characterCount()}, more when polyphony exists. */
    public int appearanceCount() {
        return bySyllable.values().stream().mapToInt(List::size).sum();
    }
}
