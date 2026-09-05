package kranji.gloss.layered;

import kranji.zi.ZiPartition;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The two sides of the stack are cut the same way, file for file.
 *
 * <h2>Why this test lives here</h2>
 *
 * <p>Neither data module can check it. kranji-gloss and kranji-gloss-seed
 * depend on kranji-core and never on each other — that separation is the
 * point of them — so the only place that can see both partition sets is the
 * module that layers them.</p>
 *
 * <h2>What it is protecting</h2>
 *
 * <p>The reason for partitioning the curated set at all: p041 on one side and
 * p041 on the other are the same characters, so a partition can be reviewed
 * with both files open. Add a 102nd file to one side, or rename the pattern,
 * and that stops being true — quietly, because both sides still load.</p>
 */
class PartitionsLineUpTest {

    private static final String CURATED = "/kranji/gloss/p%03d.tsv";
    private static final String SEEDED = "/kranji/seed/p%03d.tsv";

    @Test
    void everyPartitionHasAFileOnBothSides() {
        var missingCurated = new ArrayList<String>();
        var missingSeeded = new ArrayList<String>();

        for (int p = 0; p < ZiPartition.COUNT; p++) {
            if (!exists(CURATED.formatted(p))) missingCurated.add("p%03d".formatted(p));
            if (!exists(SEEDED.formatted(p))) missingSeeded.add("p%03d".formatted(p));
        }

        // The curated side is complete BY CONSTRUCTION - p013 holds no rows
        // today and still has a file, because "no rows yet" and "no file" are
        // different states and only one of them is a mistake.
        assertEquals(List.of(), missingCurated,
                "the curated set is missing partitions: " + missingCurated);
        assertEquals(List.of(), missingSeeded,
                "the seed is missing partitions: " + missingSeeded);
    }

    @Test
    void aCharacterFallsInTheSameFileOnBothSides() {
        // 猬 is the case that made this worth asserting: seeded in p093 as
        // "vulgar", curated in p093 as "a hedgehog". The override is only
        // findable because both live at the same index.
        assertEquals(93, ZiPartition.of(29484));

        assertTrue(read(CURATED.formatted(93)).contains("29484\t猬"),
                "猬 is curated in p093");
        assertTrue(read(SEEDED.formatted(93)).contains("29484\t猬"),
                "猬 is seeded in p093");
    }

    @Test
    void bothSidesAgreeOnTheColumns() {
        // Same header, so a row can be moved from one file to the other by
        // copying it. That is the whole ergonomic claim of the mirror.
        String columns = "# codepoint\tglyph\treading\tpriority\tmeaning\texamples\tbecause";

        assertTrue(read(CURATED.formatted(0)).contains(columns), "curated columns moved");
        assertTrue(read(SEEDED.formatted(0)).contains(columns), "seeded columns moved");
    }


    private static boolean exists(String resource) {
        try (InputStream in = PartitionsLineUpTest.class.getResourceAsStream(resource)) {
            return in != null;
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + resource, e);
        }
    }

    private static String read(String resource) {
        try (InputStream in = PartitionsLineUpTest.class.getResourceAsStream(resource)) {
            if (in == null) throw new IllegalStateException(resource + " is absent");
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + resource, e);
        }
    }
}
