package kranji.gloss.handcrafted;

import kranji.gloss.tsv.GlossTsv;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.Examples;
import kranji.simple.gloss.EgKey;
import kranji.simple.gloss.ZiCollection;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiCharUTF8;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Every hand-crafted gloss and every phrase they reference, read from file.
 *
 * <p>Written for the project. No dictionary was transcribed, ported or
 * systematically consulted, so this source carries no third-party licence — a
 * build containing only this module is distributable under whatever terms
 * Kranji itself adopts.</p>
 *
 * <p>Glosses ported from a licensed corpus belong in their own module and their
 * own source. Keeping them apart is what makes the licence of a given build a
 * checkable fact rather than a claim somebody has to remember.</p>
 *
 * <h2>Files, not Java</h2>
 *
 * <p>The set was first written as a typestate DSL, for the compile-time
 * checking. Two tab-separated files replaced it at the point the remaining work
 * became volume rather than design: several hundred rows is a thing to fill in,
 * not a thing to write, and a row is editable, sortable and greppable in a way
 * a nested builder is not.</p>
 *
 * <p>The checking did not go away, it moved. {@link GlossTsv} collects every
 * bad row with its line number and this class refuses to load any of it —
 * loudly, naming all of them at once, which is more than the compiler did.</p>
 */
public final class HandCrafted implements ZiCollection {

    private static final String SENSES = "/kranji/gloss/senses.tsv";
    private static final String PHRASES = "/kranji/gloss/phrases.tsv";

    public static final HandCrafted INSTANCE = new HandCrafted();

    /** Public for ServiceLoader, which needs a no-arg constructor it can call. */
    public HandCrafted() {}

    @Override public String name()    { return "Kranji hand-crafted"; }
    @Override public String licence() { return "project-authored; no third-party licence"; }

    private static final List<ZiGloss> GLOSSES =
            demand(GlossTsv.readSenses(SENSES, read(SENSES)));
    private static final List<ExampleEntry> PHRASE_ENTRIES =
            demand(GlossTsv.readPhrases(PHRASES, read(PHRASES)));

    private static final Map<ZiCharUTF8, ZiGloss> BY_ZI = index(GLOSSES);
    private static final Map<EgKey, ExampleEntry> BY_PHRASE = indexPhrases(PHRASE_ENTRIES);

    private static Map<ZiCharUTF8, ZiGloss> index(List<ZiGloss> entries) {
        var out = new LinkedHashMap<ZiCharUTF8, ZiGloss>();
        for (ZiGloss e : entries) out.put(e.zi(), e);
        return Map.copyOf(out);
    }

    private static Map<EgKey, ExampleEntry> indexPhrases(List<ExampleEntry> entries) {
        var out = new LinkedHashMap<EgKey, ExampleEntry>();
        for (ExampleEntry e : entries) out.put(e.key(), e);
        return Map.copyOf(out);
    }

    /**
     * The characters, as a repository.
     *
     * <p>A map for lookup and the parsed list for order — the list, not the
     * map's values, because a frozen map does not keep insertion order and the
     * file's sequence is the authored ranking.</p>
     */
    private static final Characters CHARACTERS = new Characters() {
        @Override public Optional<ZiGloss> find(ZiCharUTF8 zi) {
            return Optional.ofNullable(BY_ZI.get(zi));
        }
        @Override public List<ZiGloss> all() { return GLOSSES; }
        @Override public int size()          { return GLOSSES.size(); }
    };

    private static final Phrases PHRASE_REPO = new Phrases() {
        @Override public Optional<ExampleEntry> find(EgKey key) {
            return Optional.ofNullable(BY_PHRASE.get(key));
        }
        @Override public List<ExampleEntry> all() { return PHRASE_ENTRIES; }
        @Override public int size()               { return PHRASE_ENTRIES.size(); }
    };

    @Override public Characters characters() { return CHARACTERS; }
    @Override public Phrases phrases()       { return PHRASE_REPO; }

    /** The phrases as a plain list, for a consumer composing its own registry. */
    public static List<ExampleEntry> phraseList() { return PHRASE_ENTRIES; }

    private static final Examples REGISTRY = Examples.of(PHRASE_ENTRIES);

    /** Those phrases composed, for a consumer that looks them up by key. */
    public static Examples registry() { return REGISTRY; }

    /**
     * The data, or a refusal naming every bad row.
     *
     * <p>Not the first bad row. A file is edited in bulk, so the useful answer
     * is the whole list — fixing one problem per build is what made the
     * compiler tedious for data.</p>
     */
    private static <T> List<T> demand(GlossTsv.Read<T> read) {
        if (read.ok()) return read.entries();
        var lines = read.problems().stream().map(Object::toString).toList();
        throw new IllegalStateException(
                read.problems().size() + " problems in the gloss files:\n  "
              + String.join("\n  ", lines));
    }

    private static String read(String resource) {
        try (InputStream in = HandCrafted.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException(
                        resource + " is not on the classpath - the gloss data is missing, "
                      + "which is not the same as there being none");
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + resource, e);
        }
    }
}
