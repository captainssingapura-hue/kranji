package kranji.reading.model;

import java.util.Optional;

/**
 * What kind of thing an article is.
 *
 * <p>Declared by the article itself, in its header, so the catalogue is
 * constructed from the content rather than from a list kept beside it. Moving
 * an article between classes is an edit to the article.</p>
 *
 * <p>These are the classes a Chinese primary reader is actually organised by,
 * not a generic taxonomy. They differ in how they are read as much as in what
 * they say: 古诗 is metrical and memorised, 文言文 needs its readings checked
 * character by character, 说明文 is prose a child skims for facts.</p>
 */
public enum ArticleClass {

    GU_SHI("gushi", "古诗", "Classical poetry",
            "Regulated verse, read aloud and learnt by heart.", "🌙"),

    ER_GE("erge", "儿歌", "Nursery rhyme",
            "Short, rhymed, and repetitive - the first things read alone.", "🪕"),

    JI_XU_WEN("jixuwen", "记叙文", "Narrative",
            "Prose that tells what happened, in the order it happened.", "📝"),

    SHUO_MING_WEN("shuomingwen", "说明文", "Exposition",
            "Prose that explains a thing plainly.", "🔬"),

    YU_YAN("yuyan", "寓言", "Fable",
            "A short story that exists for its last sentence.", "🦊"),

    CHENG_YU("chengyu", "成语故事", "Idiom story",
            "The story a four-character idiom is short for.", "🏹"),

    WEN_YAN_WEN("wenyanwen", "文言文", "Classical Chinese",
            "Older grammar, denser characters, readings worth checking.", "🎋");

    private final String wireId;
    private final String chineseName;
    private final String englishName;
    private final String summary;
    private final String icon;

    ArticleClass(String wireId, String chineseName, String englishName,
                 String summary, String icon) {
        this.wireId = wireId;
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.summary = summary;
        this.icon = icon;
    }

    /** The token written in an article header's {@code type:} field. */
    public String wireId() { return wireId; }

    public String chineseName() { return chineseName; }
    public String englishName() { return englishName; }
    public String summary()     { return summary; }
    public String icon()        { return icon; }

    /** Both names, as a catalogue node is labelled. */
    public String displayName() { return chineseName + " · " + englishName; }

    /** The class a header's {@code type:} names, if it names one. */
    public static Optional<ArticleClass> ofWireId(String raw) {
        if (raw == null) return Optional.empty();
        String trimmed = raw.trim();
        for (ArticleClass c : values()) {
            if (c.wireId.equals(trimmed)) return Optional.of(c);
        }
        return Optional.empty();
    }
}
