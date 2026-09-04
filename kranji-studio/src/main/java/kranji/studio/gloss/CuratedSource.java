package kranji.studio.gloss;

import kranji.gloss.tsv.GlossTsv;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiPartition;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The curated partition files, read from the working tree when it is there and
 * from the classpath when it is not.
 *
 * <h2>Why not {@code HandCrafted}</h2>
 *
 * <p>{@link kranji.gloss.handcrafted.HandCrafted} loads once into a static and
 * never changes, which is right for the collection a build ships — a reader
 * must not see its glosses shift under it. It is exactly wrong for a bench.
 * Editing p041 and seeing the result meant a rebuild, a re-copy of the jar and
 * a restart, three steps that turned a one-line fix into a round trip.</p>
 *
 * <p>So the bench reads its own copy, from disk, every time it is asked. 101
 * small files parse in a few milliseconds; caching them would only reintroduce
 * the staleness this exists to remove.</p>
 *
 * <h2>Two locations, and which one wins</h2>
 *
 * <p>Disk first, then the classpath. Running from the repository the source
 * files are right there and are what somebody is editing; running from a
 * distributed jar there is no working tree and the packaged copy is all there
 * is. Each partition reports which it came from, because "my edit did nothing"
 * and "you are reading the jar" are the same symptom.</p>
 *
 * <h2>A bad edit is shown, not thrown</h2>
 *
 * <p>Somebody hand-editing a TSV will mistype one, and a bench that dies on
 * class-init because of it is a bench that has to be restarted to be told what
 * is wrong. Problems travel with the partition and the rows come back empty.</p>
 */
public final class CuratedSource {

    /** Override with {@code -Dkranji.gloss.source=/some/dir} to read elsewhere. */
    public static final String PROPERTY = "kranji.gloss.source";

    private static final String DEFAULT_DIR = "kranji-gloss/src/main/resources/kranji/gloss";
    private static final String RESOURCE = "/kranji/gloss/p%03d.tsv";

    private CuratedSource() {}

    /**
     * One partition as the bench sees it.
     *
     * @param origin   where the bytes came from, for a person to check
     * @param problems parse failures, each with its line; rows are empty when
     *                 this is not
     */
    public record Partition(int partition, String origin, boolean fromDisk,
                            List<ZiGloss> glosses, List<String> problems) {

        public Partition {
            glosses = List.copyOf(glosses);
            problems = List.copyOf(problems);
        }

        public String label() { return "p%03d".formatted(partition); }

        public boolean ok() { return problems.isEmpty(); }
    }

    /** Where the working copy is expected. */
    public static Path directory() {
        return Path.of(System.getProperty(PROPERTY, DEFAULT_DIR));
    }

    /** One partition, read fresh. */
    public static Partition read(int partition) {
        Path file = directory().resolve("p%03d.tsv".formatted(partition));
        boolean onDisk = Files.isReadable(file);

        String resource = RESOURCE.formatted(partition);
        String text = onDisk ? readFile(file) : readResource(resource);
        if (text == null) {
            return new Partition(partition, "absent", false, List.of(),
                    List.of("no p%03d.tsv on disk or classpath".formatted(partition)));
        }

        String origin = onDisk ? file.toAbsolutePath().normalize().toString()
                               : "classpath:" + resource;
        GlossTsv.Read<ZiGloss> parsed = GlossTsv.readSenses(origin, text);
        if (!parsed.ok()) {
            return new Partition(partition, origin, onDisk, List.of(),
                    parsed.problems().stream().map(Object::toString).toList());
        }
        return new Partition(partition, origin, onDisk, parsed.entries(), List.of());
    }

    /** All 101, read fresh. */
    public static List<Partition> readAll() {
        var out = new ArrayList<Partition>(ZiPartition.COUNT);
        for (int p = 0; p < ZiPartition.COUNT; p++) out.add(read(p));
        return List.copyOf(out);
    }

    private static String readFile(Path file) {
        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + file, e);
        }
    }

    private static String readResource(String resource) {
        try (InputStream in = CuratedSource.class.getResourceAsStream(resource)) {
            if (in == null) return null;
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + resource, e);
        }
    }
}
