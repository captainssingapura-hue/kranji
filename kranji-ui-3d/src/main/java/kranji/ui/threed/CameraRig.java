package kranji.ui.threed;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseButton;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * A {@link PerspectiveCamera} mounted in a rotation rig with mouse
 * controls. The rig is a chain of {@link Group}s, each carrying one
 * transform; the camera itself sits at the leaf and looks down -Z. To
 * orbit the world origin we rotate the rig (not the camera) — the
 * camera's local position stays fixed at a {@code zoom} distance.
 *
 * <p>Bindings:
 * <ul>
 *   <li>Primary-button drag → orbit (yaw + pitch).</li>
 *   <li>Mouse wheel → dolly (zoom in/out).</li>
 * </ul>
 */
public final class CameraRig {

    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    // Initial view = head-on (matches the 2D SVG layout). User can
    // drag to rotate and see depth.
    private final Rotate yaw   = new Rotate(0, Rotate.Y_AXIS);
    private final Rotate pitch = new Rotate(0, Rotate.X_AXIS);
    // Far dolly + small FOV ≈ near-orthographic head-on, so the
    // layout matches the SVG pixel-for-pixel before any rotation,
    // while still allowing perspective when the user orbits.
    private final Translate dolly = new Translate(0, 0, -1500);

    /** Drag tracking. */
    private double lastX, lastY;
    /** When true, drag-to-rotate is suppressed (still allows scroll-zoom). */
    private boolean rotationLocked = false;

    public CameraRig() {
        camera.setNearClip(1);
        camera.setFarClip(5000);
        camera.setFieldOfView(20);
        camera.getTransforms().addAll(yaw, pitch, dolly);
    }

    public PerspectiveCamera camera() { return camera; }

    /** Attach mouse-drag + scroll handlers to the given sub-scene. */
    public void install(SubScene sub) {
        sub.setOnMousePressed(ev -> {
            lastX = ev.getSceneX();
            lastY = ev.getSceneY();
        });
        sub.setOnMouseDragged(ev -> {
            if (ev.getButton() != MouseButton.PRIMARY) return;
            double dx = ev.getSceneX() - lastX;
            double dy = ev.getSceneY() - lastY;
            lastX = ev.getSceneX();
            lastY = ev.getSceneY();
            if (rotationLocked) return;            // interactive mode owns drags
            // Drag horizontally → yaw (around world Y); vertically → pitch (around world X).
            yaw.setAngle(yaw.getAngle() - dx * 0.4);
            pitch.setAngle(clamp(pitch.getAngle() - dy * 0.4, -89, 89));
        });
        sub.setOnScroll(ev -> {
            // Wheel up (positive deltaY) → zoom in (less negative Z).
            double z = dolly.getZ() + ev.getDeltaY() * 1.2;
            dolly.setZ(clamp(z, -3500, -120));
        });
    }

    /**
     * Reset the rig to head-on. Drag-to-orbit stays enabled — a drag on
     * a node is consumed by the node's own handler, so the camera only
     * rotates when the user drags on empty space.
     */
    public void resetHeadOn() {
        yaw.setAngle(0);
        pitch.setAngle(0);
        rotationLocked = false;
    }

    /** Re-enable orbit-on-drag (compat with previous lock-based callers). */
    public void unlock() { rotationLocked = false; }

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}
