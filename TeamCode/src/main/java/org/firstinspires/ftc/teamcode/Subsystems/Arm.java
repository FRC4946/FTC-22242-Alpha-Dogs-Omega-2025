package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Arm extends SubsystemBase {
    private final DcMotor arm2;

    private double power;

    public Arm(HardwareMap hardwareMap) {
        arm2 = hardwareMap.get(DcMotor.class, Constants.ArmConstants.arm2);

        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm2.setDirection(DcMotor.Direction.REVERSE);

        power = 0;
    }


    public void enable() {
        power = (power == 1) ? 0 : 1;
        arm2.setPower(power);
    }

    public void setPosition(int angle) {
        arm2.setTargetPosition(angle);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void resetEncoder() {
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int getAngle() {
        return arm2.getCurrentPosition();
    }
}
