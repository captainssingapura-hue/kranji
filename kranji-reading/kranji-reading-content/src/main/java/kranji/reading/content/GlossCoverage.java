package kranji.reading.content;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.LibraryTree;
import kranji.reading.model.Article;
import kranji.reading.model.ArticleCensus;
import kranji.simple.gloss.Glosses;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * How much of a library the meanings can explain.
 *
 * <h2>Three grains, and which one matters</h2>
 *
 * <p><b>Reads</b> are what a child meets: every character token in every
 * article, so a character read forty times weighs forty. A meaning behind 80%
 * of reads means four squares in five have an answer when tapped, whatever
 * the dictionary's own size says. This is the number a floor is held on.</p>
 *
 * <p><b>Pairs</b> are distinct (character, reading) — the grain a gloss is
 * written at and the grain the known set is keyed on. This is the worklist's
 * length.</p>
 *
 * <p><b>Characters</b> are whether the library has an entry for the character
 * at all. It is the coarsest grain and the one the reader's Character pane
 * says out loud — "not in the meanings library yet" — so it is counted here at
 * the same grain it is shown.</p>
 *
 * <h2>Covered means answered, not correct</h2>
 *
 * <p>A pair is covered when the composed collections hold a sound gloss for
 * it. Whether that gloss is any good is the studio's review chain's question,
 * not this one's; a coverage figure that folded in quality would be two
 * numbers pretending to be one.</p>
 *
 * <h2>Pure</h2>
 *
 * <p>Takes the tree and the registry rather than finding them, so a test can
 * measure a demo library against no glosses at all and a workbench can measure
 * whatever is mounted against whatever is discovered. Nothing here is cached;
 * the caller holds the report.</p>
 */
public final class GlossCoverage {

    private GlossCoverage() {}

    /** One uncovered pair inside one article, and how often that article reads it. */
    public record Gap(String key, int codePoint, String glyph, String reading, int times) {}

    /**
     * One article, measured. {@code missing} is its uncovered pairs, most-read
     * first. {@code shelfId} is the collection id, which is what a view keys a
     * shelf on - two shelves may share a title under different groups.
     */
    public record OfArticle(String shelfId, String group, String shelf, String address, String title,
                            int reads, int readsCovered,
                            int pairs, int pairsCovered,
                            int characters, int charactersKnown,
                            List<Gap> missing) {

        public OfArticle {
            missing = List.copyOf(missing);
        }

        public double readRatio() { return ratio(readsCovered, reads); }

        /** Every square in it has a meaning behind it. */
        public boolean complete() { return pairs == pairsCovered; }
    }

    /** One shelf, summed over its articles. */
    public record OfShelf(String shelfId, String group, String shelf, int articles, int articlesComplete,
                          int reads, int readsCovered, int pairs, int pairsCovered) {

        public double readRatio() { return ratio(readsCovered, reads); }
    }

    /**
     * One uncovered pair, weighed by the whole library.
     *
     * @param characterKnown the library has the character under some other
     *                       reading — a gap inside an entry rather than a
     *                       character it has not reached
     */
    public record Missing(String key, int codePoint, String glyph, String reading,
                          boolean characterKnown, int reads, int articles) {}

