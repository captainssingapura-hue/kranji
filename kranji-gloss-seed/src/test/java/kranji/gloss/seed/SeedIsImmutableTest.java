package kranji.gloss.seed;

import kranji.zi.ZiPartition;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The seed is a fixed substrate, and this is what makes that true rather than
 * remembered.
 *
 * <h2>Why a hash and not a rule</h2>
 *
 * <p>"Do not edit the seed" is a sentence in a file header. It survives until
 * somebody fixes one gloss by hand, at which point the data and the generator
 * disagree and the next {@code GlossSeedMain all} silently reverts the fix —
 * the failure being invisible is the whole problem, because a hand-edit and a
 * regeneration produce files that look equally plausible.</p>
 *
 * <p>So the bytes are pinned. Any edit fails here, and a deliberate
 * regeneration is a one-line change to {@link #DIGEST} arriving in the same
 * commit as the data it describes. That is the difference between immutable
 * and merely discouraged.</p>
 *
 * <h2>Why not regenerate and diff</h2>
 *
 * <p>That would be the stronger check and it cannot run: the generator needs
 * the 8MB Unihan drop, which lives in {@code input/} and is not tracked, so
 * the test would pass by being skipped on every clean checkout. Hashing what
 * is committed asks a smaller question and can always answer it.</p>
 */
class SeedIsImmutableTest {

    /**
     * SHA-256 over every seeded partition and its flag file, in partition
     * order. Regenerated 2026-09-04 after the proper-noun strike became
     * conditional.
     */
    private static final String DIGEST =
            "f66d3b6dc2a0c64c48857e166ac2eb484c05fe763e11c037f0a5f3b458124f04";

    /** Legible companions, so a failure says what moved before it says how much. */
    private static final int FILES = 202;
    private static final int SENSE_ROWS = 11538;

    @Test
    void theCommittedSeedIsExactlyWhatWasGenerated() {
        var digest = digest();
        assertEquals(FILES, digest.files(), "a partition file appeared or vanished");
        assertEquals(SENSE_ROWS, digest.senseRows(), "the number of seeded senses moved");
        assertEquals(DIGEST, digest.hex(),
                "the seed is not what it was. If GlossSeedMain produced this, update "
              + "DIGEST in the same commit; if a person edited a partition file, that "
              + "edit belongs in a layer above the seed, not in the seed");
    }

    private record Digest(String hex, int files, int senseRows) {}

    private static Digest digest() {
        MessageDigest sha = sha256();
        int files = 0;
        int senseRows = 0;

        for (int p = 0; p < ZiPartition.COUNT; p++) {
            for (String suffix : new String[] {".tsv", ".flags.tsv"}) {
                String text = read("/kranji/seed/p%03d%s".formatted(p, suffix));
                if (text == null) continue;
                files++;
                sha.update(text.getBytes(StandardCharsets.UTF_8));
                if (suffix.equals(".tsv")) senseRows += rows(text);
            }
        }
        return new Digest(HexFormat.of().formatHex(sha.digest()), files, senseRows);
    }

    private static int rows(String text) {
        int n = 0;
        for (String line : text.split("\n")) {
            String row = line.strip();
            if (!row.isEmpty() && row.charAt(0) != '#') n++;
        }
        return n;
    }

    private static MessageDigest sha256() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException never) {
            throw new IllegalStateException("SHA-256 is required of every JVM", never);
        }
    }

    private static String read(String resource) {
        try (InputStream in = SeedIsImmutableTest.class.getResourceAsStream(resource)) {
            if (in == null) return null;
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + resource, e);
        }
    }
}
