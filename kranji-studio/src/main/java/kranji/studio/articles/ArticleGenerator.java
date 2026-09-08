package kranji.studio.articles;

import kranji.reading.content.ParseFinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A folder of drafts, turned into something the library can serve.
 *
 * <h2>The rule this exists to keep</h2>
 *
 * <blockquote>Metadata is Java; the body is a resource. Listing therefore never
 * parses.</blockquote>
 *
 * <p>That property is what makes 608 articles listable without opening a file,
 * and a {@code .md} document breaks it: its sections come <i>from</i> the file.
 * So the file is read once, at build time, and what ships is a catalogue in
 * Java beside one resource per section. Nothing parses markdown at request
 * time, and nothing can: by the time a reader asks, there is no markdown
 * left.</p>
 *
 * <h2>Structure, not text</h2>
 *
 * <p>The resource is the parsed document — blocks and spans, the same bytes
 * {@link MdJson} sends to the workbench. Not the markdown, and not plain text
 * either: a {@code ‹…›} run, a pinned reading and a 破折号 are decisions the
 * subset made, and re-deriving them in a browser would be doing the work twice
 * and risking two answers.</p>
 *
 * <h2>Headings only</h2>
 *
 * <p>A section is a heading. The size budget that {@link Segments} can also
 * apply is deliberately <b>not</b> used here, and the reason is addresses: a
 * part the budget cuts has no heading to pin an id to, so its address would
 * have to be invented — and an invented address moves the moment a paragraph
 * is added. Sections are what a reader bookmarks; how many screens a section
 * takes is a question for whatever is showing it.</p>
 *
 * <h2>Every section needs an id, and this will not invent one</h2>
 *
 * <p>A missing {@code {#slug}} is an error naming the heading. That is the
 * whole of the id policy: the author writes it once, it is checked for ever
 * after, and nothing in the build is in a position to change it. A generator
 * that derived ids from heading text would re-address the library every time
 * somebody improved a title.</p>
 */
public final class ArticleGenerator {

    private ArticleGenerator() {}

    /** One draft on the way in. */
    public record Draft(String name, String source) {}

    /** One file on the way out, at a path relative to the output root. */
    public record Emitted(String path, String content) {}

    /** Something that stopped a draft, named where an author can find it. */
    public record Problem(String draft, int line, String message) {

        @Override
        public String toString() {
            return draft + (line > 0 ? ":" + line : "") + " — " + message;
        }
    }

    /**
     * What the run produced.
     *
     * <p>Files and problems both, and the caller decides. Nothing is written
     * while a problem stands — a half-generated library is worse than none,
     * because the half that generated looks finished.</p>
     */
    public record Result(List<Emitted> files, List<Problem> problems) {

        public Result {
            files = List.copyOf(files);
            problems = List.copyOf(problems);
        }

        public boolean ok() { return problems.isEmpty(); }
    }

    /**
     * Where the output goes.
     *
     * @param pkg       Java package for the generated catalogue
     * @param className the catalogue's class name
     * @param resources classpath prefix the section bodies are written under,
     *                  and the path the catalogue will name
     */
    public record Options(String pkg, String className, String resources) {

        /** The shape the reading collections already use. */
        public static Options of(String pkg, String className, String resources) {
            return new Options(pkg, className, resources);
        }
    }

    public static Result generate(List<Draft> drafts, Options options) {
        var files = new ArrayList<Emitted>();
        var problems = new ArrayList<Problem>();
        // Ordered, because the catalogue is written in the order drafts were
        // given and a generator whose output depends on a hash iteration order
        // produces a diff every time it runs.
        var documents = new LinkedHashMap<String, List<Section>>();

        for (Draft draft : drafts) {
            MdSubsetParser.Parsed parsed = MdSubsetParser.parse(draft.source());
            for (ParseFinding error : parsed.errors()) {
                problems.add(new Problem(draft.name(), error.line(), error.message()));
            }
            if (!parsed.ok()) continue;

            Segment root = Segments.of(parsed.blocks().orElseThrow(), Segments.NO_BUDGET);
            var sections = new ArrayList<Section>();
            collect(root, draft, sections, problems);
            if (sections.isEmpty()) {
                problems.add(new Problem(draft.name(), 1, "nothing to serve: no prose"));
                continue;
            }
            documents.put(root.title(), sections);
        }

        if (!problems.isEmpty()) return new Result(List.of(), problems);

        for (var sections : documents.values()) {
            for (Section section : sections) {
                files.add(new Emitted(options.resources() + "/" + section.id() + ".json",
                                      body(section)));
            }
        }
        files.add(new Emitted(javaPath(options), catalogue(documents, options)));
        return new Result(files, problems);
    }

    /** One section on the way out: an id, a title, and the prose under it. */
    private record Section(String id, String title, int level, Segment segment) {}

    /**
     * The sections of one document, in reading order.
     *
     * <p>A segment with no prose of its own is not a section — {@code ## 四}
     * holding only {@code ###} children is a heading in the document and
     * nothing a reader opens. It still needs an id, because its children are
     * addressed under nothing otherwise; that is a rule worth having and not
     * one this enforces yet.</p>
     */
    private static void collect(Segment segment, Draft draft,
                                List<Section> into, List<Problem> problems) {
        if (!segment.blocks().isEmpty()) {
            if (!segment.pinned()) {
                problems.add(new Problem(draft.name(), 0,
                        "'" + (segment.title().isEmpty() ? segment.path() : segment.title())
                        + "' has no id; write {#some-slug} on its heading"));
            } else {
                into.add(new Section(segment.id(), segment.title(),
                                     segment.level(), segment));
            }
        }
        for (Segment child : segment.children()) collect(child, draft, into, problems);
    }

    // ── What ships ─────────────────────────────────────────────────────

    /**
     * A section's body: the parsed document, not the markdown.
     *
     * <p>{@link MdJson} is the one serialiser, so these are the same bytes the
     * workbench draws from — what an author checks is what ships.</p>
     */
    private static String body(Section section) {
        return "{\"id\":" + MdJson.quote(section.id())
             + ",\"title\":" + MdJson.quote(section.title())
             + ",\"level\":" + section.level()
             + ",\"chars\":" + section.segment().chars()
             + ",\"blocks\":" + MdJson.blocks(section.segment().blocks())
             + "}";
    }

    private static String javaPath(Options options) {
        return options.pkg().replace('.', '/') + "/" + options.className() + ".java";
    }

    /**
     * The catalogue, as Java.
     *
     * <p>One {@code ArticleRef} per section, one collection per document, and
     * every string a literal — so listing the library is reading a field, which
     * is the property the whole arrangement exists to keep.</p>
     */
    private static String catalogue(Map<String, List<Section>> documents, Options options) {
        var java = new StringBuilder();
        java.append("package ").append(options.pkg()).append(";\n\n")
            .append("import kranji.reading.library.ArticleCollection;\n")
            .append("import kranji.reading.library.ArticleRef;\n")
            .append("import kranji.reading.library.CollectionId;\n\n")
            .append("import java.util.List;\n\n")
            .append("""
                    /**
                     * Generated from the markdown drafts. Do not edit.
                     *
                     * <p>One collection per document, one article per section. The bodies
                     * are the parsed documents rather than their markdown: nothing here
                     * parses at request time, which is what keeps listing free.</p>
                     */
                    """)
            .append("public final class ").append(options.className()).append(" {\n\n")
            .append("    private ").append(options.className()).append("() {}\n\n");

        int n = 0;
        for (var entry : documents.entrySet()) {
            String field = "DOC_" + (++n);
            java.append("    /** ").append(escape(entry.getKey())).append(" */\n")
                .append("    public static final ArticleCollection ").append(field)
                .append(" = new Doc(\n")
                .append("            CollectionId.named(\"").append(options.pkg())
                .append(".doc").append(n).append("\"),\n")
                .append("            \"").append(escape(entry.getKey())).append("\",\n")
                .append("            \"\",\n")
                .append("            List.of(\n");
            List<Section> sections = entry.getValue();
            for (int i = 0; i < sections.size(); i++) {
                Section s = sections.get(i);
                java.append("                    ArticleRef.of(\"").append(s.id())
                    .append("\", \"").append(escape(s.title()))
                    .append("\", \"/").append(options.resources()).append("/")
                    .append(s.id()).append(".json\")")
                    .append(i < sections.size() - 1 ? ",\n" : "\n");
            }
            java.append("            ));\n\n");
        }

        java.append("    /** Every document this run produced. */\n")
            .append("    public static List<ArticleCollection> all() {\n")
            .append("        return List.of(");
        for (int i = 1; i <= n; i++) java.append(i > 1 ? ", " : "").append("DOC_").append(i);
        java.append(");\n    }\n\n")
            .append("    private record Doc(CollectionId id, String title, String summary,\n")
            .append("                       List<ArticleRef> articles)")
            .append(" implements ArticleCollection {}\n")
            .append("}\n");
        return java.toString();
    }

    /** A Java string literal's worth of escaping. Titles are the author's. */
    private static String escape(String raw) {
        return raw.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
