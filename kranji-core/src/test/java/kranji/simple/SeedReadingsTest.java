package kranji.simple;

import kranji.pinyin.Initial;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the reading of every seeded character.
 *
 * <p>The DSL makes the <em>initial</em> structurally impossible to get wrong —
 * it comes from the partition. Nothing does the same for the final or the tone,
 * and a wrong reading is the one error in this project that would be shown to a
 * child as fact. So each entry is asserted against the reading it is meant to
 * have.</p>
 *
 * <p>This is data verification, not logic testing. It should grow with the
 * seed set, and an entry added without a line here is an entry nobody
 * checked.</p>
 */
class SeedReadingsTest {

    private static Map<String, String> expected() {
        var m = new LinkedHashMap<String, String>();
        // b-
        m.put("八", "bā");    m.put("白", "bái");  m.put("北", "běi");
        // c-
        m.put("才", "cái");   m.put("草", "cǎo");
        // ch-
        m.put("车", "chē");   m.put("长", "cháng");
        // d-
        m.put("东", "dōng");  m.put("大", "dà");   m.put("多", "duō");
        // f-
        m.put("风", "fēng");  m.put("飞", "fēi");
        // g-
        m.put("高", "gāo");   m.put("歌", "gē");
        // h-
        m.put("好", "hǎo");   m.put("和", "hé");   m.put("很", "hěn");
        m.put("海", "hǎi");   m.put("汉", "hàn");  m.put("后", "hòu");
        m.put("哈", "hā");    m.put("害", "hài");  m.put("含", "hán");
        m.put("寒", "hán");   m.put("喝", "hē");   m.put("河", "hé");
        m.put("何", "hé");    m.put("合", "hé");   m.put("黑", "hēi");
        m.put("呼", "hū");    m.put("湖", "hú");   m.put("胡", "hú");
        m.put("户", "hù");    m.put("画", "huà");  m.put("欢", "huān");
        m.put("黄", "huáng"); m.put("会", "huì");  m.put("火", "huǒ");
        m.put("或", "huò");   m.put("还", "hái");
        m.put("红", "hóng");  m.put("话", "huà");  m.put("回", "huí");
        m.put("花", "huā");   m.put("航", "háng");
        // j-
        m.put("家", "jiā");   m.put("金", "jīn");
        // k-
        m.put("开", "kāi");   m.put("看", "kàn");
        // l-
        m.put("来", "lái");   m.put("路", "lù");   m.put("绿", "lǜ");
        // m-
        m.put("明", "míng");  m.put("马", "mǎ");   m.put("木", "mù");
        // n-
        m.put("你", "nǐ");    m.put("女", "nǚ");
        // p-
        m.put("朋", "péng");  m.put("跑", "pǎo");
        // q-
        m.put("青", "qīng");  m.put("去", "qù");
        // r-
        m.put("人", "rén");   m.put("日", "rì");
        // s-
        m.put("三", "sān");   m.put("四", "sì");
        // sh-
        m.put("山", "shān");  m.put("水", "shuǐ"); m.put("是", "shì");
        // t-
        m.put("天", "tiān");  m.put("土", "tǔ");
        // x-
        m.put("小", "xiǎo");  m.put("雪", "xuě");
        // z-
        m.put("走", "zǒu");   m.put("字", "zì");
        // zero-initial
        m.put("一", "yī");    m.put("月", "yuè");  m.put("云", "yún");
        // zh-
        m.put("中", "zhōng"); m.put("知", "zhī");
        return m;
    }

    @TestFactory
    Stream<DynamicTest> everySeededCharacterReadsAsExpected() {
        return expected().entrySet().stream().map(e ->
                DynamicTest.dynamicTest(e.getKey() + " = " + e.getValue(), () -> {
                    SimpleZi zi = SimpleZiRegistry.find(e.getKey())
                            .orElseThrow(() -> new AssertionError(
                                    e.getKey() + " is not in the registry"));
                    assertEquals(e.getValue(), zi.defaultReading());
                }));
    }

    @Test
    void everyRegistryEntryIsAccountedForHere() {
        var checked = expected().keySet();
        var missing = SimpleZiRegistry.ALL.stream()
                .map(z -> z.glyph().value())
                .filter(g -> !checked.contains(g))
                .toList();
        assertEquals(java.util.List.of(), missing,
                "an entry with no expected reading here is an entry nobody verified");
    }

    @Test
    void everyPartitionHasAtLeastOneCharacter() {
        for (Initial i : Initial.values()) {
            assertTrue(SimpleZiRegistry.byInitial(i).size() >= 1,
                    () -> "partition " + i + " is empty");
        }
        assertEquals(Initial.values().length, SimpleZiRegistry.populatedInitials().size(),
                "every initial should be represented");
    }
}
