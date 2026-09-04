package kranji.reading.app.ui;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The display boundary for readings.
 *
 * <p>Everything below the display layer carries the canonical form — standard
 * written spelling plus a tone digit, {@code dong1} / {@code nü3} /
 * {@code de0}. That form is ASCII apart from {@code ü}, so it has no combining
 * marks, no two byte sequences that look alike and compare unequal, and nothing
 * to normalise — which is what lets one key be shared by IndexedDB, a JS module
 * and a Java registry without any of them disagreeing.</p>
 *
 * <p>A child reads <b>dōng</b>, not {@code dong1}. This module is the only
 * place that conversion happens, and past it the diacritic form must not be
 * compared, stored, or keyed on.</p>
 *
 * <p>Tested against a written spec rather than against
 * {@code PinyinSyllable.toDiacritic()}: two implementations agreeing would show
 * that they agree, not that either is right. The rule is small and fixed — the
 * mark goes on {@code a} if present, else {@code o} or {@code e}, else the last
 * vowel — so it can be stated and checked directly.</p>
 */
public record PinyinSwfModule() implements DomModule<PinyinSwfModule> {

    /** Yields {@code toSWF} and {@code isCanonical}. */
    public record createPinyinSwf() implements Exportable._Constant<PinyinSwfModule> {}

    public static final PinyinSwfModule INSTANCE = new PinyinSwfModule();

    @Override
    public ImportsFor<PinyinSwfModule> imports() {
        return ImportsFor.<PinyinSwfModule>builder().build();
    }

    @Override
    public ExportsOf<PinyinSwfModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createPinyinSwf()));
    }
}
