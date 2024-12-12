package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

public class TurnToAngle extends CommandBase {
    private final DriveTrain s_DriveTrain;
    private double heading;

    private final PIDFController pid;

    private final Telemetry telemetry;

    private double currentAngle, correction;

    public TurnToAngle(DriveTrain s_DriveTrain, double heading, Telemetry telemetry) {
        this.s_DriveTrain = s_DriveTrain;

        this.heading = heading;

        this.telemetry = telemetry;

        pid = new PIDFController(
                Constants.DriveTrainConstants.kP,
                Constants.DriveTrainConstants.kI,
                Constants.DriveTrainConstants.kD,
                Constants.DriveTrainConstants.kF
        );

        pid.setIntegrationBounds(-1, 1);

        pid.setTolerance(Constants.DriveTrainConstants.PIDTolerance);

        pid.setSetPoint(heading);
        addRequirements(s_DriveTrain);
    }

    @Override
    public void initialize() {
        pid.setSetPoint(heading);
        heading = Math.toRadians(heading);
    }

    @Override
    public void execute() {
        currentAngle = s_DriveTrain.getHeading();
        correction = pid.calculate(s_DriveTrain.angleWrap(currentAngle), heading);

        s_DriveTrain.setPower(
                -correction,
                correction,
                -correction,
                correction
        );

        telemetry.addData("At Sepoint", atSetpoint());
    }

    @Override
    public void end(boolean interrupted) {
        s_DriveTrain.stop();
    }

    public boolean atSetpoint() {
        return pid.atSetPoint();
    }
}
