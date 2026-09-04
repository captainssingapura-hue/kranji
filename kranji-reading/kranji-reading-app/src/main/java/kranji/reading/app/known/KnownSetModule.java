package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The set a child can read without pinyin.
 *
 * <h2>The key is (character, reading)</h2>
 *
 * <p>Not the character. 行 read xíng may be secure while 行 in 银行 is not, and
 * the entire job of this set is deciding whether to show a reading — so the
 * reading is what has to be known. A character-keyed set would drop the pinyin
 * from 银行 the moment 行走 was learnt, which is the exact failure the adaptive
 * mechanic exists to prevent.</p>
 *
 * <p>It also makes a polyphonic character something a reader can be
 * <em>partly</em> through, which is what learning one actually looks like.</p>
 *
 * <p>A set is a plain array of {@code codePoint:reading} keys, so it serialises
 * unchanged into a Secretary's state and into an export file a family owns.
 * The codepoint rather than the glyph keeps the key fixed-width ASCII and safe
 * across a surrogate pair.</p>
 *
 * <p>Pure — no DOM, no storage, no clock — so it runs under GraalVM in ordinary
 * JUnit. For a module that decides what pinyin a child sees, that is worth more
 * than the indirection costs.</p>
 */
public record KnownSetModule() implements DomModule<KnownSetModule> {

    /** Yields {@code keyOf}, {@code has}, {@code add}, {@code remove}, and the counts. */
    public record createKnownSet() implements Exportable._Constant<KnownSetModule> {}

    public static final KnownSetModule INSTANCE = new KnownSetModule();

    @Override
    public ImportsFor<KnownSetModule> imports() {
        return ImportsFor.<KnownSetModule>builder().build();
    }

    @Override
    public ExportsOf<KnownSetModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownSet()));
    }
}
