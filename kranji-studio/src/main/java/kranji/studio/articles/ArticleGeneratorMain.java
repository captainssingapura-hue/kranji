package kranji.studio.articles;

import kranji.studio.articles.ArticleGenerator.Draft;
import kranji.studio.articles.ArticleGenerator.Emitted;
import kranji.studio.articles.ArticleGenerator.Options;
import kranji.studio.articles.ArticleGenerator.Result;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * The generator, from a build.
 *
 * <pre>{@code
 * mvn -pl kranji-studio exec:java \
 *     -Dexec.mainClass=kranji.studio.articles.ArticleGeneratorMain \
 *     -Dexec.args="<drafts> <java-out> <resource-out> <package> <Class> <resource-prefix>"
 * }</pre>
 *
 * <h2>It writes nothing while anything is wrong</h2>
 *
 * <p>Every problem is printed and the exit code is 1, which is what makes this
 * a build step rather than a script. A half-generated library is worse than
 * none: the half that generated looks finished, and the failure would be
 * discovered by a reader rather than by whoever caused it.</p>
 *
 * <h2>Two output roots</h2>
 *
 * <p>Java and resources go to different places because Maven puts them in
 * different places. The generator names its files; this decides where a
 * {@code .java} and a {@code .json} each belong, which is the only thing about
 * the layout that is Maven's business rather than the format's.</p>
 */
public final class ArticleGeneratorMain {

    private ArticleGeneratorMain() {}

    private static final String USAGE =
            "usage: <drafts-dir> <java-out> <resource-out> <package> <Class> <resource-prefix>";

    public static void main(String[] args) throws IOException {
        if (args.length != 6) {
            System.err.println(USAGE);
            System.exit(2);
            return;
        }
        Path drafts = Path.of(args[0]);
        Path javaOut = Path.of(args[1]);
        Path resourceOut = Path.of(args[2]);
        var options = Options.of(args[3], args[4], args[5]);

        List<Draft> read = read(drafts);
        if (read.isEmpty()) {
            System.err.println("no .md drafts in " + drafts.toAbsolutePath());
            System.exit(2);
            return;
        }

        Result result = ArticleGenerator.generate(read, options);
        if (!result.ok()) {
            System.err.println(result.problems().size() + " problem(s); nothing written:");
            for (var problem : result.problems()) System.err.println("  " + problem);
            System.exit(1);
            return;
        }

        for (Emitted file : result.files()) {
            Path root = file.path().endsWith(".java") ? javaOut : resourceOut;
            Path target = root.resolve(file.path());
            Files.createDirectories(target.getParent());
            Files.writeString(target, file.content(), StandardCharsets.UTF_8);
            System.out.println("wrote " + target);
        }
        System.out.println(read.size() + " draft(s) -> " + result.files().size() + " file(s)");
    }

    /** Every {@code .md} in the folder, in name order so a run is repeatable. */
    private static List<Draft> read(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) return List.of();
        try (Stream<Path> files = Files.list(dir)) {
            var out = new ArrayList<Draft>();
            files.filter(p -> p.toString().endsWith(".md")).sorted().forEach(p -> {
                try {
                    out.add(new Draft(p.getFileName().toString(),
                                      Files.readString(p, StandardCharsets.UTF_8)));
                } catch (IOException e) {
                    throw new UncheckedIOException("could not read " + p, e);
                }
            });
            return List.copyOf(out);
        }
    }
}
