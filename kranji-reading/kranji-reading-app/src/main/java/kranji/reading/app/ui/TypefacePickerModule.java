package kranji.reading.app.ui;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The typeface picker, shared by every widget that shows characters.
 *
 * <p>Which typefaces exist, what they are called, and which one this reader
 * chose last are one concern, and it was being answered twice. The CSS class
 * handles cannot live here — a widget's imports put those in its own scope —
 * so the caller passes them in and this owns the rest: the list, the order,
 * the control, and the remembering.</p>
 */
public record TypefacePickerModule() implements DomModule<TypefacePickerModule> {

    /** Builds the control and reports which class is currently chosen. */
    public record createTypefacePicker() implements Exportable._Constant<TypefacePickerModule> {}

    public static final TypefacePickerModule INSTANCE = new TypefacePickerModule();

    @Override
    public ImportsFor<TypefacePickerModule> imports() {
        return ImportsFor.<TypefacePickerModule>builder().build();
    }

    @Override
    public ExportsOf<TypefacePickerModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createTypefacePicker()));
    }
}
