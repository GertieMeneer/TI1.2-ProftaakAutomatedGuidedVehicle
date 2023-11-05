package interfacing;

import TI.Timer;
import hardware.SensorCallback;
import hardware.OverrideCallback;


public class OverrideController implements SensorCallback {

    private OverrideCallback callback;

    public OverrideController(OverrideCallback overrideCallback) {
        this.callback = overrideCallback;
    }

    @Override
    public void onMeasure(int value) {
        if (value == 0) {
            callback.driveForward();
        }
        if (value == 1) {
            callback.driveBackward();
        }
        if (value == 2) {
            callback.turnLeft();
        }
        if (value == 3) {
            callback.turnRight();
        }
        if (value == 21) {
            callback.OverrideOn();
        }
        if (value == 73) {
            callback.OverrideOff();
        }
        if (value == 7) {
            callback.gripperOpen();
        }
        if (value == 8) {
            callback.gripperClose();
        }
    }
}
