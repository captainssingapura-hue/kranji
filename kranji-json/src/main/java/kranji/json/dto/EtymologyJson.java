package kranji.json.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 六书 — The Six Writings, as a sealed tagged union.
 *
 * <p>Wire tag: {@code "kind"}.</p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "kind")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EtymologyJson.Pictograph.class,          name = "pictograph"),
        @JsonSubTypes.Type(value = EtymologyJson.SimpleIndicative.class,    name = "simple_indicative"),
        @JsonSubTypes.Type(value = EtymologyJson.CompoundIndicative.class,  name = "compound_indicative"),
        @JsonSubTypes.Type(value = EtymologyJson.PhonoSemantic.class,       name = "phono_semantic"),
        @JsonSubTypes.Type(value = EtymologyJson.DerivativeCognate.class,   name = "derivative_cognate"),
        @JsonSubTypes.Type(value = EtymologyJson.PhoneticLoan.class,        name = "phonetic_loan")
})
public sealed interface EtymologyJson {

    /** 象形 — stylized drawing of the referent (e.g. 日, 月, 山). */
    record Pictograph() implements EtymologyJson {}

    /** 指事 — abstract or indicated symbol (e.g. 上, 下, 本). */
    record SimpleIndicative(String indicatorDescription) implements EtymologyJson {}

    /** 会意 — meaning combines from the parts (e.g. 明 = 日+月). */
    record CompoundIndicative(String meaningHint) implements EtymologyJson {}

    /**
     * 形声 — one part carries meaning, another carries sound.
     *
     * @param semanticPart 形旁 — meaning component (reference into the catalog)
     * @param phoneticPart 声旁 — sound component (reference into the catalog)
     */
    record PhonoSemantic(BlockRefJson semanticPart, BlockRefJson phoneticPart) implements EtymologyJson {}

    /** 转注 — cognate character with shared etymological root. */
    record DerivativeCognate(String cognateGlyph) implements EtymologyJson {}

    /** 假借 — character borrowed for its sound; once meant something else. */
    record PhoneticLoan(String originalMeaning) implements EtymologyJson {}
}
