package interfacing;

import hardware.BluetoothCallback;
import hardware.BluetoothCallback2;
import hardware.additional.Bluetooth;

public class BluetoothController implements BluetoothCallback {

    private BluetoothCallback2 callback;
    private String route = " ";

    public BluetoothController(){
    }

    @Override
    public void onMeasure(int data) {
        System.out.println(data);
        if (data == 87){  //Or 57 depends on decimal or hexadecimal.
            // 87 is capital W.
            route += "W";
            callback.letterW();
        }
        if (data == 65){
            //this is A
            route += "A";

            callback.letterA();
        }
        if (data == 83){
            //this is S
            route += "S";

            callback.letterS();
        }
        if (data == 68 ){
            //this is D
            route += "D";

            callback.letterS();
        }
        if (data == 71){
            // this ia G
            route += "G";

            callback.letterG();
        }
        if (data == 80 ){
            //this is P
            route += "P";

            callback.letterP();
        }
    }
    public String getRoute() {
        return route;
    }
}
