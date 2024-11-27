package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Claw {
    private final Servo claw;

    public Claw(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, Constants.ClawConstants.claw);
    }

    public void setClaw(double angle) {
        claw.setPosition(angle);
    }

    public double getAngle() {
        return claw.getPosition();
    }
}
