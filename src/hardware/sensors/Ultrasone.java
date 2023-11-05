package hardware.sensors;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import hardware.SensorCallback;
import hardware.Updatable;


public class Ultrasone implements Updatable {

    private int triggerPin;
    private int echoPin;
    private Timer timer;
    private SensorCallback callback;

    public Ultrasone(int triggerPin, int echoPin, SensorCallback callback) {

        this.callback = callback;
        this.triggerPin = triggerPin;
        this.echoPin = echoPin;

        BoeBot.setMode(this.echoPin, PinMode.Input);                        //setting pinmodes.
        BoeBot.setMode(this.triggerPin, PinMode.Output);
        timer = new Timer(50);
    }

    @Override
    public void update() {
        if (timer.timeout()) {
            BoeBot.digitalWrite(triggerPin, true);                  //generating pulse
            BoeBot.uwait(1);
            BoeBot.digitalWrite(triggerPin, false);

            int pulse = BoeBot.pulseIn(this.echoPin, true, 10000);//reading pulse, future update: calculating distance in cm
//            System.out.println(pulse);
            callback.onMeasure(pulse);
        }
    }
}
