package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants.WristConstants;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist extends SubsystemBase {
    private final ServoEx leftWrist2;
    private final ServoEx rightWrist3;
    double intakeAngle = WristConstants.intakeAngle;
    double teleopAngle = WristConstants.transferAngle;
    double barAngle = WristConstants.barAngle;

    public Wrist(HardwareMap hardwareMap) {
        leftWrist2 = hardwareMap.get(ServoEx.class, WristConstants.leftWrist2);
        rightWrist3 = hardwareMap.get(ServoEx.class, WristConstants.rightWrist3);

        leftWrist2.setInverted(false);
        rightWrist3.setInverted(true);
    }

    public double getLeftAngle() {
        return (leftWrist2.getAngle());
    }

    public double getRightAngle() {
        return (rightWrist3.getAngle());
    }

    public void setAngle(double angle) {
        rightWrist3.turnToAngle(angle);
        leftWrist2.turnToAngle(angle);
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Wrist:");
        telemetry.addLine("L Wrist Angle: " + getLeftAngle());
        telemetry.addLine("R Wrist Angle: " + getRightAngle());
    }
}
