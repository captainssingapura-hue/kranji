package kranji.demos;

import kranji.layout.*;

public class GravityDebug {
    public static void main(String[] args) {
        for (var e : ExampleCharacters.ALL) {
            String ch = e.character();
            if (ch.equals("清") || ch.equals("花") || ch.equals("明") || ch.equals("森") || ch.equals("品") || ch.equals("𰻝")) {
                var blocks = BlockLayoutEngine.layout(e);
                System.out.println("=== " + ch + " ===");
                for (var b : blocks) {
                    System.out.printf("  %-16s glyph=%-3s area=%.4f cx=%.3f cy=%.3f glyphDx=%+.4f glyphDy=%+.4f%n",
                            b.role(), b.glyph(), b.area(), b.cx(), b.cy(), b.glyphDx(), b.glyphDy());
                }
            }
        }
    }
}
