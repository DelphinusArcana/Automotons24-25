package org.firstinspires.ftc.teamcode.Automotons2425.AutonomousModes2425;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.Action2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.GoToPosition2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;

import java.util.ArrayList;

@Autonomous(name="MoveForwardAuto")
public class MoveForwardAuto extends AbstractAutonomous {
    @Override
    public ArrayList<Action2425> makeToDoList () {
        ArrayList<Action2425> toDoList = new ArrayList<>();
        toDoList.add(new GoToPosition2425(driveTrain, new Position(24, 0, 0), 3, positionFinder));
        return toDoList;
    }

    @Override
    public Position getStartPosition() {
        return new Position(0,0,0);
    }
    @Override
    public void extraRunningStuff() {
        telemetry.addData("Position",positionFinder.getPosition());
    }
}
