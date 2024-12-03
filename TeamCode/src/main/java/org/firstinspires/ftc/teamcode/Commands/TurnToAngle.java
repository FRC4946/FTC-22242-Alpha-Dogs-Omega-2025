package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

public class TurnToAngle extends CommandBase {
    private DriveTrain s_DriveTrain;
    private double heading;

    PIDFController pid = new PIDFController(0.1, 0, 0, 0);

    public TurnToAngle(DriveTrain s_DriveTrain, double heading) {
        this.s_DriveTrain = s_DriveTrain;

        this.heading = heading;

        addRequirements(s_DriveTrain);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        //s_DriveTrain.setPower();
    }
}
