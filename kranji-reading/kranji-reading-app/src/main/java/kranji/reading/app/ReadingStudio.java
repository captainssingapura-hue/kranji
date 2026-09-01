package kranji.reading.app;

import hue.captains.singapura.js.homing.studio.base.Studio;
import hue.captains.singapura.js.homing.studio.base.app.StudioBrand;

/**
 * Kranji Reading — an adaptive Chinese reading app for children.
 *
 * <p>Pinyin appears only where the reader has not yet secured the character,
 * and an unfamiliar character can explain its own composition. The learner
 * profile lives in the browser; the server ships the application and holds
 * nothing.</p>
 */
public record ReadingStudio() implements Studio<ReadingLandingCatalogue> {

    public static final ReadingStudio INSTANCE = new ReadingStudio();

    @Override
    public ReadingLandingCatalogue home() { return ReadingLandingCatalogue.INSTANCE; }

    @Override
    public StudioBrand standaloneBrand() {
        return new StudioBrand("Kranji · Reading", ReadingLandingCatalogue.class);
    }
}
