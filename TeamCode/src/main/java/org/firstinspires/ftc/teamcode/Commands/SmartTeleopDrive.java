package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

public class SmartTeleopDrive extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrain m_DriveTrain;

    private final GamepadEx gamepad;

    private double driveX, driveY, rotation;

    private final Telemetry telemetry;

    private final boolean slow;

    private final PIDFController pid;

    private double targetHeading;

    public SmartTeleopDrive(DriveTrain m_DriveTrain, GamepadEx gamepad, Telemetry telemetry, boolean slow) {
        this.m_DriveTrain = m_DriveTrain;

        this.gamepad = gamepad;
        this.telemetry = telemetry;

        this.slow = slow;

        pid = new PIDFController(
                Constants.DriveTrainConstants.kP,
                Constants.DriveTrainConstants.kI,
                Constants.DriveTrainConstants.kD,
                Constants.DriveTrainConstants.kF
        );
        pid.setTolerance(Constants.DriveTrainConstants.PIDTolerance);

        addRequirements(m_DriveTrain);
    }

    @Override
    public void initialize() {
        pid.setSetPoint(0);
        targetHeading = 0;
    }

    @Override
    public void execute() {

        double currentAngle = m_DriveTrain.getHeading();

        driveY = gamepad.getLeftY();
        driveX = gamepad.getLeftX();
        rotation = gamepad.getRightX();

        if (Math.abs(rotation) > 0.05) { // Driver is rotating the robot
            targetHeading = currentAngle; // Update target heading
        }

        double correction = pid.calculate(Math.toDegrees(currentAngle), targetHeading);

        double sin = Math.sin(-currentAngle);
        double cos = Math.cos(-currentAngle);

        double fieldOrientedX = driveX * cos - driveY * sin;
        double fieldOrientedY = driveX * sin + driveY * cos;

        double frontLeftPower = fieldOrientedY + fieldOrientedX + rotation + correction;
        double backLeftPower = fieldOrientedY - fieldOrientedX + rotation + correction;
        double frontRightPower = fieldOrientedY - fieldOrientedX - rotation - correction;
        double backRightPower = fieldOrientedY + fieldOrientedX - rotation - correction;

        double maxPower = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));

        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }

        if(slow) {
            frontLeftPower *= 0.2;
            backLeftPower *= 0.2;
            frontRightPower *= 0.2;
            backRightPower *= 0.2;
        }

        m_DriveTrain.setPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

        if (gamepad.wasJustPressed(GamepadKeys.Button.START)) {
            m_DriveTrain.resetYaw();
        }

        telemetry.addLine("Drivetrain");
        telemetry.addData("Heading", m_DriveTrain.getHeading());
        telemetry.addLine();
        //telemetry.update();
    }
}
