package kranji.reading.workbench;

import hue.captains.singapura.js.homing.studio.base.Studio;
import hue.captains.singapura.js.homing.studio.base.app.StudioBrand;

/** The bench as a studio: one catalogue, and a name that says whose it is. */
public record LibraryWorkbenchStudio() implements Studio<LibraryWorkbenchCatalogue> {

    public static final LibraryWorkbenchStudio INSTANCE = new LibraryWorkbenchStudio();

    @Override
    public LibraryWorkbenchCatalogue home() { return LibraryWorkbenchCatalogue.INSTANCE; }

    @Override
    public StudioBrand standaloneBrand() {
        return new StudioBrand("Kranji · Library Workbench", LibraryWorkbenchCatalogue.class);
    }
}
