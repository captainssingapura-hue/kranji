package kranji.library.zhinan;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.CollectionId;

import java.util.List;

/**
 * 使用指南 — how to use the reader, written in the language it teaches.
 *
 * <h2>Why the instructions are an article</h2>
 *
 * <p>Because the reader is the thing being explained. A help page in English
 * beside a Chinese library asks the learner to leave the app to find out how
 * the app works, and hands them a page with no pinyin over it — the one page
 * where they are most certainly a beginner. Put the same words in the reader
 * and the instructions are annotated, the characters can be marked while they
 * are read, and following the guide <em>is</em> the first exercise.</p>
 *
 * <p>It is also the honest test of the claim. If a first article cannot be
 * read here with the help the reader gives, the help is not enough, and this
 * is where that shows.</p>
 *
 * <h2>Written to be readable on day one</h2>
 *
 * <p>Short sentences, common characters, and one instruction per paragraph.
 * Every character takes the corpus principal reading — nothing here needs an
 * override, which is itself a check that the vocabulary stayed ordinary.</p>
 *
 * <p>The panes are not named. Their labels are the front end's and in English,
 * and a sentence that says which button to press is wrong the week the button
 * moves; what is described is what the reader does — mark a character, ask
 * about one, see how far they have got — which is the part that is true of the
 * product rather than of this month's layout.</p>
 *
 * <p>Written for this project, like 科普读物. Nothing is adapted from a
 * source.</p>
 */
public final class ZhiNanCollections {

    private ZhiNanCollections() {}

    private record Bundle(CollectionId id, String title, String summary,
                          List<ArticleRef> articles) implements ArticleCollection {}

    private static ArticleCollection bundle(String id, String title, String summary,
                                            ArticleRef... articles) {
        return new Bundle(CollectionId.named("kranji.library.zhinan." + id),
                title, summary, List.of(articles));
    }

    private static ArticleRef a(String slug, String title) {
        return ArticleRef.of(slug, title, "/kranji/articles/zhinan/" + slug + ".txt");
    }

    public static final ArticleCollection SHI_YONG = bundle("shiyong",
            "使用指南", "How to read here, read here.",
            a("cong-zhe-li-kai-shi", "从这里开始"));

    public static List<ArticleCollection> all() {
        return List.of(SHI_YONG);
    }
}
