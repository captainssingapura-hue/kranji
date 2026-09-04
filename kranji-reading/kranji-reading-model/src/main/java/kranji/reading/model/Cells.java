package kranji.reading.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Turns a line of tokens into the squares it is written in.
 *
 * <p>The whole of the rule: a closing mark joins the character before it, an
 * opening mark joins the character after it, and anything left over is its own
 * plain cell. Everything about 禁则 follows from that, because a cell is
 * atomic and a row cannot break inside one.</p>
 *
 * <p>This lives in the model rather than in the renderer so it can be tested
 * with plain JUnit, and so the browser receives cells rather than a rule it
 * would have to reimplement.</p>
 */
public final class Cells {

    /** Closing marks — these must never begin a line. */
    public static final String CLOSING = "。，、！？：；）】》」』’”…";

    /** Opening marks — these must never end a line. */
    public static final String OPENING = "（【《「『‘“";

    private Cells() {}

    /** The cells for one line of tokens. */
    public static List<Cell> of(List<Token> tokens) {
        var cells = new ArrayList<Cell>();
        String pendingOpen = "";

        for (Token token : tokens) {
            if (token instanceof Token.Zi zi) {
                if (pendingOpen.isEmpty()) {
                    cells.add(new Cell.Char(zi));
                } else {
                    cells.add(new Cell.CharWithPunctuation(zi, pendingOpen, ""));
                    pendingOpen = "";
                }
                continue;
            }

            String text = token.text();

            // A closing run attaches to the character already placed. Without
            // one - a line opening with 。 - it has nowhere to go and stays a
            // cell of its own rather than being dropped.
            String closing = runOf(text, CLOSING, true);
            if (!closing.isEmpty()) {
                if (attachTrailing(cells, closing)) {
                    text = text.substring(closing.length());
                }
            }

            String opening = runOf(text, OPENING, false);
            if (!opening.isEmpty()) {
                text = text.substring(0, text.length() - opening.length());
            }

            if (!text.isBlank()) cells.add(new Cell.Plain(text));
            pendingOpen = opening;
        }

        if (!pendingOpen.isEmpty()) cells.add(new Cell.Plain(pendingOpen));
        return List.copyOf(cells);
    }

    /** Adds a closing run to the last cell, when that cell can carry one. */
    private static boolean attachTrailing(List<Cell> cells, String closing) {
        if (cells.isEmpty()) return false;
        Cell last = cells.get(cells.size() - 1);
        switch (last) {
            case Cell.Char c -> {
                cells.set(cells.size() - 1,
                        new Cell.CharWithPunctuation(c.character(), "", closing));
                return true;
            }
            case Cell.CharWithPunctuation c -> {
                cells.set(cells.size() - 1, new Cell.CharWithPunctuation(
                        c.character(), c.leading(), c.trailing() + closing));
                return true;
            }
            case Cell.Plain ignored -> {
                return false;
            }
        }
    }

    /** The run of characters from {@code set} at one end of the text. */
    private static String runOf(String text, String set, boolean fromStart) {
        if (fromStart) {
            int i = 0;
            while (i < text.length() && set.indexOf(text.charAt(i)) >= 0) i++;
            return text.substring(0, i);
        }
        int i = text.length();
        while (i > 0 && set.indexOf(text.charAt(i - 1)) >= 0) i--;
        return text.substring(i);
    }
}
