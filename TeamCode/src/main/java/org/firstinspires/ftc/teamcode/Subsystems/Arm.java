package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Arm {

    private final Servo arm;

    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.get(Servo.class, Constants.ArmConstants.arm);
    }

    public void setAngle(double angle){
        arm.setPosition(angle);
    }

    public double getAngle() {
        return arm.getPosition();
    }

}