    /** The whole measurement. Lists are worst-first; the worklist is most-read first. */
    public record Report(String library,
                         List<OfArticle> articles, List<OfShelf> shelves, List<Missing> missing,
                         int reads, int readsCovered,
                         int pairs, int pairsCovered,
                         int characters, int charactersKnown) {

        public Report {
            articles = List.copyOf(articles);
            shelves = List.copyOf(shelves);
            missing = List.copyOf(missing);
        }

        public double readRatio()      { return ratio(readsCovered, reads); }
        public double pairRatio()      { return ratio(pairsCovered, pairs); }
        public double characterRatio() { return ratio(charactersKnown, characters); }

        public long articlesComplete() {
            return articles.stream().filter(OfArticle::complete).count();
        }

        /** One line, for a status bar or an assertion message. */
        public String summary() {
            return String.format(Locale.ROOT,
                    "%s: meanings behind %.1f%% of reads (%d of %d), %.1f%% of pairs (%d of %d), "
                  + "%.1f%% of characters (%d of %d); %d of %d articles complete",
                    library, 100 * readRatio(), readsCovered, reads,
                    100 * pairRatio(), pairsCovered, pairs,
                    100 * characterRatio(), charactersKnown, characters,
                    articlesComplete(), articles.size());
        }

        /** The report, as the file a build leaves behind. */
        public String render() {
            var sb = new StringBuilder();
            sb.append("Gloss coverage - ").append(library).append('\n');
            sb.append("=".repeat(17 + library.length())).append("\n\n");
            sb.append(String.format(Locale.ROOT,
                    "reads       %7d of %7d  %5.1f%%   every square a reader meets\n",
                    readsCovered, reads, 100 * readRatio()));
            sb.append(String.format(Locale.ROOT,
                    "pairs       %7d of %7d  %5.1f%%   distinct (character, reading)\n",
                    pairsCovered, pairs, 100 * pairRatio()));
            sb.append(String.format(Locale.ROOT,
                    "characters  %7d of %7d  %5.1f%%   characters the library has at all\n",
                    charactersKnown, characters, 100 * characterRatio()));
            sb.append(String.format(Locale.ROOT,
                    "articles    %7d of %7d  %5.1f%%   every pair in them covered\n\n",
                    articlesComplete(), articles.size(),
                    100 * ratio((int) articlesComplete(), articles.size())));

            sb.append("By shelf, worst first\n");
            sb.append(String.format(Locale.ROOT, "  %6s  %9s  %6s  %s\n",
                    "reads%", "articles", "compl.", "shelf"));
            for (OfShelf s : shelves) {
                sb.append(String.format(Locale.ROOT, "  %5.1f%%  %9d  %6d  %s / %s\n",
                        100 * s.readRatio(), s.articles(), s.articlesComplete(),
                        s.group(), s.shelf()));
            }

            sb.append("\nBy article, worst first\n");
            sb.append(String.format(Locale.ROOT, "  %6s  %5s  %7s  %s\n",
                    "reads%", "pairs", "missing", "article"));
            for (OfArticle a : articles) {
                sb.append(String.format(Locale.ROOT, "  %5.1f%%  %5d  %7d  %s  (%s)\n",
                        100 * a.readRatio(), a.pairs(), a.missing().size(),
                        a.title(), a.address()));
            }

            sb.append("\nMissing, most-read first - the worklist\n");
            sb.append(String.format(Locale.ROOT, "  %-4s %-8s %6s  %8s  %s\n",
                    "", "reading", "reads", "articles", "character"));
            for (Missing m : missing) {
                sb.append(String.format(Locale.ROOT, "  %-4s %-8s %6d  %8d  %s\n",
                        m.glyph(), m.reading(), m.reads(), m.articles(),
                        m.characterKnown() ? "known, this reading unwritten"
                                           : "not in the library"));
            }
            return sb.toString();
        }
    }

    /**
     * Measures a tree against a registry.
     *
     * <p>An article that does not parse is left out rather than counted as
     * uncovered — it is not a coverage problem, and {@code ArticlesTest} is
     * already the place it fails.</p>
     */
    public static Report of(String library, LibraryTree tree, Glosses glosses) {
        var articles = new ArrayList<OfArticle>();
        var byShelf = new LinkedHashMap<String, int[]>();
        var shelfNames = new LinkedHashMap<String, String[]>();
        var missingReads = new LinkedHashMap<String, Integer>();
        var missingArticles = new LinkedHashMap<String, Integer>();
        var allPairs = new LinkedHashSet<String>();
        var allChars = new LinkedHashSet<Integer>();
        int reads = 0;
        int readsCovered = 0;

        for (LibraryTree group : topLevel(tree)) {
            for (ArticleCollection c : group.collections()) {
                for (ArticleRef ref : c.articles()) {
                    var address = c.address(ref.id());
                    var parsed = Articles.read(address, ref).flatMap(ParsedArticle::article);
                    if (parsed.isEmpty()) continue;
                    OfArticle a = measure(c.id().toString(), plain(group.title()),
                                          plain(c.title()), address.toString(),
                                          parsed.get(), glosses);
                    articles.add(a);

                    reads += a.reads();
                    readsCovered += a.readsCovered();
                    for (var pair : ArticleCensus.of(parsed.get()).pairs().entrySet()) {
                        allPairs.add(pair.getKey());
                        allChars.add(codePointOf(pair.getKey()));
                    }
                    for (Gap gap : a.missing()) {
                        missingReads.merge(gap.key(), gap.times(), Integer::sum);
                        missingArticles.merge(gap.key(), 1, Integer::sum);
                    }

                    // Keyed on the collection's id rather than its title: two
                    // shelves may share a title under different groups.
                    String shelfKey = c.id().toString();
                    shelfNames.putIfAbsent(shelfKey, new String[] { plain(group.title()), plain(c.title()) });
                    int[] sums = byShelf.computeIfAbsent(shelfKey, k -> new int[6]);
                    sums[0]++;
                    sums[1] += a.complete() ? 1 : 0;
                    sums[2] += a.reads();
                    sums[3] += a.readsCovered();
                    sums[4] += a.pairs();
                    sums[5] += a.pairsCovered();
                }
            }
        }

        int pairsCovered = 0;
        for (String key : allPairs) if (glosses.find(key).isPresent()) pairsCovered++;
        int charsKnown = 0;
        for (int cp : allChars) if (knows(glosses, cp)) charsKnown++;

        var shelves = new ArrayList<OfShelf>();
        for (var e : byShelf.entrySet()) {
            String[] names = shelfNames.get(e.getKey());
            int[] s = e.getValue();
            shelves.add(new OfShelf(e.getKey(), names[0], names[1],
                    s[0], s[1], s[2], s[3], s[4], s[5]));
        }

        var missing = new ArrayList<Missing>();
        for (var e : missingReads.entrySet()) {
            int cp = codePointOf(e.getKey());
            missing.add(new Missing(e.getKey(), cp, new String(Character.toChars(cp)),
                    e.getKey().substring(e.getKey().indexOf(':') + 1),
                    knows(glosses, cp), e.getValue(), missingArticles.get(e.getKey())));
        }

        // Worst first, and ties broken by name so two builds of the same data
        // write the same file.
        articles.sort(Comparator.comparingDouble(OfArticle::readRatio)
                                .thenComparing(OfArticle::address));
        shelves.sort(Comparator.comparingDouble(OfShelf::readRatio)
                               .thenComparing(OfShelf::group).thenComparing(OfShelf::shelf));
        missing.sort(Comparator.comparingInt(Missing::reads).reversed()
                               .thenComparing(Missing::key));

        return new Report(library, articles, shelves, missing,
                reads, readsCovered, allPairs.size(), pairsCovered,
                allChars.size(), charsKnown);
    }

