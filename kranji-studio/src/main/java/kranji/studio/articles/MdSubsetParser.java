package kranji.studio.articles;

import kranji.reading.content.ParseFinding;
import kranji.studio.articles.MdDocument.Block;
import kranji.studio.articles.MdDocument.Span;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The markdown subset, made executable.
 *
 * <p>The specification is <i>The Markdown Kranji Reads</i>; this is the first
 * thing that enforces it. Structure in, structure out — see {@link MdDocument}
 * for why it is not markup.</p>
 *
 * <h2>Errors stop it; warnings do not</h2>
 *
 * <p>An error means the document does not render at all — {@link Parsed#blocks()}
 * comes back empty. That is deliberate and it is the whole point of a subset:
 * a file accepted with its tables quietly missing is a page that is not what
 * was written, and the author has no way to find out. A warning means it
 * rendered with something dropped, which is reserved for decoration that has no
 * meaning in a practice grid.</p>
 *
 * <h2>What this stage does not do</h2>
 *
 * <p>It does not check readings against the corpus, and it does not segment.
 * {@code ArticleParser} already does the first and is wired in later; the
 * second is stage two. An override becomes a ruby span so a person can see it,
 * but nothing here knows whether 地{@code {dì}} is a reading 地 actually has.</p>
 */
public final class MdSubsetParser {

    private MdSubsetParser() {}

    /** What came back: the blocks when it rendered, and everything worth saying. */
    public record Parsed(Optional<List<Block>> blocks, String title, List<ParseFinding> findings) {

        /** True when nothing prevented it from rendering. */
        public boolean ok() { return blocks.isPresent(); }

        public List<ParseFinding> errors() {
            return findings.stream()
                    .filter(f -> f.severity() == ParseFinding.Severity.ERROR).toList();
        }

        public List<ParseFinding> warnings() {
            return findings.stream()
                    .filter(f -> f.severity() == ParseFinding.Severity.WARNING).toList();
        }
    }

    // ── The constructs, as the spec names them ─────────────────────────

    private static final Pattern HEADING   = Pattern.compile("^(#{1,6})\\s+(.*)$");
    private static final Pattern PIN       = Pattern.compile("\\s*\\{#([a-z][a-z0-9-]*)}\\s*$");
    private static final Pattern BULLET    = Pattern.compile("^-\\s+(.*)$");
    private static final Pattern NUMBERED  = Pattern.compile("^(\\d+)\\.\\s+(.*)$");
    private static final Pattern QUOTE     = Pattern.compile("^>\\s?(.*)$");
    private static final Pattern FENCE     = Pattern.compile("^```\\s*(\\S*)\\s*$");
    private static final Pattern BAD_BULLET= Pattern.compile("^[*+]\\s+.*$");
    private static final Pattern TABLE     = Pattern.compile("^\\|.*$");
    private static final Pattern HTML_TAG  = Pattern.compile("^\\s*</?[a-zA-Z][^>]*>");
    private static final Pattern SETEXT    = Pattern.compile("^(=+|-{2,})\\s*$");

    private static final Pattern RUN       = Pattern.compile("‹([^‹›]*)›");
    private static final Pattern OVERRIDE  = Pattern.compile("(\\p{IsHan})\\{([^}]*)}");
    private static final Pattern EMPHASIS  = Pattern.compile("(\\*{1,2})([^*]+)\\1");
    private static final Pattern ANY_EMPH  = Pattern.compile("\\*{1,2}[^*]+\\*{1,2}");
    private static final Pattern LINK      = Pattern.compile("!?\\[[^]]*]\\([^)]*\\)");
    private static final Pattern STRIKE    = Pattern.compile("~~|==");

    /** Parses one document. Never throws; everything wrong comes back as a finding. */
    public static Parsed parse(String source) {
        var findings = new ArrayList<ParseFinding>();
        var blocks = new ArrayList<Block>();
        List<String> lines = source == null ? List.of() : source.replace("\r\n", "\n").lines().toList();

        String title = null;
        boolean inVerse = false;
        var verse = new ArrayList<List<Span>>();
        var paragraph = new ArrayList<String>();

        for (int i = 0; i < lines.size(); i++) {
            String raw = lines.get(i);
            int no = i + 1;
            String line = raw.strip();

            // Inside a fence nothing is markdown: the lines are the content.
            if (inVerse) {
                if (FENCE.matcher(line).matches()) {
                    inVerse = false;
                    blocks.add(new Block("verse", 0, "", List.copyOf(verse)));
                    verse.clear();
                } else {
                    verse.add(inline(raw, no, findings));
                }
                continue;
            }

            if (line.isEmpty()) {
                flushParagraph(blocks, paragraph, no, findings);
                continue;
            }

            Matcher fence = FENCE.matcher(line);
            if (fence.matches()) {
                flushParagraph(blocks, paragraph, no, findings);
                String info = fence.group(1);
                if (!info.equals("verse")) {
                    findings.add(ParseFinding.error(no, info.isEmpty()
                            ? "a bare fence is not a construct here; only ```verse is"
                            : "'" + info + "' is not a fence this reads; only ```verse is"));
                    while (++i < lines.size() && !FENCE.matcher(lines.get(i).strip()).matches()) { /* skip */ }
                    continue;
                }
                inVerse = true;
                continue;
            }

            if (SETEXT.matcher(line).matches() && !line.equals("---")) {
                findings.add(ParseFinding.error(no,
                        "a setext heading is not a construct here; write # instead"));
                continue;
            }
            if (line.equals("---")) {
                flushParagraph(blocks, paragraph, no, findings);
                findings.add(ParseFinding.warning(no, "a rule was dropped; headings already separate"));
                continue;
            }
            if (TABLE.matcher(line).matches()) {
                findings.add(ParseFinding.error(no, "a table is not a construct this reads"));
                continue;
            }
            if (HTML_TAG.matcher(line).find()) {
                findings.add(ParseFinding.error(no, "raw HTML is not a construct this reads"));
                continue;
            }
            if (BAD_BULLET.matcher(line).matches()) {
                findings.add(ParseFinding.error(no, "write a bullet as '- '; * and + are not read"));
                continue;
            }
            if (raw.startsWith("  ") && (BULLET.matcher(line).matches() || NUMBERED.matcher(line).matches())) {
                findings.add(ParseFinding.error(no, "a nested list is a structure the grid cannot show"));
                continue;
            }

            Matcher h = HEADING.matcher(line);
            if (h.matches()) {
                flushParagraph(blocks, paragraph, no, findings);
                int level = h.group(1).length();
                String text = h.group(2).strip();
                String pin = "";
                Matcher p = PIN.matcher(text);
                if (p.find()) { pin = p.group(1); text = text.substring(0, p.start()).strip(); }

                if (level > 3) {
                    findings.add(ParseFinding.error(no,
                            "heading level " + level + " is deeper than this reads; split the document"));
                    continue;
                }
                if (level == 1) {
                    if (title != null) {
                        findings.add(ParseFinding.error(no, "a second # title; there may be only one"));
                        continue;
                    }
                    title = text;
                    blocks.add(new Block("title", 1, pin, List.of(inline(text, no, findings))));
                    continue;
                }
                blocks.add(Block.heading(level, pin, inline(text, no, findings)));
                continue;
            }

            Matcher q = QUOTE.matcher(line);
            if (q.matches()) {
                flushParagraph(blocks, paragraph, no, findings);
                blocks.add(Block.of("quote", inline(q.group(1), no, findings)));
                continue;
            }

            Matcher b = BULLET.matcher(line);
            if (b.matches()) {
                flushParagraph(blocks, paragraph, no, findings);
                blocks.add(Block.of("li", inline(b.group(1), no, findings)));
                continue;
            }

            Matcher n = NUMBERED.matcher(line);
            if (n.matches()) {
                flushParagraph(blocks, paragraph, no, findings);
                // The author's own number, kept. A renderer that counted for
                // itself would silently renumber a list that starts at 3, and a
                // workbench exists to show what the file says.
                blocks.add(new Block("oli", number(n.group(1)), "",
                        List.of(inline(n.group(2), no, findings))));
                continue;
            }

            paragraph.add(line);
        }

        flushParagraph(blocks, paragraph, lines.size(), findings);
        if (inVerse) {
            findings.add(ParseFinding.error(lines.size(), "a ```verse fence was never closed"));
        }
        if (title == null) {
            findings.add(ParseFinding.error(1, "no # title; the first line names the document"));
        }

        boolean servable = findings.stream().noneMatch(f -> f.severity() == ParseFinding.Severity.ERROR);
        return new Parsed(servable ? Optional.of(List.copyOf(blocks)) : Optional.empty(),
                title == null ? "" : title, List.copyOf(findings));
    }

    // ── Blocks ─────────────────────────────────────────────────────────

    /**
     * A list marker's digits. This promises never to throw, and {@code \d+}
     * puts no ceiling on how many of them an author may type.
     */
    private static int number(String digits) {
        try { return Integer.parseInt(digits); }
        catch (NumberFormatException tooLong) { return 0; }
    }

    private static void flushParagraph(List<Block> blocks, List<String> lines,
                                       int no, List<ParseFinding> findings) {
        if (lines.isEmpty()) return;
        // Markdown joins them, and so do we - the reason a poem needs a fence.
        blocks.add(Block.of("p", inline(String.join("", lines), no, findings)));
        lines.clear();
    }

    // ── Inline ─────────────────────────────────────────────────────────

    /**
     * One line, as spans.
     *
     * <p>Runs first, because what is inside one is parsed and what is outside
     * it is not — which is the whole reason the delimiter exists.</p>
     */
    private static List<Span> inline(String text, int no, List<ParseFinding> findings) {
        var spans = new ArrayList<Span>();
        Matcher run = RUN.matcher(text);
        int at = 0;
        while (run.find()) {
            outsideRun(text.substring(at, run.start()), no, findings, spans);
            insideRun(run.group(1), no, findings, spans);
            at = run.end();
        }
        outsideRun(text.substring(at), no, findings, spans);
        return List.copyOf(spans);
    }

    /** Chinese, punctuation, and the readings on it. Emphasis here means nothing. */
    private static void outsideRun(String text, int no, List<ParseFinding> findings,
                                   List<Span> into) {
        if (text.isEmpty()) return;
        if (text.indexOf('‹') >= 0 || text.indexOf('›') >= 0) {
            findings.add(ParseFinding.error(no, "an unmatched ‹ or ›"));
        }
        if (text.indexOf('`') >= 0) {
            findings.add(ParseFinding.error(no,
                    "a backtick is not a construct here; a non-Chinese run is ‹like this›"));
        }
        if (LINK.matcher(text).find()) {
            findings.add(ParseFinding.error(no, "a link or image is not a construct this reads"));
        }
        if (STRIKE.matcher(text).find()) {
            findings.add(ParseFinding.error(no, "strikethrough and highlight are not read"));
        }
        String stripped = text;
        if (ANY_EMPH.matcher(stripped).find()) {
            findings.add(ParseFinding.warning(no,
                    "emphasis over Chinese was dropped; it cannot be drawn in a practice square"));
            stripped = EMPHASIS.matcher(stripped).replaceAll("$2");
        }
        ruby(stripped, into);
    }

    /** Ordinary typography. Emphasis here is kept, because there is no grid. */
    private static void insideRun(String text, int no, List<ParseFinding> findings,
                                  List<Span> into) {
        if (text.indexOf('`') >= 0) {
            findings.add(ParseFinding.error(no, "a backtick is not a construct here"));
        }
        Matcher em = EMPHASIS.matcher(text);
        int at = 0;
        while (em.find()) {
            if (em.start() > at) into.add(Span.inRun("text", text.substring(at, em.start())));
            into.add(Span.inRun(em.group(1).length() == 2 ? "strong" : "em", em.group(2)));
            at = em.end();
        }
        if (at < text.length()) into.add(Span.inRun("text", text.substring(at)));
        if (text.isEmpty()) into.add(Span.inRun("text", ""));
    }

    /** An override becomes a ruby span, so a person can see what was pinned. */
    private static void ruby(String text, List<Span> into) {
        Matcher m = OVERRIDE.matcher(text);
        int at = 0;
        while (m.find()) {
            if (m.start() > at) into.add(Span.text(text.substring(at, m.start())));
            into.add(Span.ruby(m.group(1), m.group(2)));
            at = m.end();
        }
        if (at < text.length()) into.add(Span.text(text.substring(at)));
    }
}
