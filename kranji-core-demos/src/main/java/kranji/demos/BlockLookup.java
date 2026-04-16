package kranji.demos;

import kranji.common.CommonBlocks;
import kranji.common.CommonCharacters;
import kranji.common.depth1.*;
import kranji.common.depth2.*;
import kranji.common.depth3.Depth3;
import kranji.common.depth4.Depth4;
import kranji.common.depth5.Depth5;
import kranji.component.basic.*;
import kranji.library.BasicSet;
import kranji.singular.SingularFamilies;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.body.BodyParts;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.materials.Materials;
import kranji.singular.nature.NatureElements;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.singular.tools.ToolsAndVessels;
import kranji.zi.*;

import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility for finding existing building blocks by glyph and auditing
 * inline compositions.
 *
 * <h3>Lookup mode</h3>
 * Given a file of glyphs, outputs the exact Java constant reference for each:
 * <pre>
 *   BlockLookup input.txt [output.txt]
 * </pre>
 *
 * <h3>Audit mode</h3>
 * Scans all ComposedZi structures for inline {@code new ComposedBlock(...)}
 * patterns that should be extracted as named constants:
 * <pre>
 *   BlockLookup --audit [output.txt]
 * </pre>
 */
public class BlockLookup {

    // ── Data types ─────────────────────────────────────────────────────

    record BlockInfo(String glyph, String constantName, String className,
                     String packageName, String type, int strokes, int depth) {

        /** Java expression to use this block in a composition. */
        String javaRef() {
            return className + "." + constantName;
        }

        /** What to pass as a child slot — adds .structure() for composed blocks. */
        String slotRef() {
            return depth > 0 ? javaRef() + ".structure()" : javaRef();
        }
    }

    record InlineComp(String description, String structureType,
                      List<String> childGlyphs) {}

    // ── Entry point ────────────────────────────────────────────────────

    public static void main(String[] args) throws Exception {
        BasicSet.INSTANCE.register();
        SingularFamilies.registerInto(BasicSet.INSTANCE);

        if (args.length > 0 && "--audit".equals(args[0])) {
            String out = args.length > 1 ? args[1] : null;
            runAudit(out);
        } else if (args.length > 0 && looksLikeFilePath(args[0])) {
            String in = args[0];
            String out = args.length > 1 ? args[1] : deriveOutputPath(in);
            runLookup(in, out);
        } else {
            System.err.println("Usage: BlockLookup <input.txt> [output.txt]");
            System.err.println("       BlockLookup --audit [output.txt]");
        }
    }

    // ── Index building ─────────────────────────────────────────────────

    private static Map<String, BlockInfo> buildIndex() {
        var index = new LinkedHashMap<String, BlockInfo>();

        // Singular families (kranji-singulars) — scan these FIRST so they
        // take priority over BasicComponents re-exports.
        for (Class<?> cls : List.of(
                NatureElements.class, BodyParts.class, PeopleAndRoles.class,
                Animals.class, NumbersAndMeasure.class, PlantsAndAgriculture.class,
                ToolsAndVessels.class, ActionsAndStates.class, SpaceAndDirection.class,
                Structures.class, Materials.class, AbstractConcepts.class,
                RadicalComponents.class)) {
            scanSingulars(cls, index);
        }

        // Basic component families (kranji-core)
        for (Class<?> cls : List.of(
                PersonFamily.class, WaterFamily.class, HeartFamily.class,
                HandFamily.class, AnimalFamily.class, SpeechFamily.class,
                MetalFamily.class, FoodFamily.class, MovementFamily.class,
                SilkFamily.class, ClothingFamily.class, KnifeFamily.class,
                StrikeFamily.class, EarFamily.class, PlantFamily.class,
                ShelterFamily.class, FireFamily.class, WoodFamily.class,
                EnclosureFamily.class)) {
            scanSingulars(cls, index);
        }

        // BasicComponents re-exports (fallback — only fills gaps)
        scanSingulars(BasicComponents.class, index);

        // Composed characters across all depths
        for (Class<?> cls : List.of(
                Depth1Strokes3.class, Depth1Strokes4.class, Depth1Strokes5.class,
                Depth1Strokes6.class, Depth1Strokes7.class, Depth1StrokesHigh.class,
                Depth2Strokes6.class, Depth2Strokes7.class, Depth2StrokesHigh.class,
                Depth3.class, Depth4.class, Depth5.class)) {
            scanComposed(cls, index);
        }

        return index;
    }

    private static void scanSingulars(Class<?> cls, Map<String, BlockInfo> index) {
        for (Field f : cls.getDeclaredFields()) {
            if (!isPublicStaticFinal(f)) continue;
            try {
                Object val = f.get(null);
                if (val instanceof SingularBlock sb) {
                    String type = (sb instanceof SingularZi) ? "SingularZi"
                                : (sb instanceof SingularPart) ? "SingularPart"
                                : "SingularBlock";
                    index.putIfAbsent(sb.glyph(), new BlockInfo(
                            sb.glyph(), f.getName(), cls.getSimpleName(),
                            cls.getPackageName(), type, sb.strokes(), 0));
                }
            } catch (IllegalAccessException ignored) {}
        }
    }

