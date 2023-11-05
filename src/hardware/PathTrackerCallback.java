package hardware;

public interface PathTrackerCallback {
    void forward();
    void back();
    void left();
    void right();
    void putDown();
    void pickUp();
}