    private static OfArticle measure(String shelfId, String group, String shelf, String address,
                                     Article article, Glosses glosses) {
        ArticleCensus census = ArticleCensus.of(article);
        int readsCovered = 0;
        int pairsCovered = 0;
        var missing = new ArrayList<Gap>();
        Set<Integer> chars = new LinkedHashSet<>();
        for (var pair : census.pairs().entrySet()) {
            int cp = codePointOf(pair.getKey());
            chars.add(cp);
            if (glosses.find(pair.getKey()).isPresent()) {
                readsCovered += pair.getValue();
                pairsCovered++;
            } else {
                missing.add(new Gap(pair.getKey(), cp, new String(Character.toChars(cp)),
                        pair.getKey().substring(pair.getKey().indexOf(':') + 1),
                        pair.getValue()));
            }
        }
        int charsKnown = 0;
        for (int cp : chars) if (knows(glosses, cp)) charsKnown++;
        missing.sort(Comparator.comparingInt(Gap::times).reversed().thenComparing(Gap::key));
        return new OfArticle(shelfId, group, shelf, address, plain(article.title()),
                census.total(), readsCovered,
                census.pairs().size(), pairsCovered,
                chars.size(), charsKnown, missing);
    }

    private static boolean knows(Glosses glosses, int codePoint) {
        try {
            return glosses.find(new ZiCharUTF8(codePoint)).isPresent();
        } catch (IllegalArgumentException notHan) {
            return false;
        }
    }


    /**
     * A title without its reading overrides — {@code 地{dì}图} as {@code 地图}.
     *
     * <p>A title may carry the same {@code 字{dì}} syntax a body does, for the
     * tree to annotate it correctly. That is an instruction to an annotator,
     * and this report is plain text with no annotator behind it, so the
     * braces are stripped rather than printed. The same rule
     * {@code PinyinLabel} applies before it sounds a label.</p>
     */
    private static String plain(String title) {
        if (title == null || title.indexOf('{') < 0) return title == null ? "" : title;
        var sb = new StringBuilder(title.length());
        int i = 0;
        while (i < title.length()) {
            char c = title.charAt(i);
            if (c == '{') {
                int close = title.indexOf('}', i);
                if (close > 0) { i = close + 1; continue; }
            }
            sb.append(c);
            i++;
        }
        return sb.toString();
    }
    private static int codePointOf(String pairKey) {
        return Integer.parseInt(pairKey.substring(0, pairKey.indexOf(':')));
    }

    /** The root's own children, whatever they are — or the root itself when it is a shelf. */
    private static List<LibraryTree> topLevel(LibraryTree tree) {
        return tree instanceof LibraryTree.Branch b ? b.children() : List.of(tree);
    }

    private static double ratio(int part, int whole) {
        return whole == 0 ? 1.0 : (double) part / whole;
    }
}
