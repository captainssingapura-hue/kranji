package kranji.simple.gloss;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * One character, every way it is read, and what each way means.
 *
 * <pre>
 * ZiCharUTF8
 *   └─ SoundGloss (reading)
 *        └─ Meaning (ranked)
 *             └─ Example
 * </pre>
 *
 * <p><b>Keyed on the codepoint, which is the hub.</b> An earlier draft filed
 * glosses under the reading's initial, so 得 spanned three files and 行 two.
 * That was defensible — they are separate facts — and it was awful to author,
 * because writing one character meant opening three files and no file showed a
 * character whole. CD-001 already says the codepoint is what everything joins
 * on; the layout now agrees with it.</p>
 *
 * <p>Nothing here depends on <em>which</em> partition an entry was declared in,
 * so the source can be split more finely as it grows without touching the
 * model. A handful of files suffices for a few hundred entries; several
 * thousand will want many more.</p>
 */
public record ZiGloss(ZiCharUTF8 zi, List<SoundGloss> sounds) implements ValueObject {

    public ZiGloss {
        Objects.requireNonNull(zi, "zi");
        sounds = List.copyOf(Objects.requireNonNull(sounds, "sounds"));

        if (sounds.isEmpty()) {
            throw new IllegalArgumentException(
                    zi + " has no readings - omit the entry rather than record an empty one");
        }
        for (SoundGloss sound : sounds) {
            if (!sound.zi().equals(zi)) {
                throw new IllegalArgumentException(
                        "entry for " + zi + " carries a reading belonging to " + sound.zi());
            }
        }
        var seen = new ArrayList<PinyinSyllable>();
        for (SoundGloss sound : sounds) {
            if (seen.contains(sound.reading())) {
                throw new IllegalArgumentException(
                        zi + " declares " + sound.reading().toDiacritic() + " twice");
            }
            seen.add(sound.reading());
        }
    }

    /** What this character means read this way, if that reading is glossed. */
    public Optional<SoundGloss> at(PinyinSyllable reading) {
        return sounds.stream().filter(s -> s.reading().equals(reading)).findFirst();
    }

    /** Every phrase this entry references, across every reading and sense. */
    public List<EgRef> examples() {
        var out = new ArrayList<EgRef>();
        for (SoundGloss sound : sounds) {
            for (Sense sense : sound.senses().values()) out.addAll(sense.orderedExamples());
        }
        return List.copyOf(out);
    }
}
