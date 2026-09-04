package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;
import kranji.reading.app.ui.PinyinSwfModule;

import java.util.List;

/**
 * The known set as a file a family owns.
 *
 * <p>Plain text, not an opaque format: a parent should be able to open the file
 * and see which readings are claimed. It is their record, and — with no account
 * and no sync — the only copy that survives clearing the browser.</p>
 *
 * <pre>
 * # Kranji reading record
 * # 2 characters, 3 readings
 * 床&#9;chuáng
 * 地&#9;de
 * 地&#9;dì
 * </pre>
 *
 * <p>The glyph rather than the codepoint, because the file is for a person. The
 * codepoint is recovered on the way back in, so the round trip is exact.</p>
 *
 * <p><strong>Sorted</strong>, by codepoint then reading. The set keeps insertion
 * order, but a file does not want that: two exports of nearly the same record
 * should differ only where the record differs, so a diff shows what was learnt
 * rather than what order it was clicked in.</p>
 *
 * <p>Reading back is <em>reported on</em>, never silently half-applied. An
 * import that quietly dropped a third of its lines would leave a parent
 * believing a record had been restored — so every line that could not be read
 * comes back with its number and its text.</p>
 *
 * <p>Pure — no DOM, no file system, no clock — so it runs under GraalVM in
 * ordinary JUnit. Moving the bytes is the widget's business; deciding what they
 * mean is this.</p>
 */
public record KnownTransferModule() implements DomModule<KnownTransferModule> {

    /** Yields {@code toText} and {@code fromText}. */
    public record createKnownTransfer() implements Exportable._Constant<KnownTransferModule> {}

    public static final KnownTransferModule INSTANCE = new KnownTransferModule();

    @Override
    public ImportsFor<KnownTransferModule> imports() {
        return ImportsFor.<KnownTransferModule>builder()
                .add(new ModuleImports<>(List.of(
                        new PinyinSwfModule.createPinyinSwf()),
                        PinyinSwfModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<KnownTransferModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownTransfer()));
    }
}
