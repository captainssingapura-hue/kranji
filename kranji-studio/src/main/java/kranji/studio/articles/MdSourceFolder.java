package kranji.studio.articles;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * The folder of {@code .md} files the workbench reads.
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
 * <h2>One folder, named on purpose</h2>
 *
 * <p>{@code -Dkranji.articles.dir=…}, defaulting to {@code articles-inbox}
 * beside wherever the studio was started. A studio tool that could open any
 * path is a studio tool that can read anything on the machine; a named folder
 * is the whole permission model and it is enough for a workbench.</p>
 */
public final class MdSourceFolder {

    /** Where drafts are read from. */
    public static final String DIR_PROPERTY = "kranji.articles.dir";

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
     * <p>{@code id} is how a draft is asked for. The name is not, and that is
     * deliberate twice over. A Chinese file name does not survive a query
     * string — the request arrives with every Han character replaced by
     * {@code ?}, which is where this design came from — and a client that can
     * only name drafts the server has already listed cannot name anything
     * else at all. The path checks below stay as a second lock.</p>
     */
    public record Draft(String id, String name, long chars, long modifiedEpochMs) {}

    /** A stable, ASCII id for a file name. Same name, same id, every run. */
    static String idOf(String name) {
        try {
            byte[] d = java.security.MessageDigest.getInstance("SHA-256")
                    .digest(name.getBytes(StandardCharsets.UTF_8));
            var sb = new StringBuilder(16);
            for (int i = 0; i < 8; i++) sb.append(String.format("%02x", d[i]));
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is required by the platform", e);
        }
    }

    /**
     * Every {@code .md} file in the folder, newest first.
     *
     * <p>Empty when the folder is absent, rather than an error. A workbench
     * pointed at a folder nobody has made yet should say so on screen, not
     * fail to load.</p>
     */
    public static List<Draft> drafts() {
        Path dir = dir();
        if (!Files.isDirectory(dir)) return List.of();
        try (var found = Files.list(dir)) {
            return found.filter(p -> Files.isRegularFile(p)
                            && p.getFileName().toString().toLowerCase().endsWith(".md"))
                    .map(MdSourceFolder::describe)
                    .sorted(Comparator.comparingLong(Draft::modifiedEpochMs).reversed())
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException("listing " + dir, e);
        }
    }

    private static Draft describe(Path p) {
        try {
            String name = p.getFileName().toString();
            String text = Files.readString(p, StandardCharsets.UTF_8);
            return new Draft(idOf(name), name,
                    text.codePointCount(0, text.length()),
                    Files.getLastModifiedTime(p).toMillis());
        } catch (IOException e) {
            throw new UncheckedIOException("reading " + p, e);
        }
    }

    /** The draft with this id, if the folder still holds it. */
    public static Optional<Draft> draft(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        return drafts().stream().filter(d -> d.id().equals(id)).findFirst();
    }

    /**
     * One draft's text, by the id the listing gave it.
     *
     * <p>An id only resolves if the folder still holds a file with that name,
     * so a caller cannot ask for anything the server did not offer. The path
     * checks below are then a second lock rather than the only one: a name is
     * a name and never a path, and anything carrying a separator or a
     * {@code ..} is refused rather than resolved.</p>
     */
    public static Optional<String> read(String id) {
        Optional<Draft> found = draft(id);
        if (found.isEmpty()) return Optional.empty();

        String name = found.get().name();
        if (name.contains("/") || name.contains("\\") || name.contains("..")) return Optional.empty();

        Path file = dir().resolve(name).normalize();
        if (!file.startsWith(dir()) || !Files.isRegularFile(file)) return Optional.empty();
        try {
            return Optional.of(Files.readString(file, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("reading " + file, e);
        }
    }
}
