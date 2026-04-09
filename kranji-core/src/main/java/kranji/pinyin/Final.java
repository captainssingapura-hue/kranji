package kranji.pinyin;

/**
 * 韵母 — The final of a Mandarin syllable, decomposed into head (medial),
 * body (nucleus), and tail (coda).
 *
 * @param head the medial / 韵头 (四呼)
 * @param body the nucleus / 韵腹
 * @param tail the coda / 韵尾
 */
public record Final(Head head, Body body, Tail tail) {

    /** Returns the conventional pinyin spelling of this final. */
    public String spelling() {
        var sb = new StringBuilder();
        switch (head) {
            case I -> sb.append("i");
            case U -> sb.append("u");
            case V -> sb.append("ü");
            case OPEN -> {}
        }
        sb.append(body.symbol());
        sb.append(tail.symbol());
        return sb.toString();
    }
}