    private static void scanComposed(Class<?> cls, Map<String, BlockInfo> index) {
        for (Field f : cls.getDeclaredFields()) {
            if (!isPublicStaticFinal(f)) continue;
            try {
                Object val = f.get(null);
                if (val instanceof ComposedZi cz) {
                    int depth = depthOf(cz.character());
                    index.putIfAbsent(cz.character(), new BlockInfo(
                            cz.character(), f.getName(), cls.getSimpleName(),
                            cls.getPackageName(), "ComposedZi", cz.strokes(), depth));
                }
            } catch (IllegalAccessException ignored) {}
        }
    }

    // ── Lookup mode ────────────────────────────────────────────────────

    private static void runLookup(String inputPath, String outputPath) throws IOException {
        String raw = Files.readString(Path.of(inputPath), StandardCharsets.UTF_8).trim();
        String[] glyphs = raw.contains(",")
                ? raw.split(",")
                : raw.lines().map(String::trim).filter(s -> !s.isEmpty()).toArray(String[]::new);

        Map<String, BlockInfo> index = buildIndex();

        var found = new ArrayList<BlockInfo>();
        var missing = new ArrayList<String>();

        for (String g : glyphs) {
            String ch = g.trim();
            if (ch.isEmpty()) continue;
            BlockInfo info = index.get(ch);
            if (info != null) found.add(info);
            else missing.add(ch);
        }

        var sb = new StringBuilder();
        sb.append("=== Block Lookup ===\n");
        sb.append("Requested: ").append(found.size() + missing.size()).append('\n');
        sb.append("Found:     ").append(found.size()).append('\n');
        sb.append("Missing:   ").append(missing.size()).append('\n');
        sb.append('\n');

        if (!found.isEmpty()) {
            sb.append("--- Found (").append(found.size()).append(") ---\n");
            for (BlockInfo bi : found) {
                String depthTag = bi.depth > 0 ? "[d" + bi.depth + "]" : "";
                String suffix = bi.depth > 0 ? "  \u2192 use .structure() in slots" : "";
                sb.append(String.format("  %s  %-16s %-42s (%s)%s%n",
                        bi.glyph,
                        bi.type + depthTag,
                        bi.javaRef(),
                        bi.packageName,
                        suffix));
            }
            sb.append('\n');
        }

        if (!missing.isEmpty()) {
            sb.append("--- Missing (").append(missing.size()).append(") ---\n");
            sb.append(String.join(",", missing)).append('\n');
        }

        Files.writeString(Path.of(outputPath), sb.toString(), StandardCharsets.UTF_8);
        System.out.printf("Block lookup: %d found, %d missing \u2192 %s%n",
                found.size(), missing.size(), outputPath);
    }

    // ── Audit mode ─────────────────────────────────────────────────────

