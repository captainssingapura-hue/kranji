package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced jing (tone 3). */
public final class Jing3 {
    private Jing3() {}

    /** 景 (jing3) — scenery; view. */
    public static final ChineseCharacterEntry 景_SCENERY_VIEW = entry("景")
            .py(J, OPEN, Body.I, Tail.NG, T3).strokes(12).radical(72)
            .topBottom(zi("日"), zi("京"))
            .phonoSemantic(zi("日"), zi("京"));

    /** 警 (jing3) — police; alert. */
    public static final ChineseCharacterEntry 警_POLICE_ALERT = entry("警")
            .py(J, OPEN, Body.I, Tail.NG, T3).strokes(19).radical(149)
            .topBottom(zi("敬"), zi("言"))
            .phonoSemantic(zi("言"), zi("敬"));

    /** 井 (jing3) — well. */
    public static final ChineseCharacterEntry 井_WELL = entry("井")
            .py(J, OPEN, Body.I, Tail.NG, T3).strokes(4).radical(7)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(景_SCENERY_VIEW, 警_POLICE_ALERT, 井_WELL);
}
