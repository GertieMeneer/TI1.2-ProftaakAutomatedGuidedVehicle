package hardwaretests;

import TI.BoeBot;
import TI.Servo;
import hardware.servos.GrabbingCrane;

public class GrabbingCraneTest {
    private GrabbingCrane crane = new GrabbingCrane(14);

    public static void main(String[] args) {
        new GrabbingCraneTest();
    }

    private GrabbingCraneTest() {
        crane.open();
        crane.update();
        BoeBot.wait(2000);
        crane.close();
        crane.update();
    }
}
