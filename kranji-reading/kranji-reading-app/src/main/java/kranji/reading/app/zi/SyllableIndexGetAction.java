package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.pinyin.PinyinSyllable;
import kranji.simple.PhonicProjection;
import kranji.zi.tree.ZiTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Every syllable in the corpus, flat, with how many characters are read that
 * way.
 *
 * <h2>Why a flat list beside a tree</h2>
 *
 * <p>{@code /zi-tree} already serves the same sounds, and it is the right shape
 * for browsing: initial, then final, then tone, opened one level at a time. It
 * is the wrong shape for <em>weighing</em>. Asking "which sounds have I barely
 * started" means visiting all 1,288 of them at once and sorting by an answer
 * the server cannot compute — so what a pane needs is the whole list in one
 * module, cheap to intersect against.</p>
 *
 * <p>1,288 rows of five short fields is around 60 KB, sent once and cached by
 * URL like the census. Walking the tree client-side would save that and cost a
 * second definition of what a syllable's address is.</p>
 *
 * <h2>Built from the projection, not from the readings</h2>
 *
 * <p>So the {@code path} here is the same address {@code /zi-data} answers to,
 * by construction rather than by convention. Deriving it from a
 * {@link PinyinSyllable} instead would mean re-deciding how a zero initial is
 * spelled, and the first time the projection changed its mind the two would
 * disagree silently — one pane loading nothing while the tree beside it looked
 * right.</p>
 *
 * <p>The reading is read off the node's <b>label</b> and put through the pinyin
 * model, never assembled from the segments. Segments are an address and pinyin
 * is an orthography, and the two disagree in fifteen places: the projection
 * spells ü as {@code v} and files 韵 under {@code y/ven/4}, which is written
 * {@code yun4}, and it keeps the underlying finals {@code iou uei uen} that are
 * written {@code iu ui un}. Joining the segments produced {@code yven4} — a key
 * that matches nothing in a known set and reports a reader who knows the sound
 * as not knowing it, with no error anywhere. Spelling is the model's job.</p>
 *
 * <p>Every reading is parsed back before it is written, so a projection that
 * stopped agreeing with the pinyin model fails the build rather than the
 * intersection.</p>
 *
 * <p>Nothing here knows what any reader knows. This is the corpus half; the
 * other half never leaves the device, and the arithmetic happens where they
 * meet. The census used to make the same bargain over the wire and no longer
 * needs to: the reader counts the article it is already showing.</p>
 *
 * <p>Data literals only, like the other data actions.</p>
 */
public final class SyllableIndexGetAction
        implements GetAction<RoutingContext, SyllableIndexGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/syllable-index";

    private static final String JS = "text/javascript; charset=utf-8";

    /** No query — the index is wanted whole or not at all. */
    public record Query() implements Param._QueryString {}

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query();
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(new DocContent(indexJs(), JS));
    }

    /** One syllable: where it lives, what it is called, and how big it is. */
    public record Syllable(String reading, String path, String label,
                           String initial, String initialLabel, int characters) {}

    /** Visible for testing — every syllable, in the projection's own order. */
    public static List<Syllable> syllables() {
        var out = new ArrayList<Syllable>();
        ZiTreeNode root = PhonicProjection.INSTANCE.projection().root();
        for (ZiTreeNode initial : root.children()) {
            for (ZiTreeNode fin : initial.children()) {
                for (ZiTreeNode tone : fin.children()) {
                    // From the label through the model, not from the segments.
                    // The label is the syllable as pinyin writes it; numbered()
                    // is the canonical form the known set is keyed on.
                    String reading = PinyinSyllable.parse(tone.label()).numbered();
                    // Parsed back before it is written. This is the join key a
                    // browser matches known-set entries against, and a key that
                    // is subtly wrong intersects to nothing while looking fine.
                    PinyinSyllable.parseCanonical(reading);
                    out.add(new Syllable(reading,
                            initial.segment() + "." + fin.segment() + "." + tone.segment(),
                            tone.label(), initial.segment(), initial.label(),
                            tone.characterCount()));
                }
            }
        }
        return List.copyOf(out);
    }

    /** Visible for testing — the module text. */
    public static String indexJs() {
        List<Syllable> syllables = syllables();

        var js = new StringBuilder();
        js.append("// Generated from the Kranji corpus. Data only - no behaviour.\n");
        js.append("export const syllables = [\n");
        for (int i = 0; i < syllables.size(); i++) {
            Syllable s = syllables.get(i);
            js.append("  { r: ").append(quote(s.reading()))
              .append(", p: ").append(quote(s.path()))
              .append(", l: ").append(quote(s.label()))
              .append(", i: ").append(quote(s.initial()))
              .append(", il: ").append(quote(s.initialLabel()))
              .append(", c: ").append(s.characters()).append(" }")
              .append(i + 1 < syllables.size() ? "," : "").append("\n");
        }
        js.append("];\n");
        js.append("export const count = ").append(syllables.size()).append(";\n");
        js.append("export const characters = ")
          .append(syllables.stream().mapToInt(Syllable::characters).sum()).append(";\n");
        return js.toString();
    }

    private static String quote(String raw) {
        var sb = new StringBuilder("\"");
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            switch (c) {
                case '"'  -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default   -> {
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
                }
            }
        }
        return sb.append('"').toString();
    }
}