    private static void runAudit(String outputPath) throws IOException {
        Map<String, BlockInfo> index = buildIndex();
        Set<ComposedBlock> knownBlocks = buildKnownBlocks();

        // glyph-signature → list of parent characters using it
        var inlineMap = new LinkedHashMap<String, Set<String>>();

        for (ComposedZi zi : CommonCharacters.ALL) {
            BlockStructure struct = zi.structure();
            if (struct instanceof ComposedBlock cb) {
                auditChildren(cb, zi.character(), index, knownBlocks, inlineMap);
            }
        }

        // Also audit etymology fields
        // (skip for now — structure is the main concern)

        var sb = new StringBuilder();
        sb.append("=== Inline Composition Audit ===\n");
        sb.append(String.format("Scanned: %d ComposedZi entries%n", CommonCharacters.ALL.size()));
        sb.append(String.format("Inline compositions found: %d distinct patterns%n%n", inlineMap.size()));

        // Sort by frequency (most common first)
        var sorted = inlineMap.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue().size(), a.getValue().size()))
                .toList();

        for (var entry : sorted) {
            Set<String> parents = entry.getValue();
            sb.append(String.format("  %-50s  used in %d: %s%n",
                    entry.getKey(), parents.size(), String.join(" ", parents)));
        }

        String out = outputPath != null ? outputPath : "block-audit-result.txt";
        Files.writeString(Path.of(out), sb.toString(), StandardCharsets.UTF_8);
        System.out.printf("Audit: %d inline patterns found \u2192 %s%n", inlineMap.size(), out);
    }

    /**
     * Recursively walks a composed block's children. Any child that is
     * itself a {@link ComposedBlock} (i.e. an inline "new" composition)
     * is recorded if it has no matching named constant in the index.
     */
    private static void auditChildren(ComposedBlock cb, String parentChar,
                                       Map<String, BlockInfo> index,
                                       Set<ComposedBlock> knownBlocks,
                                       Map<String, Set<String>> inlineMap) {
        for (BlockStructure child : childrenOf(cb)) {
            if (child instanceof ComposedBlock childCb) {
                String sig = describeBlock(childCb, index);
                // Check if this inline block matches a named constant
                if (!matchesNamedConstant(childCb, knownBlocks)) {
                    inlineMap.computeIfAbsent(sig, k -> new LinkedHashSet<>())
                             .add(parentChar);
                }
                // Recurse into nested compositions
                auditChildren(childCb, parentChar, index, knownBlocks, inlineMap);
            }
        }
    }

    /** Extracts children from any ComposedBlock variant. */
    private static List<BlockStructure> childrenOf(ComposedBlock cb) {
        return switch (cb) {
            case ComposedBlock.LeftRight lr -> List.of(lr.left(), lr.right());
            case ComposedBlock.TopBottom tb -> List.of(tb.top(), tb.bottom());
            case ComposedBlock.LeftMiddleRight lmr -> List.of(lmr.left(), lmr.middle(), lmr.right());
            case ComposedBlock.TopMiddleBottom tmb -> List.of(tmb.top(), tmb.middle(), tmb.bottom());
            case ComposedBlock.FullEnclosure fe -> List.of(fe.outer(), fe.inner());
            case ComposedBlock.SemiEnclosureUpperLeft se -> List.of(se.wrapper(), se.content());
            case ComposedBlock.SemiEnclosureUpperRight se -> List.of(se.wrapper(), se.content());
            case ComposedBlock.SemiEnclosureBottomLeft se -> List.of(se.wrapper(), se.content());
            case ComposedBlock.SemiEnclosureTopThree se -> List.of(se.wrapper(), se.content());
            case ComposedBlock.SemiEnclosureBottomThree se -> List.of(se.wrapper(), se.content());
            case ComposedBlock.SemiEnclosureLeftThree se -> List.of(se.wrapper(), se.content());
        };
    }

    /** Human-readable description of an inline composed block. */
    private static String describeBlock(ComposedBlock cb, Map<String, BlockInfo> index) {
        String type = cb.getClass().getSimpleName();
        String children = childrenOf(cb).stream()
                .map(child -> glyphOf(child, index))
                .collect(Collectors.joining(", "));
        return type + "(" + children + ")";
    }

    /** Tries to get a glyph for a block structure. */
    private static String glyphOf(BlockStructure bs, Map<String, BlockInfo> index) {
        if (bs instanceof SingularBlock sb) return sb.glyph();
        if (bs instanceof ComposedBlock cb) {
            // Check if this composed block matches a known ComposedZi's structure
            for (ComposedZi cz : CommonCharacters.ALL) {
                if (cz.structure().equals(cb)) return cz.character();
            }
            // Fall back to structural description
            return describeBlock(cb, index);
        }
        return "?";
    }

    /**
     * Builds the set of all "known" ComposedBlocks: ComposedZi top-level
     * structures plus explicitly named constants in {@link CommonBlocks}.
     */
    private static Set<ComposedBlock> buildKnownBlocks() {
        var known = new HashSet<ComposedBlock>();
        for (ComposedZi cz : CommonCharacters.ALL) {
            if (cz.structure() instanceof ComposedBlock cb) known.add(cb);
        }
        for (Field f : CommonBlocks.class.getDeclaredFields()) {
            if (!isPublicStaticFinal(f)) continue;
            try {
                Object val = f.get(null);
                if (val instanceof ComposedBlock cb) known.add(cb);
            } catch (IllegalAccessException ignored) {}
        }
        return known;
    }

    /** Checks if a ComposedBlock matches any known named constant. */
    private static boolean matchesNamedConstant(ComposedBlock cb, Set<ComposedBlock> knownBlocks) {
        return knownBlocks.contains(cb);
    }

    // ── Utilities ──────────────────────────────────────────────────────

    private static int depthOf(String ch) {
        for (var zi : Depth1.ALL) if (zi.character().equals(ch)) return 1;
        for (var zi : Depth2.ALL) if (zi.character().equals(ch)) return 2;
        for (var zi : Depth3.ALL) if (zi.character().equals(ch)) return 3;
        for (var zi : Depth4.ALL) if (zi.character().equals(ch)) return 4;
        for (var zi : Depth5.ALL) if (zi.character().equals(ch)) return 5;
        return -1;
    }

    private static boolean isPublicStaticFinal(Field f) {
        int m = f.getModifiers();
        return Modifier.isPublic(m) && Modifier.isStatic(m) && Modifier.isFinal(m);
    }

    private static boolean looksLikeFilePath(String s) {
        return s.endsWith(".txt") || s.endsWith(".csv") || s.contains("/") || s.contains("\\") || s.contains(".");
    }

    private static String deriveOutputPath(String inputPath) {
        int dot = inputPath.lastIndexOf('.');
        String stem = dot > 0 ? inputPath.substring(0, dot) : inputPath;
        String ext = dot > 0 ? inputPath.substring(dot) : ".txt";
        return stem + "-blocks" + ext;
    }
}
