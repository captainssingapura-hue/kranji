package kranji.simple;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.pinyin.Final;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tone;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A character and how it is said.
 *
 * <p>The broad tier of a two-tier corpus. The structural records model
 * composition, etymology, strokes, and layout for a curated set; this record
 * carries readings for everything a reader might meet. It costs minutes rather
 * than hours to add, so it can cover far more ground.</p>
 *
 * <h2>No meaning here</h2>
 *
 * <p>Deliberately absent, and not merely because a gloss is richer than a
 * string. Meaning attaches to a <em>reading</em>, not to a character: 好 hǎo is
 * <i>good</i> while 好 hào is <i>to be fond of</i>; 行 xíng is <i>to go</i>
 * while 行 háng is <i>a row</i>. A per-character field sits at the wrong
 * cardinality and can only be filled by merging senses that belong to
 * different readings.</p>
 *
 * <p>Meaning is also language-dependent, audience-dependent, and licensed
 * separately from phonic data. It belongs to a facility keyed on
 * {@code (character, phonic)}, supplied from outside this type.</p>
 *
 * <h2>Joining the tiers</h2>
 *
 * <p>The two tiers meet on {@link #glyph()} rather than by reference, so
 * coverage improves on its own: adding a structural record for a character
 * that already has a {@code SimpleZi} enriches it with neither side edited.
 * Nothing here points at the structural hierarchy, which is what lets this
 * type sit below it.</p>
 *
 * @param glyph             the character
 * @param defaultPhonic     the reading shown when nothing overrides it
 * @param additionalPhonics further readings; empty for most characters
 */
public record SimpleZi(
        ZiCharUTF8 glyph,
        PinyinSyllable defaultPhonic,
        List<PinyinSyllable> additionalPhonics
) implements ValueObject {

    public SimpleZi {
        Objects.requireNonNull(glyph, "glyph");
        Objects.requireNonNull(defaultPhonic, "defaultPhonic");
        additionalPhonics = List.copyOf(Objects.requireNonNull(additionalPhonics, "additionalPhonics"));

        if (additionalPhonics.contains(defaultPhonic)) {
            throw new IllegalArgumentException(
                    glyph + " repeats its default phonic among the additional ones");
        }
        if (additionalPhonics.size() != additionalPhonics.stream().distinct().count()) {
            throw new IllegalArgumentException(glyph + " has duplicate additional phonics");
        }
    }

    /**
     * A character with more than one reading — 多音字. The project's definition
     * of polyphony: article preparation derives its review list from exactly
     * this, so a character comes under that scrutiny the moment a second
     * reading is recorded for it.
     */
    public boolean isPolyphonic() {
        return !additionalPhonics.isEmpty();
    }

    /** Every reading, default first. */
    public List<PinyinSyllable> allPhonics() {
        var all = new ArrayList<PinyinSyllable>(additionalPhonics.size() + 1);
        all.add(defaultPhonic);
        all.addAll(additionalPhonics);
        return List.copyOf(all);
    }

    /** The default reading with its tone mark, e.g. {@code háng}. */
    public String defaultReading() {
        return defaultPhonic.toDiacritic();
    }

    /** The initial of the default reading — the character's home partition. */
    public Initial partitionInitial() {
        return defaultPhonic.initial();
    }

    /** This character with one further reading appended. */
    public SimpleZi also(Initial initial, Final fin, Tone tone) {
        return also(new PinyinSyllable(initial, fin, tone));
    }

    /** This character with one further reading appended. */
    public SimpleZi also(PinyinSyllable phonic) {
        Objects.requireNonNull(phonic, "phonic");
        var more = new ArrayList<>(additionalPhonics);
        more.add(phonic);
        return new SimpleZi(glyph, defaultPhonic, more);
    }
}
