package interfacing;

import TI.BoeBot;
import hardware.LineCallback;
import hardware.PathTrackerCallback;
import hardware.Updatable;
import hardware.sensors.Linesensor;
import hardware.servos.GrabbingCrane;


public class PathTracker implements LineCallback, Updatable {

    private Linesensor leftSensor;
    private Linesensor rightSensor;
    private Drive driving;
    private String route = " ";
    private int counter = 0;
    private GrabbingCrane grabbingCrane;

    public void setRoute(String route) {
        System.out.println(route);
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

    private boolean lineSensorChanged = false;
    private boolean condecutiveLine = false;

    public PathTracker(Drive drive, GrabbingCrane grabbingCrane) {
        leftSensor = new Linesensor(2, this);
        rightSensor = new Linesensor(0, this);
        driving = drive;
        this.grabbingCrane = grabbingCrane;
    }

    @Override
    public void onMeasure() {
        int rightValue = rightSensor.update();
        System.out.println(rightValue);
        int leftValue = leftSensor.update();
        System.out.println(leftValue);

        System.out.println("LeftSensor: " + leftValue);
        System.out.println("RightSensor: " + rightValue);

        if (rightValue > 900 || leftValue > 900) {
            if (rightValue > 900 && leftValue <= 900) {
                driving.right();
                lineSensorChanged = true;
                condecutiveLine = false;
            }

            if (leftValue > 900 && rightValue <= 900) {
                driving.left();
                lineSensorChanged = true;
                condecutiveLine = false;
            }

            if (leftValue > 900 && rightValue > 900) {
//                route = "WWDAGD";
                route = "WWDAGDAWGAW";
                if (!(counter > route.length() - 1)) {
                    if (!condecutiveLine) {
                        if (route.charAt(counter) == 'W') {
                            driving.setSpeed(20);
                            System.out.println("Dit is een W");
                        }

                        if (route.charAt(counter) == 'A') {
                            driving.turnLeft();
                        }

                        if (route.charAt(counter) == 'D') {
                            driving.turnRight();
                        }

                        if (route.charAt(counter) == 'G') {
                            grabbingCrane.open();
                            grabbingCrane.update();
                            counter++;

                            if (route.charAt(counter) == 'A') {
                                driving.turnRight();
                                driving.setSpeed(-20);
                                driving.update();
                                BoeBot.wait(250);
                                grabbingCrane.close();
                                grabbingCrane.update();
                            }

                            if (route.charAt(counter) == 'D') {
                                driving.turnLeft();
                                driving.setSpeed(-20);
                                driving.update();
                                BoeBot.wait(1000);
                                grabbingCrane.close();
                                grabbingCrane.update();
                            }
                        }
                        condecutiveLine = true;
                        counter++;
                    }
                }
                lineSensorChanged = true;
            }
        } else {
            condecutiveLine = false;
            if (lineSensorChanged) {
                driving.setSpeed(20);
            } else {
                driving.slowSpeedforward();
            }
        }
    }

    public boolean isLineSensorChanged() {
        return lineSensorChanged;
    }

    @Override
    public void update() {
        onMeasure();
    }
}