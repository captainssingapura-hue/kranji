package kranji.demos;

import kranji.layout.BlockSvgRenderer;
import kranji.svg.StructuralSvgRenderer;
import kranji.svg.StrokeSvgRenderer;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Exports every example character as SVG diagrams: structural decomposition,
 * block layout, and stroke-based rendering.
 *
 * <p>Run with: {@code mvn -pl kranji-core-demos exec:java -Dexec.mainClass=kranji.demos.SvgExportDemo}</p>
 */
public final class SvgExportDemo {

    private SvgExportDemo() {}

    public static void main(String[] args) throws Exception {
        var structuralDir = Path.of("output/svg");
        var strokeDir = Path.of("output/svg-stroke");
        var blockDir = Path.of("output/svg-block");
        Files.createDirectories(structuralDir);
        Files.createDirectories(strokeDir);
        Files.createDirectories(blockDir);

        for (var entry : ExampleCharacters.ALL) {
            var cpName = entry.codepoint().replace("+", "") + "_" + entry.character();

            // Structural (rectangle) SVG
            var structSvg = StructuralSvgRenderer.render(entry);
            var structPath = structuralDir.resolve(cpName + ".svg");
            Files.writeString(structPath, structSvg);
            System.out.println("structural  " + entry.character() + "  →  " + structPath);

            // Block layout SVG
            var blockSvg = BlockSvgRenderer.render(entry);
            var blockPath = blockDir.resolve(cpName + ".svg");
            Files.writeString(blockPath, blockSvg);
            System.out.println("block       " + entry.character() + "  →  " + blockPath);

            // Stroke-based SVG
            var strokeSvg = StrokeSvgRenderer.render(entry);
            var strokePath = strokeDir.resolve(cpName + ".svg");
            Files.writeString(strokePath, strokeSvg);
            System.out.println("stroke      " + entry.character() + "  →  " + strokePath);
        }
        System.out.println("\n" + ExampleCharacters.ALL.size() + " characters exported (structural + block + stroke)");
    }
}
