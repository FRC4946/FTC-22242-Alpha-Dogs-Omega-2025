package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Extension extends SubsystemBase {

    private final Servo leftExtension;
    private final Servo rightExtension;

    public Extension(HardwareMap hardwareMap) {
        leftExtension = hardwareMap.get(Servo.class, Constants.ExtensionConstants.leftExtension);
        rightExtension = hardwareMap.get(Servo.class, Constants.ExtensionConstants.rightExtension);

        leftExtension.setDirection(Servo.Direction.FORWARD);
        rightExtension.setDirection(Servo.Direction.REVERSE);
    }

    public void setAngle(double angle) {
        leftExtension.setPosition(angle);
        rightExtension.setPosition(angle);
    }

    public double getAngle() {
        return leftExtension.getPosition();
    }

}
