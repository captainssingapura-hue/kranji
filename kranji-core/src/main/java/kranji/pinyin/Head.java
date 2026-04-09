package kranji.pinyin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 韵头 (四呼) — The medial of a Mandarin final.
 */
public enum Head {

    /** 开口呼 — Open mouth, no medial. */
    @JsonProperty("open")
    OPEN("开口呼"),

    /** 齐齿呼 — Even teeth, medial i. */
    @JsonProperty("i")
    I("齐齿呼"),

    /** 合口呼 — Closed mouth, medial u. */
    @JsonProperty("u")
    U("合口呼"),

    /** 撮口呼 — Rounded lips, medial ü. */
    @JsonProperty("v")
    V("撮口呼");

    private final String chinese;

    Head(String chinese) {
        this.chinese = chinese;
    }

    /** Returns the Chinese name for this medial category (四呼). */
    public String chinese() {
        return chinese;
    }
}
