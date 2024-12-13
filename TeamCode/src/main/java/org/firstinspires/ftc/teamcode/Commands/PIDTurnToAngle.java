package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

public class PIDTurnToAngle extends CommandBase {
    private final DriveTrain s_Drivetrain;

    private double integralSum;
    private double lastError;

    private double kP;
    private double kI;
    private double kD;

    private ElapsedTime timer;

    double referenceAngle;

    public PIDTurnToAngle(DriveTrain s_Drivetrain, double referenceAngle) {
        this.s_Drivetrain = s_Drivetrain;

        this.referenceAngle = referenceAngle;

        addRequirements(s_Drivetrain);
    }

    @Override
    public void initialize() {

        timer = new ElapsedTime();

        integralSum = 0;
        lastError = 0;

        kP = 0.5;
        kI = 0.0;
        kD = 0.00;

        referenceAngle = Math.toRadians(referenceAngle);
    }

    @Override
    public void execute() {
        double power = PID(referenceAngle, s_Drivetrain.getHeading());
        s_Drivetrain.setPower(
                -power,
                power,
                -power,
                power
        );
    }

    public double PID(double reference, double state) {
        double error = s_Drivetrain.angleWrap(reference  - state);
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / timer.seconds();
        lastError = error;

        timer.reset();

        return (error * kP) + (derivative * kD) + (integralSum * kI);
    }

}
