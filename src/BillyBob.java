import TI.BoeBot;
import hardware.*;
import hardware.additional.Bluetooth;
import hardware.additional.Button;
import hardware.sensors.IR;
import hardware.sensors.Ultrasone;
import hardware.servos.GrabbingCrane;
import interfacing.*;

import java.util.ArrayList;

public class BillyBob implements CollisionCallback, ButtonCallback, OverrideCallback {
    private NotificationsController nc;
    private Ultrasone ultrasone;
    private ArrayList<Updatable> devices;
    private Button stopButton;
    private Button resumeButton;
    private Drive drive;
    private GrabbingCrane grabbingCrane = new GrabbingCrane(14);
    private IR ir;
    private PathTracker pathTracker;
    private Bluetooth bluetooth;
    private boolean override = false;
    private String route = "";
    private boolean isTurning;

    public static void main(String[] args) {
        BillyBob main = new BillyBob();
        main.run();
    }

    private void run() {
        while (true) {
            for (Updatable devices : this.devices) {
                devices.update();
            }

            while (override) {
                ir.update();
                resumeButton.update();

                BoeBot.rgbShow();
                drive.update();
                grabbingCrane.update();
                BoeBot.wait(1);
            }

            BoeBot.rgbShow();
            System.out.println(pathTracker.isLineSensorChanged());
            drive.update();
            grabbingCrane.update();
            BoeBot.wait(50);
        }

    }

    public BillyBob() {
        CollisionController collisionController = new CollisionController(this);
        OverrideController overrideController = new OverrideController(this);
        BluetoothController bluetoothcontroller = new BluetoothController();
        nc = new NotificationsController();
        drive = new Drive();

        ir = new IR(2, overrideController);
        resumeButton = new Button(0, this);

        this.devices = new ArrayList<>();
        this.devices.add(pathTracker = new PathTracker(drive, grabbingCrane));
        this.devices.add(ir = new IR(2, overrideController));
        this.devices.add(grabbingCrane = new GrabbingCrane(14));
        this.devices.add(ultrasone = new Ultrasone(11, 10, collisionController));
        this.devices.add(stopButton = new Button(1, this));
        this.devices.add(bluetooth = new Bluetooth(bluetoothcontroller, pathTracker));
    }

    @Override
    public void onAlmostCollision() {
        drive.slowStop();
    }

    @Override
    public void onNearCollision() {
        drive.emergencyBrake();
    }

    @Override
    public void isSafe() {

    }

    @Override
    public void buttonPressed(Button button) {
        if (stopButton == button) {
            drive.emergencyBrake();
            override = true;
            grabbingCrane.open();
        }
        if (resumeButton == button) {
            drive.slowSpeedforward();
            override = false;
            grabbingCrane.close();
        }
    }

    @Override
    public void driveForward() {
        nc.forwardWhite();

        if(isTurning) {
            drive.setSpeed(20);
        } else {
            drive.slowSpeedforward();
        }

        isTurning = false;

    }

    @Override
    public void driveBackward() {
        nc.backwardsWhite();
        if(isTurning) {
            drive.setSpeed(20);
        } else {
            drive.slowSpeedbackward();
        }
        isTurning =  false;
    }

    @Override
    public void turnLeft() {
        nc.leftWhite();

        if(isTurning)
        drive.left();
        isTurning = true;
    }

    @Override
    public void turnRight() {
        nc.rightWhite();
        drive.right();
        isTurning = true;
    }

    @Override
    public void OverrideOff() {
        override = false;
        drive.slowSpeedforward();
    }

    @Override
    public void OverrideOn() {
        override = true;
        drive.emergencyBrake();
    }

    @Override
    public void brake() {
        nc.allRed();
        drive.emergencyBrake();
    }

    @Override
    public void gripperOpen() {
        grabbingCrane.open();
    }

    @Override
    public void gripperClose() {
        grabbingCrane.close();
    }
}

