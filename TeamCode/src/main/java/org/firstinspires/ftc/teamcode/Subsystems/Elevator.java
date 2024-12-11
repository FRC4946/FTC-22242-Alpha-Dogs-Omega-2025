package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;


public class Elevator extends SubsystemBase {

    private final DcMotor leftElevator0;
    private final DcMotor rightElevator1;

    private int power;

    public Elevator(HardwareMap hardwareMap) {

        leftElevator0 = hardwareMap.get(DcMotor.class, Constants.ElevatorConstants.leftElevator);
        rightElevator1 = hardwareMap.get(DcMotor.class, Constants.ElevatorConstants.rightElevator);

        leftElevator0.setDirection(DcMotorSimple.Direction.REVERSE);
        rightElevator1.setDirection(DcMotorSimple.Direction.FORWARD);

        leftElevator0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightElevator1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftElevator0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightElevator1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        power = 0;
    }

    public void setPosition(int position) {
        leftElevator0.setTargetPosition(position);
        rightElevator1.setTargetPosition(position);

        leftElevator0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightElevator1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int getLeftPosition() {
        return leftElevator0.getCurrentPosition();
    }
    public int getRightPosition() {
        return rightElevator1.getCurrentPosition();
    }

    public void enable() {
        power = (power == 1) ? 0 : 1;
        leftElevator0.setPower(power);
        rightElevator1.setPower(power);
    }

    public void resetEncoder() {
        leftElevator0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightElevator1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftElevator0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightElevator1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}