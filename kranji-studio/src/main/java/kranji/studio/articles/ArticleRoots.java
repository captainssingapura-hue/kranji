package kranji.studio.articles;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The folders the editor may read, and the one file that remembers them.
 *
 * <h2>Why there is more than one</h2>
 *
 * <p>Drafts do not all live in one place. A checkout of the collections
 * repository, a folder of things being written, a scratch directory somebody is
 * trying an idea in — these are different roots and an editor that could only
 * see one would have people copying files about to look at them.</p>
 *
 * <h2>What this widens, and what it does not</h2>
 *
 * <p>It is worth being plain about the trade. Until now a single named folder
 * was the whole permission model: a studio tool that can open any path is a
 * studio tool that can read anything on the machine, and naming one folder in
 * the launch command meant nobody could ask it for more. A list somebody can
 * add to gives that up, and the honest description is that the studio can now
 * read whatever its user points it at.</p>
 *
 * <p>What survives is the second lock, which is the one that mattered against
 * the browser: a draft is still asked for by an id the server issued, an id is
 * only issued for a file inside a listed root, and a path from a query string
 * still resolves to nothing. A root is added by the person running the studio,
 * deliberately, in their own tool — not by a page they happen to have open.</p>
 *
 * <h2>Where the list lives</h2>
 *
 * <p>{@code ~/.kranji/article-roots.json} by default: roots are a fact about
 * the person, not about a checkout, and somebody running the studio from two
 * places wants the same shelf in both. {@code -Dkranji.articles.roots=…} points
 * it elsewhere for a set that should travel with a repository instead.</p>
 *
 * <p>Until that file exists the list is whatever {@code kranji.articles.dir}
 * says, so a studio launched the old way opens on the folder it was given and
 * nothing has to be configured before anything works. The first change writes
 * the file, seeded with what was already showing.</p>
 */
public final class ArticleRoots {

    /** Where the list of roots is kept. */
    public static final String STORE_PROPERTY = "kranji.articles.roots";

    private static final String DEFAULT_STORE = ".kranji/article-roots.json";

    private ArticleRoots() {}

    /** The store as configured, whether or not it exists. */
    public static Path store() {
        String configured = System.getProperty(STORE_PROPERTY);
        if (configured != null && !configured.isBlank()) {
            return Path.of(configured).toAbsolutePath().normalize();
        }
        return Path.of(System.getProperty("user.home", "."), DEFAULT_STORE)
                .toAbsolutePath().normalize();
    }

    /**
     * One root.
     *
     * <p>{@code present} rather than dropping a root that has gone: a folder
     * on a drive that is not mounted is still a root somebody chose, and
     * silently removing it from the list would be the tool losing their work
     * for them.</p>
     */
    public record Root(String id, String path, String name, boolean present) {}

    /** What a change did, or why it did nothing. */
    public record Change(boolean ok, String message) {

        static Change done() { return new Change(true, ""); }

        static Change refused(String why) { return new Change(false, why); }
    }

    /** A stable, ASCII id for a folder. Same folder, same id, every run. */
    static String idOf(String path) {
        return MdSourceFolder.idOf(path);
    }

    /**
     * Every root, in the order they were added.
     *
     * <p>Order is the author's: a list that sorted itself would move the row
     * somebody was about to click.</p>
     */
    public static List<Root> roots() {
        var out = new ArrayList<Root>();
        for (String path : paths()) out.add(describe(path));
        return List.copyOf(out);
    }

    public static Optional<Root> root(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        return roots().stream().filter(r -> r.id().equals(id)).findFirst();
    }

    /**
     * The folder behind an id, or empty when nothing was listed under it.
     *
     * <p>This is the check the routes lean on. An id is the only key, and one
     * is only ever issued for a root that is in the list.</p>
     */
    public static Optional<Path> dirOf(String id) {
        return root(id).map(r -> Path.of(r.path()));
    }

    /** The first root, which is what a pane opens on before anything is chosen. */
    public static Optional<Root> first() {
        List<Root> roots = roots();
        return roots.isEmpty() ? Optional.empty() : Optional.of(roots.get(0));
    }

    // ── Changing the list ──────────────────────────────────────────────

    /**
     * Adds a folder, if it is one.
     *
     * <p>Refused rather than accepted-and-broken when the path is not a
     * directory. A root that lists nothing looks exactly like a root whose
     * drafts have all been deleted, and the difference matters at the moment
     * somebody is wondering where their files went.</p>
     */
    public static Change add(String raw) {
        if (raw == null || raw.isBlank()) return Change.refused("a root needs a path");

        Path dir;
        try {
            dir = Path.of(raw.strip()).toAbsolutePath().normalize();
        } catch (InvalidPathException notAPath) {
            return Change.refused("'" + raw.strip() + "' is not a path");
        }
        if (!Files.isDirectory(dir)) {
            return Change.refused("'" + dir + "' is not a folder");
        }

        String path = dir.toString();
        List<String> paths = new ArrayList<>(paths());
        if (paths.contains(path)) return Change.refused("'" + path + "' is already a root");

        paths.add(path);
        write(paths);
        return Change.done();
    }

    /** Removes a root. The folder is untouched — this is a list, not a bin. */
    public static Change remove(String id) {
        List<String> paths = new ArrayList<>(paths());
        Optional<Root> found = root(id);
        if (found.isEmpty()) return Change.refused("no root with that id");

        paths.remove(found.get().path());
        write(paths);
        return Change.done();
    }

    // ── The store ──────────────────────────────────────────────────────

    /**
     * The configured paths, or what the launch command implied.
     *
     * <p>The seed is deliberately not written on read. A studio that wrote a
     * config file merely because somebody opened a pane would be deciding
     * something on their behalf; the first real change writes it.</p>
     */
    private static List<String> paths() {
        Path store = store();
        if (!Files.isRegularFile(store)) return seed();
        try {
            var json = new JsonObject(Files.readString(store, StandardCharsets.UTF_8));
            JsonArray listed = json.getJsonArray("roots", new JsonArray());
            var out = new ArrayList<String>();
            for (int i = 0; i < listed.size(); i++) {
                String path = listed.getString(i);
                if (path != null && !path.isBlank()) out.add(path);
            }
            return List.copyOf(out);
        } catch (IOException e) {
            throw new UncheckedIOException("reading " + store, e);
        } catch (RuntimeException notJson) {
            // A hand-edited file that no longer parses should not stop the
            // studio from opening — it should say so where somebody can see it.
            return seed();
        }
    }

    /** What {@code kranji.articles.dir} says, so the old launch still works. */
    private static List<String> seed() {
        Path dir = MdSourceFolder.dir();
        return Files.isDirectory(dir) ? List.of(dir.toString()) : List.of();
    }

    private static void write(List<String> paths) {
        Path store = store();
        try {
            Path parent = store.getParent();
            if (parent != null) Files.createDirectories(parent);
            var json = new JsonObject().put("roots", new JsonArray(new ArrayList<Object>(paths)));
            Files.writeString(store, json.encodePrettily(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("writing " + store, e);
        }
    }

    private static Root describe(String path) {
        Path dir = Path.of(path);
        Path name = dir.getFileName();
        return new Root(idOf(path), path,
                        name == null ? path : name.toString(),
                        Files.isDirectory(dir));
    }
}
