package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake extends SubsystemBase {

    private final CRServo leftIntake0;
    private final CRServo rightIntake1;

    public Intake(HardwareMap hardwareMap) {
        leftIntake0 = hardwareMap.get(CRServo.class, Constants.IntakeConstants.leftIntake0);
        rightIntake1 = hardwareMap.get(CRServo.class, Constants.IntakeConstants.rightIntake1);
        leftIntake0.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntake1.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setIntakePower(double power) {
        leftIntake0.setPower(power * Constants.IntakeConstants.intakeReduction);
        rightIntake1.setPower(power * Constants.IntakeConstants.intakeReduction);
    }
}
