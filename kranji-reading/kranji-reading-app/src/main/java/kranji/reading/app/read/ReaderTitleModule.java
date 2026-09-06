package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The article's title, read the same way its body is.
 *
 * <h2>Why the title needed this at all</h2>
 *
 * <p>It was the one line of Chinese in the pane with no reading over it. A
 * child who needs pinyin for a character in the first sentence needs it for one
 * in the title, and the title is what they meet first — so the pane opened with
 * its least readable line at the top.</p>
 *
 * <h2>Ruby rather than squares, and rather than brackets</h2>
 *
 * <p>The body is a practice grid because that is how the characters are
 * written out. A title is a name and is read as one phrase, so it flows.</p>
 *
 * <p>A bracketed reading after each character would be longer than the name
 * and would stop it being one. Over the character, the reading takes no width
 * at all — which is also what lets it be <em>per character</em>: the reading
 * sits on the character it belongs to, without anybody having had to decide
 * which characters make a word.</p>
 *
 * <h2>Adaptive on the caller's terms</h2>
 *
 * <p>Whether a reading shows is asked, never decided here — the same question
 * the squares ask, of the same known set, so the title and the article cannot
 * disagree about what this reader knows. Hiding is by visibility, so a reading
 * leaving does not move the title: the page must not shift under a child as a
 * reward for having learnt something.</p>
 *
 * <p>No CJK literal appears in this file. Titles arrive from the corpus.</p>
 */
public record ReaderTitleModule() implements DomModule<ReaderTitleModule> {

    /** Yields {@code show} and {@code restyle}. */
    public record createReaderTitle() implements Exportable._Constant<ReaderTitleModule> {}

    public static final ReaderTitleModule INSTANCE = new ReaderTitleModule();

    @Override
    public ImportsFor<ReaderTitleModule> imports() {
        return ImportsFor.<ReaderTitleModule>builder().build();
    }

    @Override
    public ExportsOf<ReaderTitleModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createReaderTitle()));
    }
}
