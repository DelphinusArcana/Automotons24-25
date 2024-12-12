package org.firstinspires.ftc.teamcode.Automotons2425;

public class ButtonWatcher2425 {
    private boolean lastValue;

    public ButtonWatcher2425() {
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
    public boolean Pressed(boolean val) {
        boolean output = false;
        if (!lastValue && val) {
            output = true;
        }
        lastValue = val;
        return output;
    }
}