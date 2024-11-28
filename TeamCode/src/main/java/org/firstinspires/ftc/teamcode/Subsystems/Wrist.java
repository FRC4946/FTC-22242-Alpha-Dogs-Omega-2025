package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants.WristConstants;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist extends SubsystemBase {
    private final Servo leftWrist2;
    private final Servo rightWrist3;
    double intakeAngle = WristConstants.intakeAngle;
    double teleopAngle = WristConstants.transferAngle;
    double barAngle = WristConstants.barAngle;

    public Wrist(HardwareMap hardwareMap) {
        leftWrist2 = hardwareMap.get(Servo.class, WristConstants.leftWrist2);
        rightWrist3 = hardwareMap.get(Servo.class, WristConstants.rightWrist3);

        leftWrist2.setDirection(Servo.Direction.FORWARD);
        rightWrist3.setDirection(Servo.Direction.REVERSE);
    }

    public double getLeftAngle() {
        return (leftWrist2.getPosition());
    }

    public double getRightAngle() {
        return (rightWrist3.getPosition());
    }

    public void setAngle(double angle) {
        rightWrist3.setPosition(angle);
        leftWrist2.setPosition(angle);
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Wrist:");
        telemetry.addLine("L Wrist Angle: " + getLeftAngle());
        telemetry.addLine("R Wrist Angle: " + getRightAngle());
    }
}
