package org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.SpinClaw2425;

public class SetSpinClaw2425 implements Action2425 {
    private SpinClaw2425 spinClaw;
    private boolean pull;
    private ElapsedTime runtime;
    private int runningMillis;
    /**
     * Constructor for SetClaw class
     * @param spinClaw The claw instance
     * @param pull If the claw will spin in or out (pull=in)
     */
    public SetSpinClaw2425(SpinClaw2425 spinClaw, boolean pull) {
        this.spinClaw = spinClaw;
        this.pull = pull;
        runtime = new ElapsedTime();
        runtime.reset();
        runningMillis=1000; //This sets the number of millis the claw will spin
    }
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     */

    //TODO: add a Wait action, or test how long it will take to close/open and incorporate it here
    @Override
    public boolean isComplete() {
        if (runtime.milliseconds()>runningMillis) {
            spinClaw.stop();
            return true;
        }
        return false;
    }
    /**
     * Tells the robot to work towards achieving the action
     */
    @Override
    public void doAction() {
        if (pull) {
            spinClaw.pull();
        } else {
            spinClaw.push();
        }
    }
}
