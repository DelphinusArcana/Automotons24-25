package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.AutonomousModes2425;

import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.Action2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.GoToPosition2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetClaw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetSpinClaw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetLift2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Position;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.ArrayList;

@Autonomous(name="DropOneInAutonomous2425")
public class DropOneInAutonomous2425 extends AbstractAutonomous{
    private double oritentationTolerance;
    private double moveTolerance;
    private int armTolerance;
    private double armLowest;
    private double raisedArmPos;
    private double liftMax;
    private double liftTolerance;
    private double liftMin;
    private double armInBasketPos;

    @Override
    public ArrayList<Action2425> makeToDoList() {
        moveTolerance = 2;
        oritentationTolerance = Math.PI/24;
        armLowest = 139;
        armTolerance = 5;
        double semiArmPos = 100;
        raisedArmPos = 62;
        liftMax = -3600;
        liftTolerance = 10;
        liftMin = 0;
        armInBasketPos = 100;
        ArrayList<Action2425> toDoList = new ArrayList<>();
        //start with a sample
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-3*24+9, 2*24+2, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetLift2425(liftKit, liftMax, liftTolerance));
        toDoList.add(new SetArm2425(clawArm,armLowest,armTolerance)); // Lowers the claw arm
        toDoList.add(new SetSpinClaw2425(spinClaw, false)); // Pushes the sample out
        //reset for teleop
        toDoList.add(new SetArm2425(clawArm, raisedArmPos,armTolerance)); // Raises the claw arm
        toDoList.add(new SetLift2425(liftKit, liftMin, liftTolerance));
        toDoList.add(new SetArm2425(clawArm, 0,armTolerance)); // Lowers the claw arm
        //move to 3point area
        toDoList.add(new GoToPosition2425(driveTrain, new Position(24, 0, 0), 3, positionFinder));
        return toDoList;
    }

    @Override
    public Position getStartPosition() {
        return new Position(-3*24+9, 2*24-10, Math.PI/2);
    }
    @Override
    public void extraRunningStuff () {

    }
}