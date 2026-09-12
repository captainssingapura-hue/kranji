package kranji.reading.workbench.coverage;

import kranji.reading.content.GlossCoverage;
import kranji.reading.workbench.relation.Relation;
import kranji.reading.workbench.relation.RelationSet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * Coverage as three linked relations: shelf, article, missing.
 *
 * <p>From the whole library down to the one pair an article lacks. Unscoped,
 * each level shows everything ranked worst-first; picking a row above narrows
 * the one below. The shelf grid carries the whole-library figure as its note,
 * because no shelf row states it and it is the first thing a person opening
 * the bench wants.</p>
 *
 * <p>Percentages travel as text with one decimal: a grid shows what it is
 * given, and {@code 0.9487} is not what anyone reads a coverage as.</p>
 */
public final class CoverageRelations implements RelationSet {

    public static final CoverageRelations INSTANCE = new CoverageRelations();

    public static final String SHELF   = "shelf";
    public static final String ARTICLE = "article";
    public static final String MISSING = "missing";

    private CoverageRelations() {}

    @Override public String family() { return "coverage"; }

    @Override public List<String> relations() { return List.of(SHELF, ARTICLE, MISSING); }

    @Override
    public String upstreamOf(String relation) {
        return switch (relation) {
            case ARTICLE -> SHELF;
            case MISSING -> ARTICLE;
            default      -> null;
        };
    }

    @Override
    public List<String> columnsOf(String relation) {
        // reads% leads on all three, because it is the sort order and the
        // number a person came to read. A gap's 'times' is how often THIS
        // article reads the pair; the library-wide weight sits beside it as
        // 'reads', so a gap that matters here and nowhere else is visibly that.
        return switch (relation) {
            case SHELF   -> List.of("reads%", "group", "shelf", "articles", "complete",
                                    "pairs", "missing");
            case ARTICLE -> List.of("reads%", "article", "shelf", "reads", "pairs",
                                    "missing", "address");
            case MISSING -> List.of("glyph", "reading", "times", "reads", "articles",
                                    "character");
            default      -> List.of();
        };
    }

    @Override
    public String noteOf(String relation) {
        return SHELF.equals(relation) ? CoverageSource.report().summary() : null;
    }

    @Override
    public List<Relation.Row> rowsOf(String relation) {
        GlossCoverage.Report report = CoverageSource.report();
        return switch (relation) {
            // One row per shelf, keyed on the collection id. Worst first, as
            // the report orders them.
            case SHELF -> report.shelves().stream()
                    .map(s -> new Relation.Row(s.shelfId(), "", s.shelf(),
                            List.of(percent(s.readRatio()), s.group(), s.shelf(),
                                    s.articles(), s.articlesComplete(),
                                    s.pairs(), s.pairs() - s.pairsCovered())))
                    .toList();

            // One row per article, under its shelf. The pk is the address,
            // which is what the reader itself opens an article by.
            case ARTICLE -> report.articles().stream()
                    .map(a -> new Relation.Row(a.address(), a.shelfId(), a.title(),
                            List.of(percent(a.readRatio()), a.title(), a.shelf(),
                                    a.reads(), a.pairs(), a.missing().size(), a.address())))
                    .toList();

            // One row per gap in one article, under the article, with the
            // pair's library-wide weight beside its weight here.
            case MISSING -> {
                var weight = new LinkedHashMap<String, GlossCoverage.Missing>();
                for (GlossCoverage.Missing m : report.missing()) weight.put(m.key(), m);
                var out = new ArrayList<Relation.Row>();
                for (GlossCoverage.OfArticle a : report.articles()) {
                    for (GlossCoverage.Gap gap : a.missing()) {
                        GlossCoverage.Missing m = weight.get(gap.key());
                        out.add(new Relation.Row(a.address() + "#" + gap.key(), a.address(),
                                gap.glyph(),
                                List.of(gap.glyph(), gap.reading(), gap.times(),
                                        m == null ? 0 : m.reads(),
                                        m == null ? 0 : m.articles(),
                                        m != null && m.characterKnown()
                                                ? "known, reading unwritten"
                                                : "not in the library")));
                    }
                }
                yield List.copyOf(out);
            }
            default -> List.of();
        };
    }

    private static String percent(double ratio) {
        return String.format(Locale.ROOT, "%.1f%%", 100 * ratio);
    }
}
