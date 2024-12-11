package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake extends SubsystemBase {

    private final Servo claw0;
    private final Servo rotate1;
    private final Servo wrist2;

    public Intake(HardwareMap hardwareMap) {
        claw0 = hardwareMap.get(Servo.class, Constants.IntakeConstants.intakeClaw0);
        rotate1 = hardwareMap.get(Servo.class, Constants.IntakeConstants.intakeRotate1);
        wrist2 = hardwareMap.get(Servo.class, Constants.WristConstants.wrist2);
        claw0.setDirection(Servo.Direction.REVERSE);
        rotate1.setDirection(Servo.Direction.FORWARD);
        wrist2.setDirection(Servo.Direction.FORWARD);
    }

    public void openClaw() {
        claw0.setPosition(Constants.IntakeConstants.open);
    }

    public void closeClaw() {
        claw0.setPosition(Constants.IntakeConstants.closed);
    }

    public void setRotation(double angle) {
        rotate1.setPosition(angle);
    }

    public void setWrist(double angle) {
        wrist2.setPosition(angle);
    }

    public double getClaw() {
        return claw0.getPosition();
    }

    public double getRotation() {
        return rotate1.getPosition();
    }

    public double getWrist() {
        return wrist2.getPosition();
    }

}
