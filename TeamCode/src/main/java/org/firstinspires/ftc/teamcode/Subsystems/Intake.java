package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake extends SubsystemBase {

    private final CRServo leftIntake0;
    private final CRServo rightIntake1;
    private double redValue;
    private double greenValue;
    private double blueValue;

    public Intake(HardwareMap hardwareMap) {
        leftIntake0 = hardwareMap.get(CRServo.class, Constants.IntakeConstants.leftIntake0);
        rightIntake1 = hardwareMap.get(CRServo.class, Constants.IntakeConstants.rightIntake1);
        leftIntake0.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntake1.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setIntakePower(double power) {
        leftIntake0.setPower(power);
        rightIntake1.setPower(power);
    }
    public double getRedValue(){
        return colourSensor.red();
    }
    public double getBlueValue(){
        return colourSensor.blue();
    }
    public double getGreenValue(){
        return colourSensor.green();
    }
    public double getLightValue(){
        return colourSensor.alpha();
    }

    public boolean isBlue(float red, float green, float blue) {
        return (blue > red && blue > green && blue > 100);
    }

    public boolean isYellow(float red, float green, float blue) {
        return (red > 100 && green > 100 && blue < 50);
    }

    public boolean isRed(float red, float green, float blue) {
        return (red > blue && red > green && red > 100);
    }
}
