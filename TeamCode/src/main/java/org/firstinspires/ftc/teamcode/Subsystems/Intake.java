package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake {
    CRServo leftIntake0;
    CRServo rightIntake1;
    public Intake(HardwareMap hardwareMap) {
        leftIntake0 = hardwareMap.get(CRServo.class, Constants.IntakeConstants.leftIntake0);
        rightIntake1 = hardwareMap.get(CRServo.class, Constants.IntakeConstants.rightIntake1);
        leftIntake0.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntake1.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setIntakePower(double power) {
        leftIntake0.setPower(1);
        rightIntake1.setPower(1);
    }
}