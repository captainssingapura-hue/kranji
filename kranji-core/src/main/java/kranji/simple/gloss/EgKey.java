package kranji.simple.gloss;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Chinese phrase, as identity and nothing else.
 *
 * <p>An earlier design made the example itself the map key while it also
 * carried its English gloss, so identity was <i>"this phrase glossed this
 * way"</i> rather than <i>"this phrase"</i>. Two glosses of 很好 were two
 * distinct keys, which meant the container was happy and a hand-written check
 * had to catch what the container should have. Splitting the key from the
 * content removes the check rather than fixing it.</p>
 *
 * <p>A sequence of {@link ZiCharUTF8} in final form — not a String — because
 * that is what a phrase <em>is</em> here, and because it lets a build assert
 * that an example contains the character it is filed under without parsing
 * anything back out.</p>
 */
public record EgKey(List<ZiCharUTF8> characters) implements ValueObject {

    /** Longer than this and it is a sentence to study, not an example to glance at. */
    public static final int MAX_LENGTH = 12;

    public EgKey {
        characters = List.copyOf(Objects.requireNonNull(characters, "characters"));
        if (characters.isEmpty()) {
            throw new IllegalArgumentException("an empty phrase shows nothing");
        }
        if (characters.size() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "a phrase of " + characters.size() + " characters is longer than "
                  + MAX_LENGTH + "; an example is glanced at, not studied");
        }
    }

    /**
     * A phrase written as it reads.
     *
     * <p>Every codepoint must be Han. An earlier version accepted a mixture so
     * long as one character matched, and an unfinished placeholder — {@code
     * 当time} — passed every check: it contained 当, so containment was
     * satisfied and it looked like coverage.</p>
     */
    public static EgKey of(String phrase) {
        Objects.requireNonNull(phrase, "phrase");
        String p = phrase.strip();
        if (p.isEmpty()) {
            throw new IllegalArgumentException("an empty phrase shows nothing");
        }
        var chars = new ArrayList<ZiCharUTF8>();
        var stray = new ArrayList<String>();
        p.codePoints().forEach(cp -> {
            if (ZiCharUTF8.isHan(cp)) chars.add(new ZiCharUTF8(cp));
            else stray.add(new String(Character.toChars(cp)));
        });
        if (!stray.isEmpty()) {
            throw new IllegalArgumentException(
                    "phrase \"" + p + "\" is not all Han - found " + stray
                  + "; a half-written example reads as coverage");
        }
        return new EgKey(chars);
    }

    /** The phrase, rendered back. */
    public String phrase() {
        var sb = new StringBuilder();
        for (ZiCharUTF8 c : characters) sb.append(c.value());
        return sb.toString();
    }

    /** Does this phrase use the character it is filed under? */
    public boolean contains(ZiCharUTF8 zi) {
        return characters.contains(zi);
    }

    public int length() { return characters.size(); }

    @Override public String toString() { return phrase(); }
}
