package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

public class TeleopDrive extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrain m_DriveTrain;

    private double driveY, driveX, rotation;

    public TeleopDrive(DriveTrain m_DriveTrain, double driveY, double driveX, double rotation) {
        this.m_DriveTrain = m_DriveTrain;

        this.driveX = driveX;
        this.driveY = driveY;
        this.rotation = rotation;

        addRequirements(m_DriveTrain);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double botHeading = m_DriveTrain.getHeading();
        double headingRadians = Math.toRadians(botHeading);


        // Rotate the movement direction counter to the bot's rotation

        double sin = Math.sin(-headingRadians);
        double cos = Math.cos(-headingRadians);

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

    @Override
    public boolean isFinished() {
        return false;
    }
}
