package kranji.codegen.singulars;

import kranji.pinyin.Initial;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InitialPackageTest {

    @Test
    void enum_zero_maps_to_zero_word() {
        assertEquals("zero", InitialPackage.forEnum(Initial.ZERO));
    }

    @Test
    void enum_consonants_lowercase_to_their_spelling() {
        assertEquals("b",  InitialPackage.forEnum(Initial.B));
        assertEquals("zh", InitialPackage.forEnum(Initial.ZH));
        assertEquals("ch", InitialPackage.forEnum(Initial.CH));
        assertEquals("sh", InitialPackage.forEnum(Initial.SH));
        assertEquals("x",  InitialPackage.forEnum(Initial.X));
        assertEquals("r",  InitialPackage.forEnum(Initial.R));
    }

    @Test
    void every_enum_value_returns_a_non_null_package() {
        for (Initial i : Initial.values()) {
            assertNotNull(InitialPackage.forEnum(i), () -> "missing mapping for " + i);
        }
    }

    @ParameterizedTest
    @CsvSource({
            // single-consonant onsets, tone-marked vowels
            "wáng,    zero",     // 王 — y/w glides are zero-initial
            "yī,      zero",     // 衣
            "yú,      zero",     // 鱼
            "wǔ,      zero",     // 五
            "ài,      zero",     // 爱 — bare vowel
            "ér,      zero",     // 儿
            "bái,     b",        // 白
            "péng,    p",        // 朋
            "mǎ,      m",        // 马
            "fēng,    f",        // 风
            "dà,      d",        // 大
            "tǔ,      t",        // 土
            "nǚ,      n",        // 女 — ü-on-n
            "lǎo,     l",        // 老
            "gǒu,     g",        // 狗
            "kǒu,     k",        // 口
            "hé,      h",        // 河
            "jiā,     j",        // 家
            "qián,    q",        // 钱
            "xiǎo,    x",        // 小
            "rén,     r",        // 人
            "zǐ,      z",        // 子
            "cǎo,     c",        // 草
            "sì,      s",        // 寺
            // two-letter retroflex onsets must beat the single-letter rule
            "zhōng,   zh",       // 中
            "chī,     ch",       // 吃
            "shǒu,    sh",       // 手
            // surface form already without diacritics
            "wang,    zero",
            "shou,    sh",
            // weird-but-valid edges
            "'',      zero"
    })
    void fromPinyinText_mappings(String pinyin, String expected) {
        // CsvSource passes a literal empty pair of single quotes as `''`;
        // strip them so we feed an actual empty string to the parser.
        String input = pinyin.equals("''") ? "" : pinyin.trim();
        assertEquals(expected, InitialPackage.fromPinyinText(input));
    }

    @Test
    void fromPinyinText_rejects_null() {
        assertThrows(NullPointerException.class,
                () -> InitialPackage.fromPinyinText(null));
    }
}
