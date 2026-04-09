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

/** Characters pronounced qi (tone 4). */
public final class Qi4 {
    private Qi4() {}

    /** 气 (qi4) — air; gas. */
    public static final ChineseCharacterEntry 气_AIR_GAS = entry("气")
            .py(Q, OPEN, Body.I, Tail.NONE, T4).strokes(4).radical(84)
            .singular()
            .pictograph();

    /** 器 (qi4) — device; vessel. */
    public static final ChineseCharacterEntry 器_DEVICE_VESSEL = entry("器")
            .py(Q, OPEN, Body.I, Tail.NONE, T4).strokes(16).radical(30)
            .topBottom(zi("口口犬"), zi("口口"))
            .compoundIndicative("device; vessel");

    /** 弃 (qi4) — abandon. */
    public static final ChineseCharacterEntry 弃_ABANDON = entry("弃")
            .py(Q, OPEN, Body.I, Tail.NONE, T4).strokes(7).radical(55)
            .topBottom(WEN_ZI_TOU, zi("弃"))
            .compoundIndicative("abandon");

    public static final List<ChineseCharacterEntry> ALL = List.of(气_AIR_GAS, 器_DEVICE_VESSEL, 弃_ABANDON);
}
