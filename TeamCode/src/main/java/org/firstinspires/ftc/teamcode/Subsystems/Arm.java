package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Arm extends SubsystemBase {

    private final ServoEx arm;

    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.get(ServoEx.class, Constants.ArmConstants.arm);
    }

    public void setAngle(double angle){
        arm.turnToAngle(angle);
    }

    public double getAngle() {
        return arm.getAngle();
    }

}
