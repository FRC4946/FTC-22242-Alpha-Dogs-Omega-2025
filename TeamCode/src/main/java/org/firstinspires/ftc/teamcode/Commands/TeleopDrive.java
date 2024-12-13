package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

public class TeleopDrive extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrain m_DriveTrain;

    private final GamepadEx gamepad;

    private double driveX, driveY, rotation;

    private final Telemetry telemetry;

    public TeleopDrive(DriveTrain m_DriveTrain, GamepadEx gamepad, Telemetry telemetry) {
        this.m_DriveTrain = m_DriveTrain;

        this.gamepad = gamepad;
        this.telemetry = telemetry;

        addRequirements();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        driveY = gamepad.getLeftY();
        driveX = gamepad.getLeftX();
        rotation = gamepad.getRightX();

        double botHeading = m_DriveTrain.getHeading();

        // Rotate the movement direction counter to the bot's rotation

        double sin = Math.sin(-botHeading);
        double cos = Math.cos(-botHeading);

        double fieldOrientedX = driveX * cos - driveY * sin;
        double fieldOrientedY = driveX * sin + driveY * cos;

        fieldOrientedX *= Constants.DriveTrainConstants.strafingBalancer;  // Counteract imperfect strafing

        double denominator = Math.max(Math.abs(fieldOrientedY) + Math.abs(fieldOrientedX) + Math.abs(rotation), 1);

        double frontLeftPower = (fieldOrientedY + fieldOrientedX + rotation) / denominator;
        double backLeftPower = (fieldOrientedY - fieldOrientedX + rotation) / denominator;
        double frontRightPower = (fieldOrientedY - fieldOrientedX - rotation) / denominator;
        double backRightPower = (fieldOrientedY + fieldOrientedX - rotation) / denominator;

        if (gamepad.wasJustPressed(GamepadKeys.Button.START)) {
            m_DriveTrain.resetYaw();
        }

        if(NewSmartIntake.isSearching() || gamepad.isDown(GamepadKeys.Button.LEFT_STICK_BUTTON)) {
            frontLeftPower *= 0.15;
            frontRightPower *= 0.15;
            backLeftPower *= 0.15;
            backRightPower *= 0.15;
        }

        m_DriveTrain.setPower(
                frontLeftPower,
                frontRightPower,
                backLeftPower,
                backRightPower);

        telemetry.addLine("Drivetrain");
        telemetry.addData("Heading", m_DriveTrain.getHeading());
        telemetry.addData("Left Distance", m_DriveTrain.getLeftDistance());
        telemetry.addData("Right Distance", m_DriveTrain.getRightDistance());
        telemetry.addLine();
    }
}
