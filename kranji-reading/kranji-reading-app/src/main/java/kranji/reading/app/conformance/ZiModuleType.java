package kranji.reading.app.conformance;

import hue.captains.singapura.js.homing.core.JsModuleType;

/**
 * Domain module types for the reading app, registered through the open
 * {@link JsModuleType} interface so the framework keeps its exhaustive switch
 * over its own standard types.
 */
public enum ZiModuleType implements JsModuleType {

    /**
     * A module dealing in characters and their phonics. Held to the rule that
     * no CJK glyph may be inlined in served JS - characters come from the
     * corpus over the wire.
     */
    ZI_MODEL;

    @Override public String slug()  { return "zi-model"; }
    @Override public String label() { return "Zi model"; }
}
