package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Claw extends SubsystemBase {
    private final Servo leftClaw;
    private final Servo rightClaw;

    public Claw(HardwareMap hardwareMap) {
        leftClaw = hardwareMap.get(Servo.class, Constants.ClawConstants.leftClaw);
        rightClaw = hardwareMap.get(Servo.class, Constants.ClawConstants.rightClaw);

        leftClaw.setDirection(Servo.Direction.REVERSE);
        rightClaw.setDirection(Servo.Direction.FORWARD);
    }

    public void setClaw(double angle) {
        leftClaw.setPosition(angle);
        rightClaw.setPosition(angle);
    }

    public double getLeftAngle() {
        return leftClaw.getPosition();
    }

    public double getRightAngle() {
        return rightClaw.getPosition();
    }
}
