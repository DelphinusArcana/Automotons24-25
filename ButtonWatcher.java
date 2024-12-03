package org.firstinspires.ftc.Automotons;

public class ButtonWatcher {
    private boolean lastValue;

    public ButtonWatcher() {
        lastValue = false;
    }
    // Use this as a button watcher:
    // In the class definition:
    // public class MyClass {
    //    ButtonWatcher upArrow;
    //    etc...
    // };
    // and usage:
    // if (upArrow.WasPressed(gamepad.UP_ARROW)) {
    //     etc...
    // }
    public boolean WasPressed(boolean val) {
        boolean output = false;
        if (!lastValue && val) {
            output = true;
        }
        lastValue = val;
        return output;
    }
}