package kranji.studio.articles;

import kranji.reading.content.ParseFinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The markdown subset, made executable.
 *
 * <p>The specification is <i>The Markdown Kranji Reads</i>; this is the first
 * thing that enforces it. Stage one of the workbench: structure in, formatted
 * HTML out, with every construct outside the subset named on the line it
 * appeared.</p>
 *
 * <h2>Errors stop it; warnings do not</h2>
 *
 * <p>An error means the document does not render at all — {@link Parsed#html()}
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
 * second is stage two. An override is carried through to the HTML as ruby so a
 * person can see it, but nothing here knows whether 地{@code {dì}} is a reading
 * 地 actually has.</p>
 */
public final class MdSubsetParser {

    private MdSubsetParser() {}

    /** What came back: the HTML when it rendered, and everything worth saying. */
    public record Parsed(Optional<String> html, String title, List<ParseFinding> findings) {

        /** True when nothing prevented it from rendering. */
        public boolean ok() { return html.isPresent(); }

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
    private static final Pattern NUMBERED  = Pattern.compile("^\\d+\\.\\s+(.*)$");
    private static final Pattern QUOTE     = Pattern.compile("^>\\s?(.*)$");
    private static final Pattern FENCE     = Pattern.compile("^```\\s*(\\S*)\\s*$");
    private static final Pattern BAD_BULLET= Pattern.compile("^[*+]\\s+.*$");
    private static final Pattern TABLE     = Pattern.compile("^\\|.*$");
    private static final Pattern HTML_TAG  = Pattern.compile("^\\s*</?[a-zA-Z][^>]*>");
    private static final Pattern SETEXT    = Pattern.compile("^(=+|-{2,})\\s*$");

    private static final Pattern RUN       = Pattern.compile("‹([^‹›]*)›");
    private static final Pattern OVERRIDE  = Pattern.compile("(\\p{IsHan})\\{([^}]*)}");
    private static final Pattern EMPHASIS  = Pattern.compile("\\*{1,2}([^*]+)\\*{1,2}");
    private static final Pattern LINK      = Pattern.compile("!?\\[[^]]*]\\([^)]*\\)");
    private static final Pattern STRIKE    = Pattern.compile("~~|==");

    /** Parses one document. Never throws; everything wrong comes back as a finding. */
    public static Parsed parse(String source) {
        var findings = new ArrayList<ParseFinding>();
        var html = new StringBuilder();
        List<String> lines = source == null ? List.of() : source.replace("\r\n", "\n").lines().toList();

        String title = null;
        boolean inVerse = false;
        var verse = new ArrayList<String>();
        var paragraph = new ArrayList<String>();
        String openList = null;   // "ul" or "ol", or null

        for (int i = 0; i < lines.size(); i++) {
            String raw = lines.get(i);
            int no = i + 1;
            String line = raw.strip();

            // Inside a fence nothing is markdown: the lines are the content.
            if (inVerse) {
                Matcher close = FENCE.matcher(line);
                if (close.matches()) {
                    inVerse = false;
                    html.append("<div class=\"kw-verse\">");
                    for (String v : verse) html.append("<div>").append(inline(v, no, findings)).append("</div>");
                    html.append("</div>\n");
                    verse.clear();
                } else {
                    verse.add(raw);
                }
                continue;
            }

            if (line.isEmpty()) {
                openList = closeList(html, openList);
                flushParagraph(html, paragraph, no, findings);
                continue;
            }

            Matcher fence = FENCE.matcher(line);
            if (fence.matches()) {
                openList = closeList(html, openList);
                flushParagraph(html, paragraph, no, findings);
                String info = fence.group(1);
                if (!info.equals("verse")) {
                    findings.add(ParseFinding.error(no, info.isEmpty()
                            ? "a bare fence is not a construct here; only ```verse is"
                            : "'" + info + "' is not a fence this reads; only ```verse is"));
                    // Skip to the closing fence so the body is not read as markdown.
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
                openList = closeList(html, openList);
                flushParagraph(html, paragraph, no, findings);
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
                openList = closeList(html, openList);
                flushParagraph(html, paragraph, no, findings);
                int level = h.group(1).length();
                String text = h.group(2).strip();
                String pin = null;
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
                }
                html.append("<h").append(level)
                    .append(pin == null ? "" : " id=\"" + escape(pin) + "\"")
                    .append(pin == null ? " class=\"kw-unpinned\"" : "")
                    .append('>').append(inline(text, no, findings))
                    .append("</h").append(level).append(">\n");
                continue;
            }

            Matcher q = QUOTE.matcher(line);
            if (q.matches()) {
                openList = closeList(html, openList);
                flushParagraph(html, paragraph, no, findings);
                html.append("<blockquote>").append(inline(q.group(1), no, findings)).append("</blockquote>\n");
                continue;
            }

            Matcher b = BULLET.matcher(line);
            Matcher n = NUMBERED.matcher(line);
            if (b.matches() || n.matches()) {
                flushParagraph(html, paragraph, no, findings);
                String want = b.matches() ? "ul" : "ol";
                if (!want.equals(openList)) {
                    openList = closeList(html, openList);
                    html.append('<').append(want).append(">\n");
                    openList = want;
                }
                html.append("<li>").append(inline(b.matches() ? b.group(1) : n.group(1), no, findings))
                    .append("</li>\n");
                continue;
            }

            openList = closeList(html, openList);
            paragraph.add(line);
        }

        closeList(html, openList);
        flushParagraph(html, paragraph, lines.size(), findings);
        if (inVerse) {
            findings.add(ParseFinding.error(lines.size(), "a ```verse fence was never closed"));
        }
        if (title == null) {
            findings.add(ParseFinding.error(1, "no # title; the first line names the document"));
        }

        boolean servable = findings.stream().noneMatch(f -> f.severity() == ParseFinding.Severity.ERROR);
        return new Parsed(servable ? Optional.of(html.toString()) : Optional.empty(),
                title == null ? "" : title, List.copyOf(findings));
    }

    // ── Blocks ─────────────────────────────────────────────────────────

    private static void flushParagraph(StringBuilder html, List<String> lines,
                                       int no, List<ParseFinding> findings) {
        if (lines.isEmpty()) return;
        // Markdown joins them, and so do we - the reason a poem needs a fence.
        html.append("<p>").append(inline(String.join("", lines), no, findings)).append("</p>\n");
        lines.clear();
    }

    private static String closeList(StringBuilder html, String open) {
        if (open != null) html.append("</").append(open).append(">\n");
        return null;
    }

    // ── Inline ─────────────────────────────────────────────────────────

    /**
     * One line of text.
     *
     * <p>Runs first, because what is inside one is parsed and what is outside
     * it is not — which is the whole reason the delimiter exists.</p>
     */
    private static String inline(String text, int no, List<ParseFinding> findings) {
        var out = new StringBuilder();
        Matcher run = RUN.matcher(text);
        int at = 0;
        while (run.find()) {
            out.append(outsideRun(text.substring(at, run.start()), no, findings));
            out.append("<span class=\"kw-run\">")
               .append(insideRun(run.group(1), no, findings))
               .append("</span>");
            at = run.end();
        }
        out.append(outsideRun(text.substring(at), no, findings));
        return out.toString();
    }

    /** Chinese, punctuation, and the readings on it. Emphasis here means nothing. */
    private static String outsideRun(String text, int no, List<ParseFinding> findings) {
        if (text.isEmpty()) return "";
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
        Matcher em = EMPHASIS.matcher(stripped);
        if (em.find()) {
            findings.add(ParseFinding.warning(no,
                    "emphasis over Chinese was dropped; it cannot be drawn in a practice square"));
            stripped = em.reset().replaceAll("$1");
        }
        return ruby(stripped);
    }

    /** Ordinary typography. Emphasis here is kept, because there is no grid. */
    private static String insideRun(String text, int no, List<ParseFinding> findings) {
        if (text.indexOf('`') >= 0) {
            findings.add(ParseFinding.error(no, "a backtick is not a construct here"));
        }
        String html = escape(text);
        html = html.replaceAll("\\*\\*([^*]+)\\*\\*", "<strong>$1</strong>");
        html = html.replaceAll("\\*([^*]+)\\*", "<em>$1</em>");
        return html;
    }

    /** An override, shown so a person can see what was pinned. Not validated here. */
    private static String ruby(String text) {
        Matcher m = OVERRIDE.matcher(text);
        var out = new StringBuilder();
        int at = 0;
        while (m.find()) {
            out.append(escape(text.substring(at, m.start())))
               .append("<ruby>").append(escape(m.group(1)))
               .append("<rt>").append(escape(m.group(2))).append("</rt></ruby>");
            at = m.end();
        }
        out.append(escape(text.substring(at)));
        return out.toString();
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
