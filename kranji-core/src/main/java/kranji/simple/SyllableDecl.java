package kranji.simple;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.pinyin.PinyinSyllable;

import java.util.List;
import java.util.Objects;

/**
 * One syllable and the characters read as it.
 *
 * <p>The unit of authoring, and the same unit the catalogue terminates at, so
 * what is written and what is browsed have the same shape.</p>
 */
public record SyllableDecl(PinyinSyllable syllable, List<ZiDecl> characters) implements ValueObject {

    public SyllableDecl {
        Objects.requireNonNull(syllable, "syllable");
        characters = List.copyOf(Objects.requireNonNull(characters, "characters"));
        if (characters.isEmpty()) {
            throw new IllegalArgumentException(
                    "a syllable with no characters is not worth declaring: " + syllable.toDiacritic());
        }
        long distinct = characters.stream().map(ZiDecl::zi).distinct().count();
        if (distinct != characters.size()) {
            throw new IllegalArgumentException(
                    "a character is declared twice under " + syllable.toDiacritic());
        }
    }
}
