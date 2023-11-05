package hardware.additional;

import TI.SerialConnection;
import TI.Timer;
import hardware.BluetoothCallback;
import hardware.Updatable;
import interfacing.BluetoothController;
import interfacing.NotificationsController;
import interfacing.PathTracker;

import java.nio.file.Path;

public class Bluetooth implements Updatable {
    private SerialConnection serial;
    private BluetoothCallback callback;
    private Timer t1 = new Timer(500);
    private PathTracker pathTracker;
    private BluetoothController bluetoothController = new BluetoothController();

    public Bluetooth (BluetoothCallback callback, PathTracker pathTracker){
      this.serial = new SerialConnection(115200);
      this.callback = callback;
      this.pathTracker = pathTracker;
      t1.mark();
    }

    public void update() {
        if (serial.available() > 0) {
            System.out.println("Serial available!");
            if(!t1.timeout()) {
                int route = serial.readByte();
                System.out.println(route);
//                callback.onMeasure(route);
            } else {
//                pathTracker.setRoute(bluetoothController.getRoute());
                t1.mark();
            }
        }
    }
}
