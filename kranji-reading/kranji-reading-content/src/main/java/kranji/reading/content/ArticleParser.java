package kranji.reading.content;

import kranji.phonic.SourceReadings;
import kranji.phonic.SyllableIndex;
import kranji.pinyin.PinyinSyllable;
import kranji.reading.library.ArticleAddress;
import kranji.reading.model.Article;
import kranji.reading.model.Block;
import kranji.reading.model.Token;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Reads the plain-text article format.
 *
 * <pre>
 * id: jing-ye-si
 * title: 静夜思
 * ---
 * 床前明月光，
 * 疑是地上霜。
 * </pre>
 *
 * <h2>Blocks come from line structure, not from syntax</h2>
 *
 * <p>A blank line ends a block. Within a block, <b>one line is a paragraph and
 * several lines are a verse</b> — so a poem needs no markup at all, and prose
 * is written as one long line per paragraph. That is a real constraint on
 * authors (a hard-wrapped paragraph becomes a verse) and it buys a format with
 * nothing to learn.</p>
 *
 * <h2>Readings</h2>
 *
 * <p>Every Han character gets the corpus's principal reading unless the source
 * overrides it with <code>{…}</code>. Both tone forms are accepted —
 * <code>{háng}</code> and <code>{hang2}</code> — because
 * {@link PinyinSyllable#parse} takes both and an author should not need a
 * diacritic input method.</p>
 *
 * <p>An override is checked against the corpus: it must be a reading that
 * character actually has. That is the check which earns its keep — it catches
 * a wrong override at preparation rather than in a child's face.</p>
 */
public final class ArticleParser {

    private ArticleParser() {}

    /**
     * Reads one article's body.
     *
     * <p>The source is the body and nothing else. Title, author and identity
     * are declared by the collection that owns the article, so listing a
     * library never opens a file and an article's identity does not live in
     * content that is meant to be editable.</p>
     */
    public static ParsedArticle parse(ArticleAddress address, String title, String source) {
        var findings = new ArrayList<ParseFinding>();
        List<String> lines = source.replace("\r\n", "\n").lines().toList();

        List<Block> blocks = blocks(lines, 1, findings);
        if (blocks.isEmpty()) findings.add(ParseFinding.error(1, "no body"));

        if (!ParseFinding.servable(findings)) {
            return new ParsedArticle(Optional.empty(), findings);
        }
        try {
            return new ParsedArticle(Optional.of(new Article(address, title, blocks)), findings);
        } catch (IllegalArgumentException e) {
            findings.add(ParseFinding.error(1, e.getMessage()));
            return new ParsedArticle(Optional.empty(), findings);
        }
    }

    // ── Body ───────────────────────────────────────────────────────────

    private static List<Block> blocks(List<String> body, int firstLineNumber,
                                      List<ParseFinding> findings) {
        var blocks = new ArrayList<Block>();
        var pending = new ArrayList<List<Token>>();
        int lineNo = firstLineNumber;

        for (String raw : body) {
            if (raw.isBlank()) {
                flush(pending, blocks);
            } else {
                List<Token> tokens = tokenise(raw.trim(), lineNo, findings);
                if (!tokens.isEmpty()) pending.add(tokens);
            }
            lineNo++;
        }
        flush(pending, blocks);
        return blocks;
    }

    /** One line is a paragraph; several lines together are a verse. */
    private static void flush(List<List<Token>> pending, List<Block> blocks) {
        if (pending.isEmpty()) return;
        blocks.add(pending.size() == 1
                ? new Block.Paragraph(pending.get(0))
                : new Block.Verse(List.copyOf(pending)));
        pending.clear();
    }

    // ── Inline ─────────────────────────────────────────────────────────

    private static List<Token> tokenise(String line, int lineNo,
                                        List<ParseFinding> findings) {
        var tokens = new ArrayList<Token>();
        var plain = new StringBuilder();
        SyllableIndex index = SyllableIndex.instance();

        int i = 0;
        while (i < line.length()) {
            int cp = line.codePointAt(i);
            int width = Character.charCount(cp);

            if (cp == '{') {
                findings.add(ParseFinding.error(lineNo,
                        "an override must follow a character, not stand alone"));
                i += width;
                continue;
            }
            if (!ZiCharUTF8.isHan(cp)) {
                plain.appendCodePoint(cp);
                i += width;
                continue;
            }

            if (plain.length() > 0) {
                tokens.add(new Token.Plain(plain.toString()));
                plain.setLength(0);
            }

            ZiCharUTF8 zi = new ZiCharUTF8(cp);
            i += width;

            String override = null;
            if (i < line.length() && line.charAt(i) == '{') {
                int close = line.indexOf('}', i);
                if (close < 0) {
                    findings.add(ParseFinding.error(lineNo,
                            "unclosed override after " + zi.value()));
                    continue;
                }
                override = line.substring(i + 1, close).trim();
                i = close + 1;
            }

            boolean authored = override != null;
            Optional<PinyinSyllable> resolved = reading(zi, override, lineNo, findings);
            if (resolved.isPresent()) {
                tokens.add(new Token.Zi(zi, resolved.get(), authored));
            }
        }
        if (plain.length() > 0) tokens.add(new Token.Plain(plain.toString()));
        return tokens;
    }

    /**
     * The reading for one character: the override when given and valid,
     * otherwise the corpus principal.
     */
    private static Optional<PinyinSyllable> reading(ZiCharUTF8 zi, String override,
                                                    int lineNo,
                                                    List<ParseFinding> findings) {
        Optional<SourceReadings> known = SyllableIndex.instance().readingsOf(zi);
        if (known.isEmpty()) {
            findings.add(ParseFinding.error(lineNo,
                    zi.value() + " (" + zi.codePointLabel() + ") is not in the corpus"));
            return Optional.empty();
        }
        SourceReadings row = known.get();

        if (override == null) {
            if (row.isPolyphonic()) {
                findings.add(ParseFinding.warning(lineNo,
                        zi.value() + " is read " + row.readingTexts().size()
                      + " ways; using " + row.principal().toDiacritic()
                      + " - override if that is wrong here"));
            }
            return Optional.of(row.principal());
        }

        PinyinSyllable parsed;
        try {
            parsed = PinyinSyllable.parse(override);
        } catch (RuntimeException e) {
            findings.add(ParseFinding.error(lineNo,
                    "'" + override + "' after " + zi.value() + " is not a reading"));
            return Optional.of(row.principal());
        }
        if (!row.all().contains(parsed)) {
            findings.add(ParseFinding.error(lineNo,
                    zi.value() + " is not read '" + parsed.toDiacritic() + "' - it reads "
                  + String.join(", ", row.readingTexts())));
            return Optional.of(row.principal());
        }
        if (parsed.equals(row.principal())) {
            findings.add(ParseFinding.warning(lineNo,
                    "the override on " + zi.value() + " matches its principal reading"));
        }
        return Optional.of(parsed);
    }
}
