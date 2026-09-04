package kranji.reading.model;

import kranji.pinyin.PinyinSyllable;
import kranji.reading.library.ArticleAddress;
import kranji.reading.library.CollectionId;
import kranji.reading.library.LocalId;
import kranji.zi.ZiCharUTF8Codec;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * What an article asks of a reader.
 *
 * <p>The arithmetic is trivial; what is worth testing is what gets counted and
 * under which key. A census counted by character rather than by reading, or
 * keyed a hair differently from the known set, produces a number that is wrong
 * without being obviously wrong — every article at 0%, or an article reported
 * easier than the child will find it.</p>
 */
class ArticleCensusTest {

    private static Token.Zi zi(String glyph, String reading) {
        return new Token.Zi(ZiCharUTF8Codec.INSTANCE.from(glyph),
                PinyinSyllable.parse(reading), false);
    }

    private static ArticleAddress address() {
        return new ArticleAddress(CollectionId.named("kranji.reader.demo.tangshi"),
                LocalId.named("x"));
    }

    private static Article article(Token... tokens) {
        return new Article(address(),
                "x", List.of(new Block.Paragraph(List.of(tokens))));
    }

    // ── The key ────────────────────────────────────────────────────────

    @Test
    void keysExactlyAsTheKnownSetDoes() {
        // Pinned deliberately. The measure is a set intersection with keys
        // built in JavaScript by KnownSetModule.keyOf; one character of
        // difference makes every article read 0% and nothing throws.
        assertEquals("34892:xing2", ArticleCensus.keyOf(zi("行", "xíng")));
        assertEquals("22909:hao3", ArticleCensus.keyOf(zi("好", "hǎo")));
    }

    @Test
    void countsByReadingNotByCharacter() {
        // An article using 行 as háng asks nothing of a reader who learnt
        // xíng. Folding them together would report a readability the child
        // cannot actually achieve.
        ArticleCensus census = ArticleCensus.of(article(zi("行", "xíng"), zi("行", "háng")));

        assertEquals(2, census.distinct());
        assertEquals(1, census.pairs().get("34892:xing2"));
        assertEquals(1, census.pairs().get("34892:hang2"));
    }

    // ── What is counted ────────────────────────────────────────────────

    @Test
    void repeatsCountTowardsTheTotal() {
        // The ratio should reflect the reading experience: a character met
        // three times is three moments of support.
        ArticleCensus census = ArticleCensus.of(
                article(zi("好", "hǎo"), zi("好", "hǎo"), zi("好", "hǎo")));

        assertEquals(3, census.total());
        assertEquals(1, census.distinct(), "and one reading to learn");
        assertEquals(3, census.pairs().get("22909:hao3"));
    }

    @Test
    void punctuationIsNotCountedAtAll() {
        // Plain tokens are never annotated, so counting them would inflate
        // every article that used a lot of commas.
        ArticleCensus census = ArticleCensus.of(
                article(zi("好", "hǎo"), new Token.Plain("，"), new Token.Plain(" ")));

        assertEquals(1, census.total());
        assertEquals(1, census.distinct());
    }

    @Test
    void countsAcrossEveryBlock() {
        // A verse is one block with several lines; its characters are the
        // article's characters like any other.
        Article a = new Article(
                address(), "x",
                List.of(new Block.Paragraph(List.of(zi("好", "hǎo"))),
                        new Block.Verse(List.of(List.of(zi("月", "yuè")),
                                               List.of(zi("好", "hǎo"))))));
        ArticleCensus census = ArticleCensus.of(a);

        assertEquals(3, census.total());
        assertEquals(2, census.distinct());
        assertEquals(2, census.pairs().get("22909:hao3"));
    }

    @Test
    void anArticleOfNoCharactersIsCountable() {
        // Not a crash and not a divide-by-zero waiting to happen downstream.
        ArticleCensus census = ArticleCensus.of(article(new Token.Plain("...")));

        assertEquals(0, census.total());
        assertEquals(0, census.distinct());
    }

    @Test
    void theTotalIsTheArticleLength() {
        // Two ways of counting the same thing; if they ever disagree, one of
        // them is wrong about what a Han token is.
        Article a = article(zi("好", "hǎo"), new Token.Plain("，"), zi("月", "yuè"));

        assertEquals(a.length(), ArticleCensus.of(a).total());
    }

    @Test
    void theCensusIsImmutable() {
        ArticleCensus census = ArticleCensus.of(article(zi("好", "hǎo")));

        assertTrue(census.pairs() instanceof java.util.Map,
                "a census handed to a renderer must not be editable by it");
        try {
            census.pairs().put("1:a", 1);
            org.junit.jupiter.api.Assertions.fail("expected an immutable map");
        } catch (UnsupportedOperationException expected) { /* good */ }
    }
}
