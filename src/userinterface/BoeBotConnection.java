package userinterface;

import javafx.scene.control.Alert;
import jssc.SerialPort;
import jssc.SerialPortException;

public class BoeBotConnection {
    private SerialPort serialPort;
    private boolean status;

    public BoeBotConnection() {
        serialPort = new SerialPort("COM12");    //try opening serial port via bluetooth device
        try {                                           //settings properties for serialport
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            status = true;
        } catch (SerialPortException e) {
            showError("Verbinding met BoeBot mislukt :("); //showing error when unsuccessfull
            status = false;
        }
    }

    public boolean getStatus() {
        return status;
    }

    public void sendRoute(String route) {
        try {                                                       //try sending route to boebot
            for (int i = 0; i < route.length(); i++) {
                serialPort.writeByte((byte)route.charAt(i));
            }
        } catch (SerialPortException f) {
            showError("Versturen van de route naar BoeBot mislukt :(");     //showing error when unsuccessfull
        }
    }

    public void showError(String message) {     //show error with custom message given as parameter
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Er is een fout opgetreden :(");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public int convertString(char a) {          //method for converting characters to integers, not needed in the end
        if(a == 'W') {
            return 1;
        }
        if(a == 'A') {
            return 2;
        }
        if(a == 'S') {
            return 3;
        }
        if(a == 'D') {
            return 4;
        }
        return 0;
    }
}
