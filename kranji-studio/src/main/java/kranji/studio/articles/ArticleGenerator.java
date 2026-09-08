package kranji.studio.articles;

import kranji.reading.content.ParseFinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
 *
 * <h2>An address is a path, because a document is a tree</h2>
 *
 * <p>A section's address is the slugs from the document down, dotted:
 * {@code yu.yang-zi.qian-hou}. {@code LocalId} is a dotted name already, so
 * this is the address the model was built for rather than a new one.</p>
 *
 * <p>What it buys is that uniqueness becomes a question about <b>siblings</b>,
 * which is local, rather than about the whole collection, which would be a
 * registry. Two chapters may each have a 前后 and neither author has to know
 * about the other. The only ids that must be unique across a collection are
 * the documents' own — and those are siblings too, of each other.</p>
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

    /** The one name a document may not take: it is the collection's listing. */
    private static final String INDEX = "index";

    public static Result generate(List<Draft> drafts, Options options) {
        var files = new ArrayList<Emitted>();
        var problems = new ArrayList<Problem>();
        // A list, because the catalogue is written in the order drafts were
        // given and a generator whose output depends on a hash iteration order
        // produces a diff every time it runs.
        var documents = new ArrayList<Document>();
        var taken = new HashSet<String>();

        for (Draft draft : drafts) {
            MdSubsetParser.Parsed parsed = MdSubsetParser.parse(draft.source());
            for (ParseFinding error : parsed.errors()) {
                problems.add(new Problem(draft.name(), error.line(), error.message()));
            }
            if (!parsed.ok()) continue;

            Segment root = Segments.of(parsed.blocks().orElseThrow(), Segments.NO_BUDGET);
            var sections = new ArrayList<Section>();
            collect(root, "", draft, sections, problems);
            if (sections.isEmpty()) {
                problems.add(new Problem(draft.name(), 1, "nothing to serve: no prose"));
                continue;
            }
            // Documents are siblings of each other, so they answer to the same
            // rule as sections do — theirs is the first segment of every
            // address underneath them. An unpinned one is already reported.
            if (root.pinned()) {
                if (INDEX.equals(root.id())) {
                    problems.add(new Problem(draft.name(), 0, "'" + INDEX + "' is the name of the "
                            + "collection's own index; give the document another {#slug}"));
                } else if (!taken.add(root.id())) {
                    problems.add(new Problem(draft.name(), 0, "'" + root.id() + "' is already "
                            + "another document's id; give this one another {#slug}"));
                }
            }
            documents.add(new Document(root.id(), root.title(), sections));
        }

        if (!problems.isEmpty()) return new Result(List.of(), problems);

        for (Document document : documents) {
            for (Section section : document.sections()) {
                files.add(new Emitted(options.resources() + "/" + section.id() + ".json",
                                      body(section)));
            }
        }
        files.add(new Emitted(options.resources() + "/" + INDEX + ".json", index(documents)));
        files.add(new Emitted(javaPath(options), catalogue(documents, options)));
        return new Result(files, problems);
    }

    /** One document on the way out: its own address, its title, its sections. */
    private record Document(String id, String title, List<Section> sections) {}

    /** One section on the way out: an address, a title, and the prose under it. */
    private record Section(String id, String title, int level, Segment segment) {}

    /**
     * The sections of one document, in reading order, addressed by their path.
     *
     * <p>The address is built on the way down — {@code prefix} is the address
     * of whatever holds this — so a slug only has to be unique among the
     * children of one heading. That check is the {@code taken} set below, and
     * it is the whole of the uniqueness policy.</p>
     *
     * <p>A heading with no prose of its own is still not a section: {@code ## 四}
     * holding only {@code ###} children is nothing a reader opens. It must be
     * pinned all the same, because its slug is a segment of its children's
     * addresses. A heading that bears nothing at all needs no id, because
     * nothing is addressed through it.</p>
     *
     * @return whether anything here or under it became a section
     */
    private static boolean collect(Segment segment, String prefix, Draft draft,
                                   List<Section> into, List<Problem> problems) {
        String id = prefix.isEmpty() ? segment.id() : prefix + "." + segment.id();
        boolean bears = !segment.blocks().isEmpty();
        if (bears) into.add(new Section(id, segment.title(), segment.level(), segment));

        var taken = new HashSet<String>();
        for (Segment child : segment.children()) {
            if (!collect(child, id, draft, into, problems)) continue;
            bears = true;
            // An unpinned child is reported by its own frame; adding '' here
            // would report it a second time as a collision with itself.
            if (child.pinned() && !taken.add(child.id())) {
                problems.add(new Problem(draft.name(), 0, "'" + child.id()
                        + "' is the id of two sections under '" + named(segment)
                        + "'; siblings need different {#slug}s"));
            }
        }

        if (bears && !segment.pinned()) {
            problems.add(new Problem(draft.name(), 0,
                    "'" + named(segment) + "' has no id; write {#some-slug} on its heading"));
        }
        return bears;
    }

    /** A heading by its title, or by its position when it has none. */
    private static String named(Segment segment) {
        return segment.title().isEmpty() ? segment.path() : segment.title();
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

    /**
     * The shape of what was generated, in one resource.
     *
     * <p>The Java catalogue is what a library lists from, and it is flat — an
     * {@code ArticleRef} has an id and a title and no notion of being under
     * anything. The nesting is real and a reader needs it to draw a tree, so
     * the level travels here until {@code ArticleSeries} exists to carry it in
     * the catalogue itself.</p>
     *
     * <p>Generated, so reading it is not parsing a document: it is one small
     * file listing what the documents are, read once.</p>
     */
    private static String index(List<Document> documents) {
        var js = new StringBuilder("{\"documents\":[");
        boolean firstDoc = true;
        for (Document document : documents) {
            if (!firstDoc) js.append(',');
            firstDoc = false;
            List<Section> sections = document.sections();
            js.append("{\"title\":").append(MdJson.quote(document.title()))
              .append(",\"id\":").append(MdJson.quote(document.id()))
              .append(",\"sections\":[");
            for (int i = 0; i < sections.size(); i++) {
                Section s = sections.get(i);
                if (i > 0) js.append(',');
                js.append("{\"id\":").append(MdJson.quote(s.id()))
                  .append(",\"title\":").append(MdJson.quote(s.title()))
                  .append(",\"level\":").append(s.level())
                  .append(",\"chars\":").append(s.segment().chars())
                  .append('}');
            }
            js.append("]}");
        }
        return js.append("]}").toString();
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
    private static String catalogue(List<Document> documents, Options options) {
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
        for (Document document : documents) {
            String field = "DOC_" + (++n);
            java.append("    /** ").append(escape(document.title())).append(" */\n")
                .append("    public static final ArticleCollection ").append(field)
                .append(" = new Doc(\n")
                .append("            CollectionId.named(\"").append(options.pkg())
                .append(".doc").append(n).append("\"),\n")
                .append("            \"").append(escape(document.title())).append("\",\n")
                .append("            \"\",\n")
                .append("            List.of(\n");
            List<Section> sections = document.sections();
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
