package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Arm extends SubsystemBase {

    private final Servo leftArm;
    private final Servo rightArm;

    public Arm(HardwareMap hardwareMap) {
        leftArm = hardwareMap.get(Servo.class, Constants.ArmConstants.leftArm);
        rightArm = hardwareMap.get(Servo.class, Constants.ArmConstants.rightArm);

        leftArm.setDirection(Servo.Direction.REVERSE);
        rightArm.setDirection(Servo.Direction.FORWARD);
    }

    public void setAngle(double angle) {
        leftArm.setPosition(angle);
        rightArm.setPosition(angle);
    }

    public double getAngle() {
        return leftArm.getPosition();
    }
}
