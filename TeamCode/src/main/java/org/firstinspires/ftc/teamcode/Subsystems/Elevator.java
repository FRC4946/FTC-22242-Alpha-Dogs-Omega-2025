package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;


public class Elevator extends SubsystemBase {

    private final DcMotor elevator;

    private int power;

    public Elevator(HardwareMap hardwareMap) {
        elevator = hardwareMap.get(DcMotor.class, Constants.ElevatorConstants.elevator);

        elevator.setDirection(DcMotorSimple.Direction.REVERSE);

        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        power = 0;
    }

    public void setPosition(int position) {
        elevator.setTargetPosition(position);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int getPosition() {
        return elevator.getCurrentPosition();
    }

    public void enable() {
        power = (power == 1) ? 0 : 1;
        elevator.setPower(power);
    }

    public void resetEncoder() {
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}
