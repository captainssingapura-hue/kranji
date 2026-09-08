package kranji.studio.articles;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * The folder of {@code .kmd} files the workbench reads.
 *
 * <h2>Why the file system and not the classpath</h2>
 *
 * <p>Everything else in this application reads articles from a jar, because a
 * published article is a build artifact. A draft is not: it is a file somebody
 * is editing, and the loop the workbench exists for is <i>save, look, fix</i>.
 * Reading from disk is what makes that loop a second long instead of a build
 * long.</p>
 *
 * <p>Which is also why nothing here caches. A cached draft is a workbench that
 * shows you the file you saved a minute ago.</p>
 *
 * <h2>{@code .kmd}, not {@code .md}</h2>
 *
 * <p>What these files hold is not CommonMark. {@code ‹…›} runs, {@code 字{dì}}
 * readings and a {@code verse} fence are extensions, and tables, links and raw
 * HTML are refused outright rather than passed through — see the subset note.
 * A file named {@code .md} invites every tool in the chain to treat it as
 * markdown and be quietly wrong about all of that, so the extension says what
 * the file actually is.</p>
 *
 * <h2>One folder, named on purpose — and now a tree inside it</h2>
 *
 * <p>{@code -Dkranji.articles.dir=…}, defaulting to {@code articles-inbox}
 * beside wherever the studio was started. A studio tool that could open any
 * path is a studio tool that can read anything on the machine; a named folder
 * is the whole permission model and it is enough for a workbench.</p>
 *
 * <p>Inside it, the directory structure <b>is</b> the shape the editor shows:
 * no manifest, no ordering file, nothing to keep in step with the folder. That
 * is an authoring convenience and nothing more — where a draft sits says
 * nothing about the address it publishes under, so moving one between folders
 * is free and never re-addresses a published section.</p>
 */
public final class MdSourceFolder {

    /** Where drafts are read from. */
    public static final String DIR_PROPERTY = "kranji.articles.dir";

    /** Kranji markdown. Lowercase; the check that uses it folds case. */
    public static final String EXTENSION = ".kmd";

    private static final String DEFAULT_DIR = "articles-inbox";

    private MdSourceFolder() {}

    /** The folder as configured, whether or not it exists. */
    public static Path dir() {
        return Path.of(System.getProperty(DIR_PROPERTY, DEFAULT_DIR)).toAbsolutePath().normalize();
    }

    /**
     * One draft. {@code chars} is characters, not bytes — a Han character is
     * three, and the difference is 2.6× on a Chinese document.
     *
     * <p>{@code path} is where it sits under the folder, always with
     * {@code '/'} so that an id is the same file on every platform. {@code name}
     * is just the last part of it, for a label.</p>
     *
     * <p>{@code id} is how a draft is asked for. The path is not, and that is
     * deliberate twice over. A Chinese file name does not survive a query
     * string — the request arrives with every Han character replaced by
     * {@code ?}, which is where this design came from — and a client that can
     * only name drafts the server has already listed cannot name anything
     * else at all. The path check in {@link #read} stays as a second lock.</p>
     */
    public record Draft(String id, String name, String path, long chars, long modifiedEpochMs) {}

    /** A stable, ASCII id for a relative path. Same path, same id, every run. */
    static String idOf(String path) {
        try {
            byte[] d = java.security.MessageDigest.getInstance("SHA-256")
                    .digest(path.getBytes(StandardCharsets.UTF_8));
            var sb = new StringBuilder(16);
            for (int i = 0; i < 8; i++) sb.append(String.format("%02x", d[i]));
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is required by the platform", e);
        }
    }

    /**
     * Every {@code .kmd} file under the folder, in path order.
     *
     * <p>Path order rather than newest-first, because what this feeds is a tree
     * and a tree that reorders itself when you save is one nobody can point
     * at.</p>
     *
     * <p>Empty when the folder is absent, rather than an error. A workbench
     * pointed at a folder nobody has made yet should say so on screen, not
     * fail to load.</p>
     */
    public static List<Draft> drafts() {
        return drafts(dir());
    }

    /**
     * The same, under a root the caller chose.
     *
     * <p>Ids are relative to their root, so two roots may each hold a
     * {@code yu.kmd} and the two are different drafts. Which means an id is
     * only an answer alongside the root it came from — every caller of
     * {@link #read(Path, String)} passes both.</p>
     */
    public static List<Draft> drafts(Path dir) {
        if (!Files.isDirectory(dir)) return List.of();

        var found = new ArrayList<Draft>();
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path d, BasicFileAttributes attrs) {
                    // Somebody will eventually point this at a checkout, and
                    // walking .git to find no drafts in it helps nobody.
                    return !d.equals(dir) && hidden(d)
                            ? FileVisitResult.SKIP_SUBTREE
                            : FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path f, BasicFileAttributes attrs) {
                    if (attrs.isRegularFile() && isDraft(f)) found.add(describe(dir, f));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path f, IOException unreadable) {
                    // One unreadable file is not a reason to show no folder.
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException("walking " + dir, e);
        }
        found.sort(Comparator.comparing(Draft::path));
        return List.copyOf(found);
    }

    private static boolean hidden(Path dir) {
        Path name = dir.getFileName();
        return name != null && name.toString().startsWith(".");
    }

    private static boolean isDraft(Path file) {
        return file.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(EXTENSION);
    }

    private static Draft describe(Path dir, Path file) {
        try {
            String path = relative(dir, file);
            String text = Files.readString(file, StandardCharsets.UTF_8);
            return new Draft(idOf(path), file.getFileName().toString(), path,
                    text.codePointCount(0, text.length()),
                    Files.getLastModifiedTime(file).toMillis());
        } catch (IOException e) {
            throw new UncheckedIOException("reading " + file, e);
        }
    }

    /** Always with {@code '/'}, so the same file has the same id everywhere. */
    private static String relative(Path dir, Path file) {
        return dir.relativize(file).toString().replace('\\', '/');
    }

    /** The draft with this id, if the folder still holds it. */
    public static Optional<Draft> draft(String id) {
        return draft(dir(), id);
    }

    /** The draft with this id under this root, if it still holds it. */
    public static Optional<Draft> draft(Path dir, String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        return drafts(dir).stream().filter(d -> d.id().equals(id)).findFirst();
    }

    /**
     * One draft's text, by the id the listing gave it.
     *
     * <p>An id only resolves if the folder still holds a file at that path, so
     * a caller cannot ask for anything the server did not offer. The resolve
     * below is then a second lock rather than the only one: the path came from
     * this class's own walk, and it is normalised and checked back against the
     * folder before anything is opened.</p>
     */
    public static Optional<String> read(String id) {
        return read(dir(), id);
    }

    /** The same, under a root the caller chose. */
    public static Optional<String> read(Path root, String id) {
        Optional<Draft> found = draft(root, id);
        if (found.isEmpty()) return Optional.empty();

        // Absolute before the comparison, or startsWith is answering a
        // question about two relative paths and not about containment.
        Path dir = root.toAbsolutePath().normalize();
        Path file = dir.resolve(found.get().path()).normalize();
        if (!file.startsWith(dir) || !Files.isRegularFile(file)) return Optional.empty();
        try {
            return Optional.of(Files.readString(file, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("reading " + file, e);
        }
    }
}
