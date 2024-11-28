package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

public class TeleopDrive extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrain m_DriveTrain;

    private final GamepadEx gamepad;

    private double driveX, driveY, rotation;

    public TeleopDrive(DriveTrain m_DriveTrain, GamepadEx gamepad) {
        this.m_DriveTrain = m_DriveTrain;

        this.gamepad = gamepad;

        addRequirements(m_DriveTrain);
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

        m_DriveTrain.setPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }
}
