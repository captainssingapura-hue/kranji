package kranji.reading.content;

import kranji.phonic.SourceReadings;
import kranji.phonic.SyllableIndex;
import kranji.pinyin.PinyinSyllable;
import kranji.reading.model.Article;
import kranji.reading.model.ArticleClass;
import kranji.reading.model.ArticleHeader;
import kranji.reading.model.ArticleId;
import kranji.reading.model.Block;
import kranji.reading.model.Token;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    /** Reads one article's source. */
    public static ParsedArticle parse(String source) {
        var findings = new ArrayList<ParseFinding>();
        List<String> lines = source.replace("\r\n", "\n").lines().toList();

        int divider = lines.indexOf("---");
        if (divider < 0) {
            findings.add(ParseFinding.error(1,
                    "no '---' separating the header from the body"));
            return new ParsedArticle(Optional.empty(), findings);
        }

        Map<String, String> header = header(lines.subList(0, divider), findings);
        List<Block> blocks = blocks(lines.subList(divider + 1, lines.size()),
                divider + 2, findings);

        String id = header.get("id");
        String title = header.get("title");
        if (id == null)    findings.add(ParseFinding.error(1, "the header needs an 'id'"));
        if (title == null) findings.add(ParseFinding.error(1, "the header needs a 'title'"));
        if (blocks.isEmpty()) findings.add(ParseFinding.error(divider + 2, "no body"));

        if (!ParseFinding.servable(findings)) {
            return new ParsedArticle(Optional.empty(), findings);
        }
        try {
            return new ParsedArticle(
                    Optional.of(new Article(new ArticleId(id), title, blocks)), findings);
        } catch (IllegalArgumentException e) {
            findings.add(ParseFinding.error(1, e.getMessage()));
            return new ParsedArticle(Optional.empty(), findings);
        }
    }

    /**
     * Reads only what precedes {@code ---}.
     *
     * <p>Listing a catalogue must not cost a full parse. {@link #parse} resolves
     * every character against the corpus and validates every override; that is
     * the right price to render an article and the wrong one to learn its
     * title.</p>
     *
     * <p>Throws rather than returning empty. These are bundled files, read at
     * boot: a missing uuid or an unknown type is an authoring mistake that
     * should stop the build, not quietly drop the article out of the
     * catalogue where nobody would notice it had gone.</p>
     *
     * @throws IllegalArgumentException when the header cannot be understood
     */
    public static ArticleHeader headerOnly(String source) {
        var ignored = new ArrayList<ParseFinding>();
        List<String> lines = source.replace("\r\n", "\n").lines().toList();
        int divider = lines.indexOf("---");
        Map<String, String> header =
                header(divider < 0 ? lines : lines.subList(0, divider), ignored);

        String id = required(header, "id");
        String title = required(header, "title");
        String rawUuid = required(header, "uuid");
        String rawType = required(header, "type");

        ArticleClass type = ArticleClass.ofWireId(rawType).orElseThrow(
                () -> new IllegalArgumentException("unknown article type '" + rawType
                        + "' - expected one of " + wireIds()));
        UUID uuid;
        try {
            uuid = UUID.fromString(rawUuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("'" + rawUuid + "' is not a uuid", e);
        }
        return new ArticleHeader(new ArticleId(id), uuid, title,
                header.getOrDefault("author", ""), type);
    }

    private static String required(Map<String, String> header, String key) {
        String value = header.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("the header needs a '" + key + "'");
        }
        return value;
    }

    private static String wireIds() {
        var out = new ArrayList<String>();
        for (ArticleClass c : ArticleClass.values()) out.add(c.wireId());
        return String.join(", ", out);
    }

    // ── Header ─────────────────────────────────────────────────────────

    private static Map<String, String> header(List<String> lines,
                                              List<ParseFinding> findings) {
        Map<String, String> out = new LinkedHashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            int colon = line.indexOf(':');
            if (colon <= 0) {
                findings.add(ParseFinding.warning(i + 1,
                        "not a 'key: value' header line, ignored: " + line));
                continue;
            }
            out.put(line.substring(0, colon).trim(), line.substring(colon + 1).trim());
        }
        return out;
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
